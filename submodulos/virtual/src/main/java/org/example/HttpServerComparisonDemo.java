package org.example;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ejemplo comparativo de rendimiento en un Servidor Web (HttpServer del JDK).
 * 
 * Este ejemplo simula un escenario típico de servidor web:
 * - 150 peticiones HTTP concurrentes.
 * - Cada petición realiza una llamada I/O bloqueante (simulada por un Thread.sleep de 100ms).
 * 
 * Se compara:
 * 1. Servidor tradicional con un Pool Fijo de Hilos (FixedThreadPool de tamaño 10).
 *    Esto simula un servidor saturado donde no hay suficientes hilos de plataforma disponibles.
 * 2. Servidor moderno con un Executor de Hilos Virtuales (un hilo virtual por petición).
 */
public class HttpServerComparisonDemo {

    private static final int REQUESTS_COUNT = 150;
    private static final int SIMULATED_LATENCY_MS = 100;
    private static final int PLATFORM_POOL_SIZE = 10;

    public static void main(String[] args) throws Exception {
        System.out.println("======================================================================");
        System.out.println("COMPARATIVA: SERVIDOR WEB CON HILOS DE PLATAFORMA VS VIRTUALES");
        System.out.println("======================================================================\n");
        System.out.println("Escenario:");
        System.out.println("  - Peticiones HTTP concurrentes a realizar: " + REQUESTS_COUNT);
        System.out.println("  - Latencia simulada por petición (E/S BD): " + SIMULATED_LATENCY_MS + "ms");
        System.out.println("  - Hilos en Pool de Plataforma del Servidor: " + PLATFORM_POOL_SIZE);
        System.out.println("  - Límite mínimo con Plataforma (150 / 10 * 100ms): ~1500ms");
        System.out.println("  - Límite mínimo con Virtuales (todas en paralelo): ~100ms\n");

        // 1. Ejecutar prueba con Hilos de Plataforma (Pool limitado)
        long tiempoPlataforma = probarServidor(Executors.newFixedThreadPool(PLATFORM_POOL_SIZE), "Plataforma");

        // Pausa de estabilización
        Thread.sleep(1000);

        // 2. Ejecutar prueba con Hilos Virtuales (Un hilo virtual por petición)
        long tiempoVirtual = probarServidor(Executors.newVirtualThreadPerTaskExecutor(), "Hilos Virtuales");

        System.out.println("\n=== RESUMEN DE TIEMPOS HTTP ===");
        System.out.printf("| %-45s | %-15s |%n", "Configuración del Servidor", "Tiempo Total");
        System.out.println("|-----------------------------------------------+-----------------|");
        System.out.printf("| 1. Pool Fijo de Plataforma (Tamaño: 10)       | %6d ms       |%n", tiempoPlataforma);
        System.out.printf("| 2. Executor de Hilos Virtuales                | %6d ms       |%n", tiempoVirtual);
        System.out.println("|-----------------------------------------------+-----------------|");
    }

    private static long probarServidor(ExecutorService serverExecutor, String tipoServidor) throws Exception {
        System.out.println("--- Probando Servidor usando: " + tipoServidor + " ---");

        // Crear HttpServer en un puerto aleatorio libre de forma dinámica (puerto 0)
        HttpServer server = HttpServer.create(new InetSocketAddress(0), 0);
        
        // Configurar el controlador REST
        server.createContext("/api", exchange -> {
            try {
                // Simular latencia de E/S bloqueante
                Thread.sleep(SIMULATED_LATENCY_MS);
                
                String response = "Resultado exitoso del servidor (" + tipoServidor + ")";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (var os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Configurar el ejecutor del servidor
        server.setExecutor(serverExecutor);
        server.start();

        int port = server.getAddress().getPort();
        System.out.println("  [Servidor] Iniciado en el puerto: " + port);

        // Lanzar clientes concurrentes en paralelo
        long totalTime;
        Instant start = Instant.now();
        System.out.println("  [Cliente] Enviando " + REQUESTS_COUNT + " peticiones concurrentes...");

        // Usamos hilos virtuales para el cliente para asegurar que las peticiones se envían todas
        // en paralelo y no sea el cliente el que cause el cuello de botella.
        // Además, creamos un HttpClient local por tarea para evitar las limitaciones de conexiones
        // concurrentes por host del pool interno por defecto del HttpClient de Java.
        try (var clientExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<Callable<String>> tasks = new ArrayList<>();
            for (int i = 0; i < REQUESTS_COUNT; i++) {
                tasks.add(() -> {
                    try (var httpClient = HttpClient.newHttpClient()) {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:" + port + "/api"))
                                .timeout(Duration.ofSeconds(10))
                                .GET()
                                .build();
                        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                        return response.body();
                    }
                });
            }
            
            var futures = clientExecutor.invokeAll(tasks);
            for (var f : futures) {
                f.get(); // Asegurar éxito
            }
        }

        totalTime = Duration.between(start, Instant.now()).toMillis();
        System.out.println("  [Cliente] Todas las respuestas recibidas en: " + totalTime + " ms");
        
        // Detener el servidor inmediatamente
        server.stop(0);
        serverExecutor.shutdown();

        System.out.println();
        return totalTime;
    }
}

package org.example;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Ejemplo comparativo de Hilos de Plataforma (Platform Threads) frente a Hilos Virtuales (Virtual Threads)
 * bajo alta concurrencia (20,000 clientes concurrentes).
 * 
 * Este ejemplo demuestra el límite físico del Sistema Operativo:
 * - Intentar crear 20,000 hilos tradicionales de plataforma provocará un fallo del sistema (OutOfMemoryError)
 *   o alcanzará el límite de procesos del SO (ulimit), el cual es capturado de forma segura.
 * - Los hilos virtuales ejecutarán las 20,000 tareas sin despeinarse utilizando un puñado de hilos nativos.
 */
public class ConnectionPoolDemo {

    private static final int CLIENTS_COUNT = 20_000; // Alta concurrencia para saturar hilos del SO
    private static final int POOL_SIZE = 100;        // Pool de conexiones simuladas
    private static final int QUERY_TIME_MS = 1;      // Tiempo de consulta corto para ejecución rápida

    private static final Semaphore connectionPool = new Semaphore(POOL_SIZE);
    private static final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("======================================================================");
        System.out.println("COMPARATIVA DE ESCALABILIDAD EXTREMA: HILOS DE PLATAFORMA VS VIRTUALES");
        System.out.println("======================================================================\n");
        System.out.println("Escenario de Alta Carga:");
        System.out.println("  - Clientes concurrentes: " + CLIENTS_COUNT);
        System.out.println("  - Tamaño del Pool de Conexiones: " + POOL_SIZE);
        System.out.println("  - Límite teórico mínimo de tiempo: " + ((CLIENTS_COUNT / POOL_SIZE) * QUERY_TIME_MS) + "ms\n");

        int hilosIniciales = threadBean.getThreadCount();

        // 1. Intentar ejecutar con Hilos de Plataforma
        long tiempoPlataforma = -1;
        try {
            tiempoPlataforma = ejecutarConHilosPlataforma(hilosIniciales);
        } catch (Throwable t) {
            System.out.println("  [Plataforma] ¡CRASH! Falló la ejecución de hilos de plataforma.");
            System.out.println("  [Plataforma] Razón del fallo: " + t.getClass().getName() + " - " + t.getMessage());
            System.out.println("  [Plataforma] Explicación: El sistema operativo no pudo crear tantos hilos nativos.");
        }

        // Estabilizar
        System.gc();
        Thread.sleep(1000);

        // 2. Ejecutar con Hilos Virtuales
        long tiempoVirtuales = -1;
        try {
            tiempoVirtuales = ejecutarConHilosVirtuales(hilosIniciales);
        } catch (Throwable t) {
            System.out.println("  [Virtual] ¡Fallo inesperado!: " + t.getMessage());
        }

        System.out.println("\n=== RESUMEN DE LA COMPARATIVA ===");
        System.out.printf("| %-45s | %-15s |%n", "Tipo de Hilos", "Tiempo Total");
        System.out.println("|-----------------------------------------------+-----------------|");
        if (tiempoPlataforma != -1) {
            System.out.printf("| 1. Hilos de Plataforma (Platform Builder)      | %6d ms       |%n", tiempoPlataforma);
        } else {
            System.out.printf("| 1. Hilos de Plataforma (Platform Builder)      |  FALLÓ (OOM)    |%n");
        }
        System.out.printf("| 2. Hilos Virtuales                             | %6d ms       |%n", tiempoVirtuales);
        System.out.println("|-----------------------------------------------+-----------------|");
    }

    private static long ejecutarConHilosPlataforma(int hilosIniciales) throws InterruptedException {
        System.out.println("--- 1. Ejecutando con Hilos de Plataforma (PlatformBuilder) ---");
        List<Thread> threads = new ArrayList<>(CLIENTS_COUNT);
        final int[] maxHilosFisicos = {0};
        Instant start = Instant.now();

        // NOTA: La creación de objetos Thread nativos puede fallar aquí por falta de memoria nativa.
        for (int i = 0; i < CLIENTS_COUNT; i++) {
            Thread t = Thread.ofPlatform()
                    .name("cliente-plataforma-", i)
                    .unstarted(() -> {
                        try {
                            connectionPool.acquire();
                            try {
                                Thread.sleep(Duration.ofMillis(QUERY_TIME_MS));
                            } finally {
                                connectionPool.release();
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
            threads.add(t);
        }

        // Arrancar hilos (esto suele disparar el OutOfMemoryError si no falló antes)
        System.out.println("  [Plataforma] Arrancando " + CLIENTS_COUNT + " hilos físicos...");
        for (Thread t : threads) {
            t.start();
        }

        // Monitorear picos
        for (int i = 0; i < 5; i++) {
            int hilosActuales = threadBean.getThreadCount();
            if (hilosActuales > maxHilosFisicos[0]) {
                maxHilosFisicos[0] = hilosActuales;
            }
            Thread.sleep(50);
        }

        // Esperar a que terminen
        for (Thread t : threads) {
            t.join();
        }

        long elapsed = Duration.between(start, Instant.now()).toMillis();
        System.out.println("  [Plataforma] Pico de hilos físicos activos en la JVM: " + maxHilosFisicos[0] 
                + " (Hilos iniciales: " + hilosIniciales + ", Incremento: +" + (maxHilosFisicos[0] - hilosIniciales) + ")");
        System.out.println("  [Plataforma] Completado en: " + elapsed + " ms");
        System.out.println();
        return elapsed;
    }

    private static long ejecutarConHilosVirtuales(int hilosIniciales) throws InterruptedException {
        System.out.println("--- 2. Ejecutando con Hilos Virtuales ---");
        List<Thread> threads = new ArrayList<>(CLIENTS_COUNT);
        final int[] maxHilosFisicos = {0};
        Instant start = Instant.now();

        for (int i = 0; i < CLIENTS_COUNT; i++) {
            Thread t = Thread.ofVirtual()
                    .name("cliente-virtual-", i)
                    .unstarted(() -> {
                        try {
                            connectionPool.acquire();
                            try {
                                Thread.sleep(Duration.ofMillis(QUERY_TIME_MS));
                            } finally {
                                connectionPool.release();
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
            threads.add(t);
        }

        System.out.println("  [Virtual] Arrancando " + CLIENTS_COUNT + " hilos virtuales...");
        for (Thread t : threads) {
            t.start();
        }

        // Monitorear picos
        for (int i = 0; i < 5; i++) {
            int hilosActuales = threadBean.getThreadCount();
            if (hilosActuales > maxHilosFisicos[0]) {
                maxHilosFisicos[0] = hilosActuales;
            }
            Thread.sleep(50);
        }

        for (Thread t : threads) {
            t.join();
        }

        long elapsed = Duration.between(start, Instant.now()).toMillis();
        System.out.println("  [Virtual] Pico de hilos físicos activos en la JVM: " + maxHilosFisicos[0] 
                + " (Hilos iniciales: " + hilosIniciales + ", Incremento: +" + (maxHilosFisicos[0] - hilosIniciales) + ")");
        System.out.println("  [Virtual] Completado en: " + elapsed + " ms");
        System.out.println();
        return elapsed;
    }
}

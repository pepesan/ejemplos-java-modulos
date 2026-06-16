package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Comparativa de rendimiento y explicación detallada de Hilos Virtuales (Project Loom) en Java 21+.
 * 
 * Este ejemplo realiza una comparativa de rendimiento al ejecutar 2.000 tareas bloqueantes (simulando E/S
 * mediante Thread.sleep) a través de 4 enfoques de concurrencia diferentes:
 * 
 * 1. Hilos de Plataforma Tradicionales creados de forma clásica (new Thread):
 *    Mapeo 1:1 directo con hilos del sistema operativo (SO). Cada hilo consume aproximadamente 1MB de pila.
 * 
 * 2. Hilos de Plataforma Tradicionales creados mediante Builder (Thread.ofPlatform()):
 *    API introducida en Java 21 para dar simetría a la creación de hilos tradicionales y virtuales.
 * 
 * 3. Hilos Virtuales Directos creados mediante la API estática (Thread.startVirtualThread()):
 *    Hilos ultraligeros gestionados por la JVM que se montan sobre hilos portadores (carrier threads) del SO.
 * 
 * 4. Hilos Virtuales gestionados por un ExecutorService (Executors.newVirtualThreadPerTaskExecutor()):
 *    Patrón de ejecución recomendado para aplicaciones empresariales, facilitando la programación estructurada.
 */
public class App {

    // Número de tareas concurrentes a ejecutar en la demostración
    private static final int TASKS_COUNT = 2_000;
    
    // Tiempo de bloqueo artificial en milisegundos (simula una llamada de red o base de datos)
    private static final int SLEEP_MS = 100;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== COMPARATIVA DE RENDIMIENTO DE HILOS (Java 21+) ===");
        System.out.println("Ejecutando " + TASKS_COUNT + " tareas concurrentes con bloqueo de " + SLEEP_MS + "ms cada una.");

        // Método 1: Hilos tradicionales instanciados de forma directa
        long time1 = runTraditionalRaw();

        // Método 2: Hilos tradicionales instanciados mediante la interfaz Builder (Thread.ofPlatform)
        long time2 = runPlatformBuilder();

        // Método 3: Hilos virtuales de arranque directo
        long time3 = runVirtualRaw();

        // Método 4: Hilos virtuales administrados bajo un ExecutorService y Concurrencia Estructurada
        long time4 = runVirtualExecutor();

        // Mostrar resultados en formato de tabla para una comparación directa y visual
        System.out.println("\n=== RESUMEN DE TIEMPOS ===");
        System.out.printf("| %-45s | %-15s |%n", "Método", "Tiempo Total");
        System.out.println("|-----------------------------------------------+-----------------|");
        System.out.printf("| 1. Tradicional sin envoltura (new Thread)      | %6d ms       |%n", time1);
        System.out.printf("| 2. Hilo de Plataforma con Builder              | %6d ms       |%n", time2);
        System.out.printf("| 3. Hilo Virtual directo (startVirtualThread)   | %6d ms       |%n", time3);
        System.out.printf("| 4. Hilo Virtual con ExecutorService            | %6d ms       |%n", time4);
        
        System.out.println("\nExplicación del rendimiento:");
        System.out.println("- Los hilos de plataforma (1 y 2) sufren de sobrecarga de creación física en el Sistema Operativo y latencia por cambio de contexto (context-switch).");
        System.out.println("- Los hilos virtuales (3 y 4) no bloquean el hilo físico del SO al dormir (sleep). La JVM desmonta automáticamente el hilo virtual del hilo portador (carrier thread) para realizar otras tareas concurrentes.");
    }

    /**
     * Método 1: Hilos nativos tradicionales creados a la antigua.
     * Cada llamada a 'new Thread()' realiza llamadas al sistema para asignar recursos del núcleo del SO.
     * Con un número elevado de tareas, esto puede causar problemas de memoria y saturación de recursos.
     */
    private static long runTraditionalRaw() throws InterruptedException {
        System.out.println("\n--- 1. Ejecutando con hilos tradicionales directos (new Thread) ---");
        List<Thread> threads = new ArrayList<>(TASKS_COUNT);
        Instant start = Instant.now();

        for (int i = 0; i < TASKS_COUNT; i++) {
            Thread t = new Thread(() -> {
                try {
                    // El bloqueo con Thread.sleep suspende el hilo físico del sistema operativo por completo
                    Thread.sleep(Duration.ofMillis(SLEEP_MS));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads.add(t);
            t.start();
        }

        // Espera de sincronización. join() bloquea el hilo principal esperando que cada uno termine
        for (Thread t : threads) {
            t.join();
        }

        long elapsed = Duration.between(start, Instant.now()).toMillis();
        System.out.println("  Completado en: " + elapsed + " ms");
        return elapsed;
    }

    /**
     * Método 2: Hilos nativos tradicionales creados con el nuevo Builder de plataforma.
     * Es funcionalmente idéntico al Método 1, pero utiliza la nueva interfaz unificada.
     */
    private static long runPlatformBuilder() throws InterruptedException {
        System.out.println("\n--- 2. Ejecutando con hilos de plataforma usando Builder (Thread.ofPlatform) ---");
        List<Thread> threads = new ArrayList<>(TASKS_COUNT);
        Instant start = Instant.now();

        for (int i = 0; i < TASKS_COUNT; i++) {
            // Thread.ofPlatform() permite configurar nombres, estado daemon y prioridades antes de arrancar
            Thread t = Thread.ofPlatform()
                    .name("hilo-plataforma-builder-", i)
                    .unstarted(() -> {
                        try {
                            Thread.sleep(Duration.ofMillis(SLEEP_MS));
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            t.join();
        }

        long elapsed = Duration.between(start, Instant.now()).toMillis();
        System.out.println("  Completado en: " + elapsed + " ms");
        return elapsed;
    }

    /**
     * Método 3: Hilos virtuales creados e iniciados directamente.
     * La JVM gestiona estos hilos. Son increíblemente baratos y rápidos de crear.
     */
    private static long runVirtualRaw() throws InterruptedException {
        System.out.println("\n--- 3. Hilos virtuales de forma directa (Thread.startVirtualThread) ---");
        List<Thread> threads = new ArrayList<>(TASKS_COUNT);
        Instant start = Instant.now();

        for (int i = 0; i < TASKS_COUNT; i++) {
            // Crea e inicia inmediatamente un hilo virtual.
            // Cuando este hilo ejecuta el sleep, la JVM detecta el bloqueo y libera el hilo físico subyacente
            // (carrier thread), permitiendo que otros miles de hilos virtuales lo utilicen de forma cooperativa.
            Thread t = Thread.startVirtualThread(() -> {
                try {
                    Thread.sleep(Duration.ofMillis(SLEEP_MS));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads.add(t);
        }

        // Los hilos virtuales son objetos del JDK que extienden de Thread, por lo que join() funciona con normalidad
        for (Thread t : threads) {
            t.join();
        }

        long elapsed = Duration.between(start, Instant.now()).toMillis();
        System.out.println("  Completado en: " + elapsed + " ms");
        return elapsed;
    }

    /**
     * Método 4: Hilos virtuales con ExecutorService.
     * Este es el patrón recomendado para producción.
     * El executor no mantiene una cola (pool) de hilos virtuales, sino que simplemente arranca un nuevo
     * hilo virtual para cada tarea asignada (submit/invokeAll).
     */
    private static long runVirtualExecutor() {
        System.out.println("\n--- 4. Hilos virtuales con ExecutorService (VirtualThreadPerTaskExecutor) ---");
        Instant start = Instant.now();

        // El bloque try-with-resources es muy potente aquí.
        // Al final del bloque, llama al método close() de ExecutorService, el cual suspende el flujo del hilo
        // principal, hace shutdown() y realiza un awaitTermination() automático esperando que finalicen todas las tareas concurrentes.
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < TASKS_COUNT; i++) {
                executor.submit(() -> {
                    try {
                        Thread.sleep(Duration.ofMillis(SLEEP_MS));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
        } // Cierre implícito que espera a la finalización de todas las tareas enviadas

        long elapsed = Duration.between(start, Instant.now()).toMillis();
        System.out.println("  Completado en: " + elapsed + " ms");
        return elapsed;
    }
}

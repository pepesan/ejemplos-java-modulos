package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ejemplo avanzado de sincronización con Hilos Virtuales.
 * 
 * Ilustra el concepto de "Pinning" (anclaje de hilos portadores):
 * 1. Qué es: Ocurre cuando un hilo virtual se ejecuta dentro de un bloque/método 'synchronized'
 *    o realiza llamadas nativas y se bloquea (por ejemplo, con Thread.sleep o E/S).
 *    Durante este bloqueo, el hilo virtual queda fijado ("pinned") a su Carrier Thread (hilo físico del SO),
 *    impidiendo que otros hilos virtuales utilicen ese hilo portador.
 * 
 * 2. La consecuencia: La JVM se ve obligada a crear hilos físicos adicionales para compensar,
 *    lo que destruye el beneficio de ligereza y velocidad de los hilos virtuales.
 * 
 * 3. La solución: Sustituir bloques 'synchronized' por bloqueos explícitos 'ReentrantLock'
 *    cuando la sección crítica contenga operaciones de E/S o bloqueantes.
 */
public class VirtualThreadSynchronizationDemo {

    // Usamos un número elevado de tareas bloqueantes simultáneas
    private static final int TASKS_COUNT = 600;
    private static final int SLEEP_MS = 150;

    public static void main(String[] args) throws InterruptedException {
        ejecutar();
    }

    public static void ejecutar() throws InterruptedException {
        System.out.println("\n======================================================================");
        System.out.println("DEMOSTRACIÓN DE SINCRONIZACIÓN Y PINNING EN HILOS VIRTUALES");
        System.out.println("======================================================================\n");

        System.out.println("Lanzando " + TASKS_COUNT + " tareas concurrentes.");
        System.out.println("Cada tarea utiliza su propio objeto de bloqueo (no hay colisión de locks/exclusión mutua),");
        System.out.println("pero realiza un bloqueo artificial (sleep) de " + SLEEP_MS + "ms dentro de la zona sincronizada.");

        // 1. Ejecución usando bloques synchronized (Produce Pinning)
        long timeSynchronized = ejecutarConSynchronized();

        // 2. Ejecución usando ReentrantLock (Evita el Pinning)
        long timeReentrant = ejecutarConReentrantLock();

        System.out.println("\n=== COMPARATIVA DE SINCRONIZACIÓN ===");
        System.out.printf("| %-45s | %-15s |%n", "Método", "Tiempo Total");
        System.out.println("|-----------------------------------------------+-----------------|");
        System.out.printf("| 1. Bloque 'synchronized' (Con Pinning)        | %6d ms       |%n", timeSynchronized);
        System.out.printf("| 2. Bloque 'ReentrantLock' (Sin Pinning)       | %6d ms       |%n", timeReentrant);
        System.out.println("|-----------------------------------------------+-----------------|");

        System.out.println("\nConclusión:");
        if (timeSynchronized > timeReentrant + 30) {
            System.out.println("✔ ReentrantLock ha sido significativamente más rápido.");
            System.out.println("  Esto se debe a que 'synchronized' obligó a la JVM a realizar anclajes (pinning) en los hilos portadores,");
            System.out.println("  provocando la creación de hilos del SO adicionales o retrasos por falta de hilos portadores disponibles.");
        } else {
            System.out.println("✔ Ambos completaron en tiempos similares, pero ReentrantLock evita el anclaje físico,");
            System.out.println("  reduciendo el consumo de memoria del sistema y la creación excesiva de hilos nativos en el planificador.");
        }
    }

    private static long ejecutarConSynchronized() throws InterruptedException {
        System.out.println("\n--- 1. Ejecutando con bloque 'synchronized' (Provocando Pinning) ---");
        List<Thread> threads = new ArrayList<>(TASKS_COUNT);
        Instant start = Instant.now();

        for (int i = 0; i < TASKS_COUNT; i++) {
            // Cada hilo tiene su propio objeto lock exclusivo para simular operaciones independientes
            final Object localLock = new Object();
            Thread t = Thread.startVirtualThread(() -> {
                synchronized (localLock) {
                    try {
                        // El sleep aquí dentro causa PINNING porque estamos dentro de un synchronized
                        Thread.sleep(Duration.ofMillis(SLEEP_MS));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }

        return Duration.between(start, Instant.now()).toMillis();
    }

    private static long ejecutarConReentrantLock() throws InterruptedException {
        System.out.println("\n--- 2. Ejecutando con bloque 'ReentrantLock' (Evitando Pinning) ---");
        List<Thread> threads = new ArrayList<>(TASKS_COUNT);
        Instant start = Instant.now();

        for (int i = 0; i < TASKS_COUNT; i++) {
            // Cada hilo tiene su propio ReentrantLock exclusivo
            final ReentrantLock localLock = new ReentrantLock();
            Thread t = Thread.startVirtualThread(() -> {
                localLock.lock();
                try {
                    // El sleep aquí NO causa pinning; la JVM desmonta el hilo virtual liberando el Carrier Thread
                    Thread.sleep(Duration.ofMillis(SLEEP_MS));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    localLock.unlock();
                }
            });
            threads.add(t);
        }

        for (Thread t : threads) {
            t.join();
        }

        return Duration.between(start, Instant.now()).toMillis();
    }
}

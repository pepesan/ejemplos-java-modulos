package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ejemplo avanzado de sincronización con Hilos Virtuales.
 * 
 * Ilustra el concepto de "Pinning" (anclaje de hilos portadores) y la solución mediante ReentrantLock:
 * 
 * 1. El Problema: "Carrier Thread Pinning" (Anclaje)
 *    - ¿Qué es? Ocurre cuando un hilo virtual entra en una sección crítica usando la palabra clave 'synchronized'
 *      (método o bloque) y posteriormente se bloquea (por ejemplo, haciendo E/S, llamadas a base de datos o Thread.sleep).
 *    - ¿Por qué ocurre? La instrucción 'synchronized' está ligada a monitores internos de la JVM (a nivel de C++). Cuando
 *      el hilo virtual se bloquea dentro de ella, la JVM no puede desasociar ("desmontar") de forma segura el hilo virtual 
 *      del hilo físico subyacente (Carrier Thread). El hilo queda anclado ("pinned").
 *    - Consecuencia: El hilo físico del sistema operativo queda bloqueado inutilizado. Para compensar esta pérdida de hilos
 *      portadores, el planificador de la JVM se ve forzado a crear hilos nativos físicos adicionales (hasta un máximo de 256),
 *      saturando la memoria del sistema y destruyendo los beneficios de ligereza de los hilos virtuales.
 * 
 * 2. La Solución: ¿Qué es ReentrantLock y cómo ayuda?
 *    - ¿Qué es? ReentrantLock es un cerrojo de exclusión mutua explícito de Java (paquete java.util.concurrent.locks).
 *    - ¿Cómo funciona exactamente? A diferencia de 'synchronized', ReentrantLock está implementado a nivel de código Java
 *      usando la clase abstracta AbstractQueuedSynchronizer (AQS) y llamadas a LockSupport.park() para pausar la ejecución.
 *    - ¿Por qué evita el anclaje? Al ser una estructura de software en Java y no un monitor nativo de la JVM, el planificador
 *      de Project Loom entiende perfectamente cuándo un hilo virtual se pausa para adquirir el lock o cuando se bloquea
 *      mientras lo retiene (ej. en un sleep o lectura de red). En ese momento, la JVM puede desmontar ("yield") el hilo 
 *      virtual con total éxito, liberando el Carrier Thread físico del sistema operativo para que ejecute otros hilos virtuales.
 *    - Regla general en Java 21+: Sustituir bloques 'synchronized' por bloqueos explícitos 'ReentrantLock' en secciones
 *      críticas de código concurrentes que involucren operaciones bloqueantes de entrada/salida (I/O) o esperas.
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
                // Al entrar en un bloque synchronized, la JVM monitoriza el lock a nivel nativo.
                synchronized (localLock) {
                    try {
                        // DETALLE CRÍTICO: Thread.sleep es una operación bloqueante. Al ejecutarse dentro
                        // de 'synchronized', el planificador de la JVM no puede desmontar ("yield") el
                        // hilo virtual de su hilo físico (Carrier Thread).
                        // Esto provoca "PINNING" (anclaje), bloqueando el Carrier Thread y forzando
                        // a la JVM a crear hilos nativos adicionales de compensación.
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
                // Adquirimos el bloqueo usando ReentrantLock. Al ser código Java (AQS),
                // el planificador de hilos virtuales sabe perfectamente cómo suspender y restaurar su ejecución.
                localLock.lock();
                try {
                    // DETALLE CRÍTICO: Thread.sleep es bloqueante, pero al estar bajo ReentrantLock, 
                    // la JVM desmonta ("yield") el hilo virtual de forma limpia del Carrier Thread.
                    // El Carrier Thread físico del SO queda 100% libre para atender a otros hilos virtuales, 
                    // evitando la saturación de hilos del SO y finalizando de forma cooperativa óptima.
                    Thread.sleep(Duration.ofMillis(SLEEP_MS));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    // Siempre liberar el lock en un bloque finally
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

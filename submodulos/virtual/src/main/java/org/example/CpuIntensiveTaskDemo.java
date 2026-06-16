package org.example;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Ejemplo avanzado que demuestra el peligro real de ejecutar tareas CPU-Intensivas en Hilos Virtuales:
 * La Inanición de Hilos (Thread Starvation) debido a la falta de preclusión por tiempo (Time-Slicing Preemption).
 * 
 * - En los hilos tradicionales, el Sistema Operativo suspende periódicamente hilos pesados de CPU
 *   para dar tiempo a otros hilos (Time-Slicing).
 * - En los hilos virtuales, el planificador del JDK es cooperativo. Si un hilo virtual consume CPU
 *   sin bloquearse en E/S (como sleep o red), acapara el hilo portador (carrier thread) y NO lo cede,
 *   dejando sin ejecución a los demás hilos virtuales que sí necesitan correr (Inanición).
 */
public class CpuIntensiveTaskDemo {

    private static final int CORES = Runtime.getRuntime().availableProcessors();
    private static final int CPU_WORK_TIME_MS = 2000; // 2 segundos de CPU al 100%

    public static void main(String[] args) throws InterruptedException {
        System.out.println("======================================================================");
        System.out.println("DEMO DE INANICIÓN (STARVATION) EN HILOS VIRTUALES");
        System.out.println("======================================================================\n");
        System.out.println("Núcleos de CPU disponibles: " + CORES);
        System.out.println("Lanzaremos " + CORES + " tareas pesadas de CPU en paralelo (para ocupar todos los cores)");
        System.out.println("y al mismo tiempo una tarea interactiva ligera que debería ejecutarse cada 150ms.\n");

        // 1. Demostración con Hilos Virtuales (Se verá inanición y retraso masivo)
        demostrarInanicionConHilosVirtuales();

        System.out.println("\nEsperando a que el sistema se estabilice...");
        Thread.sleep(2000);

        // 2. Demostración con Hilos de Plataforma (No hay inanición gracias al SO)
        demostrarConHilosPlataforma();
    }

    private static void demostrarInanicionConHilosVirtuales() throws InterruptedException {
        System.out.println("--- 1. Ejecutando con HILOS VIRTUALES (Inanición activa) ---");
        Instant start = Instant.now();
        List<Thread> cpuThreads = new ArrayList<>();

        // Lanzamos tantos hilos virtuales de CPU como núcleos para ocupar todos los hilos portadores
        for (int i = 0; i < CORES; i++) {
            Thread t = Thread.ofVirtual().start(() -> consumirCpuPesado());
            cpuThreads.add(t);
        }

        // Esperar 10ms para asegurar que todos los hilos portadores se ocupen con el bucle de CPU
        Thread.sleep(10);

        // Tarea interactiva ligera de E/S que debería despertar cada 150ms
        Thread tIo = Thread.ofVirtual().start(() -> ejecutarTareaLigeraInteractivas("Virtual", start));

        // Esperar a que terminen
        for (Thread t : cpuThreads) {
            t.join();
        }
        tIo.join();
    }

    private static void demostrarConHilosPlataforma() throws InterruptedException {
        System.out.println("--- 2. Ejecutando con HILOS DE PLATAFORMA (Time-Slicing del SO activo) ---");
        Instant start = Instant.now();
        List<Thread> cpuThreads = new ArrayList<>();

        // Lanzamos tantos hilos de plataforma de CPU como núcleos
        for (int i = 0; i < CORES; i++) {
            Thread t = Thread.ofPlatform().start(() -> consumirCpuPesado());
            cpuThreads.add(t);
        }

        // Esperar 10ms
        Thread.sleep(10);

        // Tarea interactiva ligera de E/S en hilo de plataforma
        Thread tIo = Thread.ofPlatform().start(() -> ejecutarTareaLigeraInteractivas("Plataforma", start));

        // Esperar a que terminen
        for (Thread t : cpuThreads) {
            t.join();
        }
        tIo.join();
    }

    // Tarea interactiva ligera que se duerme y despierta
    private static void ejecutarTareaLigeraInteractivas(String tipo, Instant startTime) {
        try {
            for (int i = 1; i <= 5; i++) {
                Thread.sleep(150); // Simula dormir/esperar E/S
                long elapsed = Duration.between(startTime, Instant.now()).toMillis();
                System.out.println("  [" + tipo + "] Tarea Interactiva #" + i + " ejecutada a los: " + elapsed + " ms");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Simular un trabajo de CPU puro de 2 segundos sin yield ni E/S
    private static void consumirCpuPesado() {
        long start = System.currentTimeMillis();
        // Bucle caliente de cálculo de CPU
        while (System.currentTimeMillis() - start < CPU_WORK_TIME_MS) {
            esPrimo(999999999L);
        }
    }

    private static boolean esPrimo(long n) {
        if (n <= 1) return false;
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
}

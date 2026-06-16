package com.cursosdedesarrollo.threads;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * Ejemplo que demuestra los ayudantes de concurrencia de alto nivel del paquete java.util.concurrent:
 * 1. CountDownLatch: Permite a uno o más hilos esperar hasta que se completen un conjunto de operaciones en otros hilos.
 * 2. CyclicBarrier: Permite que un conjunto de hilos se esperen mutuamente en un punto común de barrera.
 * 3. Semaphore: Controla el acceso a un recurso compartido limitado mediante un conjunto de permisos.
 */
public class ConcurrencyHelpersDemo {

    public static void ejecutar() {
        System.out.println("=== 4. AYUDANTES DE CONCURRENCIA (CountDownLatch, CyclicBarrier, Semaphore) ===");

        demostrarCountDownLatch();
        demostrarCyclicBarrier();
        demostrarSemaphore();

        System.out.println("Fin de la demostración de Ayudantes de Concurrencia.\n");
    }

    private static void demostrarCountDownLatch() {
        System.out.println("\n--- 4.1 Demo de CountDownLatch ---");
        int numeroServicios = 3;
        // Cuenta inicializada a 3. El hilo principal esperará hasta que la cuenta llegue a 0
        CountDownLatch latch = new CountDownLatch(numeroServicios);

        for (int i = 1; i <= numeroServicios; i++) {
            final int id = i;
            new Thread(() -> {
                try {
                    System.out.println("  [Servicio-" + id + "] Iniciando inicialización...");
                    Thread.sleep(100 * id); // Simular el tiempo de inicio
                    System.out.println("  [Servicio-" + id + "] ¡Servicio iniciado!");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown(); // Decrementa la cuenta del pestillo
                }
            }).start();
        }

        try {
            System.out.println("  [Main] Hilo principal esperando a que los servicios estén listos...");
            latch.await(); // Se bloquea hasta que la cuenta interna llegue a 0
            System.out.println("  [Main] ¡Todos los servicios listos! Continuando el flujo del programa principal.");
        } catch (InterruptedException e) {
            System.out.println("  [Main] Interrumpido esperando al Latch");
        }
    }

    private static void demostrarCyclicBarrier() {
        System.out.println("\n--- 4.2 Demo de CyclicBarrier ---");
        int numeroAmigos = 3;
        
        // Creamos una barrera para 3 hilos, con una acción ejecutada cuando todos lleguen
        CyclicBarrier barrera = new CyclicBarrier(numeroAmigos, () -> {
            System.out.println("  [Barrera-Accion] ¡Todos los amigos han llegado al punto de encuentro! Comienza el viaje.");
        });

        for (int i = 1; i <= numeroAmigos; i++) {
            final int amigoId = i;
            new Thread(() -> {
                try {
                    System.out.println("  [Amigo-" + amigoId + "] Saliendo hacia el punto de encuentro...");
                    Thread.sleep(100 * amigoId); // Simula el tiempo de viaje
                    System.out.println("  [Amigo-" + amigoId + "] Llegó al punto de encuentro. Esperando a los demás...");
                    
                    barrera.await(); // El hilo se suspende aquí hasta que el número de hilos llegue a 3
                    
                    System.out.println("  [Amigo-" + amigoId + "] Inicia el viaje grupal.");
                } catch (Exception e) {
                    System.out.println("  [Amigo-" + amigoId + "] Error en la barrera");
                }
            }).start();
        }

        // Dormir un momento para permitir que se impriman todos los logs del CyclicBarrier antes de pasar a la siguiente demo
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void demostrarSemaphore() {
        System.out.println("\n--- 4.3 Demo de Semaphore ---");
        // Semáforo con 2 permisos (por ejemplo, solo hay 2 conexiones de base de datos disponibles para 4 hilos)
        Semaphore conexionPool = new Semaphore(2);

        for (int i = 1; i <= 4; i++) {
            final int clienteId = i;
            new Thread(() -> {
                try {
                    System.out.println("  [Cliente-" + clienteId + "] Solicitando conexión...");
                    conexionPool.acquire(); // Adquiere un permiso (bloquea si no hay permisos disponibles)
                    
                    System.out.println("  [Cliente-" + clienteId + "] Conexión ADQUIRIDA. Realizando consultas...");
                    Thread.sleep(200); // Simula trabajo con la conexión
                    
                    System.out.println("  [Cliente-" + clienteId + "] Trabajo finalizado. Liberando conexión.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    conexionPool.release(); // Libera el permiso para que otros hilos puedan usarlo
                }
            }).start();
        }

        // Dormir un momento para asegurar que finalizan todas las conexiones antes de salir de la demo
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

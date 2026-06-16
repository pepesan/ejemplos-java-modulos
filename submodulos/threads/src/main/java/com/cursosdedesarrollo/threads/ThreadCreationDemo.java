package com.cursosdedesarrollo.threads;

/**
 * Ejemplo que demuestra las diferentes formas de crear y gestionar hilos en Java:
 * 1. Heredando de la clase Thread.
 * 2. Implementando la interfaz Runnable.
 * 3. Utilizando expresiones Lambda.
 * 4. Utilizando el constructor de hilos de plataforma (Thread.ofPlatform()).
 * 5. Hilos de tipo demonio (Daemon Threads) y la interrupción de hilos.
 */
public class ThreadCreationDemo {

    // 1. Heredando de la clase Thread
    static class MiHilo extends Thread {
        @Override
        public void run() {
            System.out.println("  [MiHilo] Ejecutándose en: " + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("  [MiHilo] Interrumpido");
            }
            System.out.println("  [MiHilo] Terminado");
        }
    }

    // 2. Implementando la interfaz Runnable
    static class MiRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("  [MiRunnable] Ejecutándose en: " + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("  [MiRunnable] Interrumpido");
            }
            System.out.println("  [MiRunnable] Terminado");
        }
    }

    public static void ejecutar() {
        System.out.println("=== 1. DEMOSTRACIÓN DE CREACIÓN Y CICLO DE VIDA DE HILOS ===");

        // Crear hilo heredando de Thread
        MiHilo hilo1 = new MiHilo();
        hilo1.setName("Hilo-Heredado");
        
        // Crear hilo implementando Runnable
        Thread hilo2 = new Thread(new MiRunnable(), "Hilo-Runnable");

        // Crear hilo usando Lambda (Runnable anónimo)
        Thread hilo3 = new Thread(() -> {
            System.out.println("  [Lambda] Ejecutándose en: " + Thread.currentThread().getName());
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                System.out.println("  [Lambda] Interrumpido");
            }
            System.out.println("  [Lambda] Terminado");
        }, "Hilo-Lambda");

        // Crear hilo usando Thread.ofPlatform() (Java 21+)
        Thread hilo4 = Thread.ofPlatform()
                .name("Hilo-Builder-Platform")
                .daemon(false)
                .unstarted(() -> {
                    System.out.println("  [Builder] Ejecutándose en: " + Thread.currentThread().getName() 
                            + " (Daemon: " + Thread.currentThread().isDaemon() + ")");
                });

        // Demostrar hilo demonio (Daemon). Los daemon threads se detienen automáticamente
        // cuando el resto de hilos de usuario finalizan.
        Thread hiloDaemon = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(50);
                    System.out.println("  [Daemon] Hilo de fondo ejecutándose...");
                } catch (InterruptedException e) {
                    System.out.println("  [Daemon] Hilo de fondo interrumpido");
                    break;
                }
            }
        }, "Hilo-Daemon");
        hiloDaemon.setDaemon(true); 

        // Iniciar los hilos
        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start(); // Iniciando el hilo construido sin arrancar
        hiloDaemon.start();

        // Esperar a que terminen usando join()
        try {
            System.out.println("Hilo principal esperando a que finalicen los hilos creados...");
            hilo1.join();
            hilo2.join();
            hilo3.join();
            hilo4.join();
        } catch (InterruptedException e) {
            System.out.println("Hilo principal interrumpido esperando a otros.");
        }

        // Demostración de interrupción de hilos
        System.out.println("Demostrando la interrupción de un hilo:");
        Thread hiloInterrumpible = new Thread(() -> {
            System.out.println("  [Interrumpible] Iniciado, durmiendo por 5 segundos...");
            try {
                Thread.sleep(5000);
                System.out.println("  [Interrumpible] Despertó normalmente");
            } catch (InterruptedException e) {
                System.out.println("  [Interrumpible] ¡Capturada InterruptedException! El estado isInterrupted() es: " 
                        + Thread.currentThread().isInterrupted());
                // Restablecer el flag de interrupción
                Thread.currentThread().interrupt();
            }
        });
        hiloInterrumpible.start();

        try {
            // Dormir un momento antes de interrumpir el hilo
            Thread.sleep(200);
            System.out.println("Interrumpiendo hiloInterrumpible desde el hilo principal...");
            hiloInterrumpible.interrupt();
            hiloInterrumpible.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Fin de la demostración de creación.\n");
    }
}

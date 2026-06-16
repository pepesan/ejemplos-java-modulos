package com.cursosdedesarrollo.threads;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Ejemplo clásico de coordinación entre hilos utilizando bloques sincronizados (synchronized)
 * y los métodos wait() y notifyAll() en un escenario de Productor-Consumidor.
 */
public class ClassicProducerConsumer {

    // Cola compartida con un límite de capacidad (Bounded Queue)
    private static class BoundedQueue<T> {
        private final Queue<T> queue = new LinkedList<>();
        private final int limit;

        public BoundedQueue(int limit) {
            this.limit = limit;
        }

        // Método sincronizado para producir elementos
        public synchronized void put(T item) throws InterruptedException {
            // Se usa un bucle 'while' en lugar de un 'if' para evitar despertares espurios (spurious wakeups)
            while (queue.size() == limit) {
                System.out.println("  [Productor] Cola llena (" + queue.size() + "/" + limit + "). Esperando...");
                wait(); // Libera temporalmente el monitor/bloqueo y suspende el hilo
            }
            queue.add(item);
            System.out.println("  [Productor] Producido: " + item + ". Total en cola: " + queue.size());
            notifyAll(); // Notifica a otros hilos en espera (los consumidores suspendidos)
        }

        // Método sincronizado para consumir elementos
        public synchronized T take() throws InterruptedException {
            while (queue.isEmpty()) {
                System.out.println("  [Consumidor] Cola vacía. Esperando...");
                wait(); // Libera temporalmente el monitor/bloqueo y suspende el hilo
            }
            T item = queue.poll();
            System.out.println("  [Consumidor] Consumido: " + item + ". Total en cola: " + queue.size());
            notifyAll(); // Notifica a otros hilos en espera (los productores suspendidos)
            return item;
        }
    }

    public static void ejecutar() {
        System.out.println("=== 2. COORDINACIÓN CLÁSICA: PRODUCTOR-CONSUMIDOR (synchronized, wait, notifyAll) ===");

        BoundedQueue<Integer> queue = new BoundedQueue<>(3);

        // Hilo Productor: Produce elementos más rápido de lo que el consumidor los consume
        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 6; i++) {
                    queue.put(i);
                    Thread.sleep(50); // Simulación de tiempo de producción rápido
                }
            } catch (InterruptedException e) {
                System.out.println("  [Productor] Interrumpido");
            }
            System.out.println("  [Productor] Finalizó");
        }, "Productor-Hilo");

        // Hilo Consumidor: Consume elementos de forma más lenta
        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 6; i++) {
                    queue.take();
                    Thread.sleep(150); // Simulación de tiempo de consumo lento
                }
            } catch (InterruptedException e) {
                System.out.println("  [Consumidor] Interrumpido");
            }
            System.out.println("  [Consumidor] Finalizó");
        }, "Consumidor-Hilo");

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            System.out.println("Hilo principal interrumpido esperando al Productor/Consumidor");
        }

        System.out.println("Fin de la demostración clásica de Productor-Consumidor.\n");
    }
}

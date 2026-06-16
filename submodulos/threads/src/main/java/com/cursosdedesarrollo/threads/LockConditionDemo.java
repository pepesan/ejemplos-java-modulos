package com.cursosdedesarrollo.threads;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Ejemplo que demuestra la coordinación entre hilos utilizando Lock y Condition explícitamente (java.util.concurrent.locks).
 * Esto proporciona mayor flexibilidad y control que el uso de bloques 'synchronized'.
 */
public class LockConditionDemo {

    private static class SharedBuffer<T> {
        private final Queue<T> buffer = new LinkedList<>();
        private final int capacity;
        
        // Bloqueo explícito con ReentrantLock
        private final Lock lock = new ReentrantLock();
        
        // Condiciones específicas asociadas al bloqueo.
        // Esto permite despertar de forma selectiva a productores o consumidores, 
        // evitando despertar a todos con notifyAll() innecesariamente.
        private final Condition notFull = lock.newCondition();
        private final Condition notEmpty = lock.newCondition();

        public SharedBuffer(int capacity) {
            this.capacity = capacity;
        }

        public void put(T value) throws InterruptedException {
            lock.lock(); // Adquiere el bloqueo de forma exclusiva
            try {
                // Siempre verificar la condición en un bucle while
                while (buffer.size() == capacity) {
                    System.out.println("  [Lock-Producer] Buffer lleno (" + buffer.size() + "). Esperando señal notFull...");
                    notFull.await(); // Suspende el hilo y libera el lock de forma atómica
                }
                buffer.add(value);
                System.out.println("  [Lock-Producer] Añadido: " + value + ". Tamaño actual: " + buffer.size());
                notEmpty.signal(); // Envía la señal al consumidor de que el buffer ya no está vacío
            } finally {
                lock.unlock(); // Es vital liberar el bloqueo en un bloque finally
            }
        }

        public T get() throws InterruptedException {
            lock.lock(); // Adquiere el bloqueo de forma exclusiva
            try {
                while (buffer.isEmpty()) {
                    System.out.println("  [Lock-Consumer] Buffer vacío. Esperando señal notEmpty...");
                    notEmpty.await(); // Suspende el hilo y libera el lock de forma atómica
                }
                T value = buffer.poll();
                System.out.println("  [Lock-Consumer] Extraído: " + value + ". Tamaño actual: " + buffer.size());
                notFull.signal(); // Envía la señal al productor de que el buffer tiene espacio libre
                return value;
            } finally {
                lock.unlock(); // Es vital liberar el bloqueo en un bloque finally
            }
        }
    }

    public static void ejecutar() {
        System.out.println("=== 3. COORDINACIÓN MODERNA: LOCKS Y CONDICIONES (ReentrantLock, Condition) ===");
        
        SharedBuffer<String> buffer = new SharedBuffer<>(2);

        Thread producer = new Thread(() -> {
            String[] tareas = {"Tarea-A", "Tarea-B", "Tarea-C", "Tarea-D"};
            try {
                for (String tarea : tareas) {
                    buffer.put(tarea);
                    Thread.sleep(80);
                }
            } catch (InterruptedException e) {
                System.out.println("  [Lock-Producer] Interrumpido");
            }
            System.out.println("  [Lock-Producer] Finalizó");
        }, "Lock-Producer-Hilo");

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < 4; i++) {
                    buffer.get();
                    Thread.sleep(120);
                }
            } catch (InterruptedException e) {
                System.out.println("  [Lock-Consumer] Interrumpido");
            }
            System.out.println("  [Lock-Consumer] Finalizó");
        }, "Lock-Consumer-Hilo");

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            System.out.println("Hilo principal interrumpido esperando al Productor/Consumidor de Lock");
        }

        System.out.println("Fin de la demostración de Locks y Condiciones.\n");
    }
}

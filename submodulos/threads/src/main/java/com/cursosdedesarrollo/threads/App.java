package com.cursosdedesarrollo.threads;

/**
 * Clase principal que ejecuta de forma secuencial todas las demostraciones de
 * creación de hilos y mecanismos de coordinación del submódulo de threads.
 */
public class App {
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("INICIANDO EJEMPLOS DE JAVA THREADS Y COORDINACIÓN ENTRE HILOS");
        System.out.println("======================================================================\n");

        // 1. Creación y ciclo de vida de hilos
        ThreadCreationDemo.ejecutar();

        // 2. Coordinación clásica usando synchronized, wait y notifyAll
        ClassicProducerConsumer.ejecutar();

        // 3. Coordinación moderna usando Lock y Condition
        LockConditionDemo.ejecutar();

        // 4. Ayudantes de concurrencia de java.util.concurrent (CountDownLatch, CyclicBarrier, Semaphore)
        ConcurrencyHelpersDemo.ejecutar();

        System.out.println("======================================================================");
        System.out.println("FINALIZADOS TODOS LOS EJEMPLOS DEL MÓDULO THREADS");
        System.out.println("======================================================================");
    }
}

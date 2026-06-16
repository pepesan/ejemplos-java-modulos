package com.cursosdedesarrollo.structured;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.concurrent.StructuredTaskScope.Subtask;

/**
 * Ejemplo detallado de Ciclo de Vida y Cancelación en Concurrencia Estructurada (Java 25).
 * 
 * Este programa demuestra:
 * 1. Cancelación en Cascada: Al cancelarse el ámbito principal (scope) o fallar una subtarea,
 *    todas las demás subtareas activas son interrumpidas automáticamente por la JVM.
 * 2. Gestión de Tiempos de Espera (Timeouts): Uso de límites temporales para cancelar
 *    tareas prolongadas de forma segura y consistente.
 */
public class StructuredConcurrencyCancellationDemo {

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("CICLO DE VIDA Y CANCELACIÓN EN CONCURRENCIA ESTRUCTURADA");
        System.out.println("======================================================================\n");

        demostrarCancelacionPorFalloEnCascada();
        demostrarCancelacionPorTimeout();
    }

    /**
     * Demostración de Cancelación en Cascada.
     * Lanzamos dos tareas: una corta que fallará rápido y otra larga que realiza trabajo pesado.
     * Con Joiner.allSuccessfulOrThrow(), al fallar la primera tarea, la segunda se cancela/interrumpe
     * inmediatamente de forma automática.
     */
    private static void demostrarCancelacionPorFalloEnCascada() {
        System.out.println("--- 1. Demo de Cancelación en Cascada (Fallo de otra tarea) ---");
        Instant start = Instant.now();

        try (var scope = StructuredTaskScope.open(Joiner.<String>allSuccessfulOrThrow())) {
            
            // Subtarea 1: Tarea de rápida ejecución que lanza un error
            Subtask<String> tareaFallida = scope.fork(() -> {
                Thread.sleep(Duration.ofMillis(100));
                System.out.println("  [Tarea-1] Ha fallado!");
                throw new RuntimeException("Fallo crítico e inesperado en Tarea 1");
            });

            // Subtarea 2: Tarea prolongada que debería ser cancelada antes de terminar
            Subtask<String> tareaLarga = scope.fork(() -> {
                try {
                    System.out.println("  [Tarea-2] Iniciada. Simulando trabajo de 2 segundos...");
                    Thread.sleep(Duration.ofMillis(2000));
                    System.out.println("  [Tarea-2] Trabajo finalizado con éxito (No debería llegar aquí)");
                    return "Éxito Tarea 2";
                } catch (InterruptedException e) {
                    System.out.println("  [Tarea-2] ¡Recibida señal de interrupción y cancelada con éxito!");
                    throw e;
                }
            });

            System.out.println("  [Main] Esperando la resolución de las subtareas en el Join...");
            // join() bloqueará y arrojará la excepción al detectar el fallo de Tarea-1
            scope.join(); 

        } catch (Exception e) {
            System.out.println("  [Main] Excepción capturada en Main: " + e.getMessage());
        }

        long elapsed = Duration.between(start, Instant.now()).toMillis();
        System.out.println("  [Main] Tiempo total transcurrido: " + elapsed + " ms (Esperábamos 2000ms si no hubiera cancelación)");
        System.out.println();
    }

    /**
     * Demostración de Cancelación por Límite de Tiempo (Timeout).
     * El hilo principal puede aplicar un timeout en el join.
     * Si las subtareas exceden dicho tiempo, se detienen automáticamente liberando recursos.
     */
    private static void demostrarCancelacionPorTimeout() {
        System.out.println("--- 2. Demo de Cancelación por Timeout ---");
        Instant start = Instant.now();

        // En Java 25, el timeout se configura en la inicialización a través del objeto Configuration
        try (var scope = StructuredTaskScope.open(
                Joiner.<String>awaitAll(),
                cfg -> cfg.withTimeout(Duration.ofMillis(500))
        )) {
            
            // Tarea lenta de red
            Subtask<String> tareaLenta = scope.fork(() -> {
                try {
                    System.out.println("  [Tarea-Lenta] Iniciando descarga pesada...");
                    Thread.sleep(Duration.ofMillis(3000));
                    return "Descarga completa";
                } catch (InterruptedException e) {
                    System.out.println("  [Tarea-Lenta] ¡Descarga interrumpida/cancelada!");
                    throw e;
                }
            });

            System.out.println("  [Main] Esperando las tareas...");
            try {
                // join() esperará hasta que terminen las tareas o venza el timeout configurado en open()
                scope.join();
                System.out.println("  [Main] Todas las tareas terminaron antes del timeout");
            } catch (StructuredTaskScope.TimeoutException e) {
                System.out.println("  [Main] ¡Se ha alcanzado el Timeout de 500ms!");
                // La salida del bloque try-with-resources llamará a scope.close(), 
                // lo que enviará señales de interrupción a todas las subtareas activas
            }
        } catch (InterruptedException e) {
            System.out.println("  [Main] Hilo principal interrumpido");
        }

        long elapsed = Duration.between(start, Instant.now()).toMillis();
        System.out.println("  [Main] Tiempo total transcurrido: " + elapsed + " ms (Evitando los 3000ms de espera)");
        System.out.println();
    }
}

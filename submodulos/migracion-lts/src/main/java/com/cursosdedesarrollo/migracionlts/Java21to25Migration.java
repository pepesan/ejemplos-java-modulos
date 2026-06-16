package com.cursosdedesarrollo.migracionlts;

import java.util.concurrent.*;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

/**
 * Ejemplos de migración y refactorización recomendada de Java 21 a Java 25.
 */
public class Java21to25Migration {

    // ScopedValue moderno (Java 24+ estable)
    public final static ScopedValue<String> CONTEXTO_USUARIO = ScopedValue.newInstance();

    public static void ejecutar() {
        System.out.println("==========================================================");
        System.out.println("   MIGRACIÓN DE JAVA 21 A JAVA 25 (LTS)                   ");
        System.out.println("==========================================================\n");

        migracionThreadLocalAScopedValues();
        migracionVariablesSinNombre();
        migracionConcurrenciaEstructurada();
    }

    private static void migracionThreadLocalAScopedValues() {
        System.out.println("--- 1. De ThreadLocal a ScopedValue ---");

        // ANTES (Java 21): ThreadLocal es mutable, pesado para miles de hilos virtuales, y puede provocar leaks si no se remueve.
        ThreadLocal<String> threadLocalContext = new ThreadLocal<>();
        threadLocalContext.set("Admin-User");
        System.out.println("Java 21 (ThreadLocal): " + threadLocalContext.get());
        threadLocalContext.remove(); // Obligatorio limpiar

        // DESPUÉS (Java 25): Inmutable, de un solo uso (scoped) y optimizado para millones de hilos virtuales.
        System.out.print("Java 25 (ScopedValue): ");
        ScopedValue.where(CONTEXTO_USUARIO, "Admin-User").run(() -> {
            System.out.println("Leído dentro del ámbito: " + CONTEXTO_USUARIO.get());
        });
        // Fuera de la llamada run(), CONTEXTO_USUARIO ya no está enlazado ni consume memoria.
        System.out.println();
    }

    private static void migracionVariablesSinNombre() {
        System.out.println("--- 2. Variables Sin Nombre (_) ---");

        // ANTES (Java 21): Obligación de declarar nombres de variables que no se van a utilizar
        try {
            int numero = Integer.parseInt("invalido");
        } catch (NumberFormatException e) {
            System.out.println("Java 21 (Excepción capturada con variable declarada inútilmente 'e')");
        }

        // DESPUÉS (Java 22/25): Uso del guion bajo (_) para indicar explícitamente que la variable se ignora
        try {
            int numero = Integer.parseInt("invalido");
        } catch (NumberFormatException _) { // Declaración sin nombre
            System.out.println("Java 25 (Excepción capturada limpiamente ignorando la variable con '_')");
        }
        System.out.println();
    }

    private static void migracionConcurrenciaEstructurada() {
        System.out.println("--- 3. Concurrencia Estructurada (Structured Task Scope) ---");

        // ANTES (Java 21): Coordinación con CompletableFuture compleja y propensa a fugas de hilos si falla una tarea
        CompletableFuture<String> futuro1 = CompletableFuture.supplyAsync(() -> "Datos A");
        CompletableFuture<String> futuro2 = CompletableFuture.supplyAsync(() -> "Datos B");
        CompletableFuture.allOf(futuro1, futuro2).join();
        try {
            System.out.println("Java 21 (CompletableFuture): " + futuro1.get() + " + " + futuro2.get());
        } catch (InterruptedException | ExecutionException _) {}

        // DESPUÉS (Java 25): StructuredTaskScope.open(Joiner) asegura el ciclo de vida de las tareas hijas.
        // Si una tarea falla, el scope cancela el resto automáticamente (Short-circuit).
        System.out.println("Java 25 (StructuredTaskScope): Iniciando tareas concurrentes estructuradas...");
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.<String>allSuccessfulOrThrow())) {
            var subtask1 = scope.fork(() -> {
                Thread.sleep(10);
                return "Datos A";
            });
            var subtask2 = scope.fork(() -> {
                Thread.sleep(10);
                return "Datos B";
            });

            scope.join(); // Espera sincronizada de todas las subtareas del bloque
            
            System.out.println("  Resultado 1: " + subtask1.get());
            System.out.println("  Resultado 2: " + subtask2.get());
        } catch (InterruptedException _) {
            System.out.println("  Operación interrumpida");
        } catch (Exception _) {
            System.out.println("  Fallo en alguna de las subtareas o error en la ejecución");
        }
        System.out.println();
    }
}

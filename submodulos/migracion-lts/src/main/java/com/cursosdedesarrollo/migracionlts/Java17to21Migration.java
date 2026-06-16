package com.cursosdedesarrollo.migracionlts;

import java.util.*;
import java.util.concurrent.*;

/**
 * Ejemplos de migración y refactorización recomendada de Java 17 a Java 21.
 */
public class Java17to21Migration {

    public static void ejecutar() {
        System.out.println("==========================================================");
        System.out.println("   MIGRACIÓN DE JAVA 17 A JAVA 21 (LTS)                   ");
        System.out.println("==========================================================\n");

        migracionColeccionesSecuenciadas();
        migracionHilosVirtuales();
    }

    private static void migracionColeccionesSecuenciadas() {
        System.out.println("--- 1. Colecciones Secuenciadas (Sequenced Collections) ---");

        List<String> lenguajes = new ArrayList<>(List.of("Java", "C#", "C++", "Python"));

        // ANTES (Java 17): Obtener primer/último elemento requiere posiciones manuales o lógica propensa a errores
        String primeroJava17 = lenguajes.get(0);
        String ultimoJava17 = lenguajes.get(lenguajes.size() - 1);
        System.out.println("Java 17 (Manual) -> Primero: " + primeroJava17 + ", Último: " + ultimoJava17);

        // DESPUÉS (Java 21): Métodos explícitos y unificados getFirst() y getLast()
        String primeroJava21 = lenguajes.getFirst();
        String ultimoJava21 = lenguajes.getLast();
        System.out.println("Java 21 (Secuenciado) -> Primero: " + primeroJava21 + ", Último: " + ultimoJava21);

        // Inversión rápida de la colección sin copiar datos
        List<String> invertida = lenguajes.reversed();
        System.out.println("Colección invertida directamente: " + invertida);
        System.out.println();
    }

    private static void migracionHilosVirtuales() {
        System.out.println("--- 2. ExecutorService con Hilos Virtuales ---");

        // ANTES (Java 17): Pool de hilos acotado para evitar fatiga de memoria del SO
        System.out.println("Java 17: Creando Fixed Thread Pool (límite físico)...");
        try (ExecutorService poolJava17 = Executors.newFixedThreadPool(10)) {
            poolJava17.submit(() -> {
                System.out.println("  Ejecutando en hilo de plataforma: " + Thread.currentThread());
            });
        }

        // DESPUÉS (Java 21): Hilos virtuales superligeros ilimitados para tareas bloqueantes
        System.out.println("Java 21: Usando Virtual Thread Executor (ilimitado y dinámico)...");
        try (ExecutorService poolJava21 = Executors.newVirtualThreadPerTaskExecutor()) {
            poolJava21.submit(() -> {
                System.out.println("  Ejecutando en hilo virtual: " + Thread.currentThread());
            });
        }
        System.out.println();
    }
}

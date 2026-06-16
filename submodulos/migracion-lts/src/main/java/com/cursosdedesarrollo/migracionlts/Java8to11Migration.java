package com.cursosdedesarrollo.migracionlts;

import java.util.*;

/**
 * Ejemplos de migración y refactorización recomendada de Java 8 a Java 11.
 */
public class Java8to11Migration {

    public static void ejecutar() {
        System.out.println("==========================================================");
        System.out.println("   MIGRACIÓN DE JAVA 8 A JAVA 11 (LTS)                    ");
        System.out.println("==========================================================\n");

        migracionColeccionesInmutables();
        migracionInferenciaDeTipos();
        migracionOptionalMejorado();
    }

    private static void migracionColeccionesInmutables() {
        System.out.println("--- 1. Colecciones Inmutables ---");

        // ANTES (Java 8): Propenso a verbosidad, usa envoltorio
        List<String> listaJava8 = Arrays.asList("a", "b", "c");
        List<String> inmutableJava8 = Collections.unmodifiableList(listaJava8);
        System.out.println("Java 8 (UnmodifiableList): " + inmutableJava8);

        // DESPUÉS (Java 9/11): Compacto, inmutable por diseño
        List<String> inmutableJava11 = List.of("a", "b", "c");
        System.out.println("Java 11 (List.of): " + inmutableJava11);
        System.out.println();
    }

    private static void migracionInferenciaDeTipos() {
        System.out.println("--- 2. Inferencia de Tipos Locales (var) ---");

        // ANTES (Java 8): Repetitivo, firmas de tipos extremadamente largas
        Map<String, List<Integer>> mapaJava8 = new HashMap<String, List<Integer>>();
        mapaJava8.put("valores", Arrays.asList(1, 2, 3));
        System.out.println("Java 8 (Tipo explícito): " + mapaJava8);

        // DESPUÉS (Java 10/11): Inferencia inteligente del compilador
        var mapaJava11 = new HashMap<String, List<Integer>>();
        mapaJava11.put("valores", List.of(1, 2, 3));
        System.out.println("Java 11 (var): " + mapaJava11);
        System.out.println();
    }

    private static void migracionOptionalMejorado() {
        System.out.println("--- 3. Patrones de Optional ---");

        Optional<String> optional = Optional.ofNullable(null);

        // ANTES (Java 8): Bucle condicional clásico e ineficiente
        System.out.print("Java 8: ");
        if (optional.isPresent()) {
            System.out.println("Valor: " + optional.get());
        } else {
            System.out.println("Valor ausente");
        }

        // DESPUÉS (Java 9/11): Programación funcional compacta
        System.out.print("Java 11 (ifPresentOrElse): ");
        optional.ifPresentOrElse(
                val -> System.out.println("Valor: " + val),
                () -> System.out.println("Valor ausente")
        );
        System.out.println();
    }
}

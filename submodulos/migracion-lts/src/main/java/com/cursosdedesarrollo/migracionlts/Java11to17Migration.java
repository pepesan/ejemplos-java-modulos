package com.cursosdedesarrollo.migracionlts;

import java.util.Objects;

/**
 * Ejemplos de migración y refactorización recomendada de Java 11 a Java 17.
 */
public class Java11to17Migration {

    public static void ejecutar() {
        System.out.println("==========================================================");
        System.out.println("   MIGRACIÓN DE JAVA 11 A JAVA 17 (LTS)                   ");
        System.out.println("==========================================================\n");

        migracionTextBlocks();
        migracionSwitchExpressions();
        migracionRecords();
        migracionPatternMatchingInstanceof();
    }

    private static void migracionTextBlocks() {
        System.out.println("--- 1. Bloques de texto (Text Blocks) ---");

        // ANTES (Java 11): Concatenaciones engorrosas y manuales con '\n'
        String jsonJava11 = "{\n" +
                "  \"nombre\": \"Juan\",\n" +
                "  \"rol\": \"Developer\"\n" +
                "}";
        System.out.println("Java 11 (JSON concatenado):\n" + jsonJava11);

        // DESPUÉS (Java 15/17): Multilínea nativo y limpio
        String jsonJava17 = """
                {
                  "nombre": "Juan",
                  "rol": "Developer"
                }""";
        System.out.println("Java 17 (Text Block):\n" + jsonJava17);
        System.out.println();
    }

    private static void migracionSwitchExpressions() {
        System.out.println("--- 2. Expresiones Switch ---");

        enum Dia { LUNES, SABADO, DOMINGO }
        Dia dia = Dia.SABADO;

        // ANTES (Java 11): Sentencia Switch clásica propensa a fallos por olvido de 'break'
        String tipoDiaJava11;
        switch (dia) {
            case LUNES:
                tipoDiaJava11 = "Trabajo";
                break;
            case SABADO:
            case DOMINGO:
                tipoDiaJava11 = "Fin de semana";
                break;
            default:
                tipoDiaJava11 = "Desconocido";
        }
        System.out.println("Java 11 (Switch clásico): " + tipoDiaJava11);

        // DESPUÉS (Java 14/17): Expresión Switch con asignación limpia y flechas (->)
        String tipoDiaJava17 = switch (dia) {
            case LUNES -> "Trabajo";
            case SABADO, DOMINGO -> "Fin de semana";
        };
        System.out.println("Java 17 (Switch Expression): " + tipoDiaJava17);
        System.out.println();
    }

    // Clase Portadora de Datos clásica (Java 11)
    private static class UsuarioJava11 {
        private final String nombre;
        private final int edad;

        public UsuarioJava11(String nombre, int edad) {
            this.nombre = nombre;
            this.edad = edad;
        }

        public String getNombre() { return nombre; }
        public int getEdad() { return edad; }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UsuarioJava11 that = (UsuarioJava11) o;
            return edad == that.edad && Objects.equals(nombre, that.nombre);
        }

        @Override
        public int hashCode() {
            return Objects.hash(nombre, edad);
        }

        @Override
        public String toString() {
            return "UsuarioJava11{" + "nombre='" + nombre + '\'' + ", edad=" + edad + '}';
        }
    }

    // DESPUÉS (Java 16/17): Record de una sola línea
    private record UsuarioJava17(String nombre, int edad) {}

    private static void migracionRecords() {
        System.out.println("--- 3. Clases de datos a Records ---");
        var user11 = new UsuarioJava11("Ana", 30);
        var user17 = new UsuarioJava17("Ana", 30);

        System.out.println("Java 11 (POJO clásico de 30 líneas): " + user11);
        System.out.println("Java 17 (Record de 1 línea): " + user17);
        System.out.println();
    }

    private static void migracionPatternMatchingInstanceof() {
        System.out.println("--- 4. Pattern Matching para instanceof ---");

        Object obj = "Este es un texto";

        // ANTES (Java 11): Validación de tipo seguida de casting explícito redundante
        if (obj instanceof String) {
            String strJava11 = (String) obj;
            System.out.println("Java 11 (Cast manual): Longitud = " + strJava11.length());
        }

        // DESPUÉS (Java 16/17): Validación y asignación automática en la misma línea
        if (obj instanceof String strJava17) {
            System.out.println("Java 17 (Pattern Matching): Longitud = " + strJava17.length());
        }
        System.out.println();
    }
}

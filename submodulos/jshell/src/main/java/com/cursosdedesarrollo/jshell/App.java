package com.cursosdedesarrollo.jshell;

import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;
import jdk.jshell.Snippet;
import jdk.jshell.VarSnippet;
import jdk.jshell.MethodSnippet;
import jdk.jshell.TypeDeclSnippet;

import java.util.List;

/**
 * Demostración del API programático de JShell (módulo jdk.jshell, Java 9+).
 *
 * JShell es el REPL (Read-Eval-Print Loop) del JDK, introducido en Java 9 (JEP 222).
 * Permite evaluar expresiones, declarar variables y métodos sin crear una clase completa.
 *
 * Esta clase muestra el USO PROGRAMÁTICO: embeber JShell dentro de una aplicación Java
 * para evaluar código dinámicamente en tiempo de ejecución.
 * Para el uso INTERACTIVO desde la terminal, ver la carpeta scripts/.
 */
public class App {

    public static void main(String[] args) {
        System.out.println("=== Demostración del API programático de JShell ===\n");

        demoEvaluacionExpresiones();
        demoDeclaracionVariables();
        demoDeclaracionMetodos();
        demoDeclaracionTipos();
        demoManejoDeerrores();
        demoInspeccionEstado();
    }

    // -------------------------------------------------------------------------
    // 1. Evaluación de expresiones
    //
    // JShell.eval(String) devuelve una lista de SnippetEvent.
    // Cada evento tiene:
    //   - snippet()  → el objeto Snippet que representa el fragmento de código
    //   - status()   → VALID, INVALID, REJECTED, DROPPED, OVERWRITTEN...
    //   - value()    → representación String del resultado (solo en expresiones)
    //   - exception()→ la excepción lanzada durante la evaluación, si la hubo
    // -------------------------------------------------------------------------
    static void demoEvaluacionExpresiones() {
        System.out.println("--- 1. Evaluación de expresiones ---");
        try (JShell shell = JShell.create()) {

            evaluar(shell, "1 + 1");
            evaluar(shell, "Math.PI * 2");
            evaluar(shell, "\"Hola\".toUpperCase()");
            evaluar(shell, "List.of(1, 2, 3).size()");   // los imports de java.lang y java.util están activos por defecto
        }
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 2. Declaración de variables
    //
    // Una declaración de variable en JShell crea un VarSnippet.
    // El valor persiste en la sesión del JShell: snippets posteriores pueden usar
    // las variables declaradas antes.
    // -------------------------------------------------------------------------
    static void demoDeclaracionVariables() {
        System.out.println("--- 2. Declaración y uso de variables ---");
        try (JShell shell = JShell.create()) {

            // Declarar variables; el resultado es el valor asignado
            evaluar(shell, "int x = 10");
            evaluar(shell, "int y = 20");
            // Usar variables declaradas antes: JShell mantiene el estado entre eval()
            evaluar(shell, "x + y");
            evaluar(shell, "String saludo = \"Hola, Java 9!\"");
            evaluar(shell, "saludo.length()");

            // Listar las variables activas en la sesión
            System.out.println("  Variables activas en la sesión:");
            shell.variables().forEach(v ->
                System.out.printf("    %s %s = %s%n", v.typeName(), v.name(), shell.varValue(v))
            );
        }
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 3. Declaración de métodos
    //
    // Los métodos declarados en JShell están disponibles para llamadas posteriores
    // dentro de la misma sesión. Esto permite construir funciones de forma incremental
    // sin necesidad de una clase envolvente.
    // -------------------------------------------------------------------------
    static void demoDeclaracionMetodos() {
        System.out.println("--- 3. Declaración y llamada de métodos ---");
        try (JShell shell = JShell.create()) {

            // Declarar un método; JShell lo compila y lo almacena
            evaluar(shell, "int suma(int a, int b) { return a + b; }");
            // Llamar al método
            evaluar(shell, "suma(3, 4)");

            // Método que usa lambdas y streams (sintaxis completa, no solo expresión)
            evaluar(shell, """
                    String mayusculas(java.util.List<String> lista) {
                        return lista.stream()
                            .map(String::toUpperCase)
                            .collect(java.util.stream.Collectors.joining(", "));
                    }
                    """);
            evaluar(shell, "mayusculas(List.of(\"java\", \"jshell\", \"api\"))");

            // Listar métodos activos
            System.out.println("  Métodos activos en la sesión:");
            shell.methods().forEach(m ->
                System.out.printf("    %s%n", m.name())
            );
        }
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 4. Declaración de tipos (clases, records, enums, interfaces)
    //
    // JShell admite declaraciones completas de tipos dentro de un snippet.
    // Los tipos quedan disponibles para instanciación en la misma sesión.
    // -------------------------------------------------------------------------
    static void demoDeclaracionTipos() {
        System.out.println("--- 4. Declaración de tipos ---");
        try (JShell shell = JShell.create()) {

            // Declarar un record (Java 16+)
            evaluar(shell, "record Punto(int x, int y) {}");
            evaluar(shell, "new Punto(3, 4)");
            evaluar(shell, "new Punto(3, 4).x()");

            // Declarar una clase simple
            evaluar(shell, """
                    class Saludo {
                        String texto;
                        Saludo(String t) { this.texto = t; }
                        String decir() { return "¡" + texto + "!"; }
                    }
                    """);
            evaluar(shell, "new Saludo(\"Hola desde JShell\").decir()");

            // Listar tipos activos
            System.out.println("  Tipos activos en la sesión:");
            shell.types().forEach(t ->
                System.out.printf("    %s%n", t.name())
            );
        }
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 5. Manejo de errores
    //
    // JShell distingue entre:
    //   - Errores de compilación → status REJECTED (el snippet no es código Java válido)
    //   - Errores en tiempo de ejecución → status VALID pero exception() != null
    //   - Snippets incompletos → status RECOVERABLE_DEFINED o RECOVERABLE_NOT_DEFINED
    //
    // Esto permite detectar y reportar errores de forma granular, lo que hace
    // de JShell una base excelente para herramientas de enseñanza o playground.
    // -------------------------------------------------------------------------
    static void demoManejoDeerrores() {
        System.out.println("--- 5. Manejo de errores ---");
        try (JShell shell = JShell.create()) {

            System.out.println("  [Error de compilación: código inválido]");
            List<SnippetEvent> eventos = shell.eval("int x = \"texto\"");  // tipos incompatibles
            for (SnippetEvent e : eventos) {
                if (e.status() == Snippet.Status.REJECTED) {
                    System.out.println("    RECHAZADO (error de compilación)");
                    // Los diagnósticos dan el mensaje de error concreto
                    shell.diagnostics(e.snippet()).forEach(d ->
                        System.out.println("    Diagnóstico: " + d.getMessage(null))
                    );
                }
            }

            System.out.println("  [Error en tiempo de ejecución: excepción]");
            eventos = shell.eval("int r = 10 / 0");  // ArithmeticException
            for (SnippetEvent e : eventos) {
                if (e.exception() != null) {
                    System.out.println("    Excepción lanzada: " + e.exception().getClass().getSimpleName()
                        + " — " + e.exception().getMessage());
                }
            }
        }
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // 6. Inspección del estado de la sesión
    //
    // JShell.snippets() devuelve TODOS los snippets evaluados (activos y no activos).
    // Es útil para construir herramientas de autocompletado, depuración o historial.
    // -------------------------------------------------------------------------
    static void demoInspeccionEstado() {
        System.out.println("--- 6. Inspección del estado completo de la sesión ---");
        try (JShell shell = JShell.create()) {

            shell.eval("int a = 1");
            shell.eval("int b = 2");
            shell.eval("String nombre = \"JShell\"");
            shell.eval("void saludar() { System.out.println(\"Hola, \" + nombre); }");
            shell.eval("record Coordenada(double lat, double lon) {}");
            shell.eval("saludar()");

            System.out.println("  Resumen de snippets activos:");
            shell.snippets()
                .filter(s -> shell.status(s) == Snippet.Status.VALID)
                .forEach(s -> System.out.printf("    [%-20s] %s%n",
                    s.getClass().getSimpleName().replace("Snippet", ""),
                    s.source().trim().replace("\n", " ").substring(0, Math.min(60, s.source().trim().length()))
                ));
        }
        System.out.println();
    }

    // -------------------------------------------------------------------------
    // Método auxiliar: evalúa un snippet e imprime el resultado de forma legible
    // -------------------------------------------------------------------------
    private static void evaluar(JShell shell, String codigo) {
        // Formatear el código para la salida (truncar si es muy largo)
        String codigoMostrado = codigo.trim().replace("\n", " ");
        if (codigoMostrado.length() > 60) codigoMostrado = codigoMostrado.substring(0, 57) + "...";

        List<SnippetEvent> eventos = shell.eval(codigo);
        for (SnippetEvent e : eventos) {
            switch (e.status()) {
                case VALID -> {
                    String valor = e.value() != null ? " → " + e.value() : " (declarado)";
                    System.out.printf("  %-62s %s%n", codigoMostrado, valor);
                }
                case REJECTED -> {
                    System.out.printf("  %-62s → ERROR DE COMPILACIÓN%n", codigoMostrado);
                    shell.diagnostics(e.snippet()).forEach(d ->
                        System.out.println("    " + d.getMessage(null)));
                }
                default -> System.out.printf("  %-62s → [%s]%n", codigoMostrado, e.status());
            }
            // Si lanzó una excepción en tiempo de ejecución, mostrarla
            if (e.exception() != null) {
                System.out.printf("    Excepción: %s%n", e.exception());
            }
        }
    }
}

package com.cursosdedesarrollo.ejemplos_switch;

import java.time.LocalDate;
import java.time.Month;

public class MainApp {
    public static void main(String[] args) {
        Month mes = LocalDate.now().getMonth();
        switch (mes) {
            case JANUARY, FEBRUARY, MARCH -> System.out.println("First Quarter");//no break needed
            case APRIL, MAY, JUNE -> System.out.println("Second Quarter");
            case JULY, AUGUST, SEPTEMBER -> System.out.println("Third Quarter");
            case OCTOBER, NOVEMBER, DECEMBER -> System.out.println("Forth Quarter");
            default -> System.out.println("Unknown Quarter");
        }

        String quarter = switch (mes) {
            case JANUARY, FEBRUARY, MARCH -> "First Quarter"; //must be a single returning value
            case APRIL, MAY, JUNE -> "Second Quarter";
            case JULY, AUGUST, SEPTEMBER -> "Third Quarter";
            case OCTOBER, NOVEMBER, DECEMBER -> "Forth Quarter";
            default -> "Unknown Quarter";
        };
        System.out.println(quarter);

        String result = switch (mes) {
            case JANUARY, FEBRUARY, MARCH -> {
                //multiple statements can be used here
                yield "First Quarter";
            }
            case APRIL, MAY, JUNE -> {
                //multiple statements can be used here
                yield "Second Quarter";
            }
            case JULY, AUGUST, SEPTEMBER -> "Third Quarter";
            case OCTOBER, NOVEMBER, DECEMBER -> {
                //multiple statements can be used here
                yield "Forth Quarter";
            }
            default -> "Unknown Quarter";
        };
        System.out.println(result);
        // Java 21 (no Preview)
        Object o = new String("Hola");
        switch (o) {
            case null -> {
                System.out.println("null");
                break;
            }
            case String s -> System.out.println("String: "+ s);
            default -> System.out.println("ni null ni cadena");
        }
        // extraer y comprobar datos
        record Point(int x, int y) {
        }
        Point p = new Point(1,2);
        switch (p) {
            // comprueba si es un Point
            // extrae los valores x e y
            // comprueba sus valores
            case Point(var x, var y) when x > 0 && y > 0 ->
                    System.out.println("first");
            default ->
                    System.out.println("other");
        }
        // comprobaciones null
        String test = null;

        switch (test) {
            case String s -> System.out.println("String");
            // en el caso de null
            case null -> System.out.println("null");
        }

        // más información: https://softwaremill.com/java-21-switch-the-power-on/

        // Java 25 — Primitive Types in Patterns (JEP 507)
        // Permite usar tipos primitivos en instanceof y en switch,
        // incluyendo conversiones widening automáticas.

        // instanceof con primitivos
        Object numero = 42;
        if (numero instanceof int i) {
            System.out.println("Es un int: " + i);
        }
        if (numero instanceof long l) {
            // widening: int → long
            System.out.println("Cabe en long: " + l);
        }
        if (!(numero instanceof double d)) {
            // narrowing no aplica: double no hace widening desde int
            System.out.println("No es double directamente");
        }

        // switch con tipos primitivos
        Object valor = 3.14f;
        switch (valor) {
            case Integer i  -> System.out.println("Integer: " + i);
            case Float f    -> System.out.println("Float: " + f);
            case Double d   -> System.out.println("Double: " + d);
            case String s   -> System.out.println("String: " + s);
            default         -> System.out.println("Otro tipo");
        }

        // switch sobre primitivo directo con guard
        int nivel = 7;
        String categoria = switch (nivel) {
            case int n when n < 0  -> "negativo";
            case int n when n == 0 -> "cero";
            case int n when n < 5  -> "bajo";
            case int n when n < 10 -> "medio";
            default                -> "alto";
        };
        System.out.println("Nivel " + nivel + " → " + categoria);
    }
}

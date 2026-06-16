package com.cursosdedesarrollo.ejemplos_switch;

import java.time.LocalDate;
import java.time.Month;

public class MainApp {
    public static void main(String[] args) {
        Month mes = LocalDate.now().getMonth();

        // 1. Switch clásico vs Switch Rules (Java 12/14)
        // La sintaxis de flecha (->) evita el comportamiento por defecto de "fall-through" (caída en cascada).
        // No se requiere la palabra clave 'break'.
        // Permite agrupar múltiples valores en un solo case separados por comas.
        switch (mes) {
            case JANUARY, FEBRUARY, MARCH -> System.out.println("First Quarter");
            case APRIL, MAY, JUNE -> System.out.println("Second Quarter");
            case JULY, AUGUST, SEPTEMBER -> System.out.println("Third Quarter");
            case OCTOBER, NOVEMBER, DECEMBER -> System.out.println("Forth Quarter");
            default -> System.out.println("Unknown Quarter");
        }

        // 2. Expresiones Switch (Switch Expressions - Java 12/14)
        // El switch ahora puede devolver directamente un valor que se asigna a una variable.
        // Debe ser exhaustivo (debe contemplar todos los valores posibles o tener un bloque 'default').
        // La instrucción completa del switch termina con un punto y coma (;).
        String quarter = switch (mes) {
            case JANUARY, FEBRUARY, MARCH -> "First Quarter";
            case APRIL, MAY, JUNE -> "Second Quarter";
            case JULY, AUGUST, SEPTEMBER -> "Third Quarter";
            case OCTOBER, NOVEMBER, DECEMBER -> "Forth Quarter";
            default -> "Unknown Quarter";
        };
        System.out.println(quarter);

        // 3. Bloques de código con 'yield' en Expresiones Switch
        // Si un caso requiere ejecutar lógica compleja o múltiples sentencias, se define un bloque con llaves { ... }.
        // Dentro de este bloque de código, se usa 'yield' seguido del valor de retorno final para "devolver" el resultado.
        String result = switch (mes) {
            case JANUARY, FEBRUARY, MARCH -> {
                System.out.println("Procesando primer trimestre...");
                yield "First Quarter";
            }
            case APRIL, MAY, JUNE -> {
                System.out.println("Procesando segundo trimestre...");
                yield "Second Quarter";
            }
            case JULY, AUGUST, SEPTEMBER -> "Third Quarter"; // En una sola línea no requiere bloque ni yield.
            case OCTOBER, NOVEMBER, DECEMBER -> {
                System.out.println("Procesando cuarto trimestre...");
                yield "Forth Quarter";
            }
            default -> "Unknown Quarter";
        };
        System.out.println(result);

        // 4. Pattern Matching para switch (Java 21 - JEP 441)
        // Permite hacer 'switch' sobre objetos y verificar su tipo dinámicamente usando patrones de tipo.
        // Se puede definir una variable para el tipo verificado (case String s).
        // Además, soporta de forma nativa la comprobación de nulos con 'case null'.
        Object o = new String("Hola");
        switch (o) {
            case null -> {
                System.out.println("El objeto es null");
                break;
            }
            case String s -> System.out.println("El objeto es un String de longitud " + s.length() + ": " + s);
            default -> System.out.println("El objeto no es ni null ni String");
        }

        // 5. Deconstrucción de Records y Cláusulas de Guarda (Java 21 - JEP 440)
        // Permite deconstruir instancias de un Record directamente en las ramas del switch.
        // Extrae las variables miembro (x, y) de forma automática.
        // Se utiliza la palabra clave 'when' para agregar condiciones adicionales (filtros o guards).
        record Point(int x, int y) {
        }
        Point p = new Point(1, 2);
        switch (p) {
            // El caso solo coincide si el objeto es un Point y cumple la condición del 'when' (x > 0 e y > 0).
            case Point(var x, var y) when x > 0 && y > 0 ->
                    System.out.println("Punto en el primer cuadrante: x=" + x + ", y=" + y);
            default ->
                    System.out.println("Punto en otro cuadrante u origen");
        }

        // 6. Tratamiento seguro de Null en Switch (Java 21)
        // Tradicionalmente, pasar un valor nulo a un switch lanzaba NullPointerException inmediatamente.
        // Ahora, al definir 'case null', podemos capturar y procesar este estado de forma limpia.
        String test = null;
        switch (test) {
            case String s -> System.out.println("Es una cadena de texto");
            case null -> System.out.println("Es una referencia nula (seguro)");
        }

        // Más información sobre switch en Java 21: https://softwaremill.com/java-21-switch-the-power-on/

        // 7. Patrones de tipos primitivos (Java 25 - JEP 507)
        // Permite usar tipos primitivos en instanceof y en switch de forma unificada.
        // Soporta conversiones automáticas sin pérdida de precisión (widening conversions).

        // instanceof con primitivos (Java 25)
        Object numero = 42;
        if (numero instanceof int i) {
            System.out.println("Es un int primitivo: " + i);
        }
        if (numero instanceof long l) {
            // Se realiza widening automático de int (42) a long.
            System.out.println("Es representable como long: " + l);
        }
        if (!(numero instanceof double d)) {
            // La conversión de int a double mediante pattern matching no aplica si no es el tipo exacto
            // o no cumple la conversión segura en este contexto.
            System.out.println("No es un double primitivo directo");
        }

        // switch con tipos de envoltura y primitivos
        Object valor = 3.14f;
        switch (valor) {
            case Integer i  -> System.out.println("Integer: " + i);
            case Float f    -> System.out.println("Float: " + f);
            case Double d   -> System.out.println("Double: " + d);
            case String s   -> System.out.println("String: " + s);
            default         -> System.out.println("Otro tipo");
        }

        // 8. Switch usando patrones de tipo primitivo con filtros 'when' (Java 25 - JEP 507)
        // Antes de Java 25, un switch sobre un tipo primitivo (como 'int') solo permitía comparar
        // contra valores constantes exactos (ej. case 1, case 2). No se podían evaluar rangos numéricos.
        // A partir de Java 25:
        // - 'case int n' actúa como un patrón de tipo primitivo que captura cualquier valor entero
        //   y lo asigna a la variable local 'n'.
        // - Al combinarse con 'when', podemos evaluar condiciones booleanas complejas (rangos) sobre 'n'.
        // - El orden de los cases es fundamental, ya que se evalúan de arriba a abajo.
        // - Es necesario un 'default' (o un case int n sin 'when' al final) para garantizar la exhaustividad del switch.
        int nivel = 7;
        String categoria = switch (nivel) {
            case int n when n < 0  -> "negativo"; // Captura 'nivel' en 'n' si es menor que 0
            case int n when n == 0 -> "cero";     // Captura 'nivel' en 'n' si es exactamente 0
            case int n when n < 5  -> "bajo";     // Captura 'nivel' en 'n' si está entre 1 y 4 (por descarte anterior)
            case int n when n < 10 -> "medio";    // Captura 'nivel' en 'n' si está entre 5 y 9 (por descarte anterior)
            default                -> "alto";     // Se ejecuta para cualquier otro caso (nivel >= 10)
        };
        System.out.println("Nivel " + nivel + " → " + categoria);
    }
}

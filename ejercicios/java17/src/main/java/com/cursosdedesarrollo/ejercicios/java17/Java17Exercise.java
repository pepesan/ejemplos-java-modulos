package com.cursosdedesarrollo.ejercicios.java17;

public class Java17Exercise {

    // Tarea 1: Definir un Record sencillo para almacenar datos de un Vehículo.
    public record Vehiculo(String marca, String modelo, String tipoMotor) {}

    // Tarea 2: Definir una jerarquía sellada de Figuras Geométricas.
    public sealed interface Figura permits Circulo, Rectangulo {}

    public record Circulo(double radio) implements Figura {}

    public record Rectangulo(double base, double altura) implements Figura {}

    /**
     * Tarea 3: Utilizar Switch Expressions exhaustivas sobre la jerarquía de figuras selladas.
     * Retorna una descripción o el área en texto.
     */
    public static String describirFigura(Figura figura) {
        return switch (figura) {
            case Circulo c -> "Círculo de radio " + c.radio();
            case Rectangulo r -> "Rectángulo de " + r.base() + "x" + r.altura();
        };
    }

    /**
     * Tarea 4: Utilizar Pattern Matching con 'instanceof' para extraer y formatear datos de un objeto genérico.
     */
    public static String formatearObjeto(Object obj) {
        if (obj instanceof String s) {
            return "Texto de longitud " + s.length() + ": " + s;
        } else if (obj instanceof Integer i) {
            return "Entero con valor al cuadrado: " + (i * i);
        } else if (obj instanceof Vehiculo v) {
            return "Vehículo " + v.marca() + " " + v.modelo();
        }
        return "Objeto no soportado";
    }
}

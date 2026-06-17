package com.cursosdedesarrollo.colecciones;

/**
 * Clase Persona (representada como Record) para servir como modelo en los ejemplos de colecciones.
 */
public record Persona(String nombre, int edad) {
    @Override
    public String toString() {
        return nombre + " (" + edad + " años)";
    }
}

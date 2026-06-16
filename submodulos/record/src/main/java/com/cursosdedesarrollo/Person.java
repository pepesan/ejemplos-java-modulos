package com.cursosdedesarrollo;

/**
 * El ejemplo de Record más simple posible (Java 16 - JEP 395).
 * - Es implícitamente final y sus campos son privados e inmutables (final).
 * - Genera automáticamente el constructor canónico, getters (name(), gender(), age()),
 *   y los métodos equals(), hashCode() y toString().
 */
public record Person(String name, String gender, int age) {}

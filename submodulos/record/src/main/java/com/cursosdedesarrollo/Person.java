package com.cursosdedesarrollo;

/**
 * El ejemplo de Record más simple posible (Java 16 - JEP 395).
 * - Se considera automáticamente final (es implícitamente final y no se puede heredar/extender).
 * - Sus campos son privados e inmutables (final).
 * - Genera automáticamente el constructor canónico, getters (name(), gender(), age()),
 *   y los métodos equals(), hashCode() y toString().
 */
public record Person(String name, String gender, int age) {}

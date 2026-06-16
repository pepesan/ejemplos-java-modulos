package com.cursosdedesarrollo;

/**
 * Ejemplo intermedio de Record que demuestra:
 * - Implementación de interfaces (Comparable).
 * - Constructor compacto para validación.
 * - Métodos de instancia adicionales.
 */
public record Student(String name, int age) implements Comparable<Student> {

    /**
     * Constructor compacto: usado para validación antes de asignar los campos.
     */
    public Student {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (age < 0) {
            throw new IllegalArgumentException("La edad no puede ser negativa");
        }
    }

    // Método de instancia adicional
    public boolean isAdult() {
        return this.age >= 18;
    }

    // Implementación de compareTo para poder ordenar colecciones de Students
    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.age, other.age);
    }
}

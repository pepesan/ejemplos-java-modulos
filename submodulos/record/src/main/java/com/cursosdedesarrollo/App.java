package com.cursosdedesarrollo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App {
    public static void main(String[] args) {
        // Java 16 - Uso básico de Records (JEP 395)
        System.out.println("--- 1. Uso básico y sencillo de Records (Person) ---");
        Person person = new Person("Jenny", "Female", 35);
        
        // Los métodos de acceso (getters) llevan el mismo nombre que el componente, sin prefijo "get"
        System.out.println("Nombre: " + person.name());
        System.out.println("Género: " + person.gender());
        System.out.println("Edad: " + person.age());
        
        // toString(), equals() y hashCode() se generan automáticamente de forma legible
        System.out.println("ToString automático: " + person);

        System.out.println("\n--- 2. Record intermedio con validaciones e interfaz (Student) ---");
        Student estudiante = new Student("Alex", 20);
        System.out.println("Estudiante: " + estudiante);
        System.out.println("¿Es mayor de edad? " + estudiante.isAdult());

        // Validación en el constructor compacto de Student
        try {
            new Student("", -5);
        } catch (IllegalArgumentException e) {
            System.out.println("Validación capturada correctamente: " + e.getMessage());
        }

        // Demostración de implementación de Comparable
        List<Student> estudiantes = new ArrayList<>();
        estudiantes.add(new Student("Jenny", 35));
        estudiantes.add(new Student("Alex", 20));
        estudiantes.add(new Student("Chris", 42));

        System.out.println("Antes de ordenar por edad: " + estudiantes);
        Collections.sort(estudiantes); // Usa el compareTo implementado en Student
        System.out.println("Después de ordenar por edad: " + estudiantes);

        System.out.println("\n--- 3. Record complejo con lógica avanzada (Rectangle) ---");
        // Inicialización con el constructor canónico con validación
        Rectangle rectangle = new Rectangle(5.0, 10.0);
        System.out.println("Rectángulo: " + rectangle);
        System.out.println("Área: " + rectangle.area());
        
        // Método de acceso personalizado (imprime log por consola)
        System.out.println("Largo: " + rectangle.length());

        // Inicialización con el constructor secundario sin parámetros (valores por defecto)
        Rectangle rectDefecto = new Rectangle();
        System.out.println("Rectángulo por defecto: " + rectDefecto);

        // Inicialización usando un método factoría estático
        Rectangle rectAureo = Rectangle.createGoldenRectangle(3.0);
        System.out.println("Rectángulo Áureo: " + rectAureo);

        System.out.println("\n--- 4. Pattern Matching con Record Patterns (Java 20/21 - JEP 440) ---");
        // Pattern Matching tradicional con instanceof
        if (person instanceof Person p) {
            System.out.println("instanceof tradicional: " + p.name());
        }

        // Record Patterns: Permite deconstruir los componentes del record directamente en la comprobación
        if (person instanceof Person(String name, String gender, int age)) {
            System.out.println("Record Pattern (Deconstruido directamente en instanceof):");
            System.out.println("  Nombre extraído: " + name);
            System.out.println("  Género extraído: " + gender);
            System.out.println("  Edad extraída: " + age);
        }
    }
}

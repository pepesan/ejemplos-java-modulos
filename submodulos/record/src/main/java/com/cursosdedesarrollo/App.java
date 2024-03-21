package com.cursosdedesarrollo;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // JAVA 16
        System.out.println( "Record Classes" );
        Person person = new Person("Jenny", "Female", 35);
        System.out.println(person.name());
        System.out.println(person.gender());
        System.out.println(person.age());
        System.out.println(person);

        // init con constructora con parámetros
        Rectangle rectangle = new Rectangle(1.0, 2.0);
        System.out.println(rectangle);
        System.out.println(rectangle.length());
        System.out.println(rectangle.width());
        // init con constructora sin parámetros
        rectangle = new Rectangle();
        System.out.println(rectangle);
        // llamada a métod estático
        rectangle = Rectangle.createGoldenRectangle(2.0);
        System.out.println(rectangle);
        // llamada a contructora con verificaciones
        try {
            rectangle = new Rectangle(-1.0, -2.0);
        }catch (IllegalArgumentException e){
            System.out.println("Los parámetros son incorrectos");
        }

        // Java 20
        person = new Person("Jenny", "Female", 35);
        // Mejora la comparación de objetos de tipo Record
        // comprobamos que la variable es el tipo
        // asignamos su valor a otra referencia
        if (person instanceof Person personObject) {
            System.out.println("Es una persona");
            System.out.println(personObject);
        }
        Person person2 = new Person("Jenny", "Female", 35);
        // múltiples condiciones
        if (person instanceof Person personObject || person2 instanceof Person) {
            System.out.println("Es una persona");
            System.out.println(person);
        }

    }
}

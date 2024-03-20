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

        Rectangle rectangle = new Rectangle(1.0, 2.0);
        System.out.println(rectangle);
        System.out.println(rectangle.length());
        System.out.println(rectangle.width());
        rectangle = new Rectangle();
        System.out.println(rectangle);

        rectangle = Rectangle.createGoldenRectangle(2.0);
        System.out.println(rectangle);

        // Java 20
        // Mejora la comporación de objetos de tipo Record
        if (person instanceof Person personObject) {
            System.out.println("Es una persona");
            System.out.println(personObject);
        }
        // múltiples condiciones
        if (person instanceof Person personObject || person instanceof Person) {
            System.out.println("Es una persona");
            System.out.println(person);
        }

    }
}

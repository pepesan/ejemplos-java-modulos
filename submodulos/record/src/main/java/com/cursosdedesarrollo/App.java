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
    }
}

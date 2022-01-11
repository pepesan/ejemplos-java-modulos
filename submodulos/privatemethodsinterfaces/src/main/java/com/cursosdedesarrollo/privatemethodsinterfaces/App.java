package com.cursosdedesarrollo.privatemethodsinterfaces;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Ejemplos de interfaces con métodos privados!" );
        Foo customFoo = new CustomFoo();
        customFoo.bar();
        Foo.buzz();
    }
}

package com.cursosdedesarrollo.privatemethodsinterfaces;

/**
 * Hello world!
 *
 */
public class AppInterfaces
{
    public static void main( String[] args )
    {
        System.out.println( "Ejemplos de interfaces con métodos privados!" );
        Foo customFoo = new CustomFoo();
        customFoo.bar();
        System.out.println(customFoo.cadena);
        // no puedo modifica el "atributo" del interfaz
        // sólo pueden ser final
        //customFoo.cadena = "Algo";
        Foo.buzz();
        // no puedo es privado
        // customFoo.baz();
        customFoo.procesaAlgo();
    }
}

package com.cursosdedesarrollo.colecciones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Cambios de colecciones" );

        List<Integer> lIntegers = List.of(2, 6, 7, 10);
        System.out.println(lIntegers);
        // nuevo método of
        Set<Integer> integers = Set.of(2, 6, 7, 10);
        System.out.println(integers);
        // no se puede modificar porque es inmutable
        //integers.add(12);
        //System.out.println(integers);
        // si inicializamos el objeto con los valores
        // que nos devuelve el of podría ser mutable
        List<Integer> integersList = new ArrayList<>(List.of(2, 6, 7, 10));
        System.out.println(integersList);
        integersList.add(12);
        System.out.println(integersList);

        // nuevo método toArray
        List<String> list = List.of("apple", "banana", "orange");
        String[] array = list.toArray(String[]::new);
        System.out.println(Arrays.toString(array));
        // nuevo método copyOf
        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(2);
        List<Integer> integers2 = List.copyOf(list2);
        System.out.println(integers2);

    }
}

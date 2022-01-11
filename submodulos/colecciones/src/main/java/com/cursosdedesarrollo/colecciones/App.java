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
        // nuevo método of
        Set<Integer> integers = Set.of(2, 6, 7, 10);
        System.out.println(integers);
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

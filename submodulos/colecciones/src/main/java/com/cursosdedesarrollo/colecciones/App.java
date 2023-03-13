package com.cursosdedesarrollo.colecciones;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        // Java 12
        // uniendo colecciones con un merger
        Collector<CharSequence, ?, String> joiningCollector = Collectors.joining("-");
        Collector<String, ?, List<String>> listCollector = Collectors.toList();
        //returns joined string and individual strings as array
        Collector<String, ?, String[]> compositeCollector = Collectors.teeing(joiningCollector, listCollector,
                (joinedString, strings) -> {
                    ArrayList<String> list3 = new ArrayList<>(strings);
                    list3.add(joinedString);
                    String[] array2 = list3.toArray(new String[0]);
                    return array2;
                });

        String[] strings = Stream.of("Apple", "Banana", "Orange").collect(compositeCollector);
        System.out.println(Arrays.toString(strings));


    }
}

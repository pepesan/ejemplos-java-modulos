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
        System.out.println(list2);
        // Copy of crea una colección inmutable desde una colección mutable
        List<Integer> integers2 = List.copyOf(list2);
        System.out.println(integers2);
        // Podemos modificar la original pero no afecta a la generada inmutable
        list2.set(1, 4);
        System.out.println(list2);
        System.out.println(integers2);



        // Java 12
        //teeing
        //  tenemos una lista de números enteros
        //  y queremos calcular la suma total
        //  y la cantidad de números que son mayores que 10
        //  de una sola tacada
        List<Integer> numeros = Arrays.asList(4, 12, 6, 8, 15, 17, 20);

        int suma = numeros.stream()
                .collect(Collectors.teeing(
                        // Primero, utilizamos el colector "summingInt" para calcular la suma total de los números
                        Collectors.summingInt(Integer::intValue),
                        // Luego, utilizamos el colector "filtering" para filtrar los números que son mayores que 10
                        // y contamos cuántos hay utilizando el colector "counting"
                        Collectors.filtering(n -> n > 10, Collectors.counting()),
                        // utilizamos una función lambda para devolver
                        // la suma total como resultado de la operación "teeing"
                        (total, mayoresDe10) -> total
                ));

        System.out.println("La suma total es: " + suma);
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

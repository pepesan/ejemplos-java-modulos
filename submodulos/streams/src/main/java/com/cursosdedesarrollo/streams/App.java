package com.cursosdedesarrollo.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Ejemplos de Streams!" );
        // takeWhile
        System.out.println("takeWhile");
        String[] fruits = {"apple", "banana", "orange", "mango", "peach"};
        Stream<String> stream = Arrays.stream(fruits).takeWhile(s -> !"orange".equals(s));
        stream.forEach(System.out::println);
        // dropWhile
        System.out.println("dropWhile");
        stream = Arrays.stream(fruits).dropWhile(s -> !"orange".equals(s));
        stream.forEach(System.out::println);
        // iterate
        System.out.println("iterate");
        Stream<String> iterate = Stream.iterate("-",
                (s) -> s.length() < 5,
                s -> {
                    // más sentencias
                    return s + "-";
                });
        iterate.forEach(System.out::println);
        System.out.println("iterate numeros");
        Stream.iterate(1, n -> n < 20 , n -> n * 2)
                .forEach(System.out::println);
        // ofNullable
        System.out.println("ofNullable con dato");
        String nullableItem = "peach";
        stream = Stream.of("apple", "banana", "orange");
        Stream<String> stream2 = Stream.concat(stream, Stream.ofNullable(nullableItem));
        stream2.forEach(System.out::println);
        // o en el caso de que sea null
        System.out.println("ofNullable con null");
        nullableItem = null;
        stream = Stream.of("apple", "banana", "orange");
        stream2 = Stream.concat(stream, Stream.ofNullable(nullableItem));
        stream2.forEach(System.out::println);
        // IntStream, LongStream, DoubleStream
        System.out.println("IntStream");
        IntStream.of(2, 4, 6, 8, 9, 10, 11)
                .takeWhile(i -> i % 2 == 0)
                .forEach(System.out::println);
        // Clase Collectors
        // filtering
        System.out.println("filtering");
        List<Integer> list = IntStream.of(2, 4, 6, 8, 10, 12)
                .boxed()
                .collect(Collectors.filtering(i -> i % 4 == 0,
                        Collectors.toList()));
        System.out.println(list);
        // flatMapping
        System.out.println("flatMapping");
        list = Stream.of(List.of(1, 2, 3, 4), List.of(5, 6, 7, 8))
                .collect(Collectors.flatMapping(
                        l -> l.stream()
                                .filter(i -> i % 2 == 0),
                        Collectors.toList()
                ));
        System.out.println(list);
        // otro ejemplo de flatMapping
        Map<Integer, List<Integer>> map =
                Stream.of(List.of(1, 2, 3, 4, 5, 6),
                                List.of(7, 8, 9, 10))
                        .collect(
                                Collectors.groupingBy(Collection::size,
                                        Collectors.flatMapping(
                                                l -> l.stream()
                                                        .filter(i -> i % 2 == 0),
                                                Collectors.toList()
                                        )));
        System.out.println(map);
        // nuevos métodos para Java 10
        // toUnmodifiableList
        list = IntStream.range(1, 5)
                .boxed()
                .collect(Collectors.toUnmodifiableList());
        System.out.println(list);
        //no funciona añadir
        //list.add(7);
        System.out.println(list.getClass().getName());

        System.out.println("Antes del Flatmap");
        String[][] array = new String[][]{{"a", "b"}, {"c", "d"}, {"e", "f"}};
        Stream.of(array).forEach(row -> {
                System.out.println("Filas");
                Stream.of(row).forEach(System.out::println);
        });
        System.out.println("Después del Flatmap");
        var mistream  = Stream.of(array)  // Stream<String[]>
                .flatMap(Stream::of);    // [a, b, c, d, e, f]
        mistream.forEach(System.out::println);
        System.out.println("Uso de Filter");
        // Uso de filter
        List<String> lines = Arrays.asList("uno", "dos", "tres");

        List<String> result = lines.stream()                // convert list to stream
                .filter(line -> !"tres".equals(line))       // we dont like tres
                .collect(Collectors.toList());              // collect the output and convert streams to a List

        result.forEach(System.out::println);                //output : uno , dos
        // Ejemplo de Map
        List<String> alpha = Arrays.asList("a", "b", "c", "d");
        List<String> collect = alpha
                .stream()
                .map(String::toUpperCase)
                /*
                .map( item -> {
                    return item.toUpperCase();
                })
                 */
                // .map( item -> item.toUpperCase())
                .collect(Collectors.toList());
        System.out.println(collect); //[A, B, C, D]

        // Ejemplo de reduce


        List<Integer> gastos= new ArrayList<Integer>();
        gastos.add(100);
        gastos.add(200);
        gastos.add(300);

        var valor = gastos.stream().reduce(0,(acumulador,numero)-> {
            return acumulador+numero;
        });
        valor = gastos.stream().reduce(0, Integer::sum);
        System.out.println("Valor del reduce: "+ valor);

        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        int max = Arrays.stream(numbers).reduce(0, (a, b) -> a > b ? a : b);  // 10
        int max1 = Arrays.stream(numbers).reduce(0, Integer::max);            // 10
        System.out.println("Valor del max: "+ max);

        // Java 25
        // Ejemplo: agrupar en ventanas de 3 elementos
        var ventanas = Stream.of(1,2,3,4,5,6,7,8,9)
                .gather(Gatherers.windowFixed(3))
                .toList();

        System.out.println(ventanas);
        // [[1,2,3], [4,5,6], [7,8,9]]


    }
}

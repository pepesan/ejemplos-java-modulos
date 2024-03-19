package com.cursosdedesarrollo.streams;

import java.util.*;
import java.util.stream.Collectors;
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
        System.out.println( "Map" );
        //map
        String[] fruits = {"apple", "banana", "orange", "mango", "peach"};
        Arrays
                // Definimos el stream
                .stream(fruits)
                // llamadas a lambdas de modificación del listado
                // modificación dato a dato
                .map(fruta -> fruta+"!")
                // presentar los datos o devolverlos
                .forEach(System.out::println);
        // filter
        System.out.println( "Filter" );
        Arrays
                // Definimos el stream
                .stream(fruits)
                // llamadas a lambdas de modificación del listado
                // devolver sólo aquellos elementos que cumplan con la condición
                .filter(fruta -> fruta.charAt(0)=='a')
                // presentar los datos o devolverlos
                .forEach(System.out::println);
        // map + filter
        System.out.println( "Map + Filter" );
        Arrays
                // Definimos el stream
                .stream(fruits)
                // llamadas a lambdas de modificación del listado
                // modificación dato a dato
                .map(fruta -> fruta+"!")
                // devolver sólo aquellos elementos que cumplan con la condición
                .filter(fruta -> fruta.charAt(0)=='a')
                // presentar los datos o devolverlos
                .forEach(System.out::println);
        // Collect o Convertir el stream a por ejemplo listado
        System.out.println( "Map + Filter -> Collect List" );
        List<String> retorno = Arrays
                // Definimos el stream
                .stream(fruits)
                // llamadas a lambdas de modificación del listado
                // modificación dato a dato
                .map(fruta -> fruta+"!")
                // devolver sólo aquellos elementos que cumplan con la condición
                .filter(fruta -> fruta.charAt(0)=='a')
                // presentar los datos o devolverlos
                .collect(Collectors.toList());
        System.out.println(retorno);
        // distinct
        System.out.println( "Distinct" );
        List<Integer> numerosRepetidos = Arrays.asList(1, 2, 3, 1, 2, 4);

        List<Integer> numerosSinRepetir = numerosRepetidos.stream().distinct().collect(Collectors.toList());
                // "Juan, Ana, Pedro, María"
        System.out.println(numerosSinRepetir);
        // Sorted
        System.out.println( "Sorted" );
        List<Persona> personas = Arrays.asList(
                new Persona("Juan", 20),
                new Persona("Ana", 30),
                new Persona("Pedro", 18)
        );
        List<Persona> personasOrdenadasPorEdad = personas
                .stream()
                // ordenando en base a un criterio
                .sorted(Comparator.comparing(Persona::getEdad))
                // recolectando
                .collect(Collectors.toList()); // [Pedro, Juan, Ana]
        System.out.println(personasOrdenadasPorEdad);
        // Limit y skip
        List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println( "Limit" );
        List<Integer> primeros5Numeros = numeros
                .stream()
                .limit(5)
                .collect(Collectors.toList()); // [1, 2, 3, 4, 5]
        System.out.println( primeros5Numeros );
        System.out.println( "Skip" );
        List<Integer> ultimos5Numeros = numeros
                .stream()
                .skip(5)
                .collect(Collectors.toList()); // [6, 7, 8, 9, 10]
        System.out.println( ultimos5Numeros );
        // findFirst()
        System.out.println( "findFirst" );
        personas = Arrays.asList(
                new Persona("Juan", 20),
                new Persona("Ana", 30),
                new Persona("Pedro", 18)
        );
        Optional<Persona> primerMenorDeEdad = personas
                .stream()
                .filter(p -> p.getEdad() < 18)
                .findFirst();
        // Persona{nombre='Pedro', edad=18}
        System.out.println( primerMenorDeEdad );
        // count
        System.out.println( "Count" );
        List<String> nombres = Arrays.asList("Juan", "Ana", "Pedro", "María");

        long cantidadNombres = nombres.stream().count(); // 4
        System.out.println( cantidadNombres );
        // takeWhile
        System.out.println("takeWhile");
        String[] fruits2 = {"apple", "banana", "orange", "mango", "peach"};
        Stream<String> stream = Arrays.stream(fruits2)
                .takeWhile(s -> !"orange".equals(s));
        stream.forEach(System.out::println);
        // dropWhile
        System.out.println("dropWhile");
        stream = Arrays.stream(fruits).dropWhile(s -> !"orange".equals(s));
        stream.forEach(System.out::println);
        // iterate
        System.out.println("iterate");
        Stream<String> iterate = Stream
                .iterate(
                        // init for
                        "-",
                        //condicion del for
                        s -> s.length() < 5,
                        // acción del for
                        s -> s + "-");
        iterate.forEach(System.out::println);
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
        // Stream<List<Integer>>
        list = Stream.of(List.of(1, 2, 3, 4), List.of(5, 6, 7, 8))
                .collect(Collectors.flatMapping(
                        // Stream<List<Integer>> -> Stream<Integer>
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
        System.out.println(list.getClass().getName());
    }
}

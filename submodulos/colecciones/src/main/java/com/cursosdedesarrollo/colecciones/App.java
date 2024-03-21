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
        try {
            integers.add(12);
        }catch(UnsupportedOperationException e){
            System.out.println("Es una variable inmutable");
        }
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
                        (total, mayoresDe10) -> (total+Integer.parseInt(""+mayoresDe10))
                ));
        System.out.println("teeing");
        System.out.println("La suma total es: " + suma +" = 82 suma y 4 de count");
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
        // Java 21
        // Colleciones Secuenciadas
        List<String> cadenas = List.of("hola", "mundo");
        // pilla el último
        System.out.println(cadenas.getLast());
        // pilla el primero
        System.out.println(cadenas.getFirst());
        // también tenemos en listados addFirst, addLast,
        // removeFirst, removeLast y reversed
        // En el caso de los Set secuenciados
        // addFirst y addLast si el elemento ya está
        // presente se moverá al principio o al final
        // Crear un SequencedSet
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>(List.of(1, 2, 3));

        Integer firstElement = linkedHashSet.getFirst();   // 1
        Integer lastElement = linkedHashSet.getLast();    // 3

        linkedHashSet.addFirst(0);  //Set contiene: [0, 1, 2, 3]
        linkedHashSet.addLast(4);   //Set contiene: [0, 1, 2, 3, 4]

        System.out.println(linkedHashSet.reversed());   //Imprime [4, 3, 2, 1, 0]
        // En el caso de los Map tenemos más mejoras
        // firstEntry, lastEntry, pollFirstEntry,
        // pollLastEntry, putFirst y putLast
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();

        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");

        map.firstEntry();   //1=One
        map.lastEntry();    //3=Three

        System.out.println(map);  //{1=One, 2=Two, 3=Three}

        Map.Entry<Integer, String> first = map.pollFirstEntry();   //1=One
        Map.Entry<Integer, String> last = map.pollLastEntry();    //3=Three

        System.out.println(map);  //{2=Two}

        map.putFirst(1, "One");     //{1=One, 2=Two}
        map.putLast(3, "Three");    //{1=One, 2=Two, 3=Three}

        System.out.println(map);  //{1=One, 2=Two, 3=Three}
        System.out.println(map.reversed());   //{3=Three, 2=Two, 1=One}
        // más info en https://www.happycoders.eu/java/java-21-features/

    }
}

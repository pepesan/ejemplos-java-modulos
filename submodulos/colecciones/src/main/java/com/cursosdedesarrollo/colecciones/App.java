package com.cursosdedesarrollo.colecciones;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Ejemplos de mejoras en la API de Colecciones en diferentes versiones de Java.
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Cambios de colecciones" );

        // ================================================================
        // Java 9 — Métodos de factoría of() (JEP 269)
        // ================================================================
        System.out.println("\n--- Java 9: List.of y Set.of ---");
        List<Integer> lIntegers = List.of(2, 6, 7, 10);
        System.out.println(lIntegers);
        
        Set<Integer> integers = Set.of(2, 6, 7, 10);
        System.out.println(integers);
        
        // No se puede modificar porque es inmutable
        try {
            integers.add(12);
        } catch(UnsupportedOperationException e){
            System.out.println("Es una variable inmutable");
        }
        
        // Si inicializamos el objeto con los valores que nos devuelve of() podemos hacerlo mutable
        List<Integer> integersList = new ArrayList<>(List.of(2, 6, 7, 10));
        System.out.println(integersList);
        integersList.add(12);
        System.out.println(integersList);

        // ================================================================
        // Java 11 — Método toArray(IntFunction)
        // Permite convertir una colección a Array sin casting manual
        // ================================================================
        System.out.println("\n--- Java 11: toArray(IntFunction) ---");
        List<String> list = List.of("apple", "banana", "orange");
        String[] array = list.toArray(String[]::new);
        System.out.println(Arrays.toString(array));

        // ================================================================
        // Java 10 — Método copyOf() para List, Set y Map
        // Crea una copia inmutable a partir de una colección
        // ================================================================
        System.out.println("\n--- Java 10: List.copyOf ---");
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

        // ================================================================
        // Java 9 — Map.of (hasta 10 pares clave-valor) (JEP 269)
        // ================================================================
        System.out.println("\n--- Java 9: Map.of ---");
        Map<String, Integer> codigosHttp = Map.of(
                "OK",                   200,
                "Created",              201,
                "Bad Request",          400,
                "Unauthorized",         401,
                "Not Found",            404,
                "Internal Server Error",500
        );
        System.out.println(codigosHttp);

        // Intento de modificación: lanza UnsupportedOperationException
        try {
            codigosHttp.put("Conflict", 409);
        } catch (UnsupportedOperationException e) {
            System.out.println("Map.of es inmutable: " + e.getClass().getSimpleName());
        }

        // ================================================================
        // Java 9 — Map.ofEntries y Map.entry (más de 10 pares) (JEP 269)
        // ================================================================
        System.out.println("\n--- Java 9: Map.ofEntries ---");
        Map<String, String> capitales = Map.ofEntries(
                Map.entry("España",     "Madrid"),
                Map.entry("Francia",    "París"),
                Map.entry("Alemania",   "Berlín"),
                Map.entry("Italia",     "Roma"),
                Map.entry("Portugal",   "Lisboa"),
                Map.entry("Países Bajos","Ámsterdam"),
                Map.entry("Bélgica",    "Bruselas"),
                Map.entry("Austria",    "Viena"),
                Map.entry("Suecia",     "Estocolmo"),
                Map.entry("Polonia",    "Varsovia"),
                Map.entry("Grecia",     "Atenas")
        );
        System.out.println("Número de entradas: " + capitales.size());
        System.out.println("Capital de España: " + capitales.get("España"));

        // ================================================================
        // Java 10 — Map.copyOf()
        // Crea una copia inmutable a partir de un mapa mutable
        // ================================================================
        System.out.println("\n--- Java 10: Map.copyOf ---");
        Map<String, Integer> mutableMap = new HashMap<>();
        mutableMap.put("a", 1);
        mutableMap.put("b", 2);
        Map<String, Integer> immutableCopy = Map.copyOf(mutableMap);
        mutableMap.put("c", 3);
        System.out.println("Original modificado: " + mutableMap);
        System.out.println("Copia inmutable sin cambios: " + immutableCopy);

        // ================================================================
        // Java 12 — Collectors.teeing() (JEP 344)
        // Redirige la entrada del stream a dos collectors y combina sus resultados
        // ================================================================
        System.out.println("\n--- Java 12: Collectors.teeing ---");
        List<Integer> numeros = Arrays.asList(4, 12, 6, 8, 15, 17, 20);

        int suma = numeros.stream()
                .collect(Collectors.teeing(
                        // Colector 1: suma total de los números
                        Collectors.summingInt(Integer::intValue),
                        // Colector 2: filtra los números > 10 y los cuenta
                        Collectors.filtering(n -> n > 10, Collectors.counting()),
                        // Función de fusión (merger)
                        (total, mayoresDe10) -> (total+Integer.parseInt(""+mayoresDe10))
                ));
        System.out.println("La suma total es: " + suma +" = 82 suma y 4 de count");
        
        // Ejemplo 2 de teeing: Uniendo string resultado y lista original en un array
        Collector<CharSequence, ?, String> joiningCollector = Collectors.joining("-");
        Collector<String, ?, List<String>> listCollector = Collectors.toList();
        
        Collector<String, ?, String[]> compositeCollector = Collectors.teeing(joiningCollector, listCollector,
                (joinedString, stringsList2) -> {
                    ArrayList<String> list3 = new ArrayList<>(stringsList2);
                    list3.add(joinedString);
                    return list3.toArray(new String[0]);
                });
        String[] strings = Stream.of("Apple", "Banana", "Orange").collect(compositeCollector);
        System.out.println(Arrays.toString(strings));

        // ================================================================
        // Java 21 — Colecciones Secuenciadas (JEP 431)
        // Proporciona métodos uniformes para acceder al primer y último elemento
        // y para procesar las colecciones en orden inverso.
        // ================================================================
        System.out.println("\n--- Java 21: Colecciones Secuenciadas ---");
        List<String> cadenas = List.of("hola", "mundo");
        System.out.println("getLast(): " + cadenas.getLast());
        System.out.println("getFirst(): " + cadenas.getFirst());

        // SequencedSet (LinkedHashSet conserva orden de inserción)
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>(List.of(1, 2, 3));

        Integer firstElement = linkedHashSet.getFirst();   // 1
        Integer lastElement = linkedHashSet.getLast();    // 3

        linkedHashSet.addFirst(0);  // Contenido: [0, 1, 2, 3]
        linkedHashSet.addLast(4);   // Contenido: [0, 1, 2, 3, 4]

        System.out.println("reversed(): " + linkedHashSet.reversed());

        // SequencedMap (LinkedHashMap conserva orden de inserción)
        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(3, "Three");

        System.out.println("firstEntry(): " + map.firstEntry());   // 1=One
        System.out.println("lastEntry(): " + map.lastEntry());     // 3=Three
        System.out.println("Map actual: " + map);

        Map.Entry<Integer, String> first = map.pollFirstEntry();   // extrae 1=One
        Map.Entry<Integer, String> last = map.pollLastEntry();    // extrae 3=Three

        System.out.println("Map tras poll: " + map);  // {2=Two}

        map.putFirst(1, "One");     // Inserta al principio: {1=One, 2=Two}
        map.putLast(3, "Three");    // Inserta al final: {1=One, 2=Two, 3=Three}

        System.out.println("Map reconstruido: " + map);
        System.out.println("Map reversed(): " + map.reversed());
    }
}

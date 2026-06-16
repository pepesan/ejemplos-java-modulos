package com.cursosdedesarrollo.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

/**
 * Guía práctica progresiva de Java Streams (Nivel: Básico a Intermedio).
 * Muestra el camino desde los conceptos básicos de Java 8 hasta las APIs de Java 24/25.
 */
public class App {

    public static void main(String[] args) {
        System.out.println("==========================================================");
        System.out.println("   PROGRESIÓN DE STREAMS: DESDE LO BÁSICO A LO AVANZADO   ");
        System.out.println("==========================================================\n");

        nivel1Creacion();
        nivel2FiltradoYTransformacion();
        nivel3OperacionesTerminales();
        nivel4ReduccionYAgregacion();
        nivel5FlatMap();
        nivel6MejorasJava9();
        nivel7TeeingJava12();
        nivel8GatherersJava24();
    }

    // ====================================================================
    // Nivel 1: Creación de Streams
    // ====================================================================
    private static void nivel1Creacion() {
        System.out.println("--- Nivel 1: Creación de Streams ---");

        // 1.1 Desde una Lista
        List<String> lenguajes = List.of("Java", "Python", "C++", "Rust");
        Stream<String> streamDesdeLista = lenguajes.stream();
        System.out.println("Stream creado desde Lista.");

        // 1.2 Desde valores directos
        Stream<Integer> streamDirecto = Stream.of(1, 2, 3, 4, 5);
        System.out.println("Stream creado directamente con Stream.of().");

        // 1.3 Desde un Array
        String[] frameworkArray = {"Spring", "Quarkus", "Micronaut"};
        Stream<String> streamDesdeArray = Arrays.stream(frameworkArray);
        System.out.println("Stream creado desde Array con Arrays.stream().\n");
    }

    // ====================================================================
    // Nivel 2: Operaciones Intermedias Clásicas (Lazy Evaluation)
    // ====================================================================
    private static void nivel2FiltradoYTransformacion() {
        System.out.println("--- Nivel 2: Operaciones Intermedias (Filtrado y Mapeo) ---");

        List<String> nombres = List.of("Alberto", "Ana", "Carlos", "Beatriz", "David", "Amalia");

        // filter(): Filtra elementos basados en un predicado boolean
        // map(): Transforma cada elemento aplicando una función
        System.out.println("Nombres que empiezan con 'A', convertidos a mayúsculas (definición sin ejecutar):");
        Stream<String> flujoProcesado = nombres.stream()
                .filter(n -> n.startsWith("A"))
                .map(String::toUpperCase);

        // Los streams son perezosos (lazy); no hacen nada hasta que se llama a una operación terminal.
        System.out.println("Ejecutando operación terminal (forEach):");
        flujoProcesado.forEach(System.out::println); // ALBERTO, ANA, AMALIA
        System.out.println();
    }

    // ====================================================================
    // Nivel 3: Operaciones Terminales Comunes (Eager Evaluation)
    // ====================================================================
    private static void nivel3OperacionesTerminales() {
        System.out.println("--- Nivel 3: Operaciones Terminales Comunes ---");

        List<String> frutas = List.of("manzana", "platano", "pera", "kiwi", "platano");

        // 3.1 Recolección clásica (Java 8): collect(Collectors.toList())
        List<String> listaClasica = frutas.stream()
                .filter(f -> f.length() > 4)
                .collect(Collectors.toList());
        System.out.println("Lista recolectada clásicamente (mutable): " + listaClasica);

        // 3.2 Recolección moderna (Java 16+): toList() directo
        List<String> listaModerna = frutas.stream()
                .filter(f -> f.length() > 4)
                .toList(); // Devuelve una lista inmutable
        System.out.println("Lista recolectada de forma moderna (inmutable): " + listaModerna);

        // 3.3 Recolección a un Conjunto (Set) para evitar duplicados
        java.util.Set<String> setFrutas = frutas.stream()
                .collect(Collectors.toSet());
        System.out.println("Conjunto sin duplicados: " + setFrutas);

        // 3.4 Unir elementos con un separador
        String cadenaUnida = frutas.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(" - "));
        System.out.println("Cadena concatenada: " + cadenaUnida + "\n");
    }

    // ====================================================================
    // Nivel 4: Reducción y Agregación
    // ====================================================================
    private static void nivel4ReduccionYAgregacion() {
        System.out.println("--- Nivel 4: Reducción y Agregación ---");

        List<Integer> numeros = List.of(5, 10, 15, 20);

        // reduce(): Combina todos los elementos en un único resultado acumulativo
        int sumaTotal = numeros.stream()
                .reduce(0, Integer::sum);
        System.out.println("Suma total reducida: " + sumaTotal);

        // Operaciones de búsqueda rápida
        Optional<Integer> maximo = numeros.stream()
                .max(Integer::compareTo);
        maximo.ifPresent(max -> System.out.println("Valor máximo: " + max));

        long totalElementos = numeros.stream()
                .filter(n -> n > 10)
                .count();
        System.out.println("Cantidad de números mayores a 10: " + totalElementos + "\n");
    }

    // ====================================================================
    // Nivel 5: Transformaciones Complejas (FlatMap)
    // ====================================================================
    private static void nivel5FlatMap() {
        System.out.println("--- Nivel 5: Transformaciones con flatMap (Aplanado) ---");

        List<List<String>> listaDeListas = List.of(
                List.of("A1", "A2"),
                List.of("B1", "B2", "B3"),
                List.of("C1")
        );

        // flatMap(): Convierte un Stream de Streams (o estructuras anidadas) en un único Stream plano
        List<String> listaPlana = listaDeListas.stream()
                .flatMap(List::stream) // Aplana cada lista interna
                .toList();
        System.out.println("Lista de listas original: " + listaDeListas);
        System.out.println("Lista aplanada resultante: " + listaPlana + "\n");
    }

    // ====================================================================
    // Nivel 6: Mejoras Modernas en la API de Streams (Java 9)
    // ====================================================================
    private static void nivel6MejorasJava9() {
        System.out.println("--- Nivel 6: Mejoras Modernas (Java 9) ---");

        // 6.1 takeWhile(): Mantiene los elementos mientras la condición sea verdadera.
        // Se detiene al primer elemento que no la cumpla.
        System.out.println("takeWhile (números menores a 10 en flujo ordenado):");
        List<Integer> numerosOrdenados = List.of(2, 4, 6, 8, 12, 14, 2, 4);
        List<Integer> menoresDeDiez = numerosOrdenados.stream()
                .takeWhile(n -> n < 10)
                .toList();
        System.out.println("Original: " + numerosOrdenados + " -> takeWhile: " + menoresDeDiez);

        // 6.2 dropWhile(): Descarta elementos mientras la condición sea verdadera.
        // Devuelve el resto desde el primer fallo.
        System.out.println("dropWhile (descarta mientras sean menores a 10):");
        List<Integer> mayoresDeDiez = numerosOrdenados.stream()
                .dropWhile(n -> n < 10)
                .toList();
        System.out.println("Original: " + numerosOrdenados + " -> dropWhile: " + mayoresDeDiez);

        // 6.3 Stream.ofNullable(): Crea un Stream seguro. Evita NullPointerException.
        System.out.println("Stream.ofNullable con variables que pueden ser nulas:");
        String elementoNulo = null;
        String elementoValido = "Hola";

        long countNulo = Stream.ofNullable(elementoNulo).count();
        long countValido = Stream.ofNullable(elementoValido).count();
        System.out.println("Cantidad de elementos con nulo: " + countNulo);    // 0
        System.out.println("Cantidad de elementos con válido: " + countValido); // 1

        // 6.4 Stream.iterate con Predicado
        System.out.println("Stream.iterate (generador con parada similar a un bucle for clásico):");
        List<Integer> secuencia = Stream.iterate(1, n -> n < 20, n -> n * 2).toList();
        System.out.println("Secuencia multiplicada x2: " + secuencia + "\n");
    }

    // ====================================================================
    // Nivel 7: Bifurcación de colectores (Java 12)
    // ====================================================================
    private static void nivel7TeeingJava12() {
        System.out.println("--- Nivel 7: Collectors.teeing (Java 12) ---");

        List<Integer> puntuaciones = List.of(90, 85, 100, 75, 95);

        // teeing(): Envía el flujo a dos colectores paralelos y combina sus resultados al final
        String resumen = puntuaciones.stream()
                .collect(Collectors.teeing(
                        Collectors.summingInt(Integer::intValue), // Colector A: Suma
                        Collectors.averagingDouble(Integer::intValue), // Colector B: Promedio
                        (suma, promedio) -> "Suma: " + suma + ", Promedio: " + promedio // Fusión
                ));

        System.out.println("Puntuaciones: " + puntuaciones);
        System.out.println("Resultado de Teeing: " + resumen + "\n");
    }

    // ====================================================================
    // Nivel 8: Recopiladores avanzados e intermedios (Java 24/25 - Gatherers)
    // ====================================================================
    private static void nivel8GatherersJava24() {
        System.out.println("--- Nivel 8: Stream Gatherers (Java 24/25) ---");

        List<Integer> secuenciaNumerica = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        // gather(): Permite aplicar transformaciones intermedias avanzadas no disponibles en la API estándar
        // windowFixed(n): Divide el stream en sublistas no solapadas de tamaño fijo
        List<List<Integer>> ventanasFijas = secuenciaNumerica.stream()
                .gather(Gatherers.windowFixed(3))
                .toList();

        System.out.println("Original: " + secuenciaNumerica);
        System.out.println("Ventanas fijas de 3 elementos: " + ventanasFijas);

        // windowSliding(n): Divide el stream en ventanas deslizantes (solapadas)
        List<List<Integer>> ventanasDeslizantes = secuenciaNumerica.stream()
                .gather(Gatherers.windowSliding(3))
                .toList();
        System.out.println("Ventanas deslizantes de 3 elementos: " + ventanasDeslizantes);
    }
}

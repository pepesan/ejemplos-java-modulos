package com.cursosdedesarrollo.streams;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Gatherers;
import java.util.stream.Stream;

public class EjemplosGatherers {
    public static void main(String[] args) {
        ejemploWindowFixed();
        ejemploWindowSliding();
        ejemploScan();
        ejemploFold();
        ejemploMapConcurrent();
        gathererPersonalizado();
    }

    // ============================================================
    // windowFixed(int n)
    // ------------------------------------------------------------
    // Divide el flujo en "bloques" consecutivos de tamaño fijo n.
    // Cada bloque se emite como una lista inmutable de hasta n elementos.
    // Muy útil para procesamiento por lotes o trocear datos en fragmentos.
    // ============================================================
    private static void ejemploWindowFixed() {
        System.out.println("=== Gatherer: windowFixed(3) ===");
        List<Integer> datos = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        datos.stream()
                .gather(Gatherers.windowFixed(3)) // crea ventanas de 3 elementos
                .forEach(ventana -> System.out.println("Ventana fija: " + ventana));

        // Salida:
        // Ventana fija: [1, 2, 3]
        // Ventana fija: [4, 5, 6]
        // Ventana fija: [7, 8]
        System.out.println();
    }

    // ============================================================
    // windowSliding(int n)
    // ------------------------------------------------------------
    // Crea ventanas deslizantes (superpuestas) de tamaño n.
    // Cada ventana avanza una posición respecto a la anterior.
    // Ideal para medias móviles o detección de patrones contiguos.
    // ============================================================
    private static void ejemploWindowSliding() {
        System.out.println("=== Gatherer: windowSliding(3) ===");
        List<Integer> datos = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        datos.stream()
                .gather(Gatherers.windowSliding(3)) // genera ventanas deslizantes
                .forEach(ventana -> System.out.println("Ventana deslizante: " + ventana));

        // Salida:
        // [1, 2, 3]
        // [2, 3, 4]
        // [3, 4, 5]
        // [4, 5, 6]
        // [5, 6, 7]
        // [6, 7, 8]
        System.out.println();
    }

    // ============================================================
    // scan(Supplier<R>, BiFunction<R,T,R>)
    // ------------------------------------------------------------
    // Acumula los elementos uno a uno y EMITE el acumulador
    // después de cada paso.
    // Es como reduce(), pero no espera al final.
    // Perfecto para sumas parciales, acumulaciones o trazas.
    // ============================================================
    private static void ejemploScan() {
        System.out.println("=== Gatherer: scan() ===");
        List<Integer> datos = List.of(1, 2, 3, 4);

        datos.stream()
                // inicializa en 0 y acumula sumas parciales
                .gather(Gatherers.scan(() -> 0, (acc, n) -> acc + n))
                .forEach(suma -> System.out.println("Suma parcial: " + suma));

        // Salida:
        // Suma parcial: 1
        // Suma parcial: 3
        // Suma parcial: 6
        // Suma parcial: 10
        System.out.println();
    }

    // ============================================================
    // fold(Supplier<R>, BiFunction<R,T,R>)
    // ------------------------------------------------------------
    // Igual que reduce(), pero implementado como gatherer final.
    // Devuelve un único valor acumulado en el flujo resultante.
    // Útil para reducir dentro de una pipeline compleja.
    // ============================================================
    private static void ejemploFold() {
        System.out.println("=== Gatherer: fold() ===");

        Optional<String> resultado =
                Stream.of("A", "B", "C", "D")
                        // inicializa en "" y concatena cada elemento
                        .gather(Gatherers.fold(() -> "", (acc, s) -> acc + s))
                        .findFirst(); // fold produce un solo elemento

        System.out.println("Concatenado: " + resultado.orElse("<vacío>"));
        // Salida: Concatenado: ABCD
        System.out.println();
    }

    // ============================================================
    // mapConcurrent(int maxConcurrency, Function<T,R>)
    // ------------------------------------------------------------
    // Aplica una función de transformación en paralelo,
    // usando hilos virtuales y manteniendo el orden original.
    // Ideal para operaciones I/O o cálculos costosos.
    // ============================================================
    private static void ejemploMapConcurrent() {
        System.out.println("=== Gatherer: mapConcurrent(3) ===");

        List<String> urls = List.of("url1", "url2", "url3", "url4", "url5");

        urls.stream()
                // hasta 3 transformaciones concurrentes a la vez
                .gather(Gatherers.mapConcurrent(3, EjemplosGatherers::descargarSimulado))
                .forEach(System.out::println);

        // Salida (orden preservado, ejecución concurrente simulada):
        // OK -> url1
        // OK -> url2
        // OK -> url3
        // OK -> url4
        // OK -> url5
        System.out.println();
    }

    private static void gathererPersonalizado() {
        Stream<Integer> datos = Stream.of(1, 1, 2, 2, 3, 1, 1, 4, 4, 5);

        // Usamos el gatherer personalizado que quita duplicados consecutivos
        datos.gather(DistinctConsecutiveGatherer.of())
                .forEach(System.out::println);

        // Salida esperada:
        // 1
        // 2
        // 3
        // 1
        // 4
        // 5
    }

    // Método auxiliar: simula un trabajo costoso (ej. descarga de red)
    private static String descargarSimulado(String url) {
        try {
            Thread.sleep(Duration.ofMillis(200)); // simula latencia
        } catch (InterruptedException ignored) {}
        return "OK -> " + url;
    }
}

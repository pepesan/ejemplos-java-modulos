package com.cursosdedesarrollo.structured;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.stream.Stream;

/**
 * Ejemplos de Structured Concurrency (JEP 499 — preview en Java 25).
 *
 * <p>Permite lanzar subtareas concurrentes cuyo ciclo de vida queda acotado
 * por el scope: el bloque try-with-resources no termina hasta que todas las
 * subtareas hayan acabado (o se hayan cancelado).
 *
 * <p>En JDK 25 el API se reescribió: {@code StructuredTaskScope} es ahora una
 * interfaz y se instancia con {@code StructuredTaskScope.open(Joiner)}.
 * Los {@code Joiner} predefinidos reemplazan a las antiguas subclases
 * {@code ShutdownOnFailure} y {@code ShutdownOnSuccess}.
 *
 * <p>Joiners disponibles:
 * <ul>
 *   <li>{@code Joiner.allSuccessfulOrThrow()} — espera a todas; lanza si alguna falla.</li>
 *   <li>{@code Joiner.anySuccessfulResultOrThrow()} — termina con la primera que tenga éxito.</li>
 *   <li>{@code Joiner.awaitAllSuccessfulOrThrow()} — igual que el primero pero devuelve Void.</li>
 *   <li>{@code Joiner.awaitAll()} — espera a todas sin lanzar nunca.</li>
 *   <li>{@code Joiner.allUntil(predicate)} — cancela según predicado personalizado.</li>
 * </ul>
 */
public class App {

    public static void main(String[] args) throws Exception {
        ejemploAllSuccessfulOrThrow();
        ejemploAnySuccessfulResultOrThrow();
        ejemploAwaitAll();
    }

    // --- Joiner.allSuccessfulOrThrow() ---------------------------------------
    // Equivalente al antiguo ShutdownOnFailure.
    // Cancela todo el scope en cuanto una subtarea falla; si todas tienen éxito
    // devuelve un Stream<Subtask<T>> con cada resultado.
    static void ejemploAllSuccessfulOrThrow() throws Exception {
        System.out.println("=== allSuccessfulOrThrow ===");

        try (var scope = StructuredTaskScope.open(Joiner.<String>allSuccessfulOrThrow())) {
            Subtask<String> usuario = scope.fork(() -> buscarUsuario(1L));
            Subtask<String> pedido  = scope.fork(() -> buscarPedido(99L));

            // join() devuelve Stream<Subtask<String>>; lanza ExecutionException si alguna falló
            Stream<Subtask<String>> resultados = scope.join();

            System.out.println("Usuario: " + usuario.get());
            System.out.println("Pedido:  " + pedido.get());
            System.out.println("Total subtareas exitosas: " + resultados.count());
        }
    }

    // --- Joiner.anySuccessfulResultOrThrow() ---------------------------------
    // Equivalente al antiguo ShutdownOnSuccess.
    // Cancela el scope en cuanto la primera subtarea devuelve un resultado.
    // Útil para consultar varias réplicas y quedarse con la respuesta más rápida.
    static void ejemploAnySuccessfulResultOrThrow() throws Exception {
        System.out.println("=== anySuccessfulResultOrThrow ===");

        try (var scope = StructuredTaskScope.open(Joiner.<String>anySuccessfulResultOrThrow())) {
            scope.fork(() -> consultarServidor("servidor-A", 200));
            scope.fork(() -> consultarServidor("servidor-B", 50));
            scope.fork(() -> consultarServidor("servidor-C", 120));

            // join() devuelve directamente el primer resultado exitoso
            String respuesta = scope.join();
            System.out.println("Respuesta más rápida: " + respuesta);
        }
    }

    // --- Joiner.awaitAll() ---------------------------------------------------
    // Espera a todas las subtareas sin cancelar ni relanzar excepciones.
    // Permite inspeccionar el estado de cada una manualmente.
    static void ejemploAwaitAll() throws InterruptedException {
        System.out.println("=== awaitAll (inspeccion manual) ===");

        try (var scope = StructuredTaskScope.open(Joiner.<String>awaitAll())) {
            var tareas = java.util.List.of(
                scope.fork(() -> procesar("A")),
                scope.fork(() -> procesar("B")),
                scope.fork(() -> { throw new RuntimeException("fallo en C"); })
            );

            scope.join(); // Void; nunca lanza

            for (var t : tareas) {
                switch (t.state()) {
                    case SUCCESS -> System.out.println("OK: "     + t.get());
                    case FAILED  -> System.out.println("Error: "  + t.exception().getMessage());
                    default      -> System.out.println("Cancelada");
                }
            }
        }
    }

    // --- Métodos auxiliares que simulan trabajo remoto -----------------------

    static String buscarUsuario(long id) throws InterruptedException {
        Thread.sleep(30);
        return "Usuario#" + id;
    }

    static String buscarPedido(long id) throws InterruptedException {
        Thread.sleep(50);
        return "Pedido#" + id;
    }

    static String consultarServidor(String nombre, long ms) throws InterruptedException {
        Thread.sleep(ms);
        return "respuesta de " + nombre;
    }

    static String procesar(String valor) throws InterruptedException {
        Thread.sleep(20);
        return "procesado:" + valor;
    }
}

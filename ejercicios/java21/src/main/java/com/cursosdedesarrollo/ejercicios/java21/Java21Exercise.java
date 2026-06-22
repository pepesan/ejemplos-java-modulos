package com.cursosdedesarrollo.ejercicios.java21;

import java.util.List;
import java.util.SequencedCollection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Java21Exercise {

    // Record para usar con Record Patterns
    public record Point(int x, int y) {}

    /**
     * Tarea 1: Lanzar una tarea concurrente usando Hilos Virtuales a través de ExecutorService.
     * Retorna el resultado obtenido de la tarea.
     */
    public static String ejecutarEnHiloVirtual(Callable<String> tarea) throws ExecutionException, InterruptedException {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> future = executor.submit(tarea);
            return future.get();
        }
    }

    /**
     * Tarea 2: Obtener y operar sobre extremos y orden inverso de una SequencedCollection.
     * Retorna un record con la información obtenida.
     */
    public record ColeccionExtremos(String primero, String ultimo, List<String> reversa) {}

    public static ColeccionExtremos procesarColeccionSecuenciada(SequencedCollection<String> items) {
        if (items == null || items.isEmpty()) {
            return new ColeccionExtremos(null, null, List.of());
        }
        String primero = items.getFirst();
        String ultimo = items.getLast();
        
        // Reversed devuelve una vista invertida. La copiamos en una lista.
        List<String> reversa = List.copyOf(items.reversed());
        
        return new ColeccionExtremos(primero, ultimo, reversa);
    }

    /**
     * Tarea 3: Utilizar deconstrucción de records (Record Patterns) en un switch.
     */
    public static String evaluarObjetoConRecordPattern(Object obj) {
        return switch (obj) {
            case Point(int x, int y) -> "Punto en coordenadas X=" + x + ", Y=" + y;
            case String s -> "Es un String: " + s;
            case null -> "El objeto es nulo";
            default -> "Otro tipo";
        };
    }

    /**
     * Tarea 4: Utilizar la sintaxis de variables sin nombre '_' para ignorar variables no utilizadas.
     * Retorna true si ocurre un NumberFormatException que es capturado de esta manera.
     */
    public static boolean parsearEnteroSeguroConUnnamed(String str) {
        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException _) {
            // Se usa '_' para indicar de forma explícita que se ignora la excepción
            return true;
        }
    }
}

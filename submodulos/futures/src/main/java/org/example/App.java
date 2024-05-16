package org.example;

import java.util.concurrent.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws ExecutionException, InterruptedException {
        // Future: Introducido en Java 5, es la base para la programación asíncrona en Java.
        // CompletableFuture: Introducido en Java 8, ofrece una versión más moderna
        // y avanzada de la programación asíncrona.

        System.out.println( "Futuros" );
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(() -> {
            // Operación asíncrona
            return "Hola, mundo!";
        });

        String resultado = future.get();
        System.out.println(resultado); // Imprime: Hola, mundo!
        executor.close();

        executor = Executors.newSingleThreadExecutor();
        // Create CompletableFutures for asynchronous tasks (replace with your actual logic)
        CompletableFuture<String> nombreFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return obtenerNombreUsuario();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture<String> emailFuture = CompletableFuture.supplyAsync(() -> {
            try {
                return obtenerEmailUsuario();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        // Combine results asynchronously (assuming both Futures return Strings)
        CompletableFuture<Void> combinedFuture = nombreFuture.thenCombine(emailFuture, (nombre, email) -> {
            System.out.println(nombre + " - " + email);
            return null;
        });

        combinedFuture.get();
        executor.close();

        executor = Executors.newSingleThreadExecutor();
        CompletableFuture<String> nombreFuture2 = CompletableFuture.supplyAsync(() -> {
            try {
                return obtenerNombreUsuario();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        CompletableFuture<String> nombreMayusculasFuture = nombreFuture2.thenApply(nombre -> nombre.toUpperCase());

        nombreMayusculasFuture.thenAccept(nombreMayusculas -> System.out.println("Nombre en mayúsculas: " + nombreMayusculas));
        Thread.sleep(3000);
        executor.close();

        executor = Executors.newSingleThreadExecutor();

        CompletableFuture<Void> procesarPedidoFuture = CompletableFuture.supplyAsync(() -> {
            try {
                procesarPedido();
                return null; // Assuming successful processing returns null
            } catch (InterruptedException e) {
                throw new RuntimeException(e); // Rethrow as unchecked exception
            }
        });

        procesarPedidoFuture.whenComplete((result, exception) -> {
            if (exception != null) {
                System.err.println("Error al procesar el pedido: " + exception.getMessage());
            } else {
                System.out.println("Pedido procesado exitosamente.");
            }

            // Notificar al usuario por correo electrónico o SMS (código no mostrado)
            notificarUsuarioPedidoProcesado();
        });
        Thread.sleep(2000);
        executor.close();

    }

    private static void procesarPedido() throws InterruptedException {
        Thread.sleep(1000);
    }

    private static void notificarUsuarioPedidoProcesado() {
        System.out.println("notificado");
    }

    private static String obtenerEmailUsuario() throws InterruptedException {
        Thread.sleep(2000);
        return "david@cursosdedesarrollo.com";
    }

    private static String obtenerNombreUsuario() throws InterruptedException {
        Thread.sleep(1000);
        return "David";
    }
}

package com.cursosdedesarrollo;

import java.lang.ScopedValue;

public class ScopedValuesExample {
    private static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

    public static void main(String[] args) {
        // Ligamos el REQUEST_ID en este bloque
        ScopedValue.where(REQUEST_ID, "req-12345").run(() -> {
            try {
                handleRequest();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
    }

    static void handleRequest() throws InterruptedException {
        System.out.println("Dentro de handleRequest: " + REQUEST_ID.get());

        // ✅ Propagación manual del binding al hilo hijo:
        String current = REQUEST_ID.get();
        Thread.ofVirtual()
                .start(() -> ScopedValue.where(REQUEST_ID, current).run(ScopedValuesExample::nestedOperation))
                .join();
    }

    static void nestedOperation() {
        // Aquí el binding está disponible porque lo re-ligamos al lanzar el hilo
        System.out.println("En hilo hijo: " + REQUEST_ID.get());
    }
}

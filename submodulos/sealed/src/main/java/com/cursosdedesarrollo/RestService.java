package com.cursosdedesarrollo;

/**
 * Record que implementa la interfaz sellada HttpService.
 * Los records son implícitamente 'final', por lo que cumplen
 * automáticamente con las reglas de sellado de Java.
 */
public record RestService(String endpoint) implements HttpService {
    @Override
    public void execute() {
        System.out.println("Ejecutando petición HTTP REST a: " + endpoint);
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }
}

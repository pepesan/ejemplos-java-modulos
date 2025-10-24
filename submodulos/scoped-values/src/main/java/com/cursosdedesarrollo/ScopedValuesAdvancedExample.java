package com.cursosdedesarrollo;

import java.lang.ScopedValue;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ejemplo avanzado de ScopedValue con rebinding manual en hilos/tareas.
 * - Re-liga el contexto (REQUEST_ID, USERNAME) para cada tarea en un executor de hilos virtuales.
 * - Muestra "sombra" (override) de USERNAME dentro de un sub-bloque.
 */
public class ScopedValuesAdvancedExample {

    // 1) Definimos dos valores de contexto: id de petición y usuario actual
    private static final ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();
    private static final ScopedValue<String> USERNAME   = ScopedValue.newInstance();

    public static void main(String[] args) throws Exception {
        // 2) Creamos un contexto "de entrada" y ejecutamos todo el flujo dentro de él
        ScopedValue.where(REQUEST_ID, "req-42")
                .where(USERNAME,   "pepesan")
                .run(() -> {
                    try {
                        atenderSolicitud();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        // 3) Fuera de este bloque, REQUEST_ID/USERNAME no están ligados (get() fallaría)
    }

    private static void atenderSolicitud() throws Exception {
        log("Inicio de la solicitud");
        // 4) Executor con hilos virtuales (AutoCloseable → try-with-resources)
        try (ExecutorService exec = Executors.newVirtualThreadPerTaskExecutor()) {

            // 5) Lista de "tareas de negocio" para procesar en paralelo
            List<Callable<String>> tareas = List.of(
                    withCurrentContext(() -> consultaRemota("clientes/123")),  // hereda contexto
                    withCurrentContext(() -> consultaRemota("pedidos/456")),
                    withCurrentContext(() -> {
                        // 6) "Sombra" (override) local: dentro de este bloque, USERNAME será "system"
                        return ScopedValue.where(USERNAME, "system").call(() -> {
                            log("Operación sensible (se ejecuta como 'system')");
                            return consultaRemota("auditoria/alta-evento");
                        });
                    })
            );

            // 7) Enviamos todas las tareas y esperamos resultados
            var futures = tareas.stream().map(exec::submit).toList();
            for (var f : futures) {
                log("Resultado: " + f.get());
            }
        }

        log("Fin de la solicitud");
    }

    /**
     * 8) Wrapper que CAPTURA los valores actuales y devuelve una Callable que re-liga el contexto
     *    antes de ejecutar la tarea (patrón seguro y portable en 21+).
     */
    private static <T> java.util.concurrent.Callable<T>
    withCurrentContext(java.util.concurrent.Callable<T> task) {
        final String req  = REQUEST_ID.isBound() ? REQUEST_ID.get() : null;
        final String user = USERNAME.isBound()   ? USERNAME.get()   : null;

        return () -> ScopedValue.where(REQUEST_ID, req)
                .where(USERNAME,   user)
                // Adaptamos Callable<T> → ScopedValue.CallableOp<T, Exception>
                .call((ScopedValue.CallableOp<T, Exception>) task::call);
    }


    /**
     * 9) Simula una operación "remota" (I/O), leyendo el contexto para trazas.
     */
    private static String consultaRemota(String recurso) throws InterruptedException {
        log("Consultando recurso: " + recurso);
        Thread.sleep(Duration.ofMillis(150)); // simulamos latencia de red
        return "OK(" + recurso + ")";
    }

    /**
     * 10) Logger de ejemplo que añade REQUEST_ID y USERNAME a cada línea.
     */
    private static void log(String msg) {
        String rid  = REQUEST_ID.isBound() ? REQUEST_ID.get() : "-";
        String user = USERNAME.isBound()   ? USERNAME.get()   : "-";
        System.out.printf("[rid=%s][user=%s] %s%n", rid, user, msg);
    }
}


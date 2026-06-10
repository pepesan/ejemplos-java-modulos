# futures — Future y CompletableFuture

**Java 5 / Java 8**

## Qué cubre

Programación asíncrona en Java: desde la interfaz `Future` básica hasta el encadenamiento fluido de `CompletableFuture`.

## Ejemplos incluidos

| Concepto | Descripción |
|----------|-------------|
| `Future<T>` | Tarea asíncrona básica con `ExecutorService.submit` y `future.get()` |
| `CompletableFuture.supplyAsync` | Inicia una tarea asíncrona que devuelve un valor |
| `thenCombine` | Combina los resultados de dos futuros cuando ambos terminan |
| `thenApply` | Transforma el resultado de un futuro (equivale a `map`) |
| `thenAccept` | Consume el resultado sin devolver nada |
| `whenComplete` | Callback al finalizar, tanto en éxito como en error |

## Caso de uso del ejemplo

Simulación de dos llamadas asíncronas (obtener nombre de usuario y email) que se combinan cuando ambas terminan, y procesado de pedido con notificación posterior.

## Clase principal

`org.example.App`

package com.cursosdedesarrollo.streams;

import java.util.Objects;
import java.util.stream.Gatherer;

/**
 * Gatherer personalizado (Java 24, API final):
 *  - Quita duplicados consecutivos.
 *  - Usa estado mutable y un integrator que devuelve boolean.
 */
class DistinctConsecutiveGatherer {

    public static <T> Gatherer<T, ?, T> of() {
        // Estado mutable: guarda el último valor emitido
        final class State {
            T last;
        }

        // Creamos el Gatherer SECUENCIAL:
        //  - initializer: crea el estado
        //  - integrator: procesa cada elemento, puede emitir y devuelve boolean (continuar)
        return Gatherer.ofSequential(
                State::new,
                // Integrator "greedy": no hace short-circuit; devuelve boolean.
                Gatherer.Integrator.ofGreedy((state, element, downstream) -> {
                    // Si es distinto del último emitido, lo emitimos y actualizamos estado
                    if (!Objects.equals(element, state.last)) {
                        state.last = element;
                        // downstream.push(...) devuelve boolean: true=continuar, false=parar
                        return downstream.push(element);
                    }
                    // Si es igual al último, no emitimos y seguimos procesando
                    return true;
                })
        );
    }
}
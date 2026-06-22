package com.cursosdedesarrollo.ejercicios.java8;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Java8Exercise {

    // 1. Interfaz Funcional Personalizada
    @FunctionalInterface
    public interface Transformador<T, R> {
        R transformar(T input);
    }

    /**
     * Tarea 1: Aplicar una transformación usando una expresión Lambda o referencia a método.
     */
    public static Integer aplicarTransformacion(String input, Transformador<String, Integer> transformador) {
        return transformador.transformar(input);
    }

    /**
     * Tarea 2: Usar Stream API para filtrar números pares, elevarlos al cuadrado y recoger el resultado.
     */
    public static List<Integer> procesarNumeros(List<Integer> numeros) {
        if (numeros == null) {
            return List.of();
        }
        return numeros.stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .collect(Collectors.toList());
    }

    /**
     * Tarea 3: Retornar la longitud del texto opcional si está presente, o 0 si está vacío o nulo.
     */
    public static int obtenerLongitudOp(Optional<String> optionalStr) {
        if (optionalStr == null) {
            return 0;
        }
        return optionalStr.map(String::length).orElse(0);
    }

    /**
     * Tarea 4: Calcular la diferencia en días entre dos fechas utilizando LocalDate de java.time.
     */
    public static long calcularDiasEntre(LocalDate inicio, LocalDate fin) {
        if (inicio == null || fin == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(inicio, fin);
    }
}

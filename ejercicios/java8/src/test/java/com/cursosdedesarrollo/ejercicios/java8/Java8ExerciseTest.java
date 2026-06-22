package com.cursosdedesarrollo.ejercicios.java8;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class Java8ExerciseTest {

    @Test
    public void testAplicarTransformacion() {
        // Usando lambda
        int longitud = Java8Exercise.aplicarTransformacion("Hola Mundo", s -> s.length());
        assertEquals(10, longitud);

        // Usando referencia a método
        int parsed = Java8Exercise.aplicarTransformacion("12345", Integer::valueOf);
        assertEquals(12345, parsed);
    }

    @Test
    public void testProcesarNumeros() {
        List<Integer> entrada = List.of(1, 2, 3, 4, 5, 6);
        List<Integer> resultado = Java8Exercise.procesarNumeros(entrada);
        
        // Pares: 2, 4, 6 -> Cuadrados: 4, 16, 36
        assertEquals(List.of(4, 16, 36), resultado);
    }

    @Test
    public void testObtenerLongitudOp() {
        assertEquals(5, Java8Exercise.obtenerLongitudOp(Optional.of("Java8")));
        assertEquals(0, Java8Exercise.obtenerLongitudOp(Optional.empty()));
        assertEquals(0, Java8Exercise.obtenerLongitudOp(null));
    }

    @Test
    public void testCalcularDiasEntre() {
        LocalDate inicio = LocalDate.of(2026, 6, 1);
        LocalDate fin = LocalDate.of(2026, 6, 22);
        
        long dias = Java8Exercise.calcularDiasEntre(inicio, fin);
        assertEquals(21, dias);
    }
}

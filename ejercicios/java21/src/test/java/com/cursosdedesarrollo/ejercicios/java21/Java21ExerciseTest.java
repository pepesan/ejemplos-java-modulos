package com.cursosdedesarrollo.ejercicios.java21;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class Java21ExerciseTest {

    @Test
    public void testEjecutarEnHiloVirtual() throws ExecutionException, InterruptedException {
        String resultado = Java21Exercise.ejecutarEnHiloVirtual(() -> {
            assertTrue(Thread.currentThread().isVirtual());
            return "Hola Virtual Thread";
        });
        assertEquals("Hola Virtual Thread", resultado);
    }

    @Test
    public void testProcesarColeccionSecuenciada() {
        List<String> lista = List.of("A", "B", "C");
        Java21Exercise.ColeccionExtremos resultado = Java21Exercise.procesarColeccionSecuenciada(lista);
        
        assertEquals("A", resultado.primero());
        assertEquals("C", resultado.ultimo());
        assertEquals(List.of("C", "B", "A"), resultado.reversa());
    }

    @Test
    public void testEvaluarObjetoConRecordPattern() {
        Java21Exercise.Point p = new Java21Exercise.Point(10, 20);
        
        assertEquals("Punto en coordenadas X=10, Y=20", Java21Exercise.evaluarObjetoConRecordPattern(p));
        assertEquals("Es un String: Test", Java21Exercise.evaluarObjetoConRecordPattern("Test"));
        assertEquals("El objeto es nulo", Java21Exercise.evaluarObjetoConRecordPattern(null));
        assertEquals("Otro tipo", Java21Exercise.evaluarObjetoConRecordPattern(12345));
    }

    @Test
    public void testParsearEnteroSeguroConUnnamed() {
        assertTrue(Java21Exercise.parsearEnteroSeguroConUnnamed("no-es-numero"));
        assertFalse(Java21Exercise.parsearEnteroSeguroConUnnamed("123"));
    }
}

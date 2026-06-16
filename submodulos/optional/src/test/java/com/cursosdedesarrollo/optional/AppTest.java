package com.cursosdedesarrollo.optional;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    public void testIfPresentOrElseConValor() {
        Optional<String> optional = Optional.of("Hola");
        AtomicBoolean planA = new AtomicBoolean(false);
        AtomicBoolean planB = new AtomicBoolean(false);

        optional.ifPresentOrElse(
                val -> planA.set(true),
                () -> planB.set(true)
        );

        assertTrue(planA.get(), "Debería ejecutarse el plan A cuando hay valor");
        assertFalse(planB.get(), "No debería ejecutarse el plan B");
    }

    @Test
    public void testIfPresentOrElseVacio() {
        Optional<String> optional = Optional.empty();
        AtomicBoolean planA = new AtomicBoolean(false);
        AtomicBoolean planB = new AtomicBoolean(false);

        optional.ifPresentOrElse(
                val -> planA.set(true),
                () -> planB.set(true)
        );

        assertFalse(planA.get(), "No debería ejecutarse el plan A");
        assertTrue(planB.get(), "Debería ejecutarse el plan B (fallback)");
    }

    @Test
    public void testOrMetodo() {
        Optional<String> vacio = Optional.empty();
        Optional<String> fallback = vacio.or(() -> Optional.of("Default"));

        assertTrue(fallback.isPresent());
        assertEquals("Default", fallback.get(), "El método or() debe suministrar el valor de fallback");
    }

    @Test
    public void testOptionalStream() {
        List<Optional<String>> lista = List.of(
                Optional.of("Java"),
                Optional.empty(),
                Optional.of("JUnit")
        );

        List<String> resultado = lista.stream()
                .flatMap(Optional::stream)
                .toList();

        assertEquals(List.of("Java", "JUnit"), resultado, 
                "flatMap con Optional.stream() debe filtrar vacíos y desglosar valores");
    }
}

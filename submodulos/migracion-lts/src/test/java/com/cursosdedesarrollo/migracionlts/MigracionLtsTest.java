package com.cursosdedesarrollo.migracionlts;

import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.concurrent.StructuredTaskScope;
import static org.junit.jupiter.api.Assertions.*;

public class MigracionLtsTest {

    // --- Pruebas de Migración Java 8 a 11 ---------------------------------

    @Test
    public void testJava8to11ColeccionesInmutables() {
        List<String> inmutable = List.of("a", "b", "c");
        assertThrows(UnsupportedOperationException.class, () -> {
            inmutable.add("d");
        }, "Las colecciones de Java 11 creadas con List.of son inmutables");
    }

    @Test
    public void testJava8to11Optional() {
        Optional<String> optional = Optional.empty();
        final boolean[] flag = {false};
        optional.ifPresentOrElse(
                val -> fail("No debería entrar aquí"),
                () -> flag[0] = true
        );
        assertTrue(flag[0], "Debería ejecutarse el plan alternativo de fallback");
    }

    // --- Pruebas de Migración Java 11 a 17 --------------------------------

    @Test
    public void testJava11to17TextBlocks() {
        String jsonJava11 = "{\n" +
                "  \"nombre\": \"Juan\"\n" +
                "}";
        
        String jsonJava17 = """
                {
                  "nombre": "Juan"
                }""";

        // stripIndent y normalizeNewlines
        assertEquals(jsonJava11.replace("\r", ""), jsonJava17.replace("\r", ""), 
                "El Text Block debe producir el mismo resultado que la concatenación clásica");
    }

    @Test
    public void testJava11to17Records() {
        record Usuario(String nombre, int edad) {}
        Usuario user1 = new Usuario("Ana", 25);
        Usuario user2 = new Usuario("Ana", 25);

        assertEquals("Ana", user1.nombre(), "El getter generado del record no lleva prefijo 'get'");
        assertEquals(user1, user2, "Los records generan equals() automáticamente comparando los campos");
        assertEquals(user1.hashCode(), user2.hashCode(), "Los records generan hashCode() automáticamente");
        assertTrue(user1.toString().contains("nombre=Ana"), "Los records generan toString() con formato de campos");
    }

    // --- Pruebas de Migración Java 17 a 21 --------------------------------

    @Test
    public void testJava17to21SequencedCollections() {
        List<String> lenguajes = new ArrayList<>(List.of("Java", "C#", "C++"));

        assertEquals("Java", lenguajes.getFirst());
        assertEquals("C++", lenguajes.getLast());
        
        List<String> invertida = lenguajes.reversed();
        assertEquals("C++", invertida.getFirst());
        assertEquals("Java", invertida.getLast());
    }

    // --- Pruebas de Migración Java 21 a 25 --------------------------------

    private final static ScopedValue<String> CONTEXTO = ScopedValue.newInstance();

    @Test
    public void testJava21to25ScopedValuesScope() {
        assertFalse(CONTEXTO.isBound(), "Fuera de un ámbito run, ScopedValue no debe estar enlazado");
        
        ScopedValue.where(CONTEXTO, "User-LTS").run(() -> {
            assertTrue(CONTEXTO.isBound());
            assertEquals("User-LTS", CONTEXTO.get(), "ScopedValue debe retornar el valor asignado en su contexto");
        });

        assertThrows(NoSuchElementException.class, CONTEXTO::get, 
                "Leer un ScopedValue sin enlazar debe lanzar NoSuchElementException");
    }

    @Test
    public void testJava21to25StructuredConcurrency() throws Exception {
        try (var scope = StructuredTaskScope.open(StructuredTaskScope.Joiner.allSuccessfulOrThrow())) {
            var task1 = scope.fork(() -> "Task1");
            var task2 = scope.fork(() -> "Task2");

            scope.join();

            assertEquals("Task1", task1.get());
            assertEquals("Task2", task2.get());
        }
    }
}

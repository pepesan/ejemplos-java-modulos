package com.cursosdedesarrollo.streams;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class EjemplosStreamsTest {

    @Test
    public void testTakeWhile() {
        List<Integer> numeros = List.of(2, 4, 6, 8, 12, 14, 2, 4);
        List<Integer> resultado = numeros.stream()
                .takeWhile(n -> n < 10)
                .toList();

        assertEquals(List.of(2, 4, 6, 8), resultado, "Debería tomar elementos hasta encontrar el 12");
    }

    @Test
    public void testDropWhile() {
        List<Integer> numeros = List.of(2, 4, 6, 8, 12, 14, 2, 4);
        List<Integer> resultado = numeros.stream()
                .dropWhile(n -> n < 10)
                .toList();

        assertEquals(List.of(12, 14, 2, 4), resultado, "Debería descartar los elementos menores a 10");
    }

    @Test
    public void testOfNullable() {
        String nulo = null;
        String valido = "Hola";

        long countNulo = Stream.ofNullable(nulo).count();
        long countValido = Stream.ofNullable(valido).count();

        assertEquals(0, countNulo, "Stream.ofNullable con null debe estar vacío");
        assertEquals(1, countValido, "Stream.ofNullable con valor debe tener tamaño 1");
    }

    @Test
    public void testStreamToListImutabilidad() {
        List<String> inmutable = Stream.of("a", "b", "c").toList();

        // Verificar que toList() devuelve una lista inmutable
        assertThrows(UnsupportedOperationException.class, () -> {
            inmutable.add("d");
        }, "Intentar modificar la lista inmutable de toList() debe lanzar UnsupportedOperationException");
    }

    @Test
    public void testGathererPersonalizado() {
        List<Integer> duplicadosConsecutivos = List.of(1, 1, 2, 2, 3, 1, 1, 4, 4, 5);
        List<Integer> filtrados = duplicadosConsecutivos.stream()
                .gather(EjemplosStreamsGathererPersonalizado.of())
                .toList();

        assertEquals(List.of(1, 2, 3, 1, 4, 5), filtrados, 
                "El Gatherer personalizado debe eliminar los duplicados consecutivos de forma correcta");
    }
}

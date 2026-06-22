package com.cursosdedesarrollo.ejercicios.java11;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Java11ExerciseTest {

    @Test
    public void testCrearPeticionHttpGet() {
        String url = "https://example.com/api";
        HttpRequest request = Java11Exercise.crearPeticionHttpGet(url);
        
        assertNotNull(request);
        assertEquals("GET", request.method());
        assertEquals(url, request.uri().toString());
    }

    @Test
    public void testObtenerLineasNoVacias() {
        String entrada = "  \n  Linea 1  \n\t\n  Linea 2\n   ";
        List<String> resultado = Java11Exercise.obtenerLineasNoVacias(entrada);
        
        assertEquals(List.of("Linea 1", "Linea 2"), resultado);
    }

    @Test
    public void testFiltrarNombresNoVacios() {
        List<String> entrada = List.of("Alba", "", "Bernardo", "", "Carlos");
        List<String> resultado = Java11Exercise.filtrarNombresNoVacios(entrada);
        
        assertEquals(List.of("Alba", "Bernardo", "Carlos"), resultado);
    }

    @Test
    public void testEscribirYLeerArchivoTemporal() throws IOException {
        String contenido = "Contenido de prueba para Java 11 Files API";
        String resultado = Java11Exercise.escribirYLeerArchivoTemporal(contenido);
        
        assertEquals(contenido, resultado);
    }
}

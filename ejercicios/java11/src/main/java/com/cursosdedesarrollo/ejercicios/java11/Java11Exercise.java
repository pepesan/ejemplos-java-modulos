package com.cursosdedesarrollo.ejercicios.java11;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Java11Exercise {

    /**
     * Tarea 1: Configurar una petición HTTP GET básica utilizando HttpClient y HttpRequest.
     * Retorna el objeto HttpRequest construido.
     */
    public static HttpRequest crearPeticionHttpGet(String uriStr) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uriStr))
                .GET()
                .build();
    }

    /**
     * Tarea 2: Limpiar una cadena multilínea usando strip(), e identificar líneas que no estén en blanco.
     * Retorna la lista de líneas no vacías resultantes.
     */
    public static List<String> obtenerLineasNoVacias(String multilineStr) {
        if (multilineStr == null) {
            return List.of();
        }
        return multilineStr.lines()
                .map(String::strip)
                .filter(line -> !line.isBlank())
                .collect(Collectors.toList());
    }

    /**
     * Tarea 3: Filtrar elementos que NO cumplan un predicado determinado, usando Predicate.not().
     */
    public static List<String> filtrarNombresNoVacios(List<String> nombres) {
        if (nombres == null) {
            return List.of();
        }
        return nombres.stream()
                .filter(Predicate.not(String::isEmpty))
                .collect(Collectors.toList());
    }

    /**
     * Tarea 4: Guardar y recuperar texto en/de un archivo temporal usando Files.writeString y Files.readString.
     */
    public static String escribirYLeerArchivoTemporal(String contenido) throws IOException {
        Path tempFile = Files.createTempFile("java11_exercise_", ".txt");
        try {
            Files.writeString(tempFile, contenido);
            return Files.readString(tempFile);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}

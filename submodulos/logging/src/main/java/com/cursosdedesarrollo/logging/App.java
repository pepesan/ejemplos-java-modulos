package com.cursosdedesarrollo.logging;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

/**
 * Hello world!
 *
 */
public class App 
{
    static {
        //loading logging.properties
        String path = Objects.requireNonNull(App.class.getClassLoader()
                        .getResource("logging.properties"))
                .getFile();
        System.setProperty("java.util.logging.config.file", path);
    }

    public static void main(String[] args) throws Exception {
        System.out.println( "Ejemplo de Logging!" );
        URL url = new URL("https://cursosdedesarrollo.com/");
        URLConnection yc = url.openConnection();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                yc.getInputStream()))) {

            in.lines().forEach(System.out::println);
        }
    }

}

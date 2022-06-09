package com.cursosdedesarrollo.nio;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Ejemplos de NIO!" );
        // lectura
        Path path = Files.writeString(
                Files.createTempFile("test", ".txt"), "test file content");
        System.out.println(path);
        String s = Files.readString(path);
        System.out.println(s);
        // escritura
        Charset latinCharset = Charset.forName("ISO-8859-3");
        path = Files.writeString(
                Files.createTempFile("test", ".txt"), "test filum", latinCharset);
        System.out.println(path);
        s = Files.readString(path, latinCharset);
        System.out.println(s);
        // presencia
        path = Path.of("C:", "temp", "test.txt");
        System.out.println(path);
        boolean exists = Files.exists(path);
        System.out.println(exists);
        // presencia por URL
        URI uri = URI.create("file:///C:/temp/test.txt");
        System.out.println(uri);
        path = Path.of(uri);
        System.out.println(path);
        System.out.println(Files.exists(path));
    }
}

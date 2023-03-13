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
        // escritura
        Path path = Files.writeString(
                Files.createTempFile("test", ".txt"), "test file content");
        System.out.println(path);
        String s = Files.readString(path);
        System.out.println(s);
        // lectura
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

        // Java 12
        // mismo contenido
        Path filePath1 = Files.createTempFile("my-file", ".txt");
        Path filePath2 = Files.createTempFile("my-file2", ".txt");
        Files.writeString(filePath1,"a test string");
        Files.writeString(filePath2,"a test string");

        long mismatch = Files.mismatch(filePath1, filePath2);
        System.out.println(mismatch);

        filePath1.toFile().deleteOnExit();
        filePath2.toFile().deleteOnExit();

        // diferente contenido
        filePath1 = Files.createTempFile("my-file", ".txt");
        filePath2 = Files.createTempFile("my-file2", ".txt");
        Files.writeString(filePath1,"a test string");
        Files.writeString(filePath2,"a test string ....");

        mismatch = Files.mismatch(filePath1, filePath2);
        System.out.println(mismatch);

        filePath1.toFile().deleteOnExit();
        filePath2.toFile().deleteOnExit();
    }
}

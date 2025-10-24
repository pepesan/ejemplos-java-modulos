package com.cursosdedesarrollo;


import org.junit.jupiter.api.Test;
// Usa List y Path sin imports de paquetes individuales
import module java.base;
import module java.xml;   // también podrías: java.sql, etc.

public class AppTest {
    @Test
    void testCosa() {
        var lista = List.of(1,2,3);
        System.out.println(lista);
    }
}

package com.cursosdedesarrollo;


import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest{
    @Test
    void testSumar() {
        Coche coche = new Coche("  toyota  ", "Corolla", 1700);
        System.out.println("Marca final: " + coche.getMarca());   // TOYOTA
        System.out.println("Modelo final: " + coche.getModelo()); // Corolla
        System.out.println("Año final: " + coche.getAnio());      // 1886 (mínimo válido)
    }
}

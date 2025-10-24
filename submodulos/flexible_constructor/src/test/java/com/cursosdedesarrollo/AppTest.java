package com.cursosdedesarrollo;


import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class AppTest{
    @Test
    void testSumar() {
        // Ejemplo 1: año anterior a 1886 → se corrige a 1886
        Coche coche = new Coche("  toyota  ", "Corolla", 1700);
        System.out.println("Marca final: " + coche.getMarca());   // TOYOTA
        System.out.println("Modelo final: " + coche.getModelo()); // Corolla
        System.out.println("Año final: " + coche.getAnio());      // 1886 (mínimo válido)

        // Ejemplo 2: año válido
        Coche coche2 = new Coche("Citroën", "C4", 2023);
        System.out.println("Marca final: " + coche2.getMarca());
        System.out.println("Modelo final: " + coche2.getModelo());
        System.out.println("Año final: " + coche2.getAnio());
    }
}

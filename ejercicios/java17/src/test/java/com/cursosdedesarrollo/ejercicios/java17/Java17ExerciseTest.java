package com.cursosdedesarrollo.ejercicios.java17;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Java17ExerciseTest {

    @Test
    public void testVehiculoRecord() {
        Java17Exercise.Vehiculo coche = new Java17Exercise.Vehiculo("Toyota", "Corolla", "Híbrido");
        
        assertEquals("Toyota", coche.marca());
        assertEquals("Corolla", coche.modelo());
        assertEquals("Híbrido", coche.tipoMotor());
        assertTrue(coche.toString().contains("Toyota"));
    }

    @Test
    public void testDescribirFiguraConSealedYSwitch() {
        Java17Exercise.Figura circulo = new Java17Exercise.Circulo(5.0);
        Java17Exercise.Figura rectangulo = new Java17Exercise.Rectangulo(4.0, 6.0);

        assertEquals("Círculo de radio 5.0", Java17Exercise.describirFigura(circulo));
        assertEquals("Rectángulo de 4.0x6.0", Java17Exercise.describirFigura(rectangulo));
    }

    @Test
    public void testFormatearObjetoConPatternMatching() {
        assertEquals("Texto de longitud 4: Java", Java17Exercise.formatearObjeto("Java"));
        assertEquals("Entero con valor al cuadrado: 16", Java17Exercise.formatearObjeto(4));
        
        Java17Exercise.Vehiculo v = new Java17Exercise.Vehiculo("Tesla", "Model 3", "Eléctrico");
        assertEquals("Vehículo Tesla Model 3", Java17Exercise.formatearObjeto(v));
        
        assertEquals("Objeto no soportado", Java17Exercise.formatearObjeto(3.14));
    }
}

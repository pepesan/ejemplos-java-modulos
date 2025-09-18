package com.cursosdedesarrollo;

public class Vehiculo {
    private final String marca;
    private final int anio;

    public Vehiculo(String marca, int anio) {
        this.marca = marca;
        this.anio = anio;
        System.out.println("Vehículo creado: " + marca + " (" + anio + ")");
    }

    public String getMarca() {
        return marca;
    }

    public int getAnio() {
        return anio;
    }
}

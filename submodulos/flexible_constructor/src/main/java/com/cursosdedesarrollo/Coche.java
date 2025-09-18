package com.cursosdedesarrollo;

public class Coche extends Vehiculo {
    private final String modelo;

    public Coche(String marca, String modelo, int anio) {
        // 🔹 Antes de llamar a super(...) en Java 25
        // podemos ejecutar lógica previa, como validar o transformar parámetros
        int anioValido = Math.max(1886, anio); // 1886 = primer coche de la historia
        String marcaNormalizada = marca.trim().toUpperCase();

        // Ahora sí podemos invocar al constructor padre con valores ya preparados
        super(marcaNormalizada, anioValido);

        // 🔹 Después de super(...) ya podemos usar this y asignar campos propios
        this.modelo = modelo.trim();
        System.out.println("Coche creado: " + getMarca() + " " + this.modelo + " (" + getAnio() + ")");
    }

    public String getModelo() {
        return modelo;
    }
}

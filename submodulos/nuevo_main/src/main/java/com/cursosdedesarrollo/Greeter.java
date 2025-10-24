package com.cursosdedesarrollo;


// ➜ Ahora el método "main" puede ser de INSTANCIA (no estático).
// ➜ El launcher creará Greeter() y llamará a ese main como punto de entrada.

class Greeter {
    private final String prefijo = "Hola";

    // Constructor por defecto (puede tener lógica si quieres)
    Greeter() {
        // Podrías inicializar recursos aquí
    }

    // Punto de entrada de INSTANCIA (admite String... args igual que el estático)
    void main(String... args) {
        var nombre = (args.length > 0) ? args[0] : "mundo";
        System.out.println(prefijo + ", " + nombre);
    }
}


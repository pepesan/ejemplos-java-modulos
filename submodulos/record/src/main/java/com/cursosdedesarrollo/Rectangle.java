package com.cursosdedesarrollo;

/**
 * Ejemplo avanzado de Record que muestra características adicionales:
 * - Constructor compacto (para validación).
 * - Constructor sobrecargado (constructor secundario).
 * - Atributos y métodos estáticos.
 * - Bloque de inicialización estático.
 * - Sobrescritura del método de acceso generado automáticamente (length()).
 */
public record Rectangle(double length, double width) {

    /**
     * Constructor compacto.
     * No lleva paréntesis ni parámetros. Se ejecuta al principio del constructor canónico
     * y es ideal para validación de datos o normalización de valores.
     */
    public Rectangle {
        if (length <= 0 || width <= 0) {
            throw new java.lang.IllegalArgumentException(
                    String.format("Dimensiones inválidas: %f, %f", length, width));
        }
    }

    /**
     * Constructor sobrecargado (secundario).
     * Siempre debe llamar delegando al constructor canónico (usando this(...)) en su primera línea.
     */
    public Rectangle(){
        this(1.0, 1.0);
    }

    // Campo estático (permitido en Records)
    private static double goldenRatio;

    // Inicializador estático (permitido en Records)
    static {
        goldenRatio = (1 + Math.sqrt(5)) / 2;
    }

    // Método estático (método factoría)
    public static Rectangle createGoldenRectangle(double width) {
        return new Rectangle(width, width * goldenRatio);
    }

    // Campo de instancia no estático: NO permitido. Descomentar provocaría error de compilación:
    // private int area; 

    /**
     * Sobrescritura del método de acceso (getter) generado de forma automática.
     * Es útil si necesitamos añadir lógica secundaria al acceder al campo (ej: logging).
     */
    @Override
    public double length() {
        System.out.println("Accediendo a length: " + length);
        return length;
    }

    // Método de instancia adicional para calcular el área
    public double area() {
        return this.length * this.width;
    }
}

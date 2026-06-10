// =============================================================================
// JShell — Script 03: Records, sealed y pattern matching
// =============================================================================
// Ejecutar con:  jshell scripts/03_records_sealed.jsh
//
// JShell soporta todas las features modernas de Java: records (Java 16),
// sealed classes (Java 17) y pattern matching (Java 16-21).
// Es una forma excelente de experimentar con estas features sin montar un proyecto.
// =============================================================================

System.out.println("=== 03 — Records, sealed classes y pattern matching ===")

// --- Records ---
// Declaración directa en la sesión JShell. Todos los campos son final.
record Punto(int x, int y) {
    // Método adicional (válido en records)
    double distanciaAlOrigen() {
        return Math.sqrt(x * x + y * y);
    }
}

record Persona(String nombre, int edad) {}

// Crear instancias
var p1 = new Punto(3, 4)
p1.x()
p1.y()
p1.distanciaAlOrigen()    // debería dar 5.0

var ana = new Persona("Ana", 30)
ana.nombre()
ana

// Records implementan equals/hashCode/toString automáticamente
var p2 = new Punto(3, 4)
p1.equals(p2)    // true, mismos componentes

// --- Sealed classes ---
// El compilador valida que el switch sea exhaustivo cuando usa sealed.
sealed interface Figura permits Circulo, Rectangulo, Triangulo {}
record Circulo(double radio)             implements Figura {}
record Rectangulo(double ancho, double alto) implements Figura {}
record Triangulo(double base, double altura) implements Figura {}

// Método que usa switch con pattern matching (exhaustivo — sin default)
double area(Figura f) {
    return switch (f) {
        case Circulo c      -> Math.PI * c.radio() * c.radio();
        case Rectangulo r   -> r.ancho() * r.alto();
        case Triangulo t    -> (t.base() * t.altura()) / 2;
    };
}

area(new Circulo(5))
area(new Rectangulo(3, 4))
area(new Triangulo(6, 8))

// --- Pattern matching con instanceof (Java 16+) ---
Object obj = new Persona("Luis", 25)
if (obj instanceof Persona p && p.edad() >= 18) {
    System.out.println(p.nombre() + " es mayor de edad");
}

// --- Deconstrucción en switch (Java 21+) ---
Figura figura = new Rectangulo(10, 5)
String descripcion = switch (figura) {
    case Circulo(var r)        -> "Círculo de radio " + r;
    case Rectangulo(var a, var b) -> "Rectángulo " + a + "x" + b;
    case Triangulo(var b, var h) -> "Triángulo base=" + b + " altura=" + h;
}
System.out.println(descripcion)

System.out.println("\nScript 03 completado.")
/exit

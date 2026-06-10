// =============================================================================
// JShell — Script 01: Conceptos básicos
// =============================================================================
// Ejecutar con:  jshell scripts/01_basico.jsh
// O abrir JShell y luego:  /open scripts/01_basico.jsh
//
// En JShell no hace falta clase ni método main. Las expresiones se evalúan
// directamente y su resultado se imprime de forma automática.
// =============================================================================

System.out.println("=== 01 — Conceptos básicos de JShell ===")

// --- Expresiones ---
// JShell evalúa y muestra el resultado de cualquier expresión.
// Crea automáticamente una variable temporal ($1, $2, ...) para el resultado.
1 + 1
"Hola " + "Mundo"
Math.PI
Math.sqrt(144)

// --- Variables ---
// Se declaran igual que en Java. var también funciona.
int x = 10
String nombre = "Java 9"
var lista = java.util.List.of("a", "b", "c")

// Usar las variables declaradas
x * 2
nombre.toUpperCase()
lista.size()

// --- Sentencias ---
// Las sentencias (sin valor de retorno) también se pueden escribir directamente
System.out.println("Hola desde JShell!")
for (String elemento : lista) {
    System.out.println("  - " + elemento);
}

// --- Bloques try-catch ---
try {
    int resultado = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Error capturado: " + e.getMessage());
}

System.out.println("\nScript 01 completado.")
/exit

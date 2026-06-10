// =============================================================================
// JShell — Script 02: Lambdas, métodos y Streams
// =============================================================================
// Ejecutar con:  jshell scripts/02_lambdas_streams.jsh
//
// JShell es perfecto para explorar APIs de forma interactiva porque:
//   - Ves el resultado inmediatamente en cada paso
//   - Puedes redefinir métodos y variables sin reiniciar
//   - No necesitas crear ficheros .java ni compilar
// =============================================================================

System.out.println("=== 02 — Lambdas, métodos y Streams ===")

// --- Declaración de métodos ---
// Los métodos se declaran sin clase envolvente y quedan disponibles en la sesión.
int cuadrado(int n) { return n * n; }
String saludar(String nombre) { return "Hola, " + nombre + "!"; }

// Llamar a los métodos declarados arriba
cuadrado(7)
saludar("Mundo")

// --- Lambdas como variables ---
// Se almacenan en interfaces funcionales estándar.
java.util.function.Predicate<Integer> esPar = n -> n % 2 == 0;
java.util.function.Function<String, Integer> longitud = String::length;

esPar.test(4)
esPar.test(7)
longitud.apply("JShell")

// --- Streams ---
var numeros = java.util.List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

// Filtrar y recoger pares
var pares = numeros.stream()
    .filter(n -> n % 2 == 0)
    .toList()

// Map + reduce
int sumaCuadrados = numeros.stream()
    .mapToInt(n -> n * n)
    .sum()

// Encadenar operaciones
numeros.stream()
    .filter(esPar)
    .map(n -> cuadrado(n))   // usamos el método declarado arriba
    .forEach(System.out::println)

// --- Método redefinido ---
// En JShell puedes redefinir un método sin error; la versión nueva reemplaza a la anterior.
int cuadrado(int n) { System.out.println("  [nueva versión]"); return n * n; }
cuadrado(5)   // ahora usa la nueva definición

System.out.println("\nScript 02 completado.")
/exit

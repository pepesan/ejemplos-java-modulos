// =============================================================================
// JShell — Script 04: Classpath y resolución de dependencias
// =============================================================================
// ATENCIÓN: este script no se puede ejecutar directamente con /open sin más
// configuración porque necesita JARs externos o el JAR del propio proyecto.
// Sirve de REFERENCIA sobre cómo añadir dependencias a una sesión JShell.
// =============================================================================

System.out.println("=== 04 — Classpath y dependencias en JShell ===")

// -------------------------------------------------------------------------
// OPCIÓN 1: --class-path al arrancar JShell
// -------------------------------------------------------------------------
// Añadir uno o varios JARs cuando se lanza la herramienta:
//
//   jshell --class-path lib/commons-lang3-3.14.0.jar
//   jshell --class-path lib/gson-2.11.0.jar:lib/commons-lang3-3.14.0.jar
//
// Los paquetes de los JARs estarán disponibles para import en la sesión.

// -------------------------------------------------------------------------
// OPCIÓN 2: /env dentro de JShell (añadir JARs sin reiniciar)
// -------------------------------------------------------------------------
// Una vez dentro de JShell:
//
//   /env --class-path /ruta/a/lib.jar
//
// IMPORTANTE: /env reinicia la sesión, borrando todas las variables y métodos.

// -------------------------------------------------------------------------
// OPCIÓN 3: --module-path + --add-modules (aplicaciones modulares JPMS)
// -------------------------------------------------------------------------
//   jshell --module-path mods/ --add-modules com.example.mimodulo
//
// Permite usar clases de módulos propios o de terceros con module-info.java.

// -------------------------------------------------------------------------
// OPCIÓN 4: JBang — resolución automática de dependencias Maven
// -------------------------------------------------------------------------
// JBang (https://www.jbang.dev) es una herramienta complementaria que añade
// resolución de dependencias Maven directamente en scripts Java/JShell.
//
// Ejemplo de script JBang (.java):
//
//   //DEPS com.google.code.gson:gson:2.11.0
//   //DEPS org.apache.commons:commons-lang3:3.14.0
//   import com.google.gson.Gson;
//   class Demo {
//       public static void main(String[] args) {
//           System.out.println(new Gson().toJson(java.util.Map.of("clave", "valor")));
//       }
//   }
//
// Para ejecutarlo: jbang Demo.java   (descarga las deps automáticamente)
//
// JBang también tiene modo REPL: jbang --interactive

// -------------------------------------------------------------------------
// Demo con clases del JDK (siempre disponibles, sin dependencias externas)
// -------------------------------------------------------------------------

// HttpClient (Java 11+) — disponible sin JARs adicionales
var cliente = java.net.http.HttpClient.newHttpClient()
System.out.println("HttpClient creado: " + cliente.getClass().getSimpleName())

// Charset por defecto (Java 18+: siempre UTF-8)
System.out.println("Charset por defecto: " + java.nio.charset.Charset.defaultCharset())

// Usando un record con la API de java.time
record Evento(String nombre, java.time.LocalDate fecha) {
    String formatear() { return nombre + " (" + fecha.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) + ")"; }
}
new Evento("Java 25 LTS", java.time.LocalDate.of(2025, 9, 16)).formatear()

System.out.println("\nScript 04 completado.")
/exit

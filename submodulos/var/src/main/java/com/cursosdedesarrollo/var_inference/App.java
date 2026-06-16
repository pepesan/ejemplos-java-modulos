package com.cursosdedesarrollo.var_inference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Ejemplos de inferencia de tipos locales con var (Java 10).
 *
 * var NO es una palabra reservada: es un nombre de tipo especial
 * que el compilador resuelve en tiempo de compilación.
 * El tipo queda fijado igual que si se hubiera escrito explícitamente.
 */
public class App {

    public static void main(String[] args) throws IOException {

        // ----------------------------------------------------------------
        // 1. Variable local simple !!
        //    Sin var: ArrayList<String> lista = new ArrayList<>();
        //    Con var: el compilador infiere ArrayList<String>
        // ----------------------------------------------------------------
        System.out.println("--- 1. Variable local simple ---");
        var lista = new ArrayList<String>();
        lista.add("Java 10");
        lista.add("Java 11");
        lista.add("Java 17");
        lista.add("Java 21");
        System.out.println(lista);
        // El tipo inferido es ArrayList<String>, no List<String>.
        // Esto permite llamar a métodos propios de ArrayList:
        lista.trimToSize();

        // ----------------------------------------------------------------
        // 2. Con tipo genérico complejo
        //    Sin var: HashMap<String, List<Integer>> mapa = new HashMap<>();
        //    Con var: mucho más legible en tipos compuestos
        // ----------------------------------------------------------------
        System.out.println("\n--- 2. Tipo genérico complejo ---");
        var mapa = new HashMap<String, List<Integer>>();
        mapa.put("pares",   List.of(2, 4, 6, 8));
        mapa.put("impares", List.of(1, 3, 5, 7));
        System.out.println(mapa);

        // ----------------------------------------------------------------
        // 3. Bucle for-each
        //    El tipo de cada elemento se infiere del tipo del iterable
        // ----------------------------------------------------------------
        System.out.println("\n--- 3. Bucle for-each ---");
        var frutas = List.of("manzana", "naranja", "pera");
        for (var fruta : frutas) {
            System.out.println(fruta.toUpperCase()); // fruta es String
        }

        // ----------------------------------------------------------------
        // 4. Bucle for clásico
        // ----------------------------------------------------------------
        System.out.println("\n--- 4. Bucle for clásico ---");
        for (var i = 0; i < 5; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // ----------------------------------------------------------------
        // 5. try-with-resources
        //    Muy útil cuando el tipo del recurso es largo
        //    (p.ej. BufferedReader, Connection, PreparedStatement...)
        // ----------------------------------------------------------------
        System.out.println("\n--- 5. try-with-resources ---");
        var texto = "línea uno\nlínea dos\nlínea tres";
        try (var reader = new BufferedReader(new StringReader(texto))) {
            var linea = reader.readLine();
            while (linea != null) {
                System.out.println(linea);
                linea = reader.readLine();
            }
        }

        // ----------------------------------------------------------------
        // 6. var en parámetros de lambda (Java 11)
        //    Permite anotar parámetros inferidos, p.ej. @NonNull.
        //    Sin var no es posible añadir anotaciones a parámetros
        //    cuyo tipo se infiere automáticamente.
        // ----------------------------------------------------------------
        System.out.println("\n--- 6. var en parámetros de lambda ---");
        var palabras = List.of("hola", "mundo", "java", "var");
        palabras.stream()
                // el tipo de p se infiere como String; var permite anotarlo si fuera necesario
                .filter((var p) -> p.length() > 3)
                .map((var p) -> p.toUpperCase())
                // Foreach es un for in pero con función asociada
                // .forEach((obj) -> System.out.println(obj))
                .forEach(System.out::println);

        // ----------------------------------------------------------------
        // 7. Resultado de una llamada a método
        //    El tipo inferido es el tipo de retorno del método
        // ----------------------------------------------------------------
        System.out.println("\n--- 7. Resultado de método ---");
        var nombre = obtenerNombre();
        System.out.println(nombre.toLowerCase()); // nombre es String

        // ----------------------------------------------------------------
        // Límites de var — casos en los que NO se puede usar
        // (descomentando cualquiera de las líneas siguientes el código
        //  NO compilaría)
        // ----------------------------------------------------------------

        // a) No se puede usar en campos de clase:
        //    var campo = "hola";   <-- error fuera de un método

        // b) No se puede usar como parámetro de método:
        //    public static void demo(var x) { }   <-- error

        // c) No se puede usar como tipo de retorno:
        //    public static var obtener() { }   <-- error

        // d) No se puede usar con null sin tipo explícito (el compilador
        //    no puede inferir nada):
        //    var nulo = null;   <-- error

        // e) No se puede usar en arrays con inicializador de llaves:
        //    var numeros = {1, 2, 3};   <-- error
        //    var numeros = new int[]{1, 2, 3};   <-- esto SÍ funciona
        var numeros = new int[]{1, 2, 3};
        System.out.println("\n--- Límites: array con new ---");
        for (var n : numeros) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    private static String obtenerNombre() {
        return "Java";
    }
}

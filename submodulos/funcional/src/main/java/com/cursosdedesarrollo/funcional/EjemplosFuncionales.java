package com.cursosdedesarrollo.funcional;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.*;

/**
 * Complemento práctico de App.java.
 *
 * Temas cubiertos:
 *   1. Referencias a métodos  — los 4 tipos
 *   2. UnaryOperator y BinaryOperator
 *   3. Funciones especializadas para primitivos (sin boxing)
 *   4. Currying y aplicación parcial
 *   5. Wrapper para checked exceptions
 *   6. Memoización con Map
 */
public class EjemplosFuncionales {

    public static void main(String[] args) {
        demoReferenciasAMetodos();
        demoOperadores();
        demoPrimitivos();
        demoCurrying();
        demoWrapperExcepciones();
        demoMemoizacion();
    }

    // ====================================================================
    // 1. Referencias a métodos
    // ====================================================================
    // Una referencia a método es una lambda que solo llama a un método
    // ya existente. En vez de escribir   x -> obj.metodo(x)   se escribe
    //   obj::metodo
    //
    // Son equivalentes en bytecode; la referencia es solo azúcar sintáctico
    // que mejora la legibilidad cuando el cuerpo de la lambda es una única
    // llamada a método.
    //
    // HAY 4 TIPOS:
    //
    //  Tipo                         Sintaxis               Equivalente lambda
    //  ─────────────────────────────────────────────────────────────────────
    //  1. Método estático           Clase::metodoEstatico  x -> Clase.met(x)
    //  2. Método de instancia       Clase::metodoInstancia x -> x.metodo()
    //     sobre el tipo
    //  3. Método de instancia       obj::metodo            x -> obj.metodo(x)
    //     sobre objeto concreto
    //  4. Constructor               Clase::new             x -> new Clase(x)
    //  ─────────────────────────────────────────────────────────────────────
    // ====================================================================
    static void demoReferenciasAMetodos() {
        System.out.println("========== Referencias a métodos ==========");

        // ── Tipo 1: Método ESTÁTICO ──────────────────────────────────────
        // La firma del método estático coincide con la firma de la interfaz
        // funcional: recibe el mismo argumento y devuelve el mismo tipo.

        // Integer.parseInt(String) → int  ≡  Function<String, Integer>
        Function<String, Integer> parsearInt = Integer::parseInt;
        System.out.println(parsearInt.apply("42"));  // 42

        // Math.abs(int) → int  ≡  IntUnaryOperator (tema 3)
        // Como IntUnaryOperator opera con int sin boxing:
        IntUnaryOperator valorAbsoluto = Math::abs;
        System.out.println(valorAbsoluto.applyAsInt(-7));  // 7

        // String.valueOf(Object) → String  ≡  Function<Object, String>
        Function<Object, String> aTexto = String::valueOf;
        System.out.println(aTexto.apply(3.14));   // 3.14
        System.out.println(aTexto.apply(null));   // null (no lanza NPE)

        // ── Tipo 2: Método de instancia sobre el TIPO ────────────────────
        // El primer parámetro de la lambda se convierte en el receptor
        // del método.  x -> x.toUpperCase()  →  String::toUpperCase
        //
        // La firma es Function<String,String>:
        //   el tipo "String" antes de :: es el receptor (T),
        //   el resultado de toUpperCase() es el retorno (R).

        Function<String, String> aMayusculas = String::toUpperCase;
        System.out.println(aMayusculas.apply("hola"));  // HOLA

        // String.compareTo es un método de instancia que recibe otro String.
        // (a, b) -> a.compareTo(b)  →  String::compareTo
        // Encaja en Comparator<String> (que es BiFunction<String,String,int>):
        Comparator<String> compAlfabetico = String::compareTo;
        List<String> palabras = List.of("naranja", "manzana", "kiwi");
        palabras.stream().sorted(compAlfabetico).forEach(System.out::println);
        // kiwi, manzana, naranja

        // ── Tipo 3: Método de instancia sobre OBJETO CONCRETO ───────────
        // El receptor es un objeto ya creado (capturado en la referencia),
        // no un parámetro de la lambda.
        //
        // System.out es una instancia de PrintStream.
        // System.out::println  ≡  x -> System.out.println(x)
        Consumer<String> imprimir = System.out::println;
        imprimir.accept("Hola desde Consumer con referencia a objeto");

        // Objeto propio como receptor
        var formateador = new Formateador(">>> ");
        Function<String, String> conPrefijo = formateador::formatear;
        System.out.println(conPrefijo.apply("mensaje importante"));
        // >>> mensaje importante

        // ── Tipo 4: Constructor ──────────────────────────────────────────
        // new Clase(arg)  →  Clase::new
        // Encaja en Function<T, Clase> si el constructor recibe un T.

        Function<String, StringBuilder> crearSB = StringBuilder::new;
        StringBuilder sb = crearSB.apply("inicial");
        System.out.println(sb); // inicial

        // BiFunction para constructores con dos argumentos
        BiFunction<String, Double, Articulo> crearArticulo = Articulo::new;
        Articulo a = crearArticulo.apply("Monitor", 349.99);
        System.out.println(a);  // Articulo[nombre=Monitor, precio=349.99]
    }

    // ====================================================================
    // 2. UnaryOperator y BinaryOperator
    // ====================================================================
    // Son subinterfaces especializadas de Function y BiFunction para el
    // caso en que entrada y salida son del MISMO tipo.
    //
    //   UnaryOperator<T>  extends Function<T, T>
    //     → recibe T, devuelve T
    //     → método: T apply(T t)
    //
    //   BinaryOperator<T> extends BiFunction<T, T, T>
    //     → recibe dos T, devuelve T
    //     → método: T apply(T t1, T t2)
    //     → métodos estáticos: maxBy(Comparator), minBy(Comparator)
    //
    // Ventaja: la firma es más concisa y expresiva cuando ambos tipos
    // son idénticos. List.replaceAll() y Stream.reduce() los usan.
    // ====================================================================
    static void demoOperadores() {
        System.out.println("\n========== UnaryOperator y BinaryOperator ==========");

        // ── UnaryOperator ────────────────────────────────────────────────
        UnaryOperator<String> recortar   = String::trim;
        UnaryOperator<String> aMayus     = String::toUpperCase;

        // andThen() heredado de Function: encadena transformaciones del mismo tipo
        UnaryOperator<String> normalizar =
                recortar.andThen(aMayus)::apply;
        // Nota: ::apply convierte la Function<String,String> resultante
        // de andThen en un UnaryOperator<String> de nuevo.

        System.out.println(recortar.apply("  hola  "));           // "hola"
        System.out.println(aMayus.apply("hola"));                 // "HOLA"
        System.out.println(normalizar.apply("  hola mundo  "));   // "HOLA MUNDO"

        // List.replaceAll() recibe un UnaryOperator para transformar cada elemento
        var nombres = new java.util.ArrayList<>(List.of("  ana", "LUIS  ", "  marta  "));
        nombres.replaceAll(normalizar);
        System.out.println(nombres);  // [ANA, LUIS, MARTA]

        // ── BinaryOperator ───────────────────────────────────────────────
        BinaryOperator<Integer> sumar  = Integer::sum;
        BinaryOperator<Integer> max    = Integer::max;
        BinaryOperator<String>  concat = (a, b) -> a + b;

        System.out.println(sumar.apply(3, 4));      // 7
        System.out.println(max.apply(10, 25));       // 25
        System.out.println(concat.apply("Ho", "la")); // Hola

        // Stream.reduce() usa BinaryOperator para acumular:
        // el acumulador recibe (resultado_parcial, siguiente_elemento)
        // y devuelve el nuevo resultado parcial — siempre el mismo tipo.
        List<Integer> numeros = List.of(1, 2, 3, 4, 5);

        int suma = numeros.stream().reduce(0, sumar);
        System.out.println("Suma: " + suma);           // 15

        int producto = numeros.stream().reduce(1, (a, b) -> a * b);
        System.out.println("Producto: " + producto);  // 120

        // ── BinaryOperator.maxBy / minBy ─────────────────────────────────
        // Métodos de fábrica estáticos que crean un BinaryOperator que
        // elige el mayor (o menor) de dos elementos según un Comparator.
        BinaryOperator<String> masLarga =
                BinaryOperator.maxBy(Comparator.comparingInt(String::length));

        System.out.println(masLarga.apply("Java", "JavaScript")); // JavaScript
        System.out.println(masLarga.apply("Python", "Go"));       // Python
    }

    // ====================================================================
    // 3. Funciones especializadas para PRIMITIVOS
    // ====================================================================
    // Las interfaces genéricas (Function<Integer, Integer>) usan autoboxing:
    // cada int se convierte en un objeto Integer y viceversa.
    // En bucles intensivos esto genera muchos objetos temporales y presión
    // sobre el GC.
    //
    // Java proporciona variantes especializadas que trabajan directamente
    // con los tipos primitivos int, long y double:
    //
    //  Categoría          Ejemplos
    //  ─────────────────────────────────────────────────────────────────
    //  Función T→prim     ToIntFunction<T>    ToLongFunction<T>
    //                     ToDoubleFunction<T>
    //  Función prim→R     IntFunction<R>      LongFunction<R>
    //                     DoubleFunction<R>
    //  Operador unario    IntUnaryOperator    LongUnaryOperator
    //  Operador binario   IntBinaryOperator   LongBinaryOperator
    //  Predicado          IntPredicate        LongPredicate
    //  Consumidor         IntConsumer         LongConsumer
    //  Proveedor          IntSupplier         LongSupplier
    //  ─────────────────────────────────────────────────────────────────
    //
    // Regla práctica: si el dato es int/long/double y el rendimiento
    // importa (colecciones grandes, cálculos intensivos), usa la variante
    // especializada. Para APIs de alto nivel y tipos de negocio, las
    // versiones genéricas son perfectas.
    // ====================================================================
    static void demoPrimitivos() {
        System.out.println("\n========== Funciones especializadas para primitivos ==========");

        // ── ToIntFunction<T> : T → int (sin boxing del resultado) ───────
        // Ejemplo: obtener la longitud (int) de un String sin crear Integer
        ToIntFunction<String> longitud = String::length;
        System.out.println(longitud.applyAsInt("Hola"));  // 4

        // ── ToDoubleFunction<T> : T → double ────────────────────────────
        ToDoubleFunction<Articulo> obtenerPrecio = Articulo::precio;
        var teclado = new Articulo("Teclado", 49.99);
        System.out.println(obtenerPrecio.applyAsDouble(teclado));  // 49.99

        // ── IntFunction<R> : int → R ─────────────────────────────────────
        // Crea un array del tamaño pedido (patrón habitual en Stream.toArray)
        IntFunction<String[]> crearArray = String[]::new;
        String[] arr = crearArray.apply(3);
        System.out.println("Array de " + arr.length + " posiciones");  // 3

        // Stream.toArray(IntFunction) usa exactamente este patrón:
        List<String> lista = List.of("a", "b", "c");
        String[] resultado = lista.stream().toArray(String[]::new);
        System.out.println(resultado.length);  // 3

        // ── IntUnaryOperator : int → int ─────────────────────────────────
        IntUnaryOperator duplicar    = n -> n * 2;
        IntUnaryOperator incrementar = n -> n + 1;

        // andThen/compose también disponibles en las variantes especializadas
        IntUnaryOperator duplicarLuegSumar = duplicar.andThen(incrementar);
        System.out.println(duplicarLuegSumar.applyAsInt(5));  // 11 (5*2+1)

        // ── IntBinaryOperator : (int, int) → int ─────────────────────────
        IntBinaryOperator potencia = (base, exp) -> (int) Math.pow(base, exp);
        System.out.println(potencia.applyAsInt(2, 10));  // 1024

        // ── IntPredicate : int → boolean ─────────────────────────────────
        IntPredicate esPar      = n -> n % 2 == 0;
        IntPredicate esPositivo = n -> n > 0;
        IntPredicate esParYPositivo = esPar.and(esPositivo);

        System.out.println(esParYPositivo.test(4));   // true
        System.out.println(esParYPositivo.test(-4));  // false
        System.out.println(esParYPositivo.test(3));   // false

        // ── Comparación: con boxing vs sin boxing ────────────────────────
        // Con boxing (Function<Integer,Integer>): crea objetos Integer
        Function<Integer, Integer> triplicarBoxed = n -> n * 3;

        // Sin boxing (IntUnaryOperator): opera sobre primitivos directamente
        IntUnaryOperator triplicarPrim = n -> n * 3;

        // El resultado es el mismo; la diferencia es rendimiento en volumen
        System.out.println(triplicarBoxed.apply(7));         // 21
        System.out.println(triplicarPrim.applyAsInt(7));     // 21
    }

    // ====================================================================
    // 4. Currying y aplicación parcial
    // ====================================================================
    // CURRYING: transformar una función de N argumentos en una cadena
    // de N funciones de 1 argumento cada una.
    //
    //   f(a, b)  →  f(a)(b)
    //
    // En Java se expresa con funciones que devuelven funciones:
    //   Function<A, Function<B, R>>
    //
    // APLICACIÓN PARCIAL (partial application): fijar uno o varios
    // argumentos de antemano y obtener una función con menos argumentos.
    //
    //   f(a, b)  →  g(b)  donde g = f con 'a' ya fijado
    //
    // ¿Para qué sirven?
    //   - Reutilizar lógica fijando el contexto (p.ej. el prefijo de log,
    //     el tipo de impuesto, el separador CSV…)
    //   - Evitar pasar el mismo argumento repetidamente
    //   - Construir funciones especializadas desde una función genérica
    // ====================================================================
    static void demoCurrying() {
        System.out.println("\n========== Currying y aplicación parcial ==========");

        // ── Currying manual ──────────────────────────────────────────────
        // Función de 2 args:  (base, exponente) → base^exponente
        // Currificada:        base → (exponente → base^exponente)

        Function<Integer, Function<Integer, Integer>> potencia =
                base -> exponente -> (int) Math.pow(base, exponente);

        // Aplicación completa: potencia(2)(10)
        System.out.println(potencia.apply(2).apply(10));   // 1024
        System.out.println(potencia.apply(3).apply(3));    //   27

        // Aplicación parcial: fijamos la base y obtenemos una función
        // especializada que solo necesita el exponente.
        Function<Integer, Integer> potenciasDeDos = potencia.apply(2);
        System.out.println(potenciasDeDos.apply(8));   // 256
        System.out.println(potenciasDeDos.apply(16));  // 65536

        Function<Integer, Integer> potenciasDeDiez = potencia.apply(10);
        System.out.println(potenciasDeDiez.apply(3));  // 1000
        System.out.println(potenciasDeDiez.apply(6));  // 1000000

        // ── Aplicación parcial con BiFunction ────────────────────────────
        // Método de utilidad que fija el primer argumento de una BiFunction
        // y devuelve una Function con solo el segundo argumento libre.
        BiFunction<String, String, String> formatear =
                (plantilla, valor) -> plantilla.formatted(valor);

        // Fijamos la plantilla; solo queda pasar el valor
        Function<String, String> enParentesis = fijarPrimerArg(formatear, "(%s)");
        Function<String, String> enCorchetes  = fijarPrimerArg(formatear, "[%s]");
        Function<String, String> enComillas   = fijarPrimerArg(formatear, "\"%s\"");

        System.out.println(enParentesis.apply("Java"));    // (Java)
        System.out.println(enCorchetes.apply("Java"));     // [Java]
        System.out.println(enComillas.apply("Java"));      // "Java"

        // ── Caso de uso real: logger con nivel fijado ────────────────────
        BiFunction<String, String, String> log =
                (nivel, mensaje) -> "[%s] %s".formatted(nivel, mensaje);

        Function<String, String> logInfo  = fijarPrimerArg(log, "INFO ");
        Function<String, String> logError = fijarPrimerArg(log, "ERROR");
        Function<String, String> logDebug = fijarPrimerArg(log, "DEBUG");

        System.out.println(logInfo.apply("Aplicación iniciada"));
        // [INFO ] Aplicación iniciada
        System.out.println(logError.apply("Conexión rechazada"));
        // [ERROR] Conexión rechazada
        System.out.println(logDebug.apply("Valor de x = 42"));
        // [DEBUG] Valor de x = 42

        // ── Currying de 3 argumentos ─────────────────────────────────────
        // Java no tiene soporte nativo para funciones de más de 2 args,
        // pero se puede expresar anidando Function.
        Function<Double, Function<Double, Function<Double, Double>>> imc =
                peso -> altura -> factor ->
                        peso / (altura * altura) * factor;

        // Uso completo: imc(70)(1.75)(1.0)
        System.out.printf("IMC: %.2f%n", imc.apply(70.0).apply(1.75).apply(1.0));
        // IMC: 22.86

        // Función especializada para una altura concreta (1.70 m)
        Function<Double, Function<Double, Double>> imcAltura170 = imc.apply(70.0);
        System.out.printf("IMC (70kg, 1.70m): %.2f%n",
                imcAltura170.apply(1.70).apply(1.0));
        // IMC (70kg, 1.70m): 24.22
    }

    // ====================================================================
    // 5. Wrapper para checked exceptions
    // ====================================================================
    // Las interfaces funcionales del JDK (Function, Predicate, etc.) NO
    // declaran checked exceptions en sus métodos abstractos.
    // Esto impide usarlas directamente cuando el código lanza una checked
    // exception (IOException, SQLException, ParseException…).
    //
    // Soluciones habituales:
    //
    //  A) Capturar la excepción dentro de la lambda y relanzar como
    //     unchecked (RuntimeException). Rápido pero pierde la verificación
    //     de compilador.
    //
    //  B) Crear una interfaz funcional propia que declare la excepción
    //     (@FunctionalInterface + throws Exception). Se mostró en App.java.
    //
    //  C) Wrapper genérico: un método de utilidad que convierte cualquier
    //     FunctionConExcepcion<T,R,E> en una Function<T,R> normal,
    //     capturando y relanzando automáticamente. Limpio y reutilizable.
    //
    // Esta demo muestra los tres patrones.
    // ====================================================================
    static void demoWrapperExcepciones() {
        System.out.println("\n========== Wrapper para checked exceptions ==========");

        // ── Patrón A: captura inline ─────────────────────────────────────
        // Rápido, pero mezcla lógica de dominio con manejo de errores.
        Function<String, Integer> parsearConTryCatch = texto -> {
            try {
                return Integer.parseInt(texto.trim());
            } catch (NumberFormatException e) {
                throw new RuntimeException("No es un número: " + texto, e);
            }
        };

        System.out.println(parsearConTryCatch.apply("  42  ")); // 42
        try {
            parsearConTryCatch.apply("abc");
        } catch (RuntimeException e) {
            System.out.println("Capturado: " + e.getMessage()); // No es un número: abc
        }

        // ── Patrón B: interfaz propia con throws ─────────────────────────
        // FunctionChecked<T,R> está definida al final de esta clase.
        // La lambda puede lanzar cualquier checked exception.
        FunctionChecked<String, Integer> parsearChecked = texto -> {
            if (texto == null || texto.isBlank()) {
                throw new java.text.ParseException("Texto vacío o nulo", 0);
            }
            return Integer.parseInt(texto.trim());
        };

        try {
            System.out.println(parsearChecked.apply("  99  ")); // 99
            parsearChecked.apply("");                           // lanza ParseException
        } catch (Exception e) {
            System.out.println("ParseException: " + e.getMessage()); // Texto vacío o nulo
        }

        // ── Patrón C: wrapper genérico ───────────────────────────────────
        // wrapChecked() convierte FunctionChecked en Function<T,R> normal.
        // La lambda puede lanzar checked exceptions; el wrapper las captura
        // y las relanza como RuntimeException sin que el llamador lo note.
        //
        // Ventaja: las lambdas en stream().map() pueden ahora lanzar
        // checked exceptions sin contaminar el código del stream.

        Function<String, Integer> parsear = wrapChecked(texto -> {
            if (texto == null || texto.isBlank()) {
                throw new java.text.ParseException("vacío", 0);
            }
            return Integer.parseInt(texto.trim());
        });

        List<String> entradas = List.of("1", "2", "3", "4", "5");
        int suma = entradas.stream()
                .map(parsear)
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("Suma: " + suma); // 15

        // Un elemento inválido propagará RuntimeException
        try {
            List.of("1", "abc", "3").stream()
                    .map(parsear)
                    .forEach(System.out::println);
        } catch (RuntimeException e) {
            // Si la excepción original era RuntimeException se relanza tal cual (sin envolver),
            // por lo que getCause() puede ser null; usamos getMessage() directamente.
            Throwable causa = e.getCause() != null ? e.getCause() : e;
            System.out.println("Error en stream: " + causa.getMessage());
            // For input string: "abc"
        }
    }

    // ====================================================================
    // 6. Memoización
    // ====================================================================
    // La memoización es una técnica de optimización que consiste en
    // CACHEAR el resultado de una función para una entrada dada, de modo
    // que si se vuelve a llamar con la misma entrada se devuelva el
    // resultado almacenado en vez de recalcularlo.
    //
    // Es especialmente útil cuando:
    //   - El cálculo es costoso (llamadas a BD, criptografía, cálculos
    //     matemáticos intensivos como fibonacci recursivo…)
    //   - La función es PURA: misma entrada → siempre misma salida, sin
    //     efectos secundarios. Si no es pura, la caché puede devolver
    //     resultados obsoletos.
    //
    // Java no tiene soporte nativo, pero Map.computeIfAbsent() proporciona
    // exactamente la semántica que necesitamos:
    //   - Si la clave existe → devuelve el valor cacheado
    //   - Si no existe       → calcula, almacena y devuelve
    //
    // El método memoizar() de abajo envuelve cualquier Function<T,R> en
    // una versión memoizada con solo una línea en el punto de uso.
    // ====================================================================
    static void demoMemoizacion() {
        System.out.println("\n========== Memoización ==========");

        // ── Sin memoizar: fibonacci recursivo exponencial O(2^n) ─────────
        // Fibonacci recursivo puro tiene complejidad O(2^n): para fib(40)
        // hace ~330 millones de llamadas. Con memoización baja a O(n).

        // ── Con memoizar(): O(n) llamadas en total ────────────────────────
        // Nota: para que la memoización funcione en la recursión, la
        // función debe referenciarse a sí misma a través de la caché.
        // Usamos una variable de instancia (array de un elemento) como
        // truco para capturar la referencia antes de que esté asignada.
        @SuppressWarnings("unchecked")
        Function<Long, Long>[] ref = new Function[1];
        ref[0] = memoizar(n -> {
            if (n <= 1) return n;
            return ref[0].apply(n - 1) + ref[0].apply(n - 2);
        });

        System.out.println("fib(10) = " + ref[0].apply(10L));   // 55
        System.out.println("fib(30) = " + ref[0].apply(30L));   // 832040
        System.out.println("fib(50) = " + ref[0].apply(50L));   // 12586269025

        // ── Ejemplo con cálculo costoso simulado ─────────────────────────
        Function<String, String> consultaSimulada = memoizar(clave -> {
            System.out.println("  [consultando BD para clave: " + clave + "]");
            return "resultado_" + clave;  // simula consulta lenta
        });

        // Primera llamada → ejecuta el cuerpo (consulta BD)
        System.out.println(consultaSimulada.apply("usuario:42"));
        // Segunda llamada con la misma clave → devuelve del caché, sin consultar
        System.out.println(consultaSimulada.apply("usuario:42"));
        // Clave distinta → consulta de nuevo
        System.out.println(consultaSimulada.apply("usuario:99"));

        // ── Map.computeIfAbsent() en línea (sin wrapper) ─────────────────
        // Para casos puntuales donde no vale la pena crear un wrapper:
        Map<Integer, Long> cache = new HashMap<>();
        for (int i : List.of(5, 5, 10, 10, 5)) {
            long v = cache.computeIfAbsent(i, k -> {
                System.out.println("  [calculando factorial de " + k + "]");
                long acc = 1;
                for (int j = 2; j <= k; j++) acc *= j;
                return acc;
            });
            System.out.println(i + "! = " + v);
        }
        // Solo calcula factorial(5) y factorial(10) una vez cada uno.
    }

    // ====================================================================
    // Utilidades
    // ====================================================================

    /** Fija el primer argumento de una BiFunction y devuelve una Function. */
    static <A, B, R> Function<B, R> fijarPrimerArg(BiFunction<A, B, R> fn, A valorFijo) {
        return b -> fn.apply(valorFijo, b);
    }

    /**
     * Envuelve una FunctionChecked en una Function<T,R> normal.
     * Las checked exceptions se relanzarán como RuntimeException.
     */
    static <T, R> Function<T, R> wrapChecked(FunctionChecked<T, R> fn) {
        return t -> {
            try {
                return fn.apply(t);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    /**
     * Devuelve una versión memoizada de la función dada.
     *
     * Usamos containsKey/get/put en lugar de computeIfAbsent porque
     * HashMap lanza ConcurrentModificationException si el mapping function
     * de computeIfAbsent modifica el mapa durante su ejecución — algo que
     * ocurre exactamente en funciones recursivas como fibonacci.
     *
     * Thread-safety: NO es thread-safe (usar ConcurrentHashMap con
     * lógica de doble comprobación si hay acceso concurrente).
     */
    static <T, R> Function<T, R> memoizar(Function<T, R> fn) {
        Map<T, R> cache = new HashMap<>();
        return entrada -> {
            if (cache.containsKey(entrada)) {
                return cache.get(entrada);
            }
            R resultado = fn.apply(entrada);
            cache.put(entrada, resultado);
            return resultado;
        };
    }

    // ====================================================================
    // Tipos auxiliares
    // ====================================================================

    record Articulo(String nombre, double precio) {
        @Override
        public String toString() {
            return "Articulo[nombre=%s, precio=%.2f]".formatted(nombre, precio);
        }
    }

    static class Formateador {
        private final String prefijo;

        Formateador(String prefijo) {
            this.prefijo = prefijo;
        }

        String formatear(String texto) {
            return prefijo + texto;
        }
    }

    /**
     * Variante de Function que permite lanzar checked exceptions.
     * Complementa a las interfaces del JDK, que no pueden declararlas.
     */
    @FunctionalInterface
    interface FunctionChecked<T, R> {
        R apply(T t) throws Exception;
    }
}

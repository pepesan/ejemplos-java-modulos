package com.cursosdedesarrollo.funcional;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Interfaces funcionales del paquete java.util.function (Java 8+).
 *
 * Una INTERFAZ FUNCIONAL es cualquier interfaz que tenga exactamente
 * UN método abstracto. La anotación @FunctionalInterface es opcional
 * pero recomendable: hace que el compilador verifique ese requisito.
 *
 * Las interfaces funcionales son la base del estilo funcional en Java:
 * permiten pasar comportamiento (código) como si fuera un valor,
 * usando lambdas o referencias a métodos.
 *
 * Interfaces del paquete java.util.function más habituales:
 *
 *   Function<T,R>      recibe T,   devuelve R
 *   Predicate<T>       recibe T,   devuelve boolean
 *   Consumer<T>        recibe T,   no devuelve nada (void)
 *   Supplier<T>        no recibe,  devuelve T
 *   BiFunction<T,U,R>  recibe T+U, devuelve R
 */
public class App {

    public static void main(String[] args) {

        demoFunction();
        demoPredicate();
        demoConsumer();
        demoSupplier();
        demoBiFunction();
        demoInterfazPropia();
    }

    // ====================================================================
    // 1. Function<T, R>
    // ====================================================================
    // Representa una función que transforma un valor de tipo T en un
    // valor de tipo R.
    // Método principal:  R apply(T t)
    // Métodos de composición:
    //   andThen(Function after)  → aplica this primero, luego after
    //   compose(Function before) → aplica before primero, luego this
    // ====================================================================
    static void demoFunction() {
        System.out.println("========== Function<T,R> ==========");

        // Definición con lambda
        Function<String, Integer> longitud = cadena -> cadena.length();

        // Equivalente con referencia a método
        Function<String, Integer> longitudRef = String::length;

        System.out.println(longitud.apply("Hola"));       // 4
        System.out.println(longitudRef.apply("Hola"));    // 4

        // --- andThen ---
        // Encadena dos funciones: primero se ejecuta 'longitud',
        // después el resultado (Integer) se pasa a 'esPar'.
        // Flujo: String → Integer → String
        Function<String, String> longitudYParidad =
                longitud.andThen(n -> n % 2 == 0 ? "longitud par" : "longitud impar");

        System.out.println(longitudYParidad.apply("Java"));     // longitud par (4)
        System.out.println(longitudYParidad.apply("Hola!!"));   // longitud par (6)
        System.out.println(longitudYParidad.apply("Hi"));       // longitud par (2)
        System.out.println(longitudYParidad.apply("Ey"));       // longitud par (2)
        System.out.println(longitudYParidad.apply("Hey"));      // longitud impar (3)

        // --- compose ---
        // El orden es el inverso a andThen:
        // primero se ejecuta la función argumento, luego 'this'.
        // Flujo: String → String (trim) → Integer (length)
        Function<String, Integer> quitarEspaciosYMedir =
                longitud.compose(String::trim);

        System.out.println(quitarEspaciosYMedir.apply("  hola  ")); // 4, no 8

        // --- Encadenamiento largo ---
        // Function.identity() devuelve la misma entrada sin transformarla.
        // Útil como valor neutro al construir pipelines condicionalmente.
        Function<String, String> pipeline =
                Function.<String>identity()
                        .andThen(String::trim)
                        .andThen(String::toUpperCase)
                        .andThen(s -> s + "!");

        System.out.println(pipeline.apply("  hola mundo  ")); // HOLA MUNDO!
    }

    // ====================================================================
    // 2. Predicate<T>
    // ====================================================================
    // Representa una condición sobre un valor de tipo T.
    // Devuelve siempre boolean.
    // Método principal:  boolean test(T t)
    // Métodos de combinación:
    //   and(Predicate other)   → this && other
    //   or(Predicate other)    → this || other
    //   negate()               → !this
    //   not(Predicate p)       → !p  (método estático, Java 11)
    // ====================================================================
    static void demoPredicate() {
        System.out.println("\n========== Predicate<T> ==========");

        Predicate<String> estaVacia   = String::isEmpty;
        Predicate<String> empiezaConJ = s -> s.startsWith("J");

        // test() evalúa la condición
        System.out.println(estaVacia.test(""));       // true
        System.out.println(estaVacia.test("Java"));   // false

        // --- negate() ---
        // Invierte el resultado: equivale a escribir !estaVacia.test(s)
        Predicate<String> noEstaVacia = estaVacia.negate();
        System.out.println(noEstaVacia.test(""));      // false
        System.out.println(noEstaVacia.test("Java"));  // true

        // --- Predicate.not() (Java 11) ---
        // Forma estática y más legible de negar, especialmente en streams
        Predicate<String> noVaciaEstatica = Predicate.not(String::isEmpty);
        List<String> palabras = List.of("Java", "", "Funcional", "", "Streams");
        palabras.stream()
                .filter(Predicate.not(String::isEmpty))
                .forEach(System.out::println); // Java, Funcional, Streams

        // --- and() ---
        // Ambas condiciones deben cumplirse (cortocircuito: si la primera
        // es false, la segunda ni se evalúa)
        Predicate<String> lenguajeJavaLargo =
                empiezaConJ.and(s -> s.length() > 4);

        System.out.println(lenguajeJavaLargo.test("Java"));       // false (length=4, no >4)
        System.out.println(lenguajeJavaLargo.test("JavaScript")); // true
        System.out.println(lenguajeJavaLargo.test("Python"));     // false (no empieza por J)

        // --- or() ---
        // Basta con que una condición se cumpla (cortocircuito: si la
        // primera es true, la segunda ni se evalúa)
        Predicate<Integer> menorDeDiez = n -> n < 10;
        Predicate<Integer> esPar       = n -> n % 2 == 0;
        Predicate<Integer> menorDeDiezOPar = menorDeDiez.or(esPar);

        System.out.println(menorDeDiezOPar.test(5));   // true  (< 10)
        System.out.println(menorDeDiezOPar.test(12));  // true  (par)
        System.out.println(menorDeDiezOPar.test(11));  // false (>= 10 y impar)

        // --- Composición avanzada ---
        // Los predicados se pueden encadenar para construir reglas complejas
        // sin anidar ifs. Útil en validaciones de formularios, filtros de BD, etc.
        Predicate<String> esContrasenaValida =
                ((Predicate<String>) s -> s.length() >= 8)          // mínimo 8 caracteres
                .and(s -> s.chars().anyMatch(Character::isUpperCase)) // al menos una mayúscula
                .and(s -> s.chars().anyMatch(Character::isDigit));    // al menos un dígito

        System.out.println(esContrasenaValida.test("abc"));         // false
        System.out.println(esContrasenaValida.test("Abcdefg1"));    // true
        System.out.println(esContrasenaValida.test("abcdefg1"));    // false (sin mayúscula)
    }

    // ====================================================================
    // 3. Consumer<T>
    // ====================================================================
    // Representa una operación que consume un valor de tipo T y
    // no devuelve nada (produce efectos secundarios: log, escribir,
    // enviar email, actualizar BD...).
    // Método principal:  void accept(T t)
    // Método de encadenamiento:
    //   andThen(Consumer after) → ejecuta this y después after
    // ====================================================================
    static void demoConsumer() {
        System.out.println("\n========== Consumer<T> ==========");

        Consumer<String> imprimirMayusculas = s -> System.out.println(s.toUpperCase());
        Consumer<String> imprimirLongitud   = s -> System.out.println("Longitud: " + s.length());

        // accept() ejecuta la acción
        imprimirMayusculas.accept("hola");   // HOLA
        imprimirLongitud.accept("hola");     // Longitud: 4

        // --- andThen() ---
        // Ejecuta ambos consumers en secuencia sobre el mismo valor.
        // El primero no afecta al valor que recibe el segundo:
        // ambos reciben el valor original.
        Consumer<String> imprimirAmbos =
                imprimirMayusculas.andThen(imprimirLongitud);

        imprimirAmbos.accept("Java"); // JAVA  /  Longitud: 4

        // --- Uso real: pipeline de efectos secundarios ---
        // En lugar de anidar llamadas o duplicar código, se componen
        // consumers para construir un pipeline de acciones.
        Consumer<Producto> registrar   = p -> System.out.println("Registrado: "  + p.nombre());
        Consumer<Producto> auditar     = p -> System.out.println("Auditado: "    + p.nombre());
        Consumer<Producto> notificar   = p -> System.out.println("Notificado: "  + p.nombre());

        Consumer<Producto> procesarAlta = registrar.andThen(auditar).andThen(notificar);

        procesarAlta.accept(new Producto("Portátil", 999.0));
        // Registrado: Portátil
        // Auditado:   Portátil
        // Notificado: Portátil
    }

    // ====================================================================
    // 4. Supplier<T>
    // ====================================================================
    // Representa un proveedor de valores: no recibe ningún argumento
    // y devuelve un valor de tipo T cada vez que se invoca.
    // Método principal:  T get()
    //
    // Casos de uso típicos:
    //   - Inicialización diferida (lazy): crear el objeto solo cuando
    //     realmente se necesite, no antes.
    //   - Valores por defecto en Optional.orElseGet()
    //   - Generación de valores únicos (IDs, timestamps, randoms)
    // ====================================================================
    static void demoSupplier() {
        System.out.println("\n========== Supplier<T> ==========");

        // Supplier simple: devuelve siempre el mismo valor
        Supplier<String> saludo = () -> "Hola desde Supplier";
        System.out.println(saludo.get()); // Hola desde Supplier
        System.out.println(saludo.get()); // Hola desde Supplier (se puede invocar N veces)

        // Supplier que genera valores distintos en cada llamada
        Supplier<Double> aleatorio = Math::random;
        System.out.println(aleatorio.get()); // 0.xyz (distinto cada vez)
        System.out.println(aleatorio.get()); // 0.abc

        // --- Inicialización diferida (lazy initialization) ---
        // El objeto ConexionBD es costoso de crear. Con Supplier,
        // la creación se pospone hasta que realmente se necesite.
        // Si el código nunca llega a llamar a proveedor.get(),
        // el objeto jamás se crea.
        Supplier<ConexionBD> proveedor = () -> {
            System.out.println("  [creando conexión — operación costosa]");
            return new ConexionBD("jdbc:postgresql://localhost/mibd");
        };

        System.out.println("Antes de necesitar la conexión...");
        // Aquí todavía no se ha creado nada
        boolean necesitaConexion = true;
        if (necesitaConexion) {
            ConexionBD conexion = proveedor.get(); // solo aquí se crea
            System.out.println("Usando: " + conexion.url());
        }

        // --- Optional.orElseGet() vs orElse() ---
        // orElse(valor)         → evalúa 'valor' SIEMPRE, aunque Optional tenga dato
        // orElseGet(supplier)   → evalúa el Supplier SOLO si Optional está vacío
        // Para valores costosos de calcular, usar siempre orElseGet.
        var opcional = java.util.Optional.of("dato presente");
        String resultado = opcional.orElseGet(() -> calcularValorCostoso());
        System.out.println(resultado); // dato presente (calcularValorCostoso NO se ejecuta)
    }

    // ====================================================================
    // 5. BiFunction<T, U, R>
    // ====================================================================
    // Como Function pero recibe DOS argumentos (T y U) y devuelve R.
    // Método principal:  R apply(T t, U u)
    // Método de composición:
    //   andThen(Function after) → transforma el resultado de BiFunction
    //
    // Nota: existe también BiPredicate<T,U> y BiConsumer<T,U> para
    // las variantes de Predicate y Consumer con dos argumentos.
    // ====================================================================
    static void demoBiFunction() {
        System.out.println("\n========== BiFunction<T,U,R> ==========");

        // Combina dos Strings en uno
        BiFunction<String, String, String> concatenar =
                (a, b) -> a + " " + b;

        System.out.println(concatenar.apply("Hola", "Mundo")); // Hola Mundo

        // Calcula el precio final aplicando un descuento porcentual
        BiFunction<Double, Double, Double> precioConDescuento =
                (precio, pctDescuento) -> precio - (precio * pctDescuento / 100);

        System.out.println(precioConDescuento.apply(100.0, 20.0)); // 80.0
        System.out.println(precioConDescuento.apply(250.0, 15.0)); // 212.5

        // --- andThen() en BiFunction ---
        // Primero aplica la BiFunction (Double), después le pasa el resultado
        // a una Function que lo formatea como String.
        BiFunction<Double, Double, String> precioFormateado =
                precioConDescuento.andThen(precio -> String.format("%.2f €", precio));

        System.out.println(precioFormateado.apply(100.0, 20.0)); // 80,00 €
        System.out.println(precioFormateado.apply(250.0, 15.0)); // 212,50 €

        // --- Uso real: combinar dos objetos en un tercero ---
        BiFunction<String, Double, Producto> crearProducto = Producto::new;

        Producto p = crearProducto.apply("Teclado", 49.99);
        System.out.println(p.nombre() + " — " + p.precio() + " €");
    }

    // ====================================================================
    // 6. Interfaz funcional propia con @FunctionalInterface
    // ====================================================================
    // Cualquier interfaz con un único método abstracto ES una interfaz
    // funcional, pero la anotación @FunctionalInterface:
    //   a) Documenta la intención explícitamente
    //   b) Hace que el compilador falle si alguien añade un segundo
    //      método abstracto por error
    //
    // Cuándo crear una propia en vez de reutilizar las del JDK:
    //   - El nombre mejora la legibilidad del dominio
    //     (Validator<T> es más claro que Predicate<T> en un contexto de validación)
    //   - La firma no encaja (p.ej. lanza checked exceptions)
    //   - Necesitas semántica específica que documentar
    // ====================================================================
    static void demoInterfazPropia() {
        System.out.println("\n========== @FunctionalInterface propia ==========");

        // Uso de la interfaz Transformador<E, S> definida al final del fichero.
        // La lambda implementa el único método abstracto: S transformar(E entrada)
        Transformador<String, Integer> contarPalabras =
                texto -> texto.trim().split("\\s+").length;

        System.out.println(contarPalabras.transformar("hola mundo Java")); // 3
        System.out.println(contarPalabras.transformar("una sola"));        // 2

        // Otro uso de la misma interfaz con tipos distintos
        Transformador<Integer, String> aRomano = n -> switch (n) {
            case 1 -> "I";
            case 4 -> "IV";
            case 5 -> "V";
            case 9 -> "IX";
            case 10 -> "X";
            default -> "?";
        };

        System.out.println(aRomano.transformar(4));  // IV
        System.out.println(aRomano.transformar(9));  // IX
        System.out.println(aRomano.transformar(10)); // X

        // Validador con checked exception declarada en la firma.
        // Predicate<T> no puede lanzar checked exceptions, pero una
        // interfaz propia sí puede declararlas.
        ValidadorConExcepcion<String> validarEmail = email -> {
            if (!email.contains("@")) {
                throw new Exception("Email inválido: falta @");
            }
            return true;
        };

        try {
            System.out.println(validarEmail.validar("usuario@dominio.com")); // true
            System.out.println(validarEmail.validar("usuariodominio.com"));  // lanza Exception
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // --------------------------------------------------------------------
    // Clases e interfaces auxiliares
    // --------------------------------------------------------------------

    record Producto(String nombre, double precio) {}

    // Simula un recurso costoso de inicializar
    record ConexionBD(String url) {}

    static String calcularValorCostoso() {
        System.out.println("  [cálculo costoso ejecutado]");
        return "valor calculado";
    }

    /**
     * Interfaz funcional genérica propia.
     * Transforma un valor de tipo E (entrada) en un valor de tipo S (salida).
     * Equivale semánticamente a Function<E,S>, pero con un nombre de dominio
     * más expresivo.
     */
    @FunctionalInterface
    interface Transformador<E, S> {
        S transformar(E entrada);

        // Puede tener métodos default sin dejar de ser funcional
        default Transformador<E, S> conLog() {
            return entrada -> {
                System.out.println("  [transformando: " + entrada + "]");
                return this.transformar(entrada);
            };
        }
    }

    /**
     * Interfaz funcional que declara una checked exception.
     * Útil cuando la operación puede fallar de forma comprobada
     * (lectura de fichero, llamada HTTP, parseo...).
     * Predicate<T> no permite esto porque su método test() no declara excepciones.
     */
    @FunctionalInterface
    interface ValidadorConExcepcion<T> {
        boolean validar(T valor) throws Exception;
    }
}

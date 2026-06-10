package com.cursosdedesarrollo;

import javax.swing.*;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.random.RandomGeneratorFactory;

/**
 * Hello world!
 *
 */
public class App
{
    private static void formatForLocale(Locale locale) {
        List<Integer> numbers = List.of(1500, 1500000, 1200000000);
        System.out.printf("-- SHORT format for locale=%s --%n", locale);
        numbers.stream().forEach((num) -> {
            NumberFormat nf = NumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.SHORT);
            String format = nf.format(num);
            System.out.println(format);
        });
        System.out.printf("-- LONG format for locale=%s --%n", locale);
        numbers.stream().forEach((num) -> {
            NumberFormat nf = NumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.LONG);
            String format = nf.format(num);
            System.out.println(format);
        });
    }
    private static void formatForLocaleHalf(Locale locale) {
        List<Integer> numbers = List.of(1500, 1500000, 1200000000);
        System.out.printf("-- SHORT format for locale=%s --%n", locale);
        numbers.stream().forEach((num) -> {
            NumberFormat nf = NumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.SHORT);
            nf.setRoundingMode(RoundingMode.HALF_DOWN);
            String format = nf.format(num);
            System.out.println(format);
        });
        System.out.printf("-- LONG format for locale=%s --%n", locale);
        numbers.stream().forEach((num) -> {
            NumberFormat nf = NumberFormat.getCompactNumberInstance(locale, NumberFormat.Style.LONG);
            nf.setRoundingMode(RoundingMode.HALF_DOWN);
            String format = nf.format(num);
            System.out.println(format);
        });
    }
    private static void printThreadInfo(String desc) {
        System.out.printf("%s, Thread: %s%n", desc, Thread.currentThread().getName());
    }

    private static void parseWithGrouping(boolean grouping) throws ParseException {
        System.out.printf("-- grouping=%s  ---%n", grouping);
        NumberFormat nf = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        nf.setGroupingUsed(grouping);
        Number num = nf.parse("1,00K");
        System.out.println(num);
        num = nf.parse("1,00M");
        System.out.println(num);
        num = nf.parse("1,00B");
        System.out.println(num);
    }
    public static void main( String[] args ) throws ParseException, InterruptedException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException, java.io.IOException {
        System.out.println( "Ejemplos Misc" );
        // JAVA 12
        // formateo por locale
        // por defecto usa redondeo
        formatForLocale(Locale.US);
        formatForLocale(new Locale("es_ES"));
        // Redondeo por la mitad hacia abajo
        formatForLocaleHalf(Locale.US);
        formatForLocaleHalf(new Locale("es_ES"));

        // puede lanzar una ParseException
        NumberFormat nf = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        System.out.println("-- short compact parsing --");
        Number num = nf.parse("1K");
        System.out.println(num);
        num = nf.parse("1M");
        System.out.println(num);
        num = nf.parse("1B");
        System.out.println(num);

        // pueden agruparse
        parseWithGrouping(false);
        parseWithGrouping(true);


        // Java 12
        // Manejo de excepciones asíncronas con CompletionStage / CompletableFuture
        ExecutorService executor = Executors.newSingleThreadExecutor();

        CompletableFuture.supplyAsync(() -> {
                    // Tarea inicial: se ejecuta en el commonPool por defecto (hilo distinto al main)
                    printThreadInfo("division task");
                    // Provoca ArithmeticException (división entre cero)
                    return 10 / 0;
                })
                // Si la etapa anterior falla, esta recuperación se ejecuta de forma asíncrona
                // usando el executor proporcionado (no el commonPool).
                .exceptionallyAsync(exception -> {
                            printThreadInfo("exceptionally Async");
                            System.err.println("exception: " + exception); // Log de la excepción
                            // Valor de fallback para continuar la cadena
                            return 1;
                        }, executor
                )
                // Transforma el resultado (ya recuperado). Se ejecutará cuando
                // termine la etapa anterior (normalmente en el mismo executor de la recuperación).
                .thenApply(input -> {
                    printThreadInfo("multiply task");
                    return input * 3; // 1 * 3 = 3
                })
                // Consume el resultado final sin devolver nada.
                .thenAccept(System.out::println); // Imprime: 3

        // Pausa el hilo principal para dar tiempo a que terminen las etapas asíncronas.
        // (Alternativa más robusta: bloquear explícitamente con join()/get()).
        Thread.sleep(2000);

        executor.shutdown(); // Cierre ordenado del executor


        // Java 12
        // Selector de ficheros
        /*
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        JFileChooser chooser = new JFileChooser();
        FileSystemView view = chooser.getFileSystemView();
        System.out.println("The shortcut panel files: ");
        File[] chooserShortcutPanelFiles = view.getChooserShortcutPanelFiles();
        for (File chooserShortcutPanelFile : chooserShortcutPanelFiles) {
            System.out.println(chooserShortcutPanelFile);
        }
        chooser.showOpenDialog(null);

         */

        // Java 14
        System.out.println("instanceof");
        // manejo de instanceof
        Object o = new String("Hola!!");
//        if(o instanceof String){
//            String s1 = (String) o;
//            if(s1.length()>5 ){
//                System.out.println("cadena: "+ s1);
//            }
//        }
        if (o instanceof String s1 && s1.length() > 5) {
            System.out.println("cadena: "+ s1);
        }

        // Java 14 — NullPointerException con mensajes detallados (JEP 358)
        // ----------------------------------------------------------------
        // Antes de Java 14 el mensaje era simplemente "NullPointerException"
        // sin indicar QUÉ referencia era null. El programador tenía que
        // deducirlo mirando el stack trace y el código fuente.
        //
        // Desde Java 14 el mensaje indica:
        //   - Qué operación se intentó (invocar método, leer campo, etc.)
        //   - Qué referencia era null y cómo se obtuvo
        //
        // Esta feature se llama "Helpful NullPointerExceptions" y está
        // activada por defecto desde Java 14.
        // ----------------------------------------------------------------

        System.out.println("\n--- NPE con variable directamente null ---");
        // Mensaje antiguo:  NullPointerException  (sin más información)
        // Mensaje Java 14+: Cannot invoke "String.length()"
        //                   because "firstName" is null
        try {
            String firstName = null;
            System.out.println(firstName.length());
        } catch (NullPointerException e) {
            System.out.println("NPE capturada: " + e.getMessage());
        }

        System.out.println("\n--- NPE en acceso a array null ---");
        // Mensaje Java 14+: Cannot store to int array
        //                   because "numeros" is null
        try {
            int[] numeros = null;
            numeros[0] = 5;
        } catch (NullPointerException e) {
            System.out.println("NPE capturada: " + e.getMessage());
        }

        System.out.println("\n--- NPE en cadena de llamadas (el caso más útil) ---");
        // Este es el escenario que más se beneficia del cambio.
        // Antes era imposible saber cuál de los eslabones de la cadena era null
        // sin añadir prints o un debugger.
        //
        // Mensaje Java 14+: Cannot invoke "com.cursosdedesarrollo.App$Direccion.getCiudad()"
        //                   because the return value of
        //                   "com.cursosdedesarrollo.App$Persona.getDireccion()" is null
        //
        // → El mensaje señala exactamente que getDireccion() devolvió null,
        //   no que persona fuera null ni que getCiudad() fallara por otra razón.
        try {
            Persona persona = new Persona("Ana", null); // dirección intencionadamente null
            String ciudad = persona.getDireccion().getCiudad(); // falla aquí
            System.out.println(ciudad);
        } catch (NullPointerException e) {
            System.out.println("NPE capturada: " + e.getMessage());
        }

        System.out.println("\n--- NPE en cadena más larga ---");
        // Con tres niveles: empresa → departamento → jefe → nombre
        // El mensaje indicará cuál de los tres retornó null.
        try {
            Empresa empresa = new Empresa("Acme", new Departamento("IT", null));
            // jefe es null → falla al llamar .getNombre() sobre él
            String nombreJefe = empresa.getDepartamento().getJefe().getNombre();
            System.out.println(nombreJefe);
        } catch (NullPointerException e) {
            System.out.println("NPE capturada: " + e.getMessage());
        }

        // Java 18 — UTF-8 como charset por defecto (JEP 400)
        // Antes de Java 18 el charset dependía del SO (Cp1252 en Windows, UTF-8 en Linux).
        // Desde Java 18 siempre es UTF-8. Para volver al comportamiento anterior: -Dfile.encoding=COMPAT
        System.out.println("\n--- Java 18: UTF-8 por defecto (JEP 400) ---");
        System.out.println("Charset por defecto: " + java.nio.charset.Charset.defaultCharset());
        System.out.println("file.encoding:       " + System.getProperty("file.encoding"));
        System.out.println("stdout.encoding:     " + System.getProperty("stdout.encoding"));

        java.nio.file.Path tmpFile = java.nio.file.Files.createTempFile("jep400", ".txt");
        String textoUnicode = "Caracteres Unicode: ñ, ü, 中文, 日本語, العربية";
        java.nio.file.Files.writeString(tmpFile, textoUnicode);
        String leido = java.nio.file.Files.readString(tmpFile);
        System.out.println("Escrito:  " + textoUnicode);
        System.out.println("Leído:    " + leido);
        System.out.println("Iguales:  " + textoUnicode.equals(leido));
        java.nio.file.Files.deleteIfExists(tmpFile);

        // Java 17
        // Mejoras en la generación de números pseudo aleatorios
        System.out.println("Random");
        int ret = RandomGeneratorFactory.of("Random").create().nextInt(0, 11);
        System.out.println(ret);

        // JAVA 18
        // comentarios javadoc con código

        // Java 21
        String string = "hola";
        try {
            int number = Integer.parseInt(string);
            // ojo al _
        } catch (NumberFormatException _) {
            System.err.println("Not a number");
        }


    }

    // Clases auxiliares para los ejemplos de NPE con cadena de llamadas

    static class Direccion {
        private final String ciudad;
        Direccion(String ciudad) { this.ciudad = ciudad; }
        public String getCiudad() { return ciudad; }
    }

    static class Persona {
        private final String nombre;
        private final Direccion direccion;
        Persona(String nombre, Direccion direccion) {
            this.nombre = nombre;
            this.direccion = direccion;
        }
        public String getNombre()      { return nombre; }
        public Direccion getDireccion() { return direccion; }
    }

    static class Empleado {
        private final String nombre;
        Empleado(String nombre) { this.nombre = nombre; }
        public String getNombre() { return nombre; }
    }

    static class Departamento {
        private final String nombre;
        private final Empleado jefe;
        Departamento(String nombre, Empleado jefe) {
            this.nombre = nombre;
            this.jefe = jefe;
        }
        public String getNombre()    { return nombre; }
        public Empleado getJefe()    { return jefe; }
    }

    static class Empresa {
        private final String nombre;
        private final Departamento departamento;
        Empresa(String nombre, Departamento departamento) {
            this.nombre = nombre;
            this.departamento = departamento;
        }
        public String getNombre()           { return nombre; }
        public Departamento getDepartamento() { return departamento; }
    }
}

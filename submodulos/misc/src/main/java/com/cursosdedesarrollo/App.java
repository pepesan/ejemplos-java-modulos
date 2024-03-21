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
    public static void main( String[] args ) throws ParseException, InterruptedException, UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
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
        // manejo de excepciones asíncrona CompletionStage
        ExecutorService executor = Executors.newSingleThreadExecutor();
        CompletableFuture.supplyAsync(() -> {
            printThreadInfo("division task");
            return 10 / 0;
        }).exceptionallyAsync(exception -> {
                    printThreadInfo("exceptionally Async");
                    System.err.println("exception: " + exception);
                    return 1;
                }, executor
        ).thenApply(input -> {
            printThreadInfo("multiply task");
            return input * 3;
        }).thenAccept(System.out::println);

        Thread.sleep(2000);//let the stages complete
        executor.shutdown();

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
        // manejo de instanceof
        Object o = new String("Hola!!");
//        if(o instanceof String){
//            String s1 = (String) o;
//            if(s1.length()>5 ){
//                System.out.println("cadena: "+ s1);
//            }
//        }
        if (o instanceof String s && s.length() > 5) {
            System.out.println("cadena: "+ s);
        }

        // Java 14
        // mejor salida de error de NullPointerException
        // ofrece el objeto que la lanza
        /*
        String firstName = null;
        System.out.println(firstName.length());
         */

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
}

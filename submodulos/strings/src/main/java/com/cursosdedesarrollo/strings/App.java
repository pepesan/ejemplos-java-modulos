package com.cursosdedesarrollo.strings;

import java.util.Base64;

import static java.lang.StringTemplate.STR;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Ejemplos de Strings!" );
        // JAVA 11
        // quitar espacios en blanco
        String s = "  test string  ";
        System.out.printf("'%s'%n", s);
        // quitar espacios en blanco de Unicode
        String striped = s.strip();
        System.out.printf("strip():%n '%s'%n", striped);
        // quitar espacios en blanco de ASCII
        String trimmed = s.trim();
        System.out.printf("trim():%n '%s'%n", trimmed);
        // quitar desde el principio
        striped = s.stripLeading();
        System.out.printf("stripLeading():%n '%s'%n", striped);
        // quitar desde el final
        striped = s.stripTrailing();
        System.out.printf("stripTrailing():%n '%s'%n", striped);

        // ¿Está en blanco?
        System.out.println("isBlank");
        s = "";
        boolean blank = s.isBlank();
        System.out.println(blank);
        s = " ";
        blank = s.isBlank();
        System.out.println(blank);

        // líneas de la cadena
        System.out.println("lines");
        s = "jujube\nsatsuma\nguava";
        s.lines()
                .forEach(System.out::println);
        // repetir?
        s = "--";
        String newString = s.repeat(10);
        System.out.println(newString);
        // Uso de emojis en Strings
        s = "\uD83E\uDD29 Star-Struck";
        System.out.println(s); //GRINNING FACE EMOJI
        s = "\uD83D\uDE00 Smiling face";
        System.out.println(s); //Smiling FACE EMOJI
        s = "🧛 Vampire";
        System.out.println(s); //Vampire EMOJI
        s= "\uD83D\uDC83 Woman Dancing";
        System.out.println(s); //Woman Dancing EMOJI

        // De momento no funciona XD (Feb  2022)
        //String Vampire🧛 = "\uD83E\uDDDB";


        // Java 12
        // indentado
        String str = "a test string";
        System.out.println(str);
        System.out.println(str.length());
        System.out.println("-- indented string --");
        String indentedStr = str.indent(5);
        System.out.println(indentedStr);
        System.out.println(indentedStr.length());
        // con saltos de línea
        str = "a test string";
        System.out.println(str);
        indentedStr = str.indent(5);
        System.out.println(indentedStr.endsWith("\n"));
        System.out.printf("'%s'%n", indentedStr);
        System.out.println("-- indented string with quotes --");
        // multilínea
        str = "a test\nstring";
        System.out.println(str);
        System.out.println("-- indented string --");
        indentedStr = str.indent(5);
        System.out.println(indentedStr);

        // negativo
        str = "     a test\n     string";
        System.out.println(str);
        indentedStr = str.indent(-2);
        System.out.println("-- negatively indented string --");
        System.out.println(indentedStr);

        // negativo sin espacio suficiente
        str = "  a\n    test\n        string";
        System.out.println(str);
        indentedStr = str.indent(-6);
        System.out.println("-- negatively indented string --");
        System.out.println(indentedStr);

        // transformaciones
        str = "1000";
        Integer integer = str.transform(Integer::parseInt);
        System.out.println(integer);
        // Java 13
        // Strings multilínea
        String inputElement = """
                            Name: Jenny
                            Phone: 8675309
                            age: 35
                            """;

        System.out.println(inputElement);
        // alineamiento
        inputElement = """
                            Name: Jenny
                        Phone: 8675309
                            age: 35
                            """;

        System.out.println(inputElement);

        // String to encode
        String message = "This is a secret message!";

        // Encode the message using Base64.Encoder
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encodeToString(message.getBytes()).getBytes();

        System.out.println("Encoded message (Base64): " + new String(encodedBytes));

        // Decode the encoded message using Base64.Decoder
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(encodedBytes);

        System.out.println("Decoded message: " + new String(decodedBytes));

        // Java 21
        /*
            String.indexOf(String str, int beginIndex, int endIndex) – searches the specified substring in a subrange of the string.
            String.indexOf(char ch, int beginIndex, int endIndex) – searches the specified character in a subrange of the string.
            String.splitWithDelimiters(String regex, int limit) – splits the string at substrings matched by the regular expression and returns an array of all parts and splitting strings. The string is split at most limit-1 times, i.e., the last element of the array could be further divisible.

            Both StringBuilder and StringBuffer have been extended by the following two methods:

            repeat(CharSequence cs, int count) – appends to the StringBuilder or StringBuffer the string cs – count times.
            repeat(int codePoint, int count) – appends the specified Unicode code point to the StringBuilder or StringBuffer – count times. A variable or constant of type char can also be passed as code point.

            Character
            isEmoji(int codePoint)
            isEmojiComponent(int codePoint)
            isEmojiModifier(int codePoint)
            isEmojiModifierBase(int codePoint)
            isEmojiPresentation(int codePoint)
            isExtendedPictographic(int codePoint)
         */

        // String Templates (preview)
        int a = 2;
        int b = 3;

        String result = STR."\{a} times \{b} = \{Math.multiplyExact(a, b)}";
    }
}

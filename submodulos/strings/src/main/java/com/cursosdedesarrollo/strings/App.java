package com.cursosdedesarrollo.strings;

import java.util.Base64;
// import java.util.FormatProcessor;
import java.util.Locale;

// import static java.lang.StringTemplate.STR;
// import static java.util.FormatProcessor.FMT;

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

        // De momento no funciona XD (Jun 2026)
        // String Vampire🧛 = "\uD83E\uDDDB";


        // Java 11
        // String.formatted(...) — equivalente a String.format() como método de instancia
        System.out.println("formatted");
        String plantilla = "Hola, %s! Tienes %d años y mides %.1f m.";
        String resultado = plantilla.formatted("Ana", 30, 1.65);
        System.out.println(resultado);



        // Java 12
        // indentado
        System.out.println("Indent");
        String str = "a test string";
        System.out.println(str);
        System.out.println(str.length());
        System.out.println("-- indented string --");
        String indentedStr = str.indent(5);
        System.out.println(indentedStr);
        indentedStr = str.indent(10);
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
        // Integer integer = str.transform((s) -> Integer.parseInt(s));
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

        // Formatear bloques de texto multilínea con Text Blocks (Java 15)
        String json = """
                
        {
            "nombre": "%s",
                "edad": %d
        }
        """.formatted("Luis", 42);
        System.out.println(json);
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

        // Ejemplos de uso de nuevos métodos de String, StringBuilder, StringBuffer y Character (Java 21)
        String texto = "Java is fun and Java is powerful";
        
        // 1. String.indexOf(String str, int beginIndex, int endIndex)
        // Busca la subcadena "Java" únicamente dentro del rango de índices especificado [10, 30).
        // Evita tener que recortar la cadena (substring) o buscar en toda la cadena si ya sabemos el rango de búsqueda.
        // Esperado: Encuentra el segundo "Java" que comienza en el índice 16.
        int indiceSubString = texto.indexOf("Java", 10, 30);
        System.out.println("indexOf(\"Java\", 10, 30): " + indiceSubString); // 16
        
        // 2. String.indexOf(char ch, int beginIndex, int endIndex)
        // Busca el carácter especificado ('a') dentro del rango de índices [5, 15).
        // Esperado: Encuentra la 'a' de la palabra "and" en el índice 12.
        int indiceChar = texto.indexOf('a', 5, 15);
        System.out.println("indexOf('a', 5, 15): " + indiceChar); // 12

        // 3. String.splitWithDelimiters(String regex, int limit)
        // A diferencia del clásico split(), este método incluye los delimitadores en el array resultante.
        // - El primer parámetro es la expresión regular del delimitador.
        // - El segundo parámetro es el límite de divisiones (se divide como máximo limit-1 veces).
        // Esperado: [uno, ,, dos, ,, tres,cuatro] (los separadores "," se incluyen como elementos del array)
        String csv = "uno,dos,tres,cuatro";
        String[] partesConDelim = csv.splitWithDelimiters(",", 3);
        System.out.println("splitWithDelimiters(\",\", 3): " + java.util.Arrays.toString(partesConDelim));

        // 4. StringBuilder.repeat(CharSequence cs, int count) y repeat(int codePoint, int count)
        // Ambos StringBuilder y StringBuffer se extienden para permitir repetir texto o caracteres N veces de forma eficiente.
        
        // Repetir una secuencia de caracteres (CharSequence)
        StringBuilder sb = new StringBuilder();
        sb.repeat("abc", 3); // Añade "abc" 3 veces
        System.out.println("StringBuilder.repeat(CharSequence, 3): " + sb.toString()); // abcabcabc

        // Repetir un carácter (especificado como punto de código Unicode)
        StringBuilder sbChar = new StringBuilder();
        sbChar.repeat('*', 5); // Añade '*' 5 veces
        System.out.println("StringBuilder.repeat(char, 5): " + sbChar.toString()); // *****

        // Los mismos métodos aplican a StringBuffer (seguro para subprocesos/thread-safe)
        StringBuffer sbf = new StringBuffer();
        sbf.repeat("xyz", 2);
        System.out.println("StringBuffer.repeat(CharSequence, 2): " + sbf.toString()); // xyzxyz

        StringBuffer sbfChar = new StringBuffer();
        sbfChar.repeat('!', 4);
        System.out.println("StringBuffer.repeat(char, 4): " + sbfChar.toString()); // !!!!

        // 5. Métodos de Character para comprobación de Emojis
        // Java 21 introduce soporte nativo a nivel de Character para la especificación Unicode de Emojis.
        int emojiGRINNING = 0x1F600;   // 😀 (Punto de código del emoji de la cara sonriente)
        int emojiComponente = 0x1F3FB; // Modificador Fitzpatrick para tono de piel claro
        int noEmoji = 'A';             // Carácter ASCII estándar

        // Comprueba si el punto de código es un Emoji general
        System.out.println("Character.isEmoji(😀): " + Character.isEmoji(emojiGRINNING)); // true
        System.out.println("Character.isEmoji('A'): " + Character.isEmoji(noEmoji)); // false
        
        // Comprueba si es un componente usado en emojis (como modificadores de tono de piel o pelo)
        System.out.println("Character.isEmojiComponent(modifier): " + Character.isEmojiComponent(emojiComponente)); // true
        
        // Comprueba si es un modificador de emoji que cambia la apariencia de un emoji base
        System.out.println("Character.isEmojiModifier(modifier): " + Character.isEmojiModifier(emojiComponente)); // true
        
        // Comprueba si el emoji puede recibir modificadores (por ejemplo, personas o partes del cuerpo)
        System.out.println("Character.isEmojiModifierBase(😀): " + Character.isEmojiModifierBase(emojiGRINNING)); // false (la cara sonriente😀 no suele cambiar de tono de piel)
        
        // Comprueba si el emoji por defecto se renderiza en estilo gráfico (colorido/emoji)
        System.out.println("Character.isEmojiPresentation(😀): " + Character.isEmojiPresentation(emojiGRINNING)); // true
        
        // Comprueba si es un pictograma extendido (símbolos e imágenes en el estándar Unicode)
        System.out.println("Character.isExtendedPictographic(😀): " + Character.isExtendedPictographic(emojiGRINNING)); // true

        // String Templates (preview)
        int a = 2;
        int b = 3;
        /*
        String result = STR."\{a} times \{b} = \{Math.multiplyExact(a, b)}";

        String nombre = "Juan";
        int edad = 30;
        String saludo = "Hola, " + nombre + "! Tienes " + edad + " años.";

        // Con String Templates:
        String saludo2 = STR."Hola, \{nombre}! Tienes \{edad} años.";

        System.out.println(saludo); // Imprime: "Hola, Juan! Tienes 30 años."
        System.out.println(saludo2); // Imprime: "Hola, Juan! Tienes 30 años."
        String feelsLike = "22";
        String temperature = "17";
        String unit = "C";
        String saludo3 = STR
                . """
      {
        "feelsLike": "\{ feelsLike }",
        "temperature": "\{ temperature }",
        "unit": "\{ unit }"
      }
      """ ;
        System.out.println(saludo3);
        String saludo4 = STR
                . """
      {
        "feelsLike": "\{ feelsLike }",
        "temperature": "\{ temperature }",
        "unit": "\{ unit }"
      }
      """ ;
        System.out.println(saludo4);

//        saludo4 = FMT
//                ."""
//      {
//        "feelsLike": "%1s\{feelsLike}",
//        "temperature": "%2.2f\{temperature}",
//        "unit": "%1s\{unit}"
//      }
//      """;
//        System.out.println(saludo4);
        Locale locale = Locale.forLanguageTag("es-es");
        FormatProcessor esFMT = FormatProcessor.create(locale);
        int x = 10;
        int y = 20;
        String result4 = esFMT."%4d\{x} + %4d\{y} = %5d\{x + y}";
        System.out.println (result4);

        int x2 = 10;
        int y2 = 20;
        result4 = FMT."0x%04x\{x2} + 0x%04x\{y2} = 0x%04x\{x2 + y2}";
        System.out.println (result4);


        result4 = FMT."%05d\{x} + %05d\{y} = %05d\{x + y}";
        System.out.println (result4);
        float f = 1.0F;
        float g = 2.112431231F;
        result4 = FMT."%2.2f\{f} + %2.2f\{g} = %2.6f\{f+g}";
        System.out.println (result4);

         */
    }
}

package com.cursosdedesarrollo;


import org.junit.jupiter.api.Test;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import static java.lang.foreign.ValueLayout.*;

/**
 * Unit test for simple App.
 */
public class AppTest{
    @Test
    void testFFM() {
        // 1) Obtenemos el "linker" nativo, que sabe cómo llamar a funciones C del SO.
        Linker linker = Linker.nativeLinker();

        // 2) Obtenemos un buscador de símbolos por defecto.
        //    En Linux/macOS, este buscador encuentra símbolos de la biblioteca C estándar (libc),
        //    como 'strlen', 'puts', etc., disponibles en el proceso.
        SymbolLookup stdlib = linker.defaultLookup();

        // 3) Definimos el descriptor de la función C que queremos invocar:
        //    size_t strlen(const char* s);
        //    - Retorno: size_t → lo mapeamos a long de Java (JAVA_LONG)
        //    - Parámetros: (const char*) → puntero → lo mapeamos como ADDRESS
        FunctionDescriptor strlenDesc =
                FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS);

        // 4) Buscamos el símbolo 'strlen' en la libc y creamos un "downcall handle".
        //    - stdlib.find("strlen") devuelve un Optional con la dirección del símbolo.
        //    - orElseThrow() lanza si no se encuentra (caso poco probable en *nix).
        //    - downcallHandle(...) crea un MethodHandle invocable desde Java.
        MethodHandle strlen = linker.downcallHandle(
                stdlib.find("strlen").orElseThrow(),
                strlenDesc
        );

        // 5) Creamos un "área" (Arena) de memoria nativa con ciclo de vida controlado.
        //    ofConfined() implica que la memoria se libera automáticamente al salir del try.
        try (Arena arena = Arena.ofConfined()) {

            // 6) Reservamos un bloque de memoria nativa y copiamos ahí una cadena UTF-8
            //    terminada en '\0' (formato C). 'allocateFrom(String)' hace ambas cosas por ti.
            MemorySegment cString = arena.allocateFrom("Hola FFM Java 22!");

            // 7) Invocamos strlen(cString). El MethodHandle espera los tipos mapeados:
            //    - cString se pasa como ADDRESS (puntero al principio del bloque).
            //    El resultado es un long (JAVA_LONG) con la longitud de la cadena (sin incluir '\0').
            long len = (long) strlen.invoke(cString);

            // 8) Mostramos el valor devuelto por strlen.
            System.out.println("Longitud nativa: " + len); // debería imprimir 17
        } // 9) Manejamos la posible excepción
        catch (Throwable e) {
            throw new RuntimeException(e);
        }// 10) Salimos del try-with-resources y el Arena libera la memoria nativa automáticamente.


    }
}

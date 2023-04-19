package com.cursosdedesarrollo.optional;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        // Java 8
        System.out.println("optional");
        Integer value = 2;
        Optional<Integer> objetoOpcional = Optional.ofNullable(value);
        System.out.println(objetoOpcional);
        System.out.println(objetoOpcional.get());
        // el valor que meto es un null
        value = null;
        //value.intValue();
        objetoOpcional = Optional.ofNullable(value);
        System.out.println("Object present: "+ objetoOpcional.isPresent());
        if (objetoOpcional.isPresent()){
            System.out.println("Object inside: " + objetoOpcional.get());
        }else{
            System.out.println("Object inside: " + "Nonai");
        }
        Optional<Integer> a = null;
        // Integer value1 = a.orElse(Integer.valueOf(0));
        // System.out.println(value1);
        // Java 9
        System.out.println( "Ejemplos de Optional!" );
        // ifPresentOrElse
        System.out.println("ifPresentOrElse");
        IntStream.of(1, 2, 3, 4)
                .filter(i -> i % 5 == 0)
                .findFirst()
                .ifPresentOrElse(
                        // ya me devuelve el dato sin el optional
                        System.out::println, // Plan A
                        () -> { // Plan B
                            System.out.println("No multiple of 3 found");
                        });
        // or
        char digit = Stream.of('a', 'b', 'c')
                .filter(Character::isDigit)
                .findFirst()
                .or(() -> Optional.of('0')) // plan B
                .get();
        System.out.println(digit);
        // Stream
        OptionalInt opt1 = IntStream.of(2, 5, 6).max();
        OptionalInt opt2 = IntStream.of(1, 3, 7).max();
        IntStream.concat(opt1.stream(), opt2.stream())
                .forEach(System.out::println);

    }
}

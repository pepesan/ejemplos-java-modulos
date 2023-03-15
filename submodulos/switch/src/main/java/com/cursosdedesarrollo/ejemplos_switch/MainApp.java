package com.cursosdedesarrollo.ejemplos_switch;

import java.time.LocalDate;
import java.time.Month;

public class MainApp {
    public static void main(String[] args) {
        Month mes = LocalDate.now().getMonth();
        switch (mes) {
            case JANUARY, FEBRUARY, MARCH -> System.out.println("First Quarter");//no break needed
            case APRIL, MAY, JUNE -> System.out.println("Second Quarter");
            case JULY, AUGUST, SEPTEMBER -> System.out.println("Third Quarter");
            case OCTOBER, NOVEMBER, DECEMBER -> System.out.println("Forth Quarter");
            default -> System.out.println("Unknown Quarter");
        }

        String quarter = switch (mes) {
            case JANUARY, FEBRUARY, MARCH -> "First Quarter"; //must be a single returning value
            case APRIL, MAY, JUNE -> "Second Quarter";
            case JULY, AUGUST, SEPTEMBER -> "Third Quarter";
            case OCTOBER, NOVEMBER, DECEMBER -> "Forth Quarter";
            default -> "Unknown Quarter";
        };
        System.out.println(quarter);

        String result = switch (mes) {
            case JANUARY, FEBRUARY, MARCH -> {
                //multiple statements can be used here
                yield "First Quarter";
            }
            case APRIL, MAY, JUNE -> {
                //multiple statements can be used here
                yield "Second Quarter";
            }
            case JULY, AUGUST, SEPTEMBER -> "Third Quarter";
            case OCTOBER, NOVEMBER, DECEMBER -> {
                //multiple statements can be used here
                yield "Forth Quarter";
            }
            default -> "Unknown Quarter";
        };
        System.out.println(result);
        Object o = new String("Hola");
        switch (o) {
            case null -> {
                System.out.println("null");
                break;
            }
            case String s -> System.out.println("String: "+ s);
            default -> System.out.println("ni null ni cadena");
        }
    }
}

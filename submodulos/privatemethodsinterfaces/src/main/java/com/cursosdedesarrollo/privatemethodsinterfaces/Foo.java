package com.cursosdedesarrollo.privatemethodsinterfaces;

public interface Foo {

    public final String cadena = "";

    default void bar() {
        System.out.print("Hello");
        baz();
        dameAlgo();
    }

    public String dameAlgo();

    //Java 8
    default void procesaAlgo(){
        System.out.println("Algo");
    }

    //Nuevo en Java 9
    private void baz() {
        System.out.println(" world!");
    }

    static void buzz() {
        System.out.print("Hello");
        staticBaz();
    }

    private static void staticBaz() {
        System.out.println(" static world!");
    }
}

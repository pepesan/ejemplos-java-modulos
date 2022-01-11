package com.cursosdedesarrollo.privatemethodsinterfaces;

public interface Foo {

    default void bar() {
        System.out.print("Hello");
        baz();
    }

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

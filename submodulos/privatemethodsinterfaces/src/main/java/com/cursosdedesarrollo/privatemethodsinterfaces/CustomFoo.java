package com.cursosdedesarrollo.privatemethodsinterfaces;

public class CustomFoo implements Foo{

    @Override
    public String dameAlgo() {
        return null;
    }

    @Override
    public void bar() {
        System.out.println("Hello");
        // no puedo usar el método privado
        // baz();
        // debería de reimplementar el método baz a mi manera
        dameAlgo();
    }
}

package com.cursosdedesarrollo.mainapp;


import com.cursosdedesarrollo.hijo.User;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        User user = new User();
        user.setName("Pepe");
        System.out.println(user.getName());
    }
}

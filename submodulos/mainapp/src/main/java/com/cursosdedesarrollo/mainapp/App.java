package com.cursosdedesarrollo.mainapp;


import com.cursosdedesarrollo.dao.Dao;
import com.cursosdedesarrollo.hijo.User;
import com.cursosdedesarrollo.servicio.MyServiceImpl;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        User user = new User();
        user.setName("Pepe");
        System.out.println(user.getName());
        Dao service = new MyServiceImpl();
        System.out.println(service.findAll());

    }
}

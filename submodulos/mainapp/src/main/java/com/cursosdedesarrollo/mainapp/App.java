package com.cursosdedesarrollo.mainapp;


import com.cursosdedesarrollo.dao.Dao;
import com.cursosdedesarrollo.hijo.User;

import java.util.List;
import java.util.Optional;

public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        User user = new User();
        user.setName("Pepe");
        System.out.println(user.getName());
        Dao<User> service = new Dao<User>() {
            @Override
            public Optional<User> findById(int id) {
                return Optional.empty();
            }

            @Override
            public List<User> findAll() {
                return null;
            }
        };
        System.out.println(service.findAll());

    }
}

package com.cursosdedesarrollo.servicio;

import com.cursosdedesarrollo.dao.Dao;
import com.cursosdedesarrollo.hijo.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyServiceImpl implements Dao<User> {

    private List<User> users;

    public MyServiceImpl(){
        this.users = new ArrayList<User>();
    }

    @Override
    public Optional<User> findById(int id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return users;
    }
}

package com.cursosdedesarrollo.hijo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class UserTest {
    private User user;
    @BeforeEach
    public void setUp(){
        user = new User();
    }
    @Test
    public void shouldBeNotNull()
    {
        assertNotNull(user);
    }
}

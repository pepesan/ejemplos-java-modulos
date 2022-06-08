package com.cursosdedesarrollo.app;


import lombok.Data;

@Data
public class ClaseData {
    private String nombre;
    private String direccion;
    ClaseData(){
        this.nombre = "";
        this.direccion = "";
    }
}

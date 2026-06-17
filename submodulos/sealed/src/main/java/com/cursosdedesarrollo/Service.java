package com.cursosdedesarrollo;

/**
 * Interfaz sellada base.
 * Permite únicamente a HttpService y LocalService extenderla.
 */
public sealed interface Service permits HttpService, LocalService {
    void execute();
}

package com.cursosdedesarrollo;

/**
 * Interfaz no sellada (non-sealed) que extiende una interfaz sellada.
 * Desella esta rama de la jerarquía, permitiendo que cualquier
 * otra interfaz o clase la extienda o implemente libremente.
 */
public non-sealed interface LocalService extends Service {
    void runLocally();
}

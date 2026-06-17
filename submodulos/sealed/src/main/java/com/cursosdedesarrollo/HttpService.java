package com.cursosdedesarrollo;

/**
 * Interfaz sellada que extiende otra interfaz sellada.
 * Al ser una interfaz, NO puede declararse como 'final'.
 * Por lo tanto, debe ser declarada como 'sealed' o 'non-sealed'.
 * En este caso es 'sealed' y permite únicamente RestService.
 */
public sealed interface HttpService extends Service permits RestService {
    String getEndpoint();
}

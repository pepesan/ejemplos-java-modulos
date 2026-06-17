package com.cursosdedesarrollo;

/**
 * Clase que implementa la interfaz LocalService.
 * Como LocalService es 'non-sealed', esta clase puede implementarla
 * de forma convencional sin necesidad de usar final, sealed o non-sealed.
 */
public class DatabaseService implements LocalService {
    @Override
    public void execute() {
        System.out.println("Ejecutando operaciones en la Base de Datos local.");
    }

    @Override
    public void runLocally() {
        System.out.println("Iniciando motor de base de datos embebido...");
    }
}

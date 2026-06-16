package com.cursosdedesarrollo.migracionlts;

/**
 * Clase principal de ejecución de los ejemplos de migración de versiones LTS.
 */
public class App {

    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println("   DEMO: GUÍA PRÁCTICA DE MIGRACIÓN ENTRE VERSIONES LTS EN JAVA       ");
        System.out.println("======================================================================\n");

        Java8to11Migration.ejecutar();
        Java11to17Migration.ejecutar();
        Java17to21Migration.ejecutar();
        Java21to25Migration.ejecutar();

        System.out.println("======================================================================");
        System.out.println("   FIN DE LAS DEMOSTRACIONES DE MIGRACIÓN                             ");
        System.out.println("======================================================================");
    }
}

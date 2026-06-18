package com.cursosdedesarrollo.reflexion;

import java.lang.reflect.Field;

public class Reflector {
    /**
     * Inspecciona reflectivamente los campos declarados del objeto provisto.
     * Esto requiere que el paquete de la clase del objeto esté abierto ('opens')
     * al módulo 'com.cursosdedesarrollo.reflexion'.
     *
     * @param obj El objeto a inspeccionar.
     * @throws Exception Si no se tiene acceso o si ocurre un error de reflexión.
     */
    public static void inspect(Object obj) throws Exception {
        Class<?> clazz = obj.getClass();
        System.out.println("Inspeccionando por reflexión (desde el módulo 'com.cursosdedesarrollo.reflexion'):");
        System.out.println("  Clase: " + clazz.getName());
        System.out.println("  Módulo de la clase: " + clazz.getModule().getName());

        for (Field field : clazz.getDeclaredFields()) {
            // Esto lanzará InaccessibleObjectException si el módulo de la clase
            // no ha abierto su paquete al módulo com.cursosdedesarrollo.reflexion
            field.setAccessible(true);
            Object value = field.get(obj);
            System.out.println("    - Campo: " + field.getName() + " (tipo: " + field.getType().getSimpleName() + ") = " + value);
        }
    }
}

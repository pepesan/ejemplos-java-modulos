module com.cursosdedesarrollo.migracion {
    requires com.cursosdedesarrollo.reflexion;
    
    // Abre el paquete para permitir acceso reflexivo (completo, incluyendo campos privados)
    // al módulo 'com.cursosdedesarrollo.reflexion'. Si comentas esta línea,
    // el Escenario 5 lanzará una InaccessibleObjectException.
    opens com.cursosdedesarrollo.migracion to com.cursosdedesarrollo.reflexion;
}

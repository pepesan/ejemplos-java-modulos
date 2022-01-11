module com.cursosdedesarrollo.service {
    exports com.cursosdedesarrollo.servicio;
    requires com.cursosdedesarrollo.hijo;
    requires com.cursosdedesarrollo.dao;
    provides com.cursosdedesarrollo.dao.Dao
            with com.cursosdedesarrollo.servicio.MyServiceImpl;
}
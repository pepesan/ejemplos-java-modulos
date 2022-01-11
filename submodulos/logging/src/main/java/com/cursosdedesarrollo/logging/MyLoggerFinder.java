package com.cursosdedesarrollo.logging;

public class MyLoggerFinder extends System.LoggerFinder {
    @Override
    public System.Logger getLogger(String name, Module module) {
        return new Slf4jLogger(name, module);
    }
}

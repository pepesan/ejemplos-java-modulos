package com.cursosdedesarrollo.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ResourceBundle;

public class Slf4jLogger implements System.Logger {
    private final Logger slf4jLogger;
    private final String name;

    public Slf4jLogger(String name, Module module) {
        this.name = name;
        slf4jLogger = LoggerFactory.getLogger(module.getName() + "-" + name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isLoggable(Level level) {
        //enable for all levels
        return true;
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String msg, Throwable thrown) {
        // redirect to different methods based on level
        slf4jLogger.trace(msg);
    }

    @Override
    public void log(Level level, ResourceBundle bundle, String format, Object... params) {
        // use ResourceBundle.getString().
        // redirect to different methods based on level
        slf4jLogger.trace(format, params);
    }
}

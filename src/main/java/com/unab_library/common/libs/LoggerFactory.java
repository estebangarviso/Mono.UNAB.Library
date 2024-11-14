package com.unab_library.common.libs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerFactory {

    // Private constructor to hide the implicit public one
    private LoggerFactory() {
    }
    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}
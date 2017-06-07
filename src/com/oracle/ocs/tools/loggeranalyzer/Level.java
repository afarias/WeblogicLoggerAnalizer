package com.oracle.ocs.tools.loggeranalyzer;

/**
 * @author Andrés Farías on 6/1/17.
 */
public enum Level {

    WARNING("WARNING"), ERROR("ERROR"), NONE("NONE"), INFO("INFO"), FATAL("FALTAL"), NOTICE("NOTICE");

    private String name;
    private static int position;

    Level(String name) {
        this.name = name;
    }

    public static int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }
}

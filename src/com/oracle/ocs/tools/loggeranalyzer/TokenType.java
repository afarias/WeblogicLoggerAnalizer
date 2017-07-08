package com.oracle.ocs.tools.loggeranalyzer;

/**
 * @author Andrés Farías on 6/1/17.
 */
public enum TokenType {
    LEVEL("Level", 2), MODULE("Module", 3), DATE("Fecha", 1), CODE("BEA Code", 4);

    /** Module's name */
    private String name;


    private int position;

    TokenType(String name, int position) {
        this.name = name;
        this.position = position;
    }

    public void setTokenPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }
}

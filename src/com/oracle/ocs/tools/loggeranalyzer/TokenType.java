package com.oracle.ocs.tools.loggeranalyzer;

/**
 * @author Andrés Farías on 6/1/17.
 */
public enum TokenType {
    LEVEL("Level");

    private String name;
    private int position;

    TokenType(String name) {
        this.name = name;
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

package com.oracle.ocs.tools.loggeranalyzer.model;

/**
 * @author Andrés Farías on 7/6/17.
 */
public class Delimiters {

    /** The token's init delimiter */
    private String initDelimiter = "<";

    /** The token's end delimiter */
    private String endDelimiter = ">";

    public Delimiters(String initDelimiter, String endDelimiter) {
        this.initDelimiter = initDelimiter;
        this.endDelimiter = endDelimiter;
    }

    public String getInitDelimiter() {
        return initDelimiter;
    }

    public void setInitDelimiter(String initDelimiter) {
        this.initDelimiter = initDelimiter;
    }

    public String getEndDelimiter() {
        return endDelimiter;
    }

    public void setEndDelimiter(String endDelimiter) {
        this.endDelimiter = endDelimiter;
    }

    public String wrap(String tokenValue) {
        return this.initDelimiter + tokenValue + this.endDelimiter;
    }
}

package com.oracle.ocs.tools.loggeranalyzer;

/**
 * @author Andrés Farías on 6/1/17.
 */
public class LogRecordToken {

    protected static final String INIT_TOKEN = "<";
    protected static final String END_TOKEN = ">";

    /** The Token's name */
    private static TokenType tokenType;

    public LogRecordToken(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public static String enclose(Level level) {
        return INIT_TOKEN + level.getName() + END_TOKEN;
    }

    public static TokenType getTokenType() {
        return tokenType;
    }
}

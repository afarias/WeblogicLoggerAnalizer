package com.oracle.ocs.tools.loggeranalyzer;

/**
 * @author Andrés Farías on 6/1/17.
 */
public class LogRecordToken {

    /** The Token's type (LEVEL, SERVER, etc.) */
    private TokenType tokenType;

    /** The Token's value */
    private String tokenValue;

    /**
     * Default constructor that requires the minimum: type and value.
     * @param tokenType The Token's type.
     * @param tokenValue The Token's value.
     */
    public LogRecordToken(TokenType tokenType, String tokenValue) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    @Override
    public String toString() {
        return "LogRecordToken{" +
                "tokenType=" + tokenType +
                ", tokenValue='" + tokenValue + '\'' +
                '}';
    }
}

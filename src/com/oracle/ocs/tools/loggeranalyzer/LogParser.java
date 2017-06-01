package com.oracle.ocs.tools.loggeranalyzer;

/**
 * @author Andrés Farías on 6/1/17.
 */
public class LogParser {

    /**
     * TODO: Document me!
     *
     * @return
     */
    public static Level parseLevel(String line) {
        for (Level level : Level.values()) {
            String enclose = LogRecordToken.enclose(level);
            if (line.toUpperCase().contains(enclose.toUpperCase()))
                return level;
        }

        return Level.NONE;
    }

    /**
     * This method is responsible for extracting the Level from a Record, based on its known position.
     *
     * @param logRecord The record from which the the level is to be extracted.
     *
     * @return The level extracted from the record.
     */
    public static Level extractLevel(LogRecord logRecord) {
        try {
            String tokenValue = TokenUtils.extractToken(logRecord.getLine(), TokenType.LEVEL.getPosition());
            return Level.valueOf(tokenValue.toUpperCase());
        } catch (IllegalArgumentException iae) {
            return Level.NONE;
        }
    }
}

package com.oracle.ocs.tools.loggeranalyzer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andrés Farías on 6/1/17.
 */
public class LogRecord {

    /** A map to store the info about the position of each token in a LogRecord */
    public static final Map<LogRecordToken, Integer> tokenPositions = new HashMap<>();

    /** The line that stores the log record */
    private String line;

    /**
     * Default and more basic constructor only receiving the line.
     *
     * @param line The line the has the log record.
     */
    public LogRecord(String line) {
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    public Level getLevel() {
        return LogParser.parseLevel(this.line);
    }

    /**
     * This method is responsible for determining if <code>this</code> log record has a specific log level.
     *
     * @return <code>true</code> if the log record has the specified level and <code>false</code> otherwise.
     */
    public boolean isLevel(Level level) {
        return this.getLevel().equals(level);
    }

    public boolean hasLevel() {
        //TODO: Implement me!
        return false;
    }

    @Override
    public String toString() {
        return "LogRecord{" +
                "line='" + line + '\'' +
                '}';
    }
}

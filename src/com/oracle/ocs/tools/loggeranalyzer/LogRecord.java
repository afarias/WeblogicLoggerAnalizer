package com.oracle.ocs.tools.loggeranalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrés Farías on 6/1/17.
 */
public class LogRecord {

    private static final Logger logger = LoggerFactory.getLogger(LogRecord.class);

    /** The lines that stores the log record */
    private List<String> lines;

    /** The severity level of this record, initially configured as NONE */
    private Level level;

    /** All the tokens within the record */
    private List<LogRecordToken> tokens;

    /**
     * Default and more basic constructor only receiving the lines.
     *
     * @param line The lines the has the log record.
     */
    public LogRecord(String line) {
        this.level = Level.NONE;
        this.lines = new ArrayList<>(Collections.singleton(line));
        this.tokens = new ArrayList<>();
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "LogRecord{" +
                "lines='" + lines + '\'' +
                '}';
    }

    public void addLine(String line) {
        this.lines.add(line);
    }

    /**
     * This method is responsible for returning the header line, which is known to be the first element of the lines
     * list.
     *
     * @return A line which is the header of the current LogRecord.
     */
    public String getHeaderLine() {

        /* Verification of basic invariant */
        if (this.lines.size() < 1) {
            throw new IllegalStateException("A RecordLog with no line.");
        }

        /* Otherwise, the first line is returned */
        return this.lines.get(0);
    }

    /**
     * This method is responsible for assigning tokens to the LogRecord.
     *
     * @param logRecordTokens The tokens to be assigned.
     *
     * @return the number of tokens assigned.
     */
    public int assignTokens(List<LogRecordToken> logRecordTokens) {

        int assignmentCounter = 0;
        for (LogRecordToken logRecordToken : logRecordTokens) {
            TokenType tokenType = logRecordToken.getTokenType();

            this.tokens.add(logRecordToken);
            switch (tokenType) {
                case LEVEL:
                    try {
                        this.level = Level.valueOf(logRecordToken.getTokenValue().toUpperCase());
                    } catch (IllegalArgumentException iae) {
                        logger.error("Undefined Level: " + logRecordToken.getTokenValue());
                    }
                    break;
            }

            assignmentCounter++;
        }

        return assignmentCounter;
    }
}

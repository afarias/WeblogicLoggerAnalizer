package com.oracle.ocs.tools.loggeranalyzer;

import com.oracle.ocs.tools.loggeranalyzer.model.Delimiters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for grouping a LogFile configuration, such as the type of record tokens that it has and
 * their position.
 *
 * @author Andrés Farías on 6/2/17.
 */
public class LogFileConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(LogFileConfiguration.class);

    /** The physical file with the Log */
    private File logFile;

    /** A map to store the info about the position of each token in a LogRecord */
    public Map<TokenType, Integer> tokenPositions;

    /** Token Delimiters */
    private Delimiters delimiters;

    public LogFileConfiguration(File logFile) {
        this.logFile = logFile;
        tokenPositions = new HashMap<>();
    }

    public File getLogFile() {
        return logFile;
    }

    public Map<TokenType, Integer> getTokenPositions() {
        return tokenPositions;
    }

    public void setDelimiter(Delimiters delimiters) {
        this.delimiters = delimiters;
    }

    public Integer addTokenPosition(TokenType tokenType, int position) {
        return tokenPositions.put(tokenType, position);
    }

    @Override
    public String toString() {
        return "LogFileConfiguration{" +
                "tokenPositions=" + tokenPositions +
                ", delimiters=" + delimiters +
                '}';
    }

    public Delimiters getDelimiters() {
        return delimiters;
    }
}

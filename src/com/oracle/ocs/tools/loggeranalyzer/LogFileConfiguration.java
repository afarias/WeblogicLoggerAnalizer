package com.oracle.ocs.tools.loggeranalyzer;

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

    /** The token's init delimiter */
    private String initDelimiter = "<";

    /** The token's end delimiter */
    private String endDelimiter = ">";

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

    public void setInitDelimiter(String initDelimiter) {
        this.initDelimiter = initDelimiter;
    }

    public void setEndDelimiter(String endDelimiter) {
        this.endDelimiter = endDelimiter;
    }

    public String getInitDelimiter() {
        return initDelimiter;
    }

    public String getEndDelimiter() {
        return endDelimiter;
    }

    /**
     * This method is responsible for enclose a text with the token delimiters.
     *
     * @param text The text to be enclosed.
     *
     * @return The text enclosed with the delimiters.
     */
    public String enclose(String text) {
        return this.initDelimiter + text + this.endDelimiter;
    }

    /**
     * This method is responsible for reading the logFile and extracting only the very first line of the file.
     *
     * @return The first line of the file.
     */
    public String getFirstLogLine() {

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.logFile));
            String line;
            if( (line = bufferedReader.readLine()) != null){
                return line;
            } else {
                return "";
            }
        } catch (java.io.IOException e) {
            logger.error("Error while reading a log file: " + this.logFile.getName());
            return "";
        }
    }

    public Integer addTokenPosition(TokenType tokenType, int position) {
        return tokenPositions.put(tokenType, position);
    }
}

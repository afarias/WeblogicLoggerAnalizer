package com.oracle.ocs.tools.loggeranalyzer.model;

import com.oracle.ocs.tools.loggeranalyzer.LogFileConfiguration;
import com.oracle.ocs.tools.loggeranalyzer.LogRecord;
import com.oracle.ocs.tools.loggeranalyzer.analyser.InferenceMachine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Andrés Farías on 7/5/17.
 */
public class LogFileUtilities {

    private final static Logger logger = LoggerFactory.getLogger(LogFileUtilities.class);

    /** The next line to be returned */
    private String nextLine;

    /** The Log File configuration */
    private LogFileConfiguration logConfig;

    /** A reader ready to read the file */
    private BufferedReader bufferedReader;

    /**
     * This constructor initialize the buffer and the current line.
     *
     * @param logFileConfiguration The configuration log file.
     *
     * @throws IOException Thrown if problems while reading or finding the file.
     */
    public LogFileUtilities(LogFileConfiguration logFileConfiguration) throws IOException {
        this.logConfig = logFileConfiguration;
        bufferedReader = new BufferedReader(new FileReader(logFileConfiguration.getLogFile()));

        /* A first line is read (the next line) */
        nextLine = bufferedReader.readLine();
    }

    /**
     * This method is responsible for traverse the file and extract the first Log Record that can be found.
     *
     * @return The record extracted, that will correspond to the first record on the file.
     *
     * @throws IOException If there is a problem when reading the file.
     */
    public LogRecord extractFirstLogRecord() throws IOException {

        InferenceMachine infMach = new InferenceMachine(logConfig.getLogFile());

        /* First is to find a log record header */
        String line;
        do {
            line = getNextLine();
        } while ((line != null) && !infMach.isLogRecordHeader(line));

        /* There was any header found? */
        if (line == null) {
            throw new IllegalArgumentException("The file contains no record log header");
        }

        LogRecord logRecord = new LogRecord(line);
        while (((line = bufferedReader.readLine()) != null) && !infMach.isLogRecordHeader(line)) {
            logRecord.addLine(line);
        }

        return logRecord;
    }

    /**
     * This method is responsible for returning the next line (which is the current line).
     *
     * @return The next line.
     */
    public String getNextLine() throws IOException {

        String currentLine = nextLine;
        this.nextLine = bufferedReader.readLine();

        return currentLine;
    }

    /**
     * This method is responsible for reading a line from the file, from its current point of reading.
     * For doing this, the next line is read already and store on the <code>nextLine</code> variable.
     *
     * @return A line read from the Log File.
     */
    public boolean hasNextLine() {
        return nextLine != null;
    }
}

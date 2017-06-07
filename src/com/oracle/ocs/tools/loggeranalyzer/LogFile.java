package com.oracle.ocs.tools.loggeranalyzer;

import com.oracle.ocs.commons.utils.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrés Farías on 5/26/17.
 */
public class LogFile {

    private static final int UNCOUNTED_LINES = -1;

    /** The LogFile configuration that contains its mains factory parameters */
    private LogFileConfiguration logFileConfiguration;

    /** The number of lines on the log. Given that is lazy data */
    private int lines;

    /** The object representation of each log record */
    private List<LogRecord> logRecords;

    /**
     * The basic constructor accepting the LogFile configuration.
     *
     * @param logFileConfiguration The Configuration LogFile.
     */
    public LogFile(LogFileConfiguration logFileConfiguration) {
        this.logFileConfiguration = logFileConfiguration;
        this.lines = UNCOUNTED_LINES;
        this.logRecords = new ArrayList<>();
    }

    /**
     * This method is responsible for counting the lines contained in the Log.
     *
     * @return The number of lines of the log's file.
     *
     * @throws IOException If there is a problem while working with the file.
     */
    public int getLines() throws IOException {

        /* If the lines have not been counted, then the file is processed */
        if (lines == UNCOUNTED_LINES) {
            this.lines = FileUtils.countLines(this.logFileConfiguration.getLogFile().getAbsolutePath());
        }

        return lines;
    }

    public void setLogRecords(List<LogRecord> logRecords) {
        this.logRecords = logRecords;
    }

    public List<LogRecord> getLogRecords() {
        return logRecords;
    }

    public LogFileConfiguration getLogFileConfiguration() {
        return logFileConfiguration;
    }
}

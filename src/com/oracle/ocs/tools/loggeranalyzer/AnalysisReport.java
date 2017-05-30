package com.oracle.ocs.tools.loggeranalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Andrés Farías on 5/26/17.
 */
public class AnalysisReport {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisReport.class);

    /* The LogFile */
    private LogFile logFile;

    public AnalysisReport(LogFile logFile) {
        this.logFile = logFile;
    }

    @Override
    public String toString() {
        String header = "Analyzed log: " + this.logFile;
        String lines = null;
        try {
            lines = "Log's # lines: " + this.logFile.getLines();
        } catch (IOException e) {
            logger.error("Error while reading the log", e);
        }

        return header + "\n" + lines;
    }
}

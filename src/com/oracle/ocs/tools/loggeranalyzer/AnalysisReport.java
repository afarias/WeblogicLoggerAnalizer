package com.oracle.ocs.tools.loggeranalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * @author Andrés Farías on 5/26/17.
 */
public class AnalysisReport {

    private static final Logger logger = LoggerFactory.getLogger(AnalysisReport.class);

    /** The LogFile being analysed */
    private LogFile logFile;

    /** The list of levels contained on the file and their appearances */
    private Map<Level, Integer> levels;

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

        lines += appendLevelReport(lines);

        return header + "\n" + lines;
    }

    private String appendLevelReport(String lines) {
        lines += "\n============================================";
        for (Level level : levels.keySet()) {
            lines += "\n" + level + ": " + levels.get(level);
        }

        return lines + "\n============================================";
    }

    public void setLevels(Map<Level, Integer> levels) {
        this.levels = levels;
    }

    public Collection<Level> getLevels() {
        return levels.keySet();
    }
}

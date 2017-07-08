package com.oracle.ocs.tools.loggeranalyzer.analyser;

import com.oracle.ocs.tools.loggeranalyzer.Level;
import com.oracle.ocs.tools.loggeranalyzer.model.LogFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
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
    private Date initDate;
    private Map<String, Map<Level, Integer>> levelsByModule;
    private Map<Level, Map<String, Integer>> codeCounting;

    public AnalysisReport(LogFile logFile) {
        this.logFile = logFile;
    }

    @Override
    public String toString() {
        String header = "\n\nAnalyzed log: " + this.logFile.getLogFileConfiguration().getLogFile() + " (init date: " + this.initDate + ")";
        String lines;
        try {
            lines = "\tLog's # lines: " + this.logFile.getLines();
            lines += "\n\tLog's # records: " + this.logFile.getLogRecords().size();
        } catch (IOException e) {
            logger.error("Error while reading the log", e);
            lines = "";
        }

        lines += appendLevelReport(lines);

        lines += appendCodeCounting(lines);

        return header + "\n" + lines;
    }

    private String appendCodeCounting(String lines) {
        lines += "\n=================CODES======================\n";
        for (Level level : codeCounting.keySet()) {
            for (String code : codeCounting.get(level).keySet()) {
                lines += "\n" + level + ": " + code + ": " + codeCounting.get(level).get(code);
            }
        }

        return lines + "\n============================================\n\n";
    }

    private String appendLevelReport(String lines) {
        lines += "\n============================================\n";
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

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public void setLevelsByModule(Map<String, Map<Level, Integer>> levelsByModule) {
        this.levelsByModule = levelsByModule;
    }

    public Map<String, Map<Level, Integer>> getLevelsByModule() {
        return levelsByModule;
    }

    public void setCodeCounting(Map<Level, Map<String, Integer>> codeCounting) {
        this.codeCounting = codeCounting;
    }

    public Map<Level, Map<String, Integer>> getCodeCounting() {
        return codeCounting;
    }
}

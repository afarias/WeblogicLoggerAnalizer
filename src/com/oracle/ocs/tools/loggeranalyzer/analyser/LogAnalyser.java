package com.oracle.ocs.tools.loggeranalyzer.analyser;

import com.oracle.ocs.tools.loggeranalyzer.*;
import com.oracle.ocs.tools.loggeranalyzer.analyser.AnalysisReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andrés Farías on 5/26/17.
 */
public class LogAnalyser {

    private static Logger logger = LoggerFactory.getLogger(Launcher.class);

    /**
     * This method is responsible for analysing the log and grouping the collected information in an
     * <code>AnalysisReport</code> object.
     *
     * @param logFile The Log to be analyzed.
     *
     * @return A report with the log file analysis.
     *
     * @throws java.io.IOException If there are problems while analysing the log file.
     */
    public AnalysisReport analyze(LogFile logFile) throws IOException {

        /* First thing is to find out the token positions */
        InferenceMachine inferenceMachine = new InferenceMachine(logFile);
        inferenceMachine.inferTokenPositions();

        /* Lines are read now and stored in memory, God help us */
        parseRecords(logFile);

        /* A report is created */
        AnalysisReport analysisReport = new AnalysisReport(logFile);

        /* Levels are counted and set to the report */
        Map<Level, Integer> levels = extractLevels(logFile);
        analysisReport.setLevels(levels);

        return analysisReport;
    }

    private List<LogRecord> parseRecords(LogFile logFile) {
        return null;
    }

    /**
     * This method is responsible for analysing the log and extracting all different levels of severity present on the
     * log.
     *
     * @param logFile The log file from which the levels are extracted.
     *
     * @return A map of Levels present on the LogFile and the number of apparitions.
     *
     * @throws java.io.IOException If the file is not to be found or there is a problem while reading it.
     */
    private Map<Level, Integer> extractLevels(LogFile logFile) throws IOException {

        /* The log configuration */
        LogFileConfiguration config = logFile.getLogFileConfiguration();
        TokenUtils tokenUtils = new TokenUtils(config.getInitDelimiter(), config.getEndDelimiter());

        /* The records are iterated from the LogFile, not from the file, which is way faster */
        Map<Level, Integer> levels = new HashMap<>();
        for (LogRecord logRecord : logFile.getLogRecords()) {
            Level level = logRecord.getLevel();

            if (!levels.containsKey(level)) {
                levels.put(level, 0);
            }
            levels.put(level, levels.get(level) + 1);

        }

        return levels;
    }
}

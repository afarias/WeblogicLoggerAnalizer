package com.oracle.ocs.tools.loggeranalyzer.analyser;

import com.oracle.ocs.tools.loggeranalyzer.Launcher;
import com.oracle.ocs.tools.loggeranalyzer.Level;
import com.oracle.ocs.tools.loggeranalyzer.LogRecord;
import com.oracle.ocs.tools.loggeranalyzer.model.LogFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
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

        /* Lines are read now and stored in memory, God help us */
        parseRecords(logFile);

        /* A report is created */
        AnalysisReport analysisReport = new AnalysisReport(logFile);

        /* Levels are counted and set to the report */
        analysisReport.setLevels(extractLevels(logFile));

        /* Initial date */
        analysisReport.setInitDate(getInitDate(logFile));

        /* Analysis by Module */
        Map<String, Map<Level, Integer>> levelsByModule = summarizeLevelsByModule(logFile);
        analysisReport.setLevelsByModule(levelsByModule);

        /* Error code counting */
        Map<Level, Map<String, Integer>> errorCounting = errorCounting(logFile);
        analysisReport.setCodeCounting(errorCounting);

        return analysisReport;
    }

    private Map<Level, Map<String, Integer>> errorCounting(LogFile logFile) {

        Map<Level, Map<String, Integer>> errorCounting = new HashMap<>();

        for (LogRecord logRecord : logFile.getLogRecords()) {
            String code = logRecord.getCode();
            if (code != null) {
                Level level = logRecord.getLevel();
                if (!errorCounting.containsKey(level)) {
                    errorCounting.put(level, new HashMap<String, Integer>());
                }
                Map<String, Integer> codeCounter = errorCounting.get(level);

                if (!codeCounter.containsKey(code)) {
                    codeCounter.put(code, 0);
                }
                codeCounter.put(code, codeCounter.get(code) + 1);
            }
        }
        return errorCounting;
    }

    private Map<String, Map<Level, Integer>> summarizeLevelsByModule(LogFile logFile) {
        Map<String, Map<Level, Integer>> levelsByModule = new HashMap<>();
        for (LogRecord logRecord : logFile.getLogRecords()) {

            /* First the log is read */
            String module = logRecord.getModule();
            if (!levelsByModule.containsKey(module)) {
                levelsByModule.put(module, new HashMap<Level, Integer>());
            }
            Map<Level, Integer> levelCounter = levelsByModule.get(module);

            /* Then it's level */
            Level level = logRecord.getLevel();
            if (!levelCounter.containsKey(level)) {
                levelCounter.put(level, 0);
            }
            levelCounter.put(level, levelCounter.get(level) + 1);
        }
        return levelsByModule;
    }

    /**
     * This method is responsible for finding the more little date of the log.
     *
     * @param logFile The Log File to be analyzed.
     *
     * @return The initial date of the log.
     */
    private Date getInitDate(LogFile logFile) {

        Date winner = new Date();
        for (LogRecord logRecord : logFile.getLogRecords()) {
            Date logDate = logRecord.getLogDate();
            if (logDate.compareTo(winner) < 0) {
                winner = logDate;
            }
        }

        return winner;
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

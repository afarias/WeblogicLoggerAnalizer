package com.oracle.ocs.tools.loggeranalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.oracle.ocs.commons.utils.FileUtils.extractFirstLine;
import static com.oracle.ocs.tools.loggeranalyzer.TokenType.LEVEL;

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
        inferTokenPositions(new LogRecord(extractFirstLine(logFile)));

        /* A report is created */
        AnalysisReport analysisReport = new AnalysisReport(logFile);

        /* Levels are counted and set to the report */
        Map<Level, Integer> levels = extractLevels(logFile);
        analysisReport.setLevels(levels);

        return analysisReport;
    }

    /**
     * This method is responsible for analysing the log and try to understand the format.
     *
     * @param logRecord The logfile on which the inference is performed.
     */
    private void inferTokenPositions(LogRecord logRecord) {

        String line = logRecord.getLine();
        try {
            int position = inferLevelPosition(new LogRecord(line));
            logger.info("INFERRED LEVEL POSITION: " + position);
        } catch (IOException e) {
            logger.error("There was an error while reading the log file: ", e);
        }
    }

    /**
     * This method is responsible for trying to infer the position of the Level token in a log record.
     *
     * @param logRecord The log record analysed in order to make the inference.
     *
     * @return The position on the line of the token that has the Log Record Level. If no inference is performed, the
     * method returns the -1 value.
     */
    protected int inferLevelPosition(LogRecord logRecord) throws IOException {

        /* If the log record contains a level... */
        if (!logRecord.getLevel().equals(Level.NONE)) {

            int index, lastIndex = 0;
            String line = logRecord.getLine();
            int position = 0;
            while ((index = line.indexOf(LogRecordToken.INIT_TOKEN, lastIndex)) != -1) {
                position++;
                lastIndex = line.indexOf(LogRecordToken.END_TOKEN, index);
                String token = line.substring(index + 1, lastIndex);
                logger.info("Token founded: " + token);

                if (logRecord.getLevel().getName().equalsIgnoreCase(token)) {
                    LEVEL.setTokenPosition(position);
                    return position;
                }
            }

        }

        return -1;
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

        FileReader fileReader = new FileReader(logFile.getLogFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;

        Map<Level, Integer> levels = new HashMap<Level, Integer>();
        while ((line = bufferedReader.readLine()) != null) {
            LogRecord logRecord = new LogRecord(line);
            Level level = LogParser.extractLevel(logRecord);

            if (!levels.containsKey(level)) {
                levels.put(level, 0);
            }
            levels.put(level, levels.get(level) + 1);
        }

        return levels;
    }
}

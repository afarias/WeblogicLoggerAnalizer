package com.oracle.ocs.tools.loggeranalyzer.analyser;

import com.oracle.ocs.tools.loggeranalyzer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Andrés Farías on 6/7/17.
 */
public class InferenceMachine {

    private static Logger logger = LoggerFactory.getLogger(InferenceMachine.class);

    /** The Log which is subject to be analysed */
    private LogFile logFile;

    public InferenceMachine(LogFile logFile) {
        this.logFile = logFile;
    }

    /**
     * This method is responsible for analysing the log and try to understand the format. Then, this understanding
     */
    public void inferTokenPositions() {

        /* From the configuration file it's obtained the file and the log */
        LogFileConfiguration logFileConfiguration = logFile.getLogFileConfiguration();
        String line = logFileConfiguration.getFirstLogLine();

        int position = 0;
        try {
            position = inferLevelPosition(new LogRecord(line));
            logger.info("INFERRED LEVEL POSITION: " + position);
        } catch (IOException e) {
            logger.error("There was an error while reading the log file: ", e);
        }

        logFileConfiguration.addTokenPosition(TokenType.LEVEL, position);
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
        LogFileConfiguration logConfig = logFile.getLogFileConfiguration();
        TokenUtils tokenUtils = new TokenUtils(logConfig.getInitDelimiter(), logConfig.getEndDelimiter());

        int position = 0;
        for (LogRecordToken logRecordToken : tokenUtils.extractTokensFromLine(logRecord.getHeaderLine())) {
            position++;

            String tokenValue = logRecordToken.getTokenValue();
            try {
                Level level = Level.valueOf(tokenValue);
                logger.debug("Infering Level Token. Level detected: " + level);
                return position;
            }

            /* If the token was not a Level, an IllegalArgumentException is thrown, so here it is cached */
            catch (IllegalArgumentException iae) {
                logger.debug("Infering Level Token. Not a level: " + tokenValue);
            }
        }

        return -1;
    }
}

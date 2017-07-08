package com.oracle.ocs.tools.loggeranalyzer.analyser;

import com.oracle.ocs.commons.utils.FileUtils;
import com.oracle.ocs.tools.loggeranalyzer.*;
import com.oracle.ocs.tools.loggeranalyzer.model.Delimiters;
import com.oracle.ocs.tools.loggeranalyzer.model.LogFileUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.oracle.ocs.commons.utils.DateUtils.determineDateFormat;

/**
 * @author Andrés Farías on 6/7/17.
 */
public class InferenceMachine {

    private static Logger logger = LoggerFactory.getLogger(InferenceMachine.class);

    /** The file to be analyzed */
    private File file;

    /** The configuration file built from the inference machine */
    private LogFileConfiguration logConfig;

    private TokenUtils tokenUtils;

    /**
     * This constructor does not requires a full LogFile, but only the file.
     *
     * @param aLogFile The file to be analyzed.
     */
    public InferenceMachine(File aLogFile) {
        this.file = aLogFile;
        logConfig = new LogFileConfiguration(this.file);
        this.tokenUtils = new TokenUtils(logConfig.getDelimiters());
    }

    /**
     * This method is responsible for inferring several things about the Log File.
     *
     * @return The Log Configuration File.
     */
    public LogFileConfiguration inferConfiguration() {

        inferTokenDelimiters();
        inferTokenPositions();

        return logConfig;
    }

    /**
     * This method is responsible for inferring which are the delimiters of the log. The algorithm used will be to test
     * the existence of tokens with any type of delimiters. The delimiter that has the more number of tokens will be
     * the
     * chosen one.
     */
    private Delimiters inferTokenDelimiters() {

        int tokens1, tokens2, lineCounter = 1;

        String extractLine;
        do {
            try {
                extractLine = FileUtils.extractLine(lineCounter, this.file);
                tokens1 = new TokenUtils(new Delimiters("<", ">")).extractTokensFromLine(extractLine).size();
                tokens2 = new TokenUtils(new Delimiters("[", "]")).extractTokensFromLine(extractLine).size();
            } catch (IOException e) {
                logger.error("Problems reading the Log File", e);
                return new Delimiters("<", ">");
            }

            lineCounter++;
        } while ((tokens1 == 0) && (tokens2 == 0));

        Delimiters delimiters;
        if (tokens1 >= tokens2) {
            delimiters = new Delimiters("<", ">");
        } else {
            delimiters = new Delimiters("[", "]");
        }
        this.logConfig.setDelimiter(delimiters);

        return delimiters;
    }

    /**
     * This method is responsible for analysing the log and try to understand the format. Then, this understanding
     */
    public LogFileConfiguration inferTokenPositions() {

        /* From the configuration file it's obtained the file and the log */
        LogRecord logRecord;
        try {
            logRecord = new LogFileUtilities(this.logConfig).extractFirstLogRecord();
        } catch (IllegalArgumentException iae) {
            return logConfig;
        } catch (IOException e) {
            logger.error("Error when reading the log file.");
            return logConfig;
        }
        int position;

        /* Inferring the Level Token position */
        try {
            position = inferSeverityLevelPosition(logRecord);
            logConfig.addTokenPosition(TokenType.LEVEL, position);
            logger.info("INFERRED LEVEL POSITION: " + position);
        } catch (IOException e) {
            logger.error("There was an error while reading the log file: ", e);
        }

        /* Inferring the Date Token position */
        try {
            position = inferDatePosition(logRecord);
            logConfig.addTokenPosition(TokenType.DATE, position);
            logger.info("INFERRED DATE POSITION: " + position);
        } catch (IOException e) {
            logger.error("There was an error while reading the log file: ", e);
        }

        return logConfig;
    }

    /**
     * This method is responsible for trying to infer the position of the Date token in a log record.
     *
     * @param logRecord The log record analysed in order to make the inference.
     *
     * @return The position on the line of the token that has the Date Record Level. If no inference is performed, the
     * method returns the -1 value.
     */
    protected int inferDatePosition(LogRecord logRecord) throws IOException {

        /* If the log record contains a Date... */
        int position = 0;
        for (LogRecordToken logRecordToken : tokenUtils.extractTokensFromLine(logRecord.getHeaderLine())) {
            position++;

            String tokenValue = logRecordToken.getTokenValue();
            try {

                String date = determineDateFormat(tokenValue);
                logger.info("Date Token inferred. Date format detected: {}, at position {}", date, position);
                return position;
            }

            /* If the token was not a Level, an IllegalArgumentException is thrown, so here it is cached */ catch (IllegalArgumentException iae) {
                logger.debug("Infering Level Token. Not a level: " + tokenValue);
            }
        }

        return -1;
    }

    /**
     * This method is responsible for trying to infer the position of the Level token in a log record.
     *
     * @param logRecord The log record analysed in order to make the inference.
     *
     * @return The position on the line of the token that has the Log Record Level. If no inference is performed, the
     * method returns the -1 value.
     */
    protected int inferSeverityLevelPosition(LogRecord logRecord) throws IOException {

        /* If the log record contains a level... */
        int position = 0;
        for (LogRecordToken logRecordToken : tokenUtils.extractTokensFromLine(logRecord.getHeaderLine())) {
            position++;

            String tokenValue = logRecordToken.getTokenValue();
            try {
                Level level = Level.valueOf(tokenValue.toUpperCase());
                logger.debug("Infering Level Token. Level detected: " + level);
                return position;
            }

            /* If the token was not a Level, an IllegalArgumentException is thrown, so here it is cached */ catch (IllegalArgumentException iae) {
                logger.debug("Infering Level Token. Not a level: " + tokenValue);
            }
        }

        return -1;
    }


    /**
     * This method is responsible for determining if the given <code>line</code> is a log record header or not. This is
     * based on the presence on the token extraction and trying to match the token extracted with their corresponding
     * type.
     *
     * @param line The line to be parsed and analysed for determining if it's about a Header Record.
     *
     * @return <code>true</code> if the line represents a log record header and <code>false</code> otherwise.
     */
    public boolean isLogRecordHeader(String line) {

        Map<TokenType, Integer> tokenPositions = this.logConfig.getTokenPositions();
        int numberOfTokens = tokenPositions.keySet().size();

        List<LogRecordToken> logRecordTokens = tokenUtils.extractTokensFromLine(line);
        int tokenExtracted = logRecordTokens.size();

        /* If there are less tokens than expected (tokenPositions.size()) then it is not a header */
        if (numberOfTokens > tokenExtracted || (tokenExtracted == 0)) {
            return false;
        }

        return true;
    }
}

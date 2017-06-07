package com.oracle.ocs.tools.loggeranalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class aims to provide basic functionality for handle tokens.
 *
 * @author Andrés Farías on 6/1/17.
 */
public class TokenUtils {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    /* Initial token's delimiter */
    private String initDelimiter;

    /* Final token's delimiter */
    private String endDelimiter;

    /**
     * The more basic constructor requires the token's delimiters to be defined.
     *
     * @param initDelimiter Initial token's delimiter
     * @param endDelimiter  Final token's delimiter
     */
    public TokenUtils(String initDelimiter, String endDelimiter) {
        this.initDelimiter = initDelimiter;
        this.endDelimiter = endDelimiter;
    }

    /**
     * This method is responsible for parsing a line and returning the token value at a specific position.
     *
     * @param line     The line to be parsed to extract the log.
     * @param position The token position on the line.
     *
     * @return The token's vale.
     */
    public String extractTokenAtPosition(String line, int position) {

        List<LogRecordToken> logRecordTokens = extractTokensFromLine(line);

        /* The token at the specified position is returned (only if there are enough tokens) */
        if (position <= logRecordTokens.size()) {
            return logRecordTokens.get(position - 1).getTokenValue();
        }

        /* Otherwise, there were less tokens than the asked position */
        logger.error("{} token extrated: {}", logRecordTokens.size(), logRecordTokens);
        throw new IllegalArgumentException("No that so many tokens!");
    }

    /**
     * This method is responsible to retrieving all the tokens from a line.
     *
     * @param line The line from which the tokens are retrieved.
     *             TODO: Test this method.
     *
     * @return A list of tokens extracted from the line.
     */
    public List<LogRecordToken> extractTokensFromLine(String line) {

        if (line.length() <= 2) {
            return Collections.emptyList();
        }

        List<LogRecordToken> tokens = new ArrayList<>();

        int index, last = 0, loop = 0;
        while ((index = line.indexOf(this.initDelimiter, last)) != -1) {

            /* If the END_TOKEN is found, a token has been found */
            if ((last = line.indexOf(this.endDelimiter, index)) != -1) {
                tokens.add(new LogRecordToken(null, line.substring(index + 1, last)));
            } else {
                return tokens;
            }
        }

        return tokens;
    }
}

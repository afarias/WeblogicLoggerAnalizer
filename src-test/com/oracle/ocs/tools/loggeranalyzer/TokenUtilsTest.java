package com.oracle.ocs.tools.loggeranalyzer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TokenUtilsTest {

    private String initDelimiter;
    private String endDelimiter;
    private TokenUtils tokenUtils;

    public TokenUtilsTest() {
        this.initDelimiter = "<";
        this.endDelimiter = ">";
        this.tokenUtils = new TokenUtils(initDelimiter, endDelimiter);
    }

    @Test
    public void testExtractToken() throws Exception {

        String tokenValue = "A TOKEN";
        String token = initDelimiter + tokenValue + endDelimiter;
        String extractedTokenValue = tokenUtils.extractTokenAtPosition(token, 1);


        assertEquals(tokenValue, extractedTokenValue);
    }

    @Test
    public void testExtractToken02() throws Exception {

        String tokenValue = "A TOKEN";
        String token = initDelimiter + tokenValue + endDelimiter;

        String line = "<TIME!> " + token;
        String extractedTokenValue = tokenUtils.extractTokenAtPosition(line, 2);

        assertEquals(tokenValue, extractedTokenValue);
    }

    @Test
    public void testExtractToken03() throws Exception {

        String tokenValue = "A TOKEN";
        String token = initDelimiter + tokenValue + endDelimiter;

        String line = initDelimiter + "TIME!" + endDelimiter + token + " " + initDelimiter + "MINUTES!" + endDelimiter;
        String extractedTokenValue = tokenUtils.extractTokenAtPosition(line, 2);


        assertEquals(tokenValue, extractedTokenValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExtractToken04() throws Exception {
        tokenUtils.extractTokenAtPosition("", 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExtractToken05() throws Exception {
        tokenUtils.extractTokenAtPosition("<", 2);
    }
}
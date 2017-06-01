package com.oracle.ocs.tools.loggeranalyzer;

import org.junit.Test;

import static com.oracle.ocs.tools.loggeranalyzer.LogRecordToken.END_TOKEN;
import static com.oracle.ocs.tools.loggeranalyzer.LogRecordToken.INIT_TOKEN;
import static org.junit.Assert.*;

public class TokenUtilsTest {

    @Test
    public void testExtractToken() throws Exception {

        String tokenValue = "A TOKEN";
        String token = INIT_TOKEN + tokenValue + END_TOKEN;
        String extractedTokenValue = TokenUtils.extractToken(token, 1);


        assertEquals(tokenValue, extractedTokenValue);
    }

    @Test
    public void testExtractToken02() throws Exception {

        String tokenValue = "A TOKEN";
        String token = INIT_TOKEN + tokenValue + END_TOKEN;

        String line = "<TIME!> " + token;
        String extractedTokenValue = TokenUtils.extractToken(line, 2);


        assertEquals(tokenValue, extractedTokenValue);
    }

    @Test
    public void testExtractToken03() throws Exception {

        String tokenValue = "A TOKEN";
        String token = INIT_TOKEN + tokenValue + END_TOKEN;

        String line = INIT_TOKEN + "TIME!"+ END_TOKEN + token + " " + INIT_TOKEN + "MINUTES!"+ END_TOKEN;
        String extractedTokenValue = TokenUtils.extractToken(line, 2);


        assertEquals(tokenValue, extractedTokenValue);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExtractToken04() throws Exception {
        TokenUtils.extractToken("", 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExtractToken05() throws Exception {
        TokenUtils.extractToken("<", 2);
    }
}
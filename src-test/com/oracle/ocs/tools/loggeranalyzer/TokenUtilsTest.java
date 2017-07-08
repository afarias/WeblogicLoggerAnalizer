package com.oracle.ocs.tools.loggeranalyzer;

import com.oracle.ocs.tools.loggeranalyzer.model.Delimiters;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class TokenUtilsTest {

    private Delimiters delimiters;
    private TokenUtils tokenUtils;

    public TokenUtilsTest() {
        this.delimiters = new Delimiters("<", ">");
        this.tokenUtils = new TokenUtils(delimiters);
    }

    @Test
    public void testExtractToken() throws Exception {

        String tokenValue = "A TOKEN";
        String token = delimiters.wrap(tokenValue);
        String extractedTokenValue = tokenUtils.extractTokenAtPosition(token, 1);


        assertEquals(tokenValue, extractedTokenValue);
    }

    @Test
    public void testExtractToken02() throws Exception {

        String tokenValue = "A TOKEN";
        String token = delimiters.wrap(tokenValue);

        String line = "<TIME!> " + token;
        String extractedTokenValue = tokenUtils.extractTokenAtPosition(line, 2);

        assertEquals(tokenValue, extractedTokenValue);
    }

    @Test
    public void testExtractToken03() throws Exception {

        String tokenValue = "A TOKEN";
        String token = delimiters.wrap(tokenValue);

        String line = delimiters.wrap("TIME!") + token + " " + delimiters.wrap("MINUTES!");
        String extractedTokenValue = tokenUtils.extractTokenAtPosition(line, 2);


        assertEquals(tokenValue, extractedTokenValue);
    }

    @Test
    public void testExtractTokens01() throws Exception {
        String line = "####<Jun 21, 2017 3:51:29 PM CLT> <Error> <oracle.soa.services.workflow.common> <srvbpm1.bice.local> <bpm01> <[ACTIVE] ExecuteThread: '120' for queue: 'weblogic.kernel.Default (self-tuning)'> <nzuniga> <> <005KbqlePWf5qYWFLzzG8A0002Lc000Ma6> <1498074689943> <BEA-000000> <<.>";
        List<LogRecordToken> logRecordTokens = tokenUtils.extractTokensFromLine(line);

        assertEquals(12, logRecordTokens.size());
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
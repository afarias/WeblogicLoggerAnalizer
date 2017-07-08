package com.oracle.ocs.tools.loggeranalyzer.model;

import com.oracle.ocs.tools.loggeranalyzer.LogFileConfiguration;
import com.oracle.ocs.tools.loggeranalyzer.LogRecord;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class LogFileUtilitiesTest {

    @Test
    public void testExtractFirstLogRecord01() throws Exception {

        URL resource = ClassLoader.getSystemResource("com/oracle/ocs/tools/loggeranalyzer/model/logTest01.log");
        LogFileUtilities logFileUtilities = new LogFileUtilities(new LogFileConfiguration(new File(resource.toURI())));

        LogRecord logRecord = logFileUtilities.extractFirstLogRecord();
        assertEquals(1, logRecord.getLines().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExtractFirstLogRecord02() throws Exception {

        URL resource = ClassLoader.getSystemResource("com/oracle/ocs/tools/loggeranalyzer/model/logTest02.log");
        LogFileUtilities logFileUtilities = new LogFileUtilities(new LogFileConfiguration(new File(resource.toURI())));

        logFileUtilities.extractFirstLogRecord();
    }

    @Test
    public void testHasNextLine01() throws Exception {

        URL resource = ClassLoader.getSystemResource("com/oracle/ocs/tools/loggeranalyzer/model/logTest01.log");
        LogFileUtilities logFileUtilities = new LogFileUtilities(new LogFileConfiguration(new File(resource.toURI())));

        boolean hasNext = logFileUtilities.hasNextLine();
        assertEquals(true, hasNext);
    }

    @Test
    public void testNextLine01() throws Exception {

        URL resource = ClassLoader.getSystemResource("com/oracle/ocs/tools/loggeranalyzer/model/logTest01.log");
        LogFileUtilities logFileUtilities = new LogFileUtilities(new LogFileConfiguration(new File(resource.toURI())));

        String nextLine = logFileUtilities.getNextLine();
        assertEquals("####<Jun 21, 2017 4:22:17 PM CLT> <Error> <oracle.soa.services.workflow.common> <srvbpm1.bice.local> <bpm01> <[ACTIVE] ExecuteThread: '60' for queue: 'weblogic.kernel.Default (self-tuning)'> <fnorambu> <> <005KbsVp3ls5qYWFLzzG8A0002Lc000N59> <1498076537925> <BEA-000000> <<.>", nextLine);
    }
}
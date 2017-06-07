package com.oracle.ocs.tools.loggeranalyzer;

import org.junit.Test;

import java.io.File;
import java.util.List;

import static com.oracle.ocs.tools.loggeranalyzer.TokenType.LEVEL;
import static org.junit.Assert.*;

public class LogFileFactoryTest {

    private LogFileFactory logFileFactory;

    public LogFileFactoryTest() {
        File logFile = new File("");
        LogFileConfiguration logFileConfig = new LogFileConfiguration(logFile);
        logFileConfig.setInitDelimiter("<");
        logFileConfig.setEndDelimiter(">");
        logFileConfig.addTokenPosition(LEVEL, 2);


        this.logFileFactory = new LogFileFactory(logFileConfig);
    }

    @Test
    public void testIsLogRecordHeader01() throws Exception {
        String line = "<May 4, 2017 10:53:47 AM> <INFO> <NodeManager> <Server output log file is '/u01/app/bpm11117/domains/bpmBICEPROD/servers/bpm01/logs/bpm01.out'>";
        boolean isLogRecordHeader = logFileFactory.isLogRecordHeader(line);

        assertTrue(isLogRecordHeader);
    }

    @Test
    public void testIsLogRecordHeader02() throws Exception {
        String line = "<May 4, 2017 10:53:47 AM> <INFO> <NodeManager> <Server output log file is '/u01/app/bpm11117/domains/bpmBICEPROD/servers/bpm01/logs/bpm01.out'>";
        boolean isLogRecordHeader = logFileFactory.isLogRecordHeader(line);

        assertTrue(isLogRecordHeader);
    }

    @Test
    public void testIsLogRecordHeaderFail01() throws Exception {
        String line = "[WARN ] Use of -Djrockit.optfile is deprecated and discouraged.";
        boolean isLogRecordHeader = logFileFactory.isLogRecordHeader(line);

        assertFalse(isLogRecordHeader);
    }

    @Test
    public void testIsLogRecordHeaderFail02() throws Exception {
        String line = "<oracle.tip.adapter.apps.AppsConnectionFactory> ConnectionManager cm: weblogic.connector.outbound.ConnectionManagerImpl@48fd952b-eis/Apps/Apps ManagedConnectionFactory mcf: oracle.tip.adapter.apps.AppsManagedConnectionFactory@4303022b";
        boolean isLogRecordHeader = logFileFactory.isLogRecordHeader(line);

        assertFalse(isLogRecordHeader);
    }


    @Test
    public void testParseLogRecordTokens() throws Exception {
        String line = "<May 4, 2017 10:53:47 AM> <INFO> <NodeManager> <Server output log file is '/u01/app/bpm11117/domains/bpmBICEPROD/servers/bpm01/logs/bpm01.out'>";
        List<LogRecordToken> logRecordTokens = logFileFactory.parseLogRecordTokens(line, logFileFactory.getTokenPositions());

        assertEquals(1, logRecordTokens.size());
    }
}
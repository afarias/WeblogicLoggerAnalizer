package com.oracle.ocs.tools.loggeranalyzer;

import com.oracle.ocs.tools.loggeranalyzer.model.Delimiters;
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
        logFileConfig.setDelimiter(new Delimiters("<", ">"));
        logFileConfig.addTokenPosition(LEVEL, 2);


        this.logFileFactory = new LogFileFactory(logFileConfig);
    }


    @Test
    public void testParseLogRecordTokens() throws Exception {
        String line = "<May 4, 2017 10:53:47 AM> <INFO> <NodeManager> <Server output log file is '/u01/app/bpm11117/domains/bpmBICEPROD/servers/bpm01/logs/bpm01.out'>";
        List<LogRecordToken> logRecordTokens = logFileFactory.parseLogRecordTokens(line, logFileFactory.getTokenPositions());

        assertEquals(1, logRecordTokens.size());
    }
}
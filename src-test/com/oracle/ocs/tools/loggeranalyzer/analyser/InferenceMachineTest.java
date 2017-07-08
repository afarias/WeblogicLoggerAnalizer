package com.oracle.ocs.tools.loggeranalyzer.analyser;

import com.oracle.ocs.tools.loggeranalyzer.LogFileConfiguration;
import com.oracle.ocs.tools.loggeranalyzer.LogRecord;
import com.oracle.ocs.tools.loggeranalyzer.TokenType;
import com.oracle.ocs.tools.loggeranalyzer.model.Delimiters;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class InferenceMachineTest {

    private Logger logger = LoggerFactory.getLogger(InferenceMachineTest.class);

    private LogFileConfiguration logConfig;

    private InferenceMachine inferenceMachine;

    public InferenceMachineTest() {
        this.logConfig = new LogFileConfiguration(null);
        this.logConfig.setDelimiter(new Delimiters("<", ">"));

        this.logConfig.addTokenPosition(TokenType.DATE, 1);

        this.inferenceMachine = new InferenceMachine(logConfig.getLogFile());
    }

    @Test
    public void testInferLevelPosition01() throws Exception {
        int inferLevelPosition = inferenceMachine.inferSeverityLevelPosition(new LogRecord("<ERROR>"));

        assertEquals(1, inferLevelPosition);
    }

    @Test
    public void testInferLevelPosition11() throws Exception {
        int inferLevelPosition = inferenceMachine.inferSeverityLevelPosition(new LogRecord("<TROLL>"));

        assertEquals(-1, inferLevelPosition);
    }

    /**
     * This test tries with whitespaces within the line.
     *
     * @throws Exception
     */
    @Test
    public void testInferLevelPosition02() throws Exception {
        int inferLevelPosition = inferenceMachine.inferSeverityLevelPosition(new LogRecord(" <ERROR> "));

        assertEquals(1, inferLevelPosition);
    }

    /**
     * This test tries with other Level.
     *
     * @throws Exception
     */
    @Test
    public void testInferLevelPosition03() throws Exception {
        int inferLevelPosition = inferenceMachine.inferSeverityLevelPosition(new LogRecord("<INFO>"));

        assertEquals(1, inferLevelPosition);
    }

    /**
     * This test tries with a Level in a second token position.
     *
     * @throws Exception
     */
    @Test
    public void testInferLevelPosition04() throws Exception {
        int inferLevelPosition = inferenceMachine.inferSeverityLevelPosition(new LogRecord("<Chalalala> <INFO>"));

        assertEquals(2, inferLevelPosition);
    }

    /**
     * This test tries with a Level in a second token position.
     *
     * @throws Exception
     */
    @Test
    public void testInferLevelPosition05() throws Exception {
        int inferLevelPosition = inferenceMachine.inferSeverityLevelPosition(new LogRecord("<Chalalala> <DUMN> Andres <INFO>"));

        assertEquals(3, inferLevelPosition);
    }

    @Test
    public void testDetermineDateFormat03() throws Exception {
        String regex = "^[a-z]{3}\\s{1,}\\d{1,2},\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}\\s(AM|PM)";
        String date = "May 4, 2004 10:53:47 AM";
        boolean matches = date.toLowerCase().matches(regex.toLowerCase());

        assertTrue(matches);
    }

    @Test
    public void testInferDatePosition() throws Exception {
        LogRecord logRecord = new LogRecord("<May 4, 2017 10:53:47 AM> <INFO> <NodeManager> <Server output log file is '/u01/app/bpm11117/domains/bpmBICEPROD/servers/bpm01/logs/bpm01.out'>");

        int position = inferenceMachine.inferDatePosition(logRecord);
        assertEquals(1, position);
    }
    @Test
    public void testIsLogRecordHeader01() throws Exception {
        String line = "<May 4, 2017 10:53:47 AM> <INFO> <NodeManager> <Server output log file is '/u01/app/bpm11117/domains/bpmBICEPROD/servers/bpm01/logs/bpm01.out'>";
        boolean isLogRecordHeader = inferenceMachine.isLogRecordHeader(line);

        assertTrue(isLogRecordHeader);
    }

    @Test
    public void testIsLogRecordHeader02() throws Exception {
        String line = "<May 4, 2017 10:53:47 AM> <INFO> <NodeManager> <Server output log file is '/u01/app/bpm11117/domains/bpmBICEPROD/servers/bpm01/logs/bpm01.out'>";
        boolean isLogRecordHeader = inferenceMachine.isLogRecordHeader(line);

        assertTrue(isLogRecordHeader);
    }

    @Test
    public void testIsLogRecordHeaderFail01() throws Exception {
        String line = "[WARN ] Use of -Djrockit.optfile is deprecated and discouraged.";
        boolean isLogRecordHeader = inferenceMachine.isLogRecordHeader(line);

        assertFalse(isLogRecordHeader);
    }

    @Test
    public void testIsLogRecordHeaderFail02() throws Exception {
        String line = "<oracle.tip.adapter.apps.AppsConnectionFactory> ConnectionManager cm: weblogic.connector.outbound.ConnectionManagerImpl@48fd952b-eis/Apps/Apps ManagedConnectionFactory mcf: oracle.tip.adapter.apps.AppsManagedConnectionFactory@4303022b";
        boolean isLogRecordHeader = inferenceMachine.isLogRecordHeader(line);

        assertTrue(isLogRecordHeader);
    }

}
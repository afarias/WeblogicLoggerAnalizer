package com.oracle.ocs.tools.loggeranalyzer.analyser;

import com.oracle.ocs.tools.loggeranalyzer.LogFile;
import com.oracle.ocs.tools.loggeranalyzer.LogFileConfiguration;
import com.oracle.ocs.tools.loggeranalyzer.LogRecord;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InferenceMachineTest {

    private LogFileConfiguration logConfig;

    private InferenceMachine inferenceMachine;

    public InferenceMachineTest() {
        this.logConfig = new LogFileConfiguration(null);
        this.logConfig.setInitDelimiter("<");
        this.logConfig.setEndDelimiter(">");

        this.inferenceMachine = new InferenceMachine(new LogFile(logConfig));
    }

    @Test
    public void testInferLevelPosition01() throws Exception {
        int inferLevelPosition = inferenceMachine.inferLevelPosition(new LogRecord("<ERROR>"));

        assertEquals(1, inferLevelPosition);
    }

    @Test
    public void testInferLevelPosition11() throws Exception {
        int inferLevelPosition = inferenceMachine.inferLevelPosition(new LogRecord("<TROLL>"));

        assertEquals(-1, inferLevelPosition);
    }

    /**
     * This test tries with whitespaces within the line.
     *
     * @throws Exception
     */
    @Test
    public void testInferLevelPosition02() throws Exception {
        int inferLevelPosition = inferenceMachine.inferLevelPosition(new LogRecord(" <ERROR> "));

        assertEquals(1, inferLevelPosition);
    }

    /**
     * This test tries with other Level.
     *
     * @throws Exception
     */
    @Test
    public void testInferLevelPosition03() throws Exception {
        int inferLevelPosition = inferenceMachine.inferLevelPosition(new LogRecord("<INFO>"));

        assertEquals(1, inferLevelPosition);
    }

    /**
     * This test tries with a Level in a second token position.
     *
     * @throws Exception
     */
    @Test
    public void testInferLevelPosition04() throws Exception {
        int inferLevelPosition = inferenceMachine.inferLevelPosition(new LogRecord("<Chalalala> <INFO>"));

        assertEquals(2, inferLevelPosition);
    }

    /**
     * This test tries with a Level in a second token position.
     *
     * @throws Exception
     */
    @Test
    public void testInferLevelPosition05() throws Exception {
        int inferLevelPosition = inferenceMachine.inferLevelPosition(new LogRecord("<Chalalala> <DUMN> Andres <INFO>"));

        assertEquals(3, inferLevelPosition);
    }

}
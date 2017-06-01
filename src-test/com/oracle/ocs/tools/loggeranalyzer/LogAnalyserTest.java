package com.oracle.ocs.tools.loggeranalyzer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LogAnalyserTest {

    @Test
    public void testInferLevelPosition01() throws Exception {

        LogAnalyser logAnalyser = new LogAnalyser();
        int inferLevelPosition = logAnalyser.inferLevelPosition(new LogRecord("<ERROR>"));

        assertEquals(1, inferLevelPosition);
    }

    @Test
    public void testInferLevelPosition11() throws Exception {

        LogAnalyser logAnalyser = new LogAnalyser();
        int inferLevelPosition = logAnalyser.inferLevelPosition(new LogRecord("<TROLL>"));

        assertEquals(-1, inferLevelPosition);
    }

    /**
     * This test tries with whitespaces within the line.
     *
     * @throws Exception
     */
    @Test
    public void testInferLevelPosition02() throws Exception {

        LogAnalyser logAnalyser = new LogAnalyser();
        int inferLevelPosition = logAnalyser.inferLevelPosition(new LogRecord(" <ERROR> "));

        assertEquals(1, inferLevelPosition);
    }

    /**
     * This test tries with other Level.
     *
     * @throws Exception
     */
    @Test
    public void testInferLevelPosition03() throws Exception {

        LogAnalyser logAnalyser = new LogAnalyser();
        int inferLevelPosition = logAnalyser.inferLevelPosition(new LogRecord("<INFO>"));

        assertEquals(1, inferLevelPosition);
    }

    /**
     * This test tries with a Level in a second token position.
     *
     * @throws Exception
     */
    @Test
    public void testInferLevelPosition04() throws Exception {

        LogAnalyser logAnalyser = new LogAnalyser();
        int inferLevelPosition = logAnalyser.inferLevelPosition(new LogRecord("<Chalalala> <INFO>"));

        assertEquals(2, inferLevelPosition);
    }

    /**
     * This test tries with a Level in a second token position.
     *
     * @throws Exception
     */
    @Test
    public void testInferLevelPosition05() throws Exception {

        LogAnalyser logAnalyser = new LogAnalyser();
        int inferLevelPosition = logAnalyser.inferLevelPosition(new LogRecord("<Chalalala> <DUMN> Andres <INFO>"));

        assertEquals(3, inferLevelPosition);
    }
}
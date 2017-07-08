package com.oracle.ocs.commons.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class FileUtilsTest {

    @Test
    public void testExtractLine01() throws Exception {
        URL resource = ClassLoader.getSystemResource("com/oracle/ocs/commons/utils/test.txt");
        String theLine = FileUtils.extractLine(1, new File(resource.toURI()));

        assertEquals("Line 1", theLine);
    }

    @Test
    public void testExtractLine02() throws Exception {
        URL resource = ClassLoader.getSystemResource("com/oracle/ocs/commons/utils/test.txt");
        String theLine = FileUtils.extractLine(2, new File(resource.toURI()));

        assertEquals("Line 2", theLine);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExtractLine03() throws Exception {
        URL resource = ClassLoader.getSystemResource("com/oracle/ocs/commons/utils/test.txt");
        FileUtils.extractLine(-1, new File(resource.toURI()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExtractLine04() throws Exception {
        URL resource = ClassLoader.getSystemResource("com/oracle/ocs/commons/utils/test.txt");
        FileUtils.extractLine(3, new File(resource.toURI()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExtractLine05() throws Exception {
        URL resource = ClassLoader.getSystemResource("com/oracle/ocs/commons/utils/test.txt");
        FileUtils.extractLine(4, new File(resource.toURI()));
    }

    @Test
    public void countLine01() throws Exception {
        URL resource = ClassLoader.getSystemResource("com/oracle/ocs/commons/utils/test.txt");
        int countedLines = FileUtils.countLines(new File(resource.toURI()).getAbsolutePath());

        assertEquals(2, countedLines);
    }

    @Test
    public void extractFirstLine01() throws Exception {
        URL resource = ClassLoader.getSystemResource("com/oracle/ocs/commons/utils/test.txt");
        String extractFirstLine = FileUtils.extractFirstLine(new File(resource.toURI()));

        assertEquals("Line 1", extractFirstLine);
    }

    @Test(expected = IllegalArgumentException.class)
    public void extractFirstLine02() throws Exception {
        URL resource = ClassLoader.getSystemResource("com/oracle/ocs/commons/utils/testEmpty.txt");
        FileUtils.extractFirstLine(new File(resource.toURI()));
    }
}
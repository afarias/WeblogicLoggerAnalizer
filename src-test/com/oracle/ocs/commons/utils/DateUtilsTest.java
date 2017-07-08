package com.oracle.ocs.commons.utils;

import com.oracle.ocs.tools.loggeranalyzer.analyser.InferenceMachine;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

import static com.oracle.ocs.commons.utils.DateUtils.determineDateFormat;
import static org.junit.Assert.*;

public class DateUtilsTest {

    private Logger logger = LoggerFactory.getLogger(DateUtilsTest.class);

    @Test
    public void testDetermineDateFormat() throws Exception {
        String foundDate = determineDateFormat("2017-12-21");
        assertNotNull(foundDate);
        logger.info("Test passed. Date found: " + foundDate);
    }

    @Test
    public void testDetermineDateFormat02() throws Exception {
        String foundDate = determineDateFormat("May 4, 2004 10:53:47 AM");

        assertNotNull(foundDate);
        logger.info("Test passed. Date found: " + foundDate);
    }

    @Test
    public void testDetermineDateFormat03() throws Exception {
        String foundDate = determineDateFormat("may 4, 2017 10:53:52 am clst");

        assertNotNull(foundDate);
        logger.info("Test passed. Date found: " + foundDate);
    }

    @Test
    public void testFormat() throws Exception {

        Date parsedDate = DateUtils.format("May 4, 2004 10:53:47 AM");

        assertNotNull(parsedDate);

        Calendar instance = Calendar.getInstance();
        instance.setTime(parsedDate);
        assertEquals(2004, instance.get(Calendar.YEAR));
        assertEquals(Calendar.MAY, instance.get(Calendar.MONTH));
        assertEquals(4, instance.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testFormat02() throws Exception {

        Date parsedDate = DateUtils.format("may 4, 2017 10:53:52 am clst");

        assertNotNull(parsedDate);

        Calendar instance = Calendar.getInstance();
        instance.setTime(parsedDate);
        assertEquals(2017, instance.get(Calendar.YEAR));
        assertEquals(Calendar.MAY, instance.get(Calendar.MONTH));
        assertEquals(4, instance.get(Calendar.DAY_OF_MONTH));
    }
}
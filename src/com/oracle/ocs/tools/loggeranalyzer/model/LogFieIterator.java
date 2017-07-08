package com.oracle.ocs.tools.loggeranalyzer.model;

import com.oracle.ocs.tools.loggeranalyzer.LogRecord;

import java.util.Iterator;

/**
 * @author Andrés Farías on 7/5/17.
 */
public class LogFieIterator implements Iterator<LogRecord>{
    
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public LogRecord next() {
        return null;
    }

    @Override
    public void remove() {

    }
}

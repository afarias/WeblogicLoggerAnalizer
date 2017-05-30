package com.oracle.ocs.tools.loggeranalyzer;

import com.oracle.ocs.commons.files.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Andrés Farías on 5/26/17.
 */
public class LogFile {

    /** The physical file with the Log */
    private File logFile;

    /** The number of lines on the log */
    private int lines;

    public LogFile(File file) {
        this.logFile = file;
    }

    public File getLogFile() {
        return logFile;
    }

    /**
     * This method is responsible for counting the lines contained in the Log.
     *
     * @return The number of lines of the log's file.
     *
     * @throws IOException If there is a problem while working with the file.
     */
    public int getLines() throws IOException {
        return FileUtils.countLines(this.logFile.getAbsolutePath());
    }
}

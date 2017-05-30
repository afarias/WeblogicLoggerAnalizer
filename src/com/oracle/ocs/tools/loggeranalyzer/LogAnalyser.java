package com.oracle.ocs.tools.loggeranalyzer;

import com.oracle.ocs.commons.files.FileUtils;

import java.io.IOException;

/**
 * @author Andrés Farías on 5/26/17.
 */
public class LogAnalyser {

    /**
     * This method is responsible for analysing the log and grouping the collected information in an
     * <code>AnalysisReport</code> object.
     *
     * @param logFile The Log to be analyzed.
     *
     * @return A report with the log file analysis.
     *
     * @throws java.io.IOException If there are problems while analysing the log file.
     */
    public AnalysisReport analyze(LogFile logFile) throws IOException {

        AnalysisReport analysisReport = new AnalysisReport(logFile);

        return analysisReport;
    }
}

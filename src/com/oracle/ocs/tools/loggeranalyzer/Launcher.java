package com.oracle.ocs.tools.loggeranalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * This class aims to easy the launching process.
 *
 * @author Andrés Farías on 5/18/17.
 */
public class Launcher {

    private static Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) throws IOException {

        /* The file is chosen */
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("/Users/andres/Dropbox/work/oracle/bice/ORACLE BPM/"));

        /* The Dialog is opened */
        Component parent = new JFrame("WebLogic Logger Analyzer");
        fileChooser.showOpenDialog(parent);

        File selectedFile = fileChooser.getSelectedFile();
        logger.info("Archivo nombre: " + selectedFile.getName());
        logger.info("Archivo ruta: " + selectedFile.getAbsolutePath());

        /* Se crea el objeto que representa el Log */
        LogFile logFile = new LogFile(selectedFile);

        LogAnalyser logAnalyzer = new LogAnalyser();
        AnalysisReport report = logAnalyzer.analyze(logFile);

        logger.info(report.toString());
        System.exit(0);
    }
}

package com.oracle.ocs.tools.loggeranalyzer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * This class aims to easy the launching process.
 *
 * @author Andrés Farías on 5/18/17.
 */
public class Launcher {

    private static Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) throws IOException {

        URL resource = new Thread().getContextClassLoader().getResource("log4j.xml");
        /* The file is chosen */
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("/temp/logs"));

        /* The Dialog is opened */
        Component parent = new JFrame("WebLogic Logger Analyzer");
        fileChooser.showOpenDialog(parent);

        logger.info("Archivo nombre: " + fileChooser.getSelectedFile().getName());
        logger.info("Archivo ruta: " + fileChooser.getSelectedFile().getAbsolutePath());

        /* Se crea el objeto que representa el Log */
        LogFile logFile = new LogFile(fileChooser.getSelectedFile());

        LogAnalyser logAnalyzer = new LogAnalyser();
        AnalysisReport report = logAnalyzer.analyze(logFile);

        logger.info(report.toString());
        System.exit(0);
    }
}

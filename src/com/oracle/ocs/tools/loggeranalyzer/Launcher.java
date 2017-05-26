package com.oracle.ocs.tools.loggeranalyzer;

import org.apache.log4j.lf5.util.LogFileParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

/**
 * This class aims to easy the launching process.
 *
 * @author Andrés Farías on 5/18/17.
 */
public class Launcher {

    private static Logger logger = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) {

        URL resource = new Thread().getContextClassLoader().getResource("log4j.xml");
        /* The file is chosen */
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("/temp/logs"));

        /* The Dialog is opened */
        Component parent = new JFrame("WebLogic Logger Analyzer");
        int result = fileChooser.showOpenDialog(parent);

        logger.debug("Resultado del diálogo: " + result);
        logger.debug("Archivo recuperado: " + fileChooser.getSelectedFile().getName());

        /* Se crea el objeto que representa el Log */
        LogFile logFile = new LogFile(fileChooser.getSelectedFile());
    }
}

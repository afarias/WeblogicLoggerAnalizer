package com.oracle.ocs.commons.utils;

import java.io.*;

/**
 * @author Andrés Farías on 5/30/17.
 */
public class FileUtils {

    /**
     * This is the fastest version I have found so far, about 6 times faster than readLines. On a 150MB log file this
     * takes 0.35 seconds, versus 2.40 seconds when using readLines(). Just for fun, linux' wc -l command takes 0.15
     * seconds.
     *
     * @return
     *
     * @throws IOException Thrown if there are problems while reading the file.
     * @see     https://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java#
     */
    public static int countLines(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count+1;
        } finally {
            is.close();
        }
    }

    /**
     * This method is responsible for  extract the first line of the File.
     *
     * @param file The file whose first line is to be extracted.
     *
     * @return The file's first line.
     *
     * @throws IOException Thrown if there is a problem while reading the file.
     */
    public static String extractFirstLine(File file) throws IOException {

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        if ((line = br.readLine()) != null) {
            return line;
        }

        throw new IllegalArgumentException("The file has no lines");
    }

    /**
     * This method is responsible for extracting the a given line, in the given <code>position</code> of the file.
     *
     * @param position The line number requested.
     * @param file     The file from which the line is retrieved.
     *
     * @return The line extracted.
     *
     * @throws IOException              If there is a problem when reading the file.
     * @throws IllegalArgumentException Thrown if the position is negative, or if the position is greater than
     *                                  the lines in the file.
     */
    public static String extractLine(int position, File file) throws IOException {

        if (position <= 0) {
            throw new IllegalArgumentException("The line number should be a positive integer");
        }

        String line;
        BufferedReader br = new BufferedReader(new FileReader(file));
        int counter = 0;
        do {
            line = br.readLine();
            counter++;
        } while (counter < position && (line != null));

        if ((position >= counter) && (line == null)) {
            throw new IllegalArgumentException("The file has not so many lines.");
        }

        return line;
    }
}

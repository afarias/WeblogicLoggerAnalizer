package com.oracle.ocs.commons.files;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
     * @see https://stackoverflow.com/questions/453018/number-of-lines-in-a-file-in-java#
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
            return (count == 0 && !empty) ? 1 : count;
        } finally {
            is.close();
        }
    }
}

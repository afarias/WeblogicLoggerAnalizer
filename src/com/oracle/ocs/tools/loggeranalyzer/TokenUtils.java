package com.oracle.ocs.tools.loggeranalyzer;

import static com.oracle.ocs.tools.loggeranalyzer.LogRecordToken.END_TOKEN;
import static com.oracle.ocs.tools.loggeranalyzer.LogRecordToken.INIT_TOKEN;

/**
 * @author Andrés Farías on 6/1/17.
 */
public class TokenUtils {

    public static String extractToken(String line, int position) {

        int index, last = 0, loop = 0;
        while ((index = line.indexOf(INIT_TOKEN, last)) != -1) {

            /* If the END_TOKEN is found, a token has been found */
            if ((last = line.indexOf(END_TOKEN, index)) != -1) {

                /* The founder counter is increased */
                loop++;

                /* If it is the token at the specified position, it is returned */
                if (loop == position) {
                    return line.substring(index+1, last);
                }
            } else {
                last = index;

                if (line.length() == index + 1){
                    throw new IllegalArgumentException("No that so many tokens!");
                }
            }
        }

        throw new IllegalArgumentException("No that so many tokens!");
    }

}

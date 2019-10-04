package org.fundamentals.fp.playground.cf;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleCurl {

    public static String fetch(URL myURL) {

        LOGGER.info("Thread: {}", Thread.currentThread().getName());
        LOGGER.info("Requeted URL: {}", myURL);

        StringBuilder sb = new StringBuilder();

        try {

            InputStreamReader in = null;
            URLConnection urlConn = myURL.openConnection();
            if (urlConn != null) {
                urlConn.setReadTimeout(5 * 1000);
            }
            if (urlConn != null && urlConn.getInputStream() != null) {
                in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
                BufferedReader bufferedReader = new BufferedReader(in);
                if (bufferedReader != null) {
                    int cp;
                    while ((cp = bufferedReader.read()) != -1) {
                        sb.append((char) cp);
                    }
                    bufferedReader.close();
                }
            }
            in.close();
        } catch (Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
            throw new RuntimeException("Exception while calling URL: " + myURL, e);
        }

        return sb.toString();
    }

}

package com.lgs.eden.api;

import com.lgs.eden.utils.config.Language;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Methods to factorize some code
 * in the API related methods.
 */
public class APIHelper {

    /**
     * Check if the code is "en" or "fr". If not one of those, return "en". The website
     * is only available in one of those.
     */
    public static String formatCode(String code) {
        // not an allowed code
        if (!code.equals(Language.FR.code) && !code.equals(Language.EN.code))
            code = Language.EN.code; // then "en"
        return code; // return
    }

    /**
     * Fake some delay, meaning we wait for xxx ms
     */
    public static void fakeDelay(int ms) {
        // fake some delay
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignore) {}
    }

    public static void makeHTTPSRequest() throws APIException {
        try {
            HttpsURLConnection.setFollowRedirects(false);
            HttpsURLConnection connection = (HttpsURLConnection) new URL("https://duckduckgo.com/").openConnection();
            InputStream inputStream = connection.getInputStream();
            inputStream.close();
        } catch (IOException e) {
            throw new APIException(APIResponseCode.CONNECTION_FAILED, e);
        }
    }
}

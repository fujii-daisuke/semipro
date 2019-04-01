package red.semipro.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpUrlConnector {

    public enum RequestMethod {
        GET("GET"),
        POST("POST")
        ;
        private final String requestMethod;
        private RequestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
        }
        public String getRequestMethod() {
            return this.requestMethod;
        }
    }
    
    public static String callGet(String urlStr) throws IOException {
        HttpsURLConnection connection = null;
        StringBuilder result = new StringBuilder();
        try {
            log.debug(urlStr);
            URL url = new URL(urlStr);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            
            int status = connection.getResponseCode();
            if (status == HttpsURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
                String encoding = connection.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                InputStreamReader inReader = new InputStreamReader(in, encoding);
                BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    result.append(line);
                }
                bufReader.close();
                inReader.close();
                in.close();
            } else {
                log.warn("status: " + status);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result.toString();
    }
}

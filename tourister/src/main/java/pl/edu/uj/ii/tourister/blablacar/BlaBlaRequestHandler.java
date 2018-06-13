package pl.edu.uj.ii.tourister.blablacar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.uj.ii.tourister.Properties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class BlaBlaRequestHandler {
    private Logger LOG  = LoggerFactory.getLogger("tourister-logger");
    public String sendGET(String a, String b){
        a = Properties.aPoint;
        LOG.info("A point is: " + a);
        String params = "?fn=" + a + "&tn=" + b;
        try {
            String result = getTrips(params);
            System.out.println(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getTrips(String params) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(Properties.BLA_BLA_URL + params);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setRequestProperty("key", Properties.API_KEY_BLABLA);
        httpURLConnection.setRequestProperty("User-Agent", Properties.USER_AGENT);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            result.append(line);
        }
        bufferedReader.close();

        return result.toString();
    }
}

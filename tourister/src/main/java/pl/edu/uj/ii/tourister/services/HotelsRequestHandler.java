package pl.edu.uj.ii.tourister.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.Properties;
import pl.edu.uj.ii.tourister.model.Response;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HotelsRequestHandler {
    private Logger LOG = LoggerFactory.getLogger("tourister-logger");

    public Response getHotelsFromRequest(String destination, String distance) {
        System.out.println(destination + " " + distance);
        String params;
        if (distance.equalsIgnoreCase(""))
            params = "?apikey=" + Properties.API_KEY + "&dest=" + destination;
        else
            params = "?apikey=" + Properties.API_KEY + "&dest=" + destination + "&distance=" + distance;
        Response responseClass = new Response();
        try {
            String response = getHotelDeals(params);
            System.out.println("RESPONSE: " + response);
            responseClass.setData(response);
            return responseClass;
        } catch (IOException e) {
            LOG.error("Server doesn't send a RESPONSE. Please, check headers and params!");
        }
        return responseClass;
    }

    private String getHotelDeals(String params) throws IOException{
        StringBuilder result = new StringBuilder();
        URL url = new URL(Properties.HOT_DEALS_URL + params);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", Properties.USER_AGENT);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            result.append(line);
        }
        bufferedReader.close();
        LOG.info("We have a response");
        return result.toString();
    }
}

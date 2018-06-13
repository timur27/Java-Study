package pl.edu.uj.ii.tourister.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.uj.ii.tourister.Properties;
import pl.edu.uj.ii.tourister.model.Response;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HotelsRequestHandler {
    private Logger LOG = LoggerFactory.getLogger("tourister-logger");

    public Response getHotelsFromRequest(String ... parameters) {
        String destination = parameters[0];
        String distance = parameters[1];
        String acceptType = parameters[2];
        String starRating = null;
        if (parameters.length > 3){
            starRating = parameters[3];
        }

        LOG.info("Destination is: " + destination + ". Distance is: " + distance);
        String params;
        if (distance.equalsIgnoreCase(""))
            params = "?apikey=" + Properties.API_KEY + "&dest=" + destination;
        else
            params = "?apikey=" + Properties.API_KEY + "&dest=" + destination + "&distance=" + distance;
        Response responseClass = new Response();
        try {
            LOG.info("Prepared to send request to server");
            String response = getHotelDeals(params, acceptType, starRating);
            responseClass.setData(response);
            return responseClass;
        } catch (IOException e) {
            LOG.error("Server doesn't send a RESPONSE. Please, check headers and params!");
        }
        return responseClass;
    }

    public String getHotelDeals(String params, String acceptType, String starRating) throws IOException{
        LOG.info("The accept type is: " + acceptType);
        StringBuilder result = new StringBuilder();
        if (acceptType != null){
            if (acceptType.equals("json") || acceptType.equals("xml")){
                params += "&format=" + acceptType;
            }
            else{
                LOG.error("Unfortunately, this service is not responding in " + acceptType + " format. Response will have xml format");
            }
        }
        LOG.info(starRating);
        if (starRating != null){
            params += "&starrating=" + starRating;
        }

        LOG.info("Parameters are: " + params);
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
        LOG.info(result.toString());
        return result.toString();
    }
}
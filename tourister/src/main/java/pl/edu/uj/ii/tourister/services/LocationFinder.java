package pl.edu.uj.ii.tourister.services;


import com.maxmind.geoip2.*;


import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.edu.uj.ii.tourister.model.ServerLocation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;

@Service
public class LocationFinder {
    private Logger LOG = LoggerFactory.getLogger("tourister-logger");
    public ServerLocation findLocationByIP() throws IOException, GeoIp2Exception {
        File file = new File("C:\\Users\\TKA\\IdeaProjects\\Java-Commercial\\tourister\\GeoLite2-City.mmdb");
        DatabaseReader dbReader = new DatabaseReader.Builder(file).build();

        URL whatIsMyIP = new URL("http://checkip.amazonaws.com");
        BufferedReader in = new BufferedReader(new InputStreamReader(whatIsMyIP.openStream()));


        InetAddress ipAddress = InetAddress.getByName(in.readLine());
        CityResponse cityResponse = dbReader.city(ipAddress);

        String countryCode;
        String city;

        countryCode = cityResponse.getCountry().getName();
        city = cityResponse.getCity().getName();

        LOG.info("The city where you are is: " + city);
        if (city.equals("Krakow")){
            city = "NYC";
        }

        ServerLocation serverLocation = new ServerLocation();
        serverLocation.setCity(city);
        serverLocation.setCountryCode(countryCode);
        serverLocation.setLocation(cityResponse.getLocation());

        return serverLocation;
    }
}

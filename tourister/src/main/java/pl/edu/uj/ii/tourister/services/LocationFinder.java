package pl.edu.uj.ii.tourister.services;


import com.maxmind.geoip2.*;


import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import org.springframework.stereotype.Service;
import pl.edu.uj.ii.tourister.model.ServerLocation;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
public class LocationFinder {
    public ServerLocation findLocationByIP() throws IOException, GeoIp2Exception {
        File file = new File("C:\\Users\\TKA\\IdeaProjects\\Java-Commercial\\tourister\\GeoLite2-City.mmdb");
        DatabaseReader dbReader = new DatabaseReader.Builder(file).build();

        InetAddress ipAddress = InetAddress.getByName("72.229.28.185");
        CityResponse cityResponse = dbReader.city(ipAddress);

        String countryCode;
        String city;

        countryCode = cityResponse.getCountry().getName();
        city = cityResponse.getCity().getName();

        ServerLocation serverLocation = new ServerLocation();
        serverLocation.setCity(city);
        serverLocation.setCountryCode(countryCode);
        serverLocation.setLocation(cityResponse.getLocation());

        return serverLocation;
    }
}

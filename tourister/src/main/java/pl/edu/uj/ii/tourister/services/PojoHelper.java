package pl.edu.uj.ii.tourister.services;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import pl.edu.uj.ii.tourister.Properties;
import pl.edu.uj.ii.tourister.model.Hotel;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class PojoHelper {

    public CityResponse generateCityResponse() throws IOException, GeoIp2Exception {
        DatabaseReader dbReader = new DatabaseReader.Builder(Properties.ipFile).build();
        CityResponse cityResponse = dbReader.city(InetAddress.getByName("72.229.28.185"));
        return cityResponse;
    }

    public InetAddress generateInetAddress() throws UnknownHostException {
        return InetAddress.getByName("72.229.28.185");
    }


    public List<Hotel> generateHotels(){
        Hotel firstHotel = new Hotel();
        firstHotel.setHotelName("Radisson");
        firstHotel.setStarsRating(4);
        firstHotel.setCity("Cracow");
        firstHotel.setPrice(114);

        Hotel secondHotel = new Hotel();
        secondHotel.setHotelName("Wyndham");
        secondHotel.setStarsRating(4);
        secondHotel.setCity("Gummersbach");
        secondHotel.setPrice(95);

        Hotel thirdHotel = new Hotel();
        thirdHotel.setHotelName("Ibis");
        thirdHotel.setStarsRating(3);
        thirdHotel.setCity("Warsaw");
        thirdHotel.setPrice(80);


        List<Hotel> hotels = new ArrayList<>();
        hotels.add(firstHotel);
        hotels.add(secondHotel);
        hotels.add(thirdHotel);

        return hotels;
    }
}

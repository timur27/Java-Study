package pl.edu.uj.ii.tourister.services;


import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.model.ServerLocation;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;


@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;
    @Autowired
    private LocationFinder locationFinder;
    @Autowired
    private RequestGenerator requestGenerator;
    @Autowired
    private XMLParser xmlParser;


    public Hotel findTheCheapest(List<Hotel> hotelList){
        return hotelList.stream().min(Comparator.comparing(Hotel::getPrice)).get();
    }

    public List<Hotel> findNearest()  {
        ServerLocation serverLocation = null;
        try {
            serverLocation = locationFinder.findLocationByIP();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeoIp2Exception e) {
            e.printStackTrace();
        }

        try {
            return xmlParser.parseAndFill(requestGenerator.generateGET(serverLocation.getCity(), "5"));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
}

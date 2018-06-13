package pl.edu.uj.ii.tourister.services;


import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import pl.edu.uj.ii.tourister.blablacar.BlaBlaRequestHandler;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.model.ServerLocation;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


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
    @Autowired
    private BlaBlaRequestHandler blaBlaRequestHandler;
    @Autowired
    private DBHelper dbHelper;
    @Autowired
    private Mapper mapper;
    private Scanner scn = new Scanner(System.in);
    private Logger LOG = LoggerFactory.getLogger("tourister-logger");

    public Hotel findTheCheapest(List<Hotel> hotelList){
        LOG.info("Trying to find the cheapest hotel from the list");
        if (hotelList.size() == 0)
            return null;
        else
            return hotelList.stream().min(Comparator.comparing(Hotel::getPrice)).get();
    }

    public List<Hotel> findNearest(){
        ServerLocation serverLocation = findServerLocation();
        String nearHotelsResponse = requestGenerator.generateGET(serverLocation.getCity(), "5");
        List<Hotel> nearestHotels = parseFromStringToObject(nearHotelsResponse);
        return nearestHotels;
    }

    public ServerLocation findServerLocation(){
        ServerLocation serverLocation = null;
        try {
            serverLocation = locationFinder.findLocationByIP();
        }
        catch (IOException|GeoIp2Exception e){
            LOG.error("Can't find a location. Try to turn on VPN");
            e.printStackTrace();
        }
        return serverLocation;
    }

    public List<Hotel> parseFromStringToObject(String input){
        List<Hotel> output = null;
        try {
            output = xmlParser.parseAndFill(input);
        } catch (ParserConfigurationException|IOException|SAXException e) {
            e.printStackTrace();
        }
        return output;
    }


    public List<String> planeTheTripToHotel(){
        LOG.info("These cities you have visited/chosed as the best");
        LOG.info("Chosing the best cities with hotels");
        Map<String, List<Hotel>> hotelsPerCity = findBestFromThatWeHave();
        List<String> allWeHave = new ArrayList<>(hotelsPerCity.keySet());
        System.out.println(allWeHave);
        return allWeHave;
    }

    public List<Hotel> findAll(){
        LOG.info("Finding all the hotels");
        return hotelRepository.findAll();
    }

    public Map<String, List<Hotel>> findBestFromThatWeHave() {
        List<Hotel> allWeHave = findAll();
        Map<String, List<Hotel>> hotelsPerCity = allWeHave.stream().collect(Collectors.groupingBy(Hotel::getCity));
        return hotelsPerCity;
    }

    public boolean checkIfDatabaseEmpty(){
        return dbHelper.getAll().isEmpty();
    }
}

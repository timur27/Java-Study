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
import pl.edu.uj.ii.tourister.model.Trips;
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
    private Mapper mapper;
    private Scanner scn = new Scanner(System.in);
    private Logger LOG = LoggerFactory.getLogger("tourister-logger");




    public Hotel findTheCheapest(List<Hotel> hotelList){
        LOG.info("Trying to find the cheapest hotel from the list");
        return hotelList.stream().min(Comparator.comparing(Hotel::getPrice)).get();
    }

    public List<Hotel> findNearest()  {
        ServerLocation serverLocation = null;
        try {
            LOG.info("Trying to find nearest hotel from those we have");
            serverLocation = locationFinder.findLocationByIP();
        } catch (IOException e) {
            LOG.error("Can't find a location. Try to turn on VPN");
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

    public void planeTheTripToHotel(){
        System.out.println("These cities you have visited/chosed as the best");
        LOG.info("Chosing the best cities with hotels");
        Map<String, List<Hotel>> hotelsPerCity = findBestFromThatWeHave();
        List<String> allWeHave = new ArrayList<>(hotelsPerCity.keySet());
        System.out.println(allWeHave);
        System.out.println("Do you want to choose something from this?");
        String chosenCity = scn.next();
        List<Hotel> concreteCityHotels = hotelsPerCity.get(chosenCity);
        String resultJSON = blaBlaRequestHandler.sendGET("", chosenCity);
        Trips foundTrips = mapper.mapToObject(resultJSON);
        System.out.println("Distance between two points: " + foundTrips.getDistance());
        System.out.println("Recommended price: " + foundTrips.getRecommended_price());
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
}

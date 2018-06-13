package pl.edu.uj.ii.tourister.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;
import pl.edu.uj.ii.tourister.blablacar.BlaBlaRequestHandler;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.model.TripInfo;
import pl.edu.uj.ii.tourister.model.Trips;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;
import pl.edu.uj.ii.tourister.services.HotelService;
import pl.edu.uj.ii.tourister.services.Mapper;
import pl.edu.uj.ii.tourister.services.RequestGenerator;
import pl.edu.uj.ii.tourister.services.XMLParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static pl.edu.uj.ii.tourister.Properties.aPoint;

@Controller
public class TripsController {
    @Autowired
    private BlaBlaRequestHandler blaBlaRequestHandler;
    @Autowired
    private Mapper mapper;
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RequestGenerator requestGenerator;
    @Autowired
    private XMLParser xmlParser;
    private Logger LOG = LoggerFactory.getLogger("tourister-logger");


    @RequestMapping(value = "/blablatrip/info", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String getTipFromAToB(@RequestParam("city") String bCity){
        String choosenCity = bCity;
        String response = blaBlaRequestHandler.sendGET(aPoint, bCity);
        Trips tripData = mapper.mapToObject(response);
        return mapper.mapToJSON(tripData);
    }

    @RequestMapping(value = "/choosetrip", method = RequestMethod.GET, params = {"a", "b"}, produces = "application/json")
    @ResponseBody
    public String chooseTrip(@RequestParam("a") String a, @RequestParam("b") String bPoint){
        String cityName = bPoint;
        List<Hotel> hotelList = hotelRepository.findByCity(cityName);
        LOG.info(hotelList.toString());
        Hotel cheapestHotel = hotelService.findTheCheapest(hotelList);
        LOG.info("I AM HERE");
        TripInfo tripInfo = new TripInfo();
        if (cheapestHotel != null){
            LOG.info("We have found cheapest hotel in database");
            tripInfo.setHotelPrice(cheapestHotel.getPrice());
        }
        else {
            String hotelsRespone = requestGenerator.generateGET(bPoint, "5");
            try {
                LOG.info("Did not found hotels in DB. Trying to look in Hotwire.");
                List<Hotel> hotelsList = xmlParser.parseAndFill(hotelsRespone);
                cheapestHotel = hotelService.findTheCheapest(hotelsList);
                tripInfo.setHotelPrice(cheapestHotel.getPrice());
                LOG.info("We have found the cheapest hotel from hotwire");
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            }
        }

        LOG.info("Begin with founding trip");
        aPoint = a;
        String tripResponse = blaBlaRequestHandler.sendGET(a, bPoint);
        Trips trips = mapper.mapToObject(tripResponse);
        LOG.info("Found an information to trip");
        tripInfo.setTripPrice(Integer.valueOf(trips.getRecommended_price()));
        tripInfo.setDistance(trips.getDistance());
        tripInfo.setWholePrice();
        tripInfo.setaPoint(a);
        tripInfo.setbPoint(bPoint); 
        return mapper.mapToJSON(tripInfo);
    }



}


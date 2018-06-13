package pl.edu.uj.ii.tourister.controllers;


import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;
import pl.edu.uj.ii.tourister.DBHelper;
import pl.edu.uj.ii.tourister.Statuses;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.model.Response;
import pl.edu.uj.ii.tourister.services.HotelService;
import pl.edu.uj.ii.tourister.services.HotelsRequestHandler;
import pl.edu.uj.ii.tourister.services.RequestGenerator;
import pl.edu.uj.ii.tourister.services.XMLParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

import static pl.edu.uj.ii.tourister.Properties.aPoint;

@Controller
public class HotelsController {
    @Autowired
    HotelsRequestHandler hotelsRequestHandler;
    @Autowired
    XMLParser xmlParser;
    @Autowired
    DBHelper dbHelper;
    @Autowired
    private RequestGenerator requestGenerator;
    @Autowired
    private HotelService hotelService;
    private Logger LOG = LoggerFactory.getLogger("tourister-logger");

    @RequestMapping(value = "/hoteldeals", method = RequestMethod.GET, produces={"application/json","application/xml"}, params = {"dest", "distance"})
    @ResponseBody
    public String getHotelsWithParameters(@RequestParam(value = "starrating", required = false) String starRating, @RequestParam(value = "format", required = false) String format, @RequestParam("dest") String destination, @RequestParam("distance") String distance) throws IOException, SAXException, ParserConfigurationException, GeoIp2Exception {
        LOG.info("Trying to fetch hotels with destination: " + destination + " and distance: " + distance + " from hotwire service api...");
        Response response = hotelsRequestHandler.getHotelsFromRequest(destination, distance, format, starRating);
        String responseData = response.getData();
        return responseData;
    }


    @RequestMapping(value = "/hoteldeals", method = RequestMethod.POST, produces={"application/json","application/xml"}, params = {"dest", "distance"})
    @ResponseBody
    public String saveHotelsToDatabase(@RequestParam(value = "save", required = true) String databaseOption,@RequestParam(value = "format", required = false) String format, @RequestParam("dest") String destination, @RequestParam("distance") String distance) throws IOException, SAXException, ParserConfigurationException, GeoIp2Exception {
        LOG.info("Trying to fetch hotels with destination: " + destination + " and distance: " + distance + " from hotwire service api...");
        Response response = hotelsRequestHandler.getHotelsFromRequest(destination, distance, format);
        List<Hotel> hotels = requestGenerator.createAndSendRequest(Statuses.GET_HOTELS, destination, distance);
        if (databaseOption.equals("all")){
            LOG.info("Saving hotels to database...");
            dbHelper.performTaskOnData(hotels);
        }
        else if (databaseOption.equals("cheapest")){
            LOG.info("Finding the cheapest hotel from all the list and saving just this hotel to database...");
            dbHelper.saveJustTheCheapestHotel(hotels);
        }
        aPoint = destination;
        LOG.info("The data was successfully saved in database");
        String result = "The data was successfully save in database";
        return result;
    }


    @RequestMapping(value = "/hoteldeals/mycity", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String findNearYou(){
        List<Hotel> outputHotelList =  hotelService.findNearest();
        String output = xmlParser.parseObjectToXML(outputHotelList);
        System.out.println(output);
        return output;
    }

    @RequestMapping(value = "hoteldeals/favourite", method = RequestMethod.GET)
    @ResponseBody
    public String planeTheTripToFavoriteHotel(){
        LOG.info("Trying to check if you have hotels in database...");
        boolean isEmpty = hotelService.checkIfDatabaseEmpty();
        if (isEmpty){
            LOG.info("Unfortunately, the database has no records to choose from");
            return "Unfortunately, the database has no records to choose from";
        }
        else {
            return hotelService.planeTheTripToHotel().toString();
        }
    }

}

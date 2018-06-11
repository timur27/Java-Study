package pl.edu.uj.ii.tourister.controllers;


import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;
import pl.edu.uj.ii.tourister.DBHelper;
import pl.edu.uj.ii.tourister.services.HotelsRequestHandler;
import pl.edu.uj.ii.tourister.services.XMLParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

@Controller
public class MainController {
    @Autowired
    HotelsRequestHandler hotelsRequestHandler;
    @Autowired
    XMLParser xmlParser;
    @Autowired
    DBHelper dbHelper;


    @RequestMapping(value = "/hoteldeals", method = RequestMethod.GET, produces = {"application/xml"}, params = {"dest", "distance"})
    @ResponseBody
    public String getHotel(@RequestParam("dest") String destination, @RequestParam("distance") String distance) throws IOException, SAXException, ParserConfigurationException, GeoIp2Exception {
        String hotelData = hotelsRequestHandler.getHotelsFromRequest(destination, distance).getData();
        dbHelper.performTaskOnData(xmlParser.parseAndFill(hotelData));

        return hotelData;
    }
}

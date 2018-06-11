package pl.edu.uj.ii.tourister.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;
import pl.edu.uj.ii.tourister.DBHelper;
import pl.edu.uj.ii.tourister.Properties;
import pl.edu.uj.ii.tourister.Statuses;
import pl.edu.uj.ii.tourister.model.Hotel;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@Service
public class RequestGenerator {
    @Autowired
    private HotelsRequestHandler hotelsRequestHandler;
    @Autowired
    private XMLParser xmlParser;
    private Scanner scn = new Scanner(System.in);
    private Logger LOG = LoggerFactory.getLogger("transaction-logger");

    public List<Hotel> createAndSendRequest(Statuses status) throws IOException, SAXException, ParserConfigurationException {
        switch (status){
            case GET_HOTELS:
                return xmlParser.parseAndFill(generateGET());
        }
        return null;
    }

    public String generateGET(){
        String destination = "";
        String distance = "";
        System.out.println("Which place do you want to visit?");
        destination = scn.next();
        Properties.aPoint = destination;
        System.out.println("Which distance from the place? (Can be empty)");
        LOG.info("Generating GET Request to Hotwire API");
        distance = scn.nextLine();
        return hotelsRequestHandler.getHotelsFromRequest(destination, distance).getData();
    }

    public String generateGET(String destination, String distance){
        return hotelsRequestHandler.getHotelsFromRequest(destination, distance).getData();
    }
}

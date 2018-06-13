package pl.edu.uj.ii.tourister;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.xml.sax.SAXException;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.model.Response;
import pl.edu.uj.ii.tourister.services.HotelsRequestHandler;
import pl.edu.uj.ii.tourister.services.XMLParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HotelRequestHandlerTest {
    @Autowired
    private HotelsRequestHandler hotelsRequestHandler;
    @Autowired
    private XMLParser xmlParser;

    @Test
    public void testGetHotelsFromRequest(){
        Response response = hotelsRequestHandler.getHotelsFromRequest("Paris", "5", "xml");
        Assert.assertTrue(response.getData().contains("xml"));
    }

    @Test
    public void testGetHotelDeals() throws IOException, SAXException, ParserConfigurationException {
        String params = "?apikey=" + Properties.API_KEY + "&dest=" + "Paris" + "&distance=" + "5";
        String hotelList = hotelsRequestHandler.getHotelDeals(params, "xml", null);
        List<Hotel> hotels = xmlParser.parseAndFill(hotelList);
        Assert.assertTrue(hotels.size() == 100);
    }
}

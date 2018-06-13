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
import pl.edu.uj.ii.tourister.services.PojoHelper;
import pl.edu.uj.ii.tourister.services.RequestGenerator;
import pl.edu.uj.ii.tourister.services.XMLParser;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class XMLParserTest {
    @Autowired
    private XMLParser xmlParser;
    @Autowired
    private RequestGenerator requestGenerator;
    private PojoHelper pojoHelper;

    @Before
    public void setUp(){
        pojoHelper = new PojoHelper();
    }

    @Test
    public void testParseAndFill() throws ParserConfigurationException, SAXException, IOException {
//        requestGenerator.createAndSendRequest(Statuses.GET_HOTELS, "Moscow", "10");
        String xml = requestGenerator.generateGET("Moscow", "10");
        List<Hotel> hotels = xmlParser.parseAndFill(xml);
        Assert.assertTrue(hotels.get(0).getCity().equals("Moscow"));
    }

    @Test
    public void testParseObjectToXml(){
        String finalXML = xmlParser.parseObjectToXML(pojoHelper.generateHotels());
        Assert.assertTrue(finalXML.contains("Warsaw"));
    }
}
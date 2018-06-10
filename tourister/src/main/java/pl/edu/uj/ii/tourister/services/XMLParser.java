package pl.edu.uj.ii.tourister.services;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import pl.edu.uj.ii.tourister.model.Hotel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class XMLParser {
    private DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder documentBuilder;
    private Scanner scn = new Scanner(System.in);

    public List<Hotel> parseAndFill(String xml) throws ParserConfigurationException, IOException, SAXException {
        documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document doc = documentBuilder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("HotelDeal");
        List<Hotel> hotelList = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++){
            hotelList.add(getHotel(nodeList.item(i)));
        }

        for (Hotel hotel: hotelList){
            System.out.println(hotel.getCity());
            System.out.println(hotel.getPrice());
            System.out.println(hotel.getStarsRating());
            System.out.println(hotel.getHotelName());
        }

        return hotelList;
    }


    private Hotel getHotel(Node node){
        Hotel hotel = new Hotel();
        if (node.getNodeType() == Node.ELEMENT_NODE){
            Element element = (Element) node;
            hotel.setCity(getTagValue("City", element));
            hotel.setPrice(Double.valueOf(getTagValue("Price", element)));
            hotel.setStarsRating(Double.valueOf(getTagValue("StarRating", element)));
            hotel.setHotelName(getTagValue("Headline", element));
        }
        return hotel;
    }

    private String getTagValue(String tag, Element element){
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }

}

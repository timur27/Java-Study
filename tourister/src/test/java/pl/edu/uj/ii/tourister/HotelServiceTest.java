package pl.edu.uj.ii.tourister;


import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.model.ServerLocation;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;
import pl.edu.uj.ii.tourister.services.HotelService;
import pl.edu.uj.ii.tourister.services.PojoHelper;
import pl.edu.uj.ii.tourister.services.RequestGenerator;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HotelServiceTest {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RequestGenerator requestGenerator;
    @Autowired
    private HotelRepository hotelRepository;
    private PojoHelper pojoHelper;
    private DatabaseReader databaseReader;



    @Before
    public void setUp() throws IOException {
        pojoHelper = new PojoHelper();
        databaseReader = new DatabaseReader.Builder(Properties.ipFile).build();
    }

    @Test
    public void testFindTheCheapest(){
        List<Hotel> hotels = pojoHelper.generateHotels();
        Hotel resultHotel = hotelService.findTheCheapest(hotels);
        Assert.assertTrue(resultHotel.getCity().equals("Warsaw"));
        Assert.assertEquals(resultHotel.getPrice(), 80, 0.01);
        Assert.assertTrue(resultHotel.getHotelName().equals("Ibis"));
    }


    @Test
    public void testFindServerLocation() throws IOException, GeoIp2Exception {
        this.databaseReader = Mockito.mock(DatabaseReader.class);
        Mockito.when(databaseReader.city(pojoHelper.generateInetAddress())).thenReturn(pojoHelper.generateCityResponse());

        ServerLocation serverLocation = hotelService.findServerLocation();
        System.out.println(serverLocation.getCity());
        Assert.assertTrue(serverLocation.getCity().equals("NYC"));
        Assert.assertEquals(serverLocation.getCountryCode(), "Poland");
    }

    @Test
    public void testParseFromStringToObject(){
        String output = requestGenerator.generateGET("Alcatraz", "*~20");
        List<Hotel> testHotels = hotelService.parseFromStringToObject(output);
        Optional<Hotel> testHotel = testHotels.stream().filter(elem -> elem.getCity().equals("San Francisco")).findFirst();
        Assert.assertTrue(!testHotels.isEmpty());
        Assert.assertTrue(testHotel.isPresent());
    }

    @Test
    public void testPlaneTripToHotel(){
        List<Hotel> hotels = pojoHelper.generateHotels();
        for (Hotel hotel: hotels){
            hotelRepository.save(hotel);
        }
        List<String> citiesWeHave = hotelService.planeTheTripToHotel();
        Assert.assertTrue(citiesWeHave.contains("Warsaw"));
        Assert.assertTrue(!citiesWeHave.isEmpty());
    }

    @Test
    public void testFindBestFromThatWeHave(){
        List<Hotel> hotels = pojoHelper.generateHotels();
        for (Hotel hotel: hotels){
            hotelRepository.save(hotel);
        }
        Map<String, List<Hotel>> finalHotels = hotelService.findBestFromThatWeHave();
        Assert.assertTrue(finalHotels.containsKey("Cracow"));
    }
}

package pl.edu.uj.ii.tourister;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.uj.ii.tourister.model.Trip;
import pl.edu.uj.ii.tourister.model.Trips;
import pl.edu.uj.ii.tourister.services.Mapper;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MapperTest {
    @Autowired
    private Mapper mapper;



    @Test
    public void testMapToObject(){
        String tripJSON = "  {\n" +
                "    \"distance\":\"1100\",\n" +
                "    \"recommended_price\":\"89\"\n" +
                "  }\n" +
                "         ";

        Trips testTrips = mapper.mapToObject(tripJSON);
        Assert.assertTrue(testTrips.getDistance() == 1100);
        Assert.assertTrue(testTrips.getRecommended_price().equals("89"));
    }


    @Test
    public void testMapToJson(){
        Trips testTrips = new Trips();
        testTrips.setDistance(1000);
        testTrips.setRecommended_price("213");

        String json = mapper.mapToJSON(testTrips);
        Assert.assertTrue(json.contains("distance"));
        Assert.assertTrue(json.contains("recommended_price"));
        Assert.assertTrue(json.contains("top_trips"));
    }
}

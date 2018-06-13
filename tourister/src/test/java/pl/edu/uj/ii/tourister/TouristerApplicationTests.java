package pl.edu.uj.ii.tourister;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.uj.ii.tourister.controllers.HotelsController;
import pl.edu.uj.ii.tourister.controllers.TripsController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TouristerApplicationTests {
    @Autowired
    private HotelsController hotelsController;
    @Autowired
    private TripsController tripsController;

    @Test
    public void contextLoads() {
        Assert.assertTrue(hotelsController != null);
        Assert.assertTrue(tripsController != null);
    }

}

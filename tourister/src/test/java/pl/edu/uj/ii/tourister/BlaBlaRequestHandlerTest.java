package pl.edu.uj.ii.tourister;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.uj.ii.tourister.blablacar.BlaBlaRequestHandler;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BlaBlaRequestHandlerTest {
    @Autowired
    private BlaBlaRequestHandler blaBlaRequestHandler;


    @Test
    public void testGetTrips() throws IOException {
        String params = "?fn=" + "Rome" + "&tn=" + "Paris";
        String output = blaBlaRequestHandler.getTrips(params);
        Assert.assertTrue(output.contains("distance"));
    }
}

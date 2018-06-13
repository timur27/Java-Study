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
import pl.edu.uj.ii.tourister.model.ServerLocation;
import pl.edu.uj.ii.tourister.services.LocationFinder;

import java.io.IOException;
import java.net.UnknownHostException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LocationFinderTest {
    private DatabaseReader databaseReader;
    @Autowired
    private LocationFinder locationFinder;
    private PojoHelper pojoHelper;



    @Before
    public void setUp() throws IOException {
        databaseReader = new DatabaseReader.Builder(Properties.ipFile).build();
        pojoHelper = new PojoHelper();
    }


    @Test
    public void testFindLocationByIP() throws IOException, GeoIp2Exception {
        this.databaseReader = Mockito.mock(DatabaseReader.class);
        Mockito.when(databaseReader.city(pojoHelper.generateInetAddress())).thenReturn(pojoHelper.generateCityResponse());
        ServerLocation serverLocation = locationFinder.findLocationByIP();
        Assert.assertTrue(serverLocation.getCountryCode().equals("Poland"));
    }

}

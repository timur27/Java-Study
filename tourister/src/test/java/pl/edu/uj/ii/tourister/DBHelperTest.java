package pl.edu.uj.ii.tourister;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;
import pl.edu.uj.ii.tourister.services.DBHelper;
import pl.edu.uj.ii.tourister.services.PojoHelper;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DBHelperTest {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private DBHelper dbHelper;
    private PojoHelper pojoHelper;

    @Before
    public void setUp(){
        pojoHelper = new PojoHelper();
    }

    @Test
    public void testPerformTaskOnData(){
        int startSize = hotelRepository.findAll().size();
        List<Hotel> hotelList = pojoHelper.generateHotels();
        dbHelper.performTaskOnData(hotelList);
        Assert.assertTrue(startSize == hotelRepository.findAll().size() - hotelList.size());
    }
}

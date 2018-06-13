package pl.edu.uj.ii.tourister;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.uj.ii.tourister.services.RequestGenerator;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RequestGeneratorTest {
    @Autowired
    private RequestGenerator requestGenerator;


    @Test
    public void testGenerateGET(){
        String destination = "Rome";
        String distance = "10";
        String output = requestGenerator.generateGET(destination, distance);
        Assert.assertTrue(output.contains("xml"));
    }
}

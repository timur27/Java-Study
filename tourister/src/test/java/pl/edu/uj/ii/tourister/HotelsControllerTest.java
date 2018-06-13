package pl.edu.uj.ii.tourister;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@SpringBootTest
@RunWith(SpringRunner.class)
public class HotelsControllerTest {
    final TestRestTemplate testRestTemplate = new TestRestTemplate();
    final RestTemplate restTemplate = new RestTemplate();
    private Logger LOG = LoggerFactory.getLogger("tourister-logger");

    @Test
    public void testGetHotelsEndpoint(){
        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);



        String url = "http://localhost:8080/hoteldeals?apikey=" + Properties.API_KEY + "&dest=Paris&distance=5";


        String msg = testRestTemplate.getForObject(url, String.class);
//                String message = restTemplate.getForObject(builder.toUriString(), String.class);
        System.out.println(msg);

        Assert.assertEquals(msg, "AB");
//
//        HttpEntity<?> entity = new HttpEntity<>(headers);
//        HttpEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
//                entity, String.class);
//
//        System.out.println(response);
//        Assert.assertEquals(response, "AC");
    }
}

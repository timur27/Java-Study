package pl.edu.uj.ii.tourister;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.UriComponentsBuilder;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
public class HotelsControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private HotelRepository hotelRepository;

    private MockMvc mockMvc;
    private Logger LOG = LoggerFactory.getLogger("tourister-logger");

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetHotelsWithParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hoteldeals").param("dest", "Paris").param("distance", "10"))
                .andExpect(status().isOk()).andExpect(content().string(containsString("StarRating")));
    }

    @Test
    public void testSaveHotelsToDatabase() throws Exception{
        int startSize = hotelRepository.findAll().size();
        mockMvc.perform(MockMvcRequestBuilders.post("/hoteldeals").param("dest", "Paris").param("distance", "10")
        .param("save", "cheapest")).andExpect(status().isOk());
        Assert.assertEquals(startSize, hotelRepository.findAll().size()-1);
    }

    @Test
    public void testFindNearYou() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hoteldeals/mycity")).andExpect(status().isOk())
                .andExpect(content().string(containsString("[{")));
    }

    @Test
    public void testPlaneTripToFavoriteHotel() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/hoteldeals/favourite")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Unfortunately, the database has no records to choose from")));
    }

    @Test
    public void testGetHotelsWithBadParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hoteldeals")).andExpect(status().is(400));
    }
}
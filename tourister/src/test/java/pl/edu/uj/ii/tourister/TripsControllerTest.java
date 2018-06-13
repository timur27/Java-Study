package pl.edu.uj.ii.tourister;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TripsControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;


    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetTripFromAToB() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/blablatrip/info").param("city", "Rome")).andExpect(status().isOk())
                .andExpect(content().string(containsString("{")));
    }

    @Test
    public void testChooseTrip()throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/choosetrip").param("a", "Paris").param("b", "Rome"))
                .andExpect(content().string(containsString("wholePrice")))
                .andExpect(content().string(containsString("tripPrice")))
                .andExpect(content().string(containsString("hotelPrice")));
    }


}

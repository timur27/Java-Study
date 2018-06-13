package pl.edu.uj.ii.tourister.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import pl.edu.uj.ii.tourister.model.Trips;

import java.io.IOException;

@Service
public class Mapper {
    public Trips mapToObject(String tripJSON){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Trips trips = objectMapper.readValue(tripJSON, Trips.class);
            return trips;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String mapToJSON(Object o){
        ObjectMapper mapper = new ObjectMapper();
        String result = "";
        try {
            result = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return result;
    }

}

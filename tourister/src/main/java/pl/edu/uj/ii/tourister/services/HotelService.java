package pl.edu.uj.ii.tourister.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;

import java.util.List;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;


    public List<Hotel> findCheapestHotels(){
        return hotelRepository.findAll();
    }
}

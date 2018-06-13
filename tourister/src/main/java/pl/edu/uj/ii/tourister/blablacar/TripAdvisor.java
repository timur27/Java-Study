package pl.edu.uj.ii.tourister.blablacar;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.ii.tourister.model.ChoosenHotel;
import pl.edu.uj.ii.tourister.repoitory.ChoosenHotelRepository;

import java.util.List;

@Service
public class TripAdvisor {
    @Autowired
    private ChoosenHotelRepository choosenHotelRepository;


    public ChoosenHotel findLastChoosenHotel(){
        return choosenHotelRepository.findAll().get(choosenHotelRepository.findAll().size()-1);
    }
}

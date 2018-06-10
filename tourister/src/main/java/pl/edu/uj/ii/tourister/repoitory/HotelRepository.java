package pl.edu.uj.ii.tourister.repoitory;

import org.springframework.data.repository.CrudRepository;
import pl.edu.uj.ii.tourister.model.Hotel;

import java.util.List;

public interface HotelRepository extends CrudRepository<Hotel, Integer> {
    List<Hotel> findAll();

}
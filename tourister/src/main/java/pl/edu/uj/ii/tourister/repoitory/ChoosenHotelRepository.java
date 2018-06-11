package pl.edu.uj.ii.tourister.repoitory;

import org.springframework.data.repository.CrudRepository;
import pl.edu.uj.ii.tourister.model.ChoosenHotel;

import java.util.List;

public interface ChoosenHotelRepository extends CrudRepository<ChoosenHotel, Integer> {
    List<ChoosenHotel> findAll();
}

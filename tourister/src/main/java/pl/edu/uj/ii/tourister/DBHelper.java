package pl.edu.uj.ii.tourister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;
import pl.edu.uj.ii.tourister.services.HotelService;

import java.util.List;
import java.util.Scanner;



@Service
public class DBHelper {
    private Scanner scn = new Scanner(System.in);
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelService hotelService;

    public void performTaskOnData(List<Hotel>hotels){
        System.out.println("Otrzymalismy liste hoteli, które są w ofercie. Co chcesz z nimi zrobić?");
        System.out.println("1: Dodać wszystkie hotele do bazy danych");
        System.out.println("2: Dodać tylko najlepszą opcję do bazy danych");
        System.out.println("3: Wyszukać najlepszą lokalizację od Ciebie");
        System.out.println("4: Wybrać najtańszy hotel");

        String answer = scn.next();
        switch (answer){
            case "1":
                for (Hotel hotel: hotels){
                    hotelRepository.save(hotel);
                }
                break;
            case "2":
                hotelRepository.save(hotelService.findTheCheapest(hotels));
                break;
            case "3":
                hotelService.findNearest();
                break;
            case "4":
                Hotel hotel = hotelService.findTheCheapest(hotels);
                System.out.println("Hotel name: " + hotel.getHotelName());
                System.out.println("Hotel star: " + hotel.getStarsRating());
                System.out.println("Price in $$$: " + hotel.getPrice());
                System.out.println();
                break;
        }
    }
}

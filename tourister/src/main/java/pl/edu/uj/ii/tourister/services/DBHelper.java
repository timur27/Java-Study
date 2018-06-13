package pl.edu.uj.ii.tourister.services;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.ii.tourister.blablacar.BlaBlaRequestHandler;
import pl.edu.uj.ii.tourister.blablacar.TripAdvisor;
import pl.edu.uj.ii.tourister.model.ChoosenHotel;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.model.ServerLocation;
import pl.edu.uj.ii.tourister.repoitory.ChoosenHotelRepository;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;
import pl.edu.uj.ii.tourister.services.HotelService;
import pl.edu.uj.ii.tourister.services.LocationFinder;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;



@Service
public class DBHelper {
    private Scanner scn = new Scanner(System.in);
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private ChoosenHotelRepository choosenHotelRepository;
    @Autowired
    private TripAdvisor tripAdvisor;
    @Autowired
    private BlaBlaRequestHandler blaBlaRequestHandler;
    @Autowired
    private ServerLocation serverLocation;
    private Logger LOG = LoggerFactory.getLogger("tourister-logger");

    public void saveJustTheCheapestHotel(List<Hotel> hotels){
        Hotel cheapestHotel = hotelService.findTheCheapest(hotels);
        hotelRepository.save(cheapestHotel);
    }

    public List<Hotel> getAll(){
        return hotelRepository.findAll();
    }

    public void performTaskOnData(List<Hotel>hotels){
        LOG.info("Performing a task on data");

        for (Hotel hotel: hotels){
            hotelRepository.save(hotel);
            LOG.info("Saved hotel called: " + hotel.getHotelName() + " in database");
        }


//        System.out.println("Otrzymalismy liste hoteli, które są w ofercie. Co chcesz z nimi zrobić?");
//        System.out.println("1: Dodać wszystkie hotele do bazy danych");
//        System.out.println("2: Dodać tylko najlepszą opcję do bazy danych");
//        System.out.println("4: Wybrać najtańszy hotel");
//        System.out.println();


//        String answer = scn.next();
//        switch (answer){
//            case "1":
//                for (Hotel hotel: hotels){
//                    hotelRepository.save(hotel);
//                    LOG.info("Saved hotel called: " + hotel.getHotelName() + " in database");
//                }
//                break;
//            case "2":
//                hotelRepository.save(hotelService.findTheCheapest(hotels));
//                break;
//            case "4":
//                Hotel hotel = hotelService.findTheCheapest(hotels);
//                System.out.println("Hotel name: " + hotel.getHotelName());
//                System.out.println("Hotel star: " + hotel.getStarsRating());
//                System.out.println("Price in $$$: " + hotel.getPrice());
//                System.out.println();
//                System.out.println("Chcesz tam pojechać?");
//                String decision = scn.next();
//                if (decision.equalsIgnoreCase("tak")){
//                    ChoosenHotel choosenHotel = new ChoosenHotel();
//                    choosenHotel.setCity(hotel.getCity());
//                    choosenHotel.setHotelName(hotel.getHotelName());
//                    choosenHotel.setPrice(hotel.getPrice());
//                    choosenHotel.setStarsRating(hotel.getStarsRating());
//                    choosenHotelRepository.save(choosenHotel);
//                    ChoosenHotel choosenHotel1 = tripAdvisor.findLastChoosenHotel();
//                    ServerLocation serverLocation = new LocationFinder().findLocationByIP();
//                    blaBlaRequestHandler.sendGET(serverLocation.getCity(), choosenHotel.getCity());
//                    break;
//                }
//                else break;
//        }
    }
}

package pl.edu.uj.ii.tourister;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.edu.uj.ii.tourister.blablacar.TripAdvisor;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;
import pl.edu.uj.ii.tourister.services.DBHelper;
import pl.edu.uj.ii.tourister.services.HotelService;
import pl.edu.uj.ii.tourister.services.RequestGenerator;

import java.util.Scanner;



@SpringBootApplication
public class TouristerApplication {
    @Autowired
    private RequestGenerator requestGenerator;
    @Autowired
    private TripAdvisor tripAdvisor;
    @Autowired
    private DBHelper dbHelper;
    @Autowired
    private HotelService hotelService;
    @Autowired
    private HotelRepository hotelRepository;
    private Scanner scn = new Scanner(System.in);

    private static final Logger LOG = LoggerFactory.getLogger("tourister-logger");

    public static void main(String[] args) {
        SpringApplication.run(TouristerApplication.class, args);
    }
//
//    @Bean
//    CommandLineRunner runner() {
//        return args -> {
//            boolean next = true;
//            while(next){
//                LOG.info("First step to do in our application");
//                System.out.println("Powiedz mi, w czym mogę Tobie teraz pomóc?");
//                System.out.println("1: Wyszukać najkorzystniejsze hotele według miasta?");
//                System.out.println("2: Wyszukać najlepszą lokalizację hotelu od Ciebie");
//                System.out.println("3: Zaplanować przejazd do wybranego hotelu");
//                System.out.println("5: Zakończyć działanie aplikacji");
//
//                String answer = scn.next();
//                LOG.info("Wybrano opcje!");
//                switch (answer){
//                    case "1":
//                        List<Hotel> hotelList = requestGenerator.createAndSendRequest(Statuses.GET_HOTELS);
//                        LOG.info("Done with hotelSearch");
//                        for (Hotel hotel: hotelList){
//                            System.out.println(hotel);
//                        }
//                        dbHelper.performTaskOnData(hotelList);
//                        break;
//                    case "2":
//                        hotelService.findNearest();
//                        break;
//                    case "3":
//                        hotelService.planeTheTripToHotel();
//                        break;
//                    case "5":
//                        next = false;
//                        break;
//                    default: break;
//                }
//            }
//        };
//    }
}


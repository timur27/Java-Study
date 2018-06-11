package pl.edu.uj.ii.tourister;


import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.uj.ii.tourister.blablacar.TripAdvisor;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;
import pl.edu.uj.ii.tourister.services.HotelService;
import pl.edu.uj.ii.tourister.services.RequestGenerator;
import pl.edu.uj.ii.tourister.services.XMLParser;

import java.util.List;
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

    public static void main(String[] args) {
        SpringApplication.run(TouristerApplication.class, args);
    }

    @Bean
    CommandLineRunner runner() {
        return args -> {
            boolean next = true;
            while(next){
                System.out.println("Powiedz mi, w czym mogę Tobie teraz pomóc?");
                System.out.println("1: Wyszukać najkorzystniejsze hotele według miasta?");
                System.out.println("2: Wyszukać najlepszą lokalizację hotelu od Ciebie");
                System.out.println("3: Zaplanować przejazd do wybranego hotelu");
                System.out.println("5: Zakończyć działanie aplikacji");

                String answer = scn.next();
                switch (answer){
                    case "1":
                        List<Hotel> hotelList = requestGenerator.createAndSendRequest(Statuses.GET_HOTELS);
                        for (Hotel hotel: hotelList){
                            System.out.println(hotel);
                        }
                        dbHelper.performTaskOnData(hotelList);
                        break;
                    case "2":
                        hotelService.findNearest();
                        break;
                    case "3":

                    case "5":
                        next = false;
                        break;
                    default: break;
                }
            }
        };
    }
}


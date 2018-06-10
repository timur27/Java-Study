package pl.edu.uj.ii.tourister;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;
import pl.edu.uj.ii.tourister.services.RequestGenerator;
import pl.edu.uj.ii.tourister.services.XMLParser;

import java.util.Scanner;

@SpringBootApplication
public class TouristerApplication {
    @Autowired
    private RequestGenerator requestGenerator;
    @Autowired
    private DBHelper dbHelper;
    @Autowired
    private XMLParser xmlParser;
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
                System.out.println("1: Wyszukać najkorzystniejsze hotele?");
                System.out.println("2: Znaleźć najtańsze podróże");
                System.out.println("5: Zakończyć działanie aplikacji");


                String answer = scn.next();
                switch (answer){
                    case "1":
                        for (Hotel hotel: requestGenerator.createAndSendRequest(Statuses.GET_HOTELS)){
                            System.out.println(hotel);
                            dbHelper.performTaskOnData(requestGenerator.createAndSendRequest(Statuses.GET_HOTELS));
                        }
                        break;
                    case "2":
                        for (Hotel hotel: hotelRepository.findAll()){
                            System.out.println(hotel);
                        }
                        break;
                    case "5":
                        next = false;
                        break;
                }
                answer = "";
            }
        };
    }
}


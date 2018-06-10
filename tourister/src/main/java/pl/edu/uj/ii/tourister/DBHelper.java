package pl.edu.uj.ii.tourister;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.ii.tourister.model.Hotel;
import pl.edu.uj.ii.tourister.repoitory.HotelRepository;

import java.util.List;
import java.util.Scanner;



@Service
public class DBHelper {
    private Scanner scn = new Scanner(System.in);
    @Autowired
    private HotelRepository hotelRepository;

    public void performTaskOnData(List<Hotel>hotels){
        System.out.println("Otrzymalismy liste hoteli, które są w ofercie. Co chcesz z nimi zrobić?");
        System.out.println("1: Dodać wszystkie hotele do bazy danych");
        System.out.println("2: Dodać tylko po jednej najlepszej opcji w miejscie do bazy danych");
        System.out.println("3: Wyszukać najlepszą lokalizację od Ciebie");
        System.out.println("4: Wybrać najtańszy hotel");

        String answer = scn.next();
        switch (answer){
            case "1": for (Hotel hotel: hotels){
                hotelRepository.save(hotel);
            }
        }
    }
}

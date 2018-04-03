package uj.jwzp.w4.launchers;

import lombok.extern.slf4j.Slf4j;
import uj.jwzp.w4.logic.MovieLister;
import uj.jwzp.w4.model.Movie;

@Slf4j
public class ManualMain {

    public static void main(String[] args) {
        MovieLister lister = new MovieLister("sda.txt");
        lister.moviesDirectedBy("Hoffman").stream()
                .map(Movie::toString)
                .forEach(log::info);
    }
}
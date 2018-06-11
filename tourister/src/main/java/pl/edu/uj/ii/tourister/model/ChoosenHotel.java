package pl.edu.uj.ii.tourister.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ChoosenHotel {

    @Id
    @GeneratedValue
    private int id;
    private double price;
    private String city;
    private double starsRating;
    private String hotelName;


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getStarsRating() {
        return starsRating;
    }

    public void setStarsRating(double starsRating) {
        this.starsRating = starsRating;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }
}

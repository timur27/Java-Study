package pl.edu.uj.ii.tourister.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trips {
    private int distance;
    private String recommended_price;
    private Trip[] top_trips;

    public String getRecommended_price() {
        return recommended_price;
    }

    public void setRecommended_price(String recommended_price) {
        this.recommended_price = recommended_price;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Trip[] getTop_trips() {
        return top_trips;
    }

    public void setTop_trips(Trip[] top_trips) {
        this.top_trips = top_trips;
    }

    @Override
    public String toString() {
        return "Trips{" +
                "distance=" + distance +
                '}';
    }
}

package pl.edu.uj.ii.tourister.model;

import com.maxmind.geoip2.record.Location;

public class ServerLocation {
    private String countryCode;
    private String city;
    private Location location;

    public String getCountryCode() {
        return countryCode;
    }



    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (city.equals("New York")){
            this.city = "NYC";
        }
        else{
            this.city = city;
        }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}

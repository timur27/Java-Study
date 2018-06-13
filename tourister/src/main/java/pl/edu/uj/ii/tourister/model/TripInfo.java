package pl.edu.uj.ii.tourister.model;

public class TripInfo {
    private double hotelPrice;
    private int tripPrice;
    private int wholePrice;
    private String aPoint;
    private String bPoint;
    private int distance;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public double getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(double hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public int getTripPrice() {
        return tripPrice;
    }

    public void setTripPrice(int tripPrice) {
        this.tripPrice = tripPrice;
    }

    public int getWholePrice() {
        return wholePrice;
    }

    public void setWholePrice() {
        this.wholePrice = (int)(this.tripPrice + this.hotelPrice);
    }

    public String getaPoint() {
        return aPoint;
    }

    public void setaPoint(String aPoint) {
        this.aPoint = aPoint;
    }

    public String getbPoint() {
        return bPoint;
    }

    public void setbPoint(String bPoint) {
        this.bPoint = bPoint;
    }
}

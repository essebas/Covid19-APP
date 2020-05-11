package co.sebasdeveloper.pruebaservinformacion.model;

public class LocationDataModel {

    private String location;
    private String country_code;
    private double latitude;
    private double longitude;
    private int confirmed;
    private int dead;
    private String recovered;
    private String update;

    public LocationDataModel() {
    }

    public LocationDataModel(String location, String country_code, double latitude, double longitude, int confirmed, int dead, String recovered, String update) {
        this.location = location;
        this.country_code = country_code;
        this.latitude = latitude;
        this.longitude = longitude;
        this.confirmed = confirmed;
        this.dead = dead;
        this.recovered = recovered;
        this.update = update;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(int confirmed) {
        this.confirmed = confirmed;
    }

    public int getDead() {
        return dead;
    }

    public void setDead(int dead) {
        this.dead = dead;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
}

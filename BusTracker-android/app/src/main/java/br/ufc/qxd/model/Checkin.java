package br.ufc.qxd.model;


public class Checkin {
    private String id;
    private String id_user;
    private String latitude;
    private String longitude;
    private String bus;
    private String date;
    private String hour;
    private String capacity;

    public Checkin(){}

    public Checkin(String id, String id_user, String latitude, String longitude, String bus, String date, String hour, String capacity) {
        this.id = id;
        this.id_user = id_user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bus = bus;
        this.date = date;
        this.hour = hour;
        this.capacity = capacity;
    }

    public Checkin(String id_user, String latitude, String longitude, String bus, String date, String hour, String capacity) {
        this.id_user = id_user;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bus = bus;
        this.date = date;
        this.hour = hour;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Checkin{" +
                "id='" + id + '\'' +
                ", id_user='" + id_user + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", bus='" + bus + '\'' +
                ", date='" + date + '\'' +
                ", hour='" + hour + '\'' +
                ", capacity='" + capacity + '\'' +
                '}';
    }
}

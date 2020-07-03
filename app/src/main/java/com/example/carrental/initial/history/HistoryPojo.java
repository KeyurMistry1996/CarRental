package com.example.carrental.initial.history;

public class HistoryPojo {
    public HistoryPojo(boolean booking, String carId, String dropLocation, String dropTime, String pickTime, String pickLocation, String price, String user) {
        this.booking = booking;
        this.carId = carId;
        this.dropLocation = dropLocation;
        this.dropTime = dropTime;
        this.pickTime = pickTime;
        this.pickLocation = pickLocation;
        this.price = price;
        this.user = user;
    }

    public HistoryPojo() {
    }

    public boolean isBooking() {
        return booking;
    }

    public void setBooking(boolean booking) {
        this.booking = booking;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDropLocation() {
        return dropLocation;
    }

    public void setDropLocation(String dropLocation) {
        this.dropLocation = dropLocation;
    }

    public String getDropTime() {
        return dropTime;
    }

    public void setDropTime(String dropTime) {
        this.dropTime = dropTime;
    }

    public String getPickTime() {
        return pickTime;
    }

    public void setPickTime(String pickTime) {
        this.pickTime = pickTime;
    }

    public String getPickLocation() {
        return pickLocation;
    }

    public void setPickLocation(String pickLocation) {
        this.pickLocation = pickLocation;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    private boolean booking;
    private String carId;
    private String dropLocation;
    private String dropTime;
    private String pickTime;
    private String pickLocation;
    private String price;
    private String user;
}

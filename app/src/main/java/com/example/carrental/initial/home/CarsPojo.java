package com.example.carrental.initial.home;

public class CarsPojo {
    public  CarsPojo(){}
    public CarsPojo(boolean airbag, boolean audio, boolean automatic, boolean bluetooth, String brand, String description, boolean gps, String model, String odormeter, int person_number, String popularity, int price, int rating, String transmission, String url_Of_CarImage, String user_email, int year,boolean status) {
        this.airbag = airbag;
        this.audio = audio;
        this.automatic = automatic;
        this.bluetooth = bluetooth;
        this.brand = brand;
        this.description = description;
        this.gps = gps;
        this.model = model;
        this.odormeter = odormeter;
        this.person_number = person_number;
        this.popularity = popularity;
        this.price = price;
        this.rating = rating;
        this.transmission = transmission;
        this.url_Of_CarImage = url_Of_CarImage;
        this.user_email = user_email;
        this.year = year;
        this.status = status;
    }

    public boolean isAirbag() {
        return airbag;
    }

    public void setAirbag(boolean airbag) {
        this.airbag = airbag;
    }

    public boolean isAudio() {
        return audio;
    }

    public void setAudio(boolean audio) {
        this.audio = audio;
    }

    public boolean isAutomatic() {
        return automatic;
    }

    public void setAutomatic(boolean automatic) {
        this.automatic = automatic;
    }

    public boolean isBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isGps() {
        return gps;
    }

    public void setGps(boolean gps) {
        this.gps = gps;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getOdormeter() {
        return odormeter;
    }

    public void setOdormeter(String odormeter) {
        this.odormeter = odormeter;
    }

    public int getPerson_number() {
        return person_number;
    }

    public void setPerson_number(int person_number) {
        this.person_number = person_number;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getUrl_Of_CarImage() {
        return url_Of_CarImage;
    }

    public void setUrl_Of_CarImage(String url_Of_CarImage) {
        this.url_Of_CarImage = url_Of_CarImage;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    private boolean airbag;
    private boolean audio;
    private boolean automatic;
    private boolean bluetooth;
    private String brand;
    private String description;
    private boolean gps;
    private String model;
    private String odormeter;
    private int person_number;
    private String popularity;
    private int price;
    private int rating;
    private String transmission;
    private String url_Of_CarImage;
    private String user_email;
    private int year;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    private boolean status;


}

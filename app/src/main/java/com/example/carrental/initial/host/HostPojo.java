package com.example.carrental.initial.host;

import static com.example.carrental.initial.host.AboutCarActivity.carBrandtxt;
import static com.example.carrental.initial.host.AboutCarActivity.carModeltxt;
import static com.example.carrental.initial.host.AboutCarActivity.carOdormetertxt;
import static com.example.carrental.initial.host.AboutCarActivity.carTransmissiontxt;
import static com.example.carrental.initial.host.AboutCarActivity.carYeartxt;

public class HostPojo {

    String brand;
    String model;
    int year;
    String transmission;
    String odormeter;
    String Url_Of_CarImage;
    int price;
    String description;
    int person_number;
    boolean gps;
    boolean audio;
    boolean automatic;
    boolean bluetooth;
    boolean airbag;
    String user_email;
    int rating;
    String popularity;


    public HostPojo(String brand, String model, int year, String transmission, String odormeter, String url_Of_CarImage, int price, String description, int person_number, boolean gps, boolean audio, boolean automatic, boolean bluetooth, boolean airbag, String user_email,  int rating,String popularity  ) {
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.transmission = transmission;
        this.odormeter = odormeter;
        Url_Of_CarImage = url_Of_CarImage;
        this.price = price;
        this.description = description;
        this.person_number = person_number;
        this.gps = gps;
        this.audio = audio;
        this.automatic = automatic;
        this.bluetooth = bluetooth;
        this.airbag = airbag;
        this.user_email = user_email;
        this.popularity = popularity;
        this.rating = rating;
    }



    public HostPojo() {
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getOdormeter() {
        return odormeter;
    }

    public void setOdormeter(String odormeter) {
        this.odormeter = odormeter;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPerson_number() {
        return person_number;
    }

    public void setPerson_number(int person_number) {
        this.person_number = person_number;
    }

    public boolean isGps() {
        return gps;
    }

    public void setGps(boolean gps) {
        this.gps = gps;
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

    public boolean isAirbag() {
        return airbag;
    }

    public void setAirbag(boolean airbag) {
        this.airbag = airbag;
    }

    public int getRating() {
        return rating;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }



//    public HostPojo(String brand, String model, int year, String transmission, String odormeter, String url_Of_CarImage, int price, String description, int person_number, boolean GPS, boolean audio, boolean automatic, boolean bluetooth, boolean airbag, String user_email,int rating,String popularity) {
//        Brand = brand;
//        Model = model;
//        Year = year;
//        Transmission = transmission;
//        Odormeter = odormeter;
//        Url_Of_CarImage = url_Of_CarImage;
//        Price = price;
//        Description = description;
//        Person_number = person_number;
//        this.GPS = GPS;
//        Audio = audio;
//        Automatic = automatic;
//        Bluetooth = bluetooth;
//        Airbag = airbag;
//        this.user_email = user_email;
//        this.rating = rating;
//        this.popularity = popularity;
//    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }


    public String getUrl_Of_CarImage() {
        return Url_Of_CarImage;
    }

    public void setUrl_Of_CarImage(String url_Of_CarImage) {
        Url_Of_CarImage = url_Of_CarImage;
    }

}

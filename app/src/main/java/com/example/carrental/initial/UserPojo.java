package com.example.carrental.initial;

public class UserPojo {


    String name;
    String email;
    String password;
    String number;
    String userurl;

    public String getUserurl() {
        return userurl;
    }

    public UserPojo(String userurl) {
        this.userurl = userurl;
    }

    public void setUserurl(String userurl) {
        this.userurl = userurl;
    }

    public UserPojo(String name, String email, String password, String number) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


}

package com.example.carrental.admin;

public class UserInfo {
    public UserInfo(String id,String name, String email, String number) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.id = id;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String name;
    private String email;
    private String number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;
}

package com.example.carrental;

public class UserPojo {


    String name;
    String email;
    String password;
    String number;
    String user_type;

    public UserPojo(String name, String email, String password, String number, String user_type) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.number = number;
        this.user_type = user_type;
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
    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

}

package com.example.carrental.admin;

import com.google.firebase.firestore.auth.User;

public class UserModel {
    public String getName() {
        return name;
    }

    public  UserModel(){}

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    private String profileurl;

    public UserModel(String name){
        this.name = name;
    }

}

package com.example.thymen.positivenews;

import java.util.ArrayList;

public class User {
    String name;
    String email;
    String firstLogin;
    ArrayList<String> preferences;
    ArrayList<String> savedArticles;

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

    public String getFirstLogin() { return firstLogin; }

    public void setFirstLogin(String firstLogin) { this.firstLogin = firstLogin; }



}


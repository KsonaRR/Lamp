package com.groovylamp.models;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class User {
    int id;
    String login;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }



    public User(String login) {
        this.id = Integer.parseInt("1"+new GregorianCalendar().getTimeInMillis()/10000);
        this.login = login;
    }




}

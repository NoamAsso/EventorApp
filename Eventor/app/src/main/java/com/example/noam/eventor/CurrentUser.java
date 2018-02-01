package com.example.noam.eventor;

/**
 * Created by Noam on 24/01/2018.
 */

public class CurrentUser {//this is the Logged in user

    User user;

    private static CurrentUser sInstance;

    public static CurrentUser getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentUser();
        }
        return sInstance;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

package com.example.noam.eventor;

import java.util.ArrayList;

/**
 * Created by Noam Assouline and Itay ringler!
 * all rights reserved :)
 */
/**
 * Created by Noam on 25/01/2018.
 */

public class CurrentUserEvents {// holds the events that the user is registered to

    private ArrayList<Integer> userEvents;
    private static CurrentUserEvents sInstance;

    public static CurrentUserEvents getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentUserEvents();
        }
        return sInstance;
    }
    public void setUserEvents(ArrayList<Integer> userEvents){
        this.userEvents = userEvents;
    }

    public ArrayList<Integer> getUserEvents() {
        return userEvents;
    }

    public void addEventId(int id){
        userEvents.add(id);
    }

}
package com.example.noam.eventor;

/**
 * Created by Noam on 25/01/2018.
 */

public class CurrentEvent {

    GenericEvent event;
    private static CurrentEvent sInstance;

    public static CurrentEvent getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentEvent();
        }
        return sInstance;
    }
    public void setCurrentEvent(GenericEvent event){
        this.event = event;
    }

    public GenericEvent getCurrentEvent() {
        return event;
    }
}
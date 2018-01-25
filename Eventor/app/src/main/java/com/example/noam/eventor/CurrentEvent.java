package com.example.noam.eventor;

/**
 * Created by Noam on 25/01/2018.
 */

public class CurrentEvent {

    static
    GenericEvent event;
    private static CurrentEvent sInstance;

    public static CurrentEvent getInstance() {
        if (sInstance == null) {
            sInstance = new CurrentEvent();
            event = new GenericEvent();
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
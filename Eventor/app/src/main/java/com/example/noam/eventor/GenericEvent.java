package com.example.noam.eventor;

/**
 * Created by Noam on 13/01/2018.
 */



import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Noam on 12/01/2018.
 */


public class GenericEvent {

public static String lastId = null;
    private String id;
    private String userName;
    private Date creationDate;
    private Date date;
    private String dateTest;
    private int maxUsers;
    private int currentUsers;
    private String title;
    private String description;
    private int price;
    //eventType missing
    //subeventType missing
    private String phoneNum;
    private String email;
    private String friendsById[];
    private Boolean isPrivate;
    private Bitmap eventImage;
    private Place eventLocation;



    public GenericEvent() {
        this.id = null;
        this.userName = null;
        this.date = null;
        this.creationDate = new Date();
        this.date = new Date();
        this.maxUsers = 0;
        this.currentUsers = 0;
        this.description = null;
        this.price = 0;
        this.phoneNum = null;
        this.email = null;
        this.friendsById = null;
        this.isPrivate = false;
        this.eventImage = null;
        this.eventLocation = null;
        this.eventLocation = new Place() {
            @Override
            public String getId() {
                return null;
            }

            @Override
            public List<Integer> getPlaceTypes() {
                return null;
            }

            @Override
            public CharSequence getAddress() {
                return null;
            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public CharSequence getName() {
                return null;
            }

            @Override
            public LatLng getLatLng() {
                return null;
            }

            @Override
            public LatLngBounds getViewport() {
                return null;
            }

            @Override
            public Uri getWebsiteUri() {
                return null;
            }

            @Override
            public CharSequence getPhoneNumber() {
                return null;
            }

            @Override
            public float getRating() {
                return 0;
            }

            @Override
            public int getPriceLevel() {
                return 0;
            }

            @Override
            public CharSequence getAttributions() {
                return null;
            }

            @Override
            public Place freeze() {
                return null;
            }

            @Override
            public boolean isDataValid() {
                return false;
            }
        };
    }

    public Place getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(Place eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getDateTest() {
        return dateTest;
    }

    public void setDateTest(String dateTest) {
        this.dateTest = dateTest;
    }

    public static String getLastId() {
        return lastId;
    }

    public static void setLastId(String lastId) {
        GenericEvent.lastId = lastId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }




    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getMaxUsers() {
        return this.maxUsers;
    }

    public void setMaxUsers(int maxUsers) {
        this.maxUsers = maxUsers;
    }

    public int getCurrentUsers() {
        return currentUsers;
    }

    public void setCurrentUsers(int currentUsers) {
        this.currentUsers = currentUsers;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getFriendsById() {
        return friendsById;
    }

    public void setFriendsById(String[] friendsById) {
        this.friendsById = friendsById;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Bitmap getEventImage() {
        return eventImage;
    }

    public void setEventImage(Bitmap eventImage) {
        this.eventImage = eventImage;
    }






}

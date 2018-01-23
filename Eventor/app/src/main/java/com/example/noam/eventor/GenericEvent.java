package com.example.noam.eventor;

/**
 * Created by Noam on 13/01/2018.
 */



import android.graphics.Bitmap;
import android.net.Uri;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Noam on 12/01/2018.
 */


public class GenericEvent {

    private int id;
    private String category;
    private String userName;
    private Date creationDate;
    private Date date;
    private String stringDate;
    private int maxUsers;
    private int currentUsers;
    private String description;
    private int price;
    //eventType missing
    //subeventType missing
    private ArrayList<Integer> friendsById;
    private Boolean isPrivate;
    private String eventImage;
    private String placeID;
    private Double longitude;
    private Double latitude;



    public GenericEvent() {
        this.id = 0;
        this.userName = "empty";
        this.date = null;
        this.creationDate = new Date();
        this.date = new Date();
        this.maxUsers = 0;
        this.currentUsers = 0;
        this.description = null;
        this.price = 0;
        this.friendsById = null;
        this.isPrivate = false;
        this.eventImage = null;
        this.placeID = null;
        this.longitude=0.0;
        this.latitude=0.0;

    }



    public String getstringDate() {
        return stringDate;
    }

    public void setstringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }

    public void setplaceID(String placeID) {
        this.placeID = placeID;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getStringDate() {
        return stringDate;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
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


    public ArrayList getFriendsById() {
        return friendsById;
    }

    public void setFriendsById(ArrayList friendsById) {
        this.friendsById = friendsById;
    }
    public void addToFriendsById(Integer friend){
        this.friendsById.add(friend);
    }
    public Object getItem(int i) {
        if(!friendsById.isEmpty())
            return friendsById.get(i);

        else
            return null;
    }

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }






}

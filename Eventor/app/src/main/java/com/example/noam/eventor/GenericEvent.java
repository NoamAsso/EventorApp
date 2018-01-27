package com.example.noam.eventor;

/**
 * Created by Noam on 13/01/2018.
 */

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Noam on 12/01/2018.
 */

public class GenericEvent {

    private int id;
    private int adminUserId;
    private String category;
    private Date creationDate;
    private Date date;
    private int intDate;
    private int intCreationDate;
    private int maxUsers;
    private int currentUsers;
    private String description;
    private int price;
    private Boolean isPrivate;
    private String eventImage;
    private String placeID;
    private double longitude;
    private double latitude;

    public GenericEvent(String category, Date date, int maxUsers, String description, int price,
                        boolean isPrivate, String eventImage, String placeID, double longitude,
                        double latitude, int adminUserId) {
        this.category = category;
        this.id = 0;            //received by the server
        this.date = date;
        this.adminUserId = adminUserId;   //received by the server
        this.creationDate = new Date();
        this.intCreationDate = (int)(this.creationDate.getTime()/1000);
        this.maxUsers = maxUsers;
        this.currentUsers = 1;          //Only the creator is in the event
        this.description = description;
        this.price = price;
        this.isPrivate = isPrivate;
        this.eventImage = eventImage;
        this.placeID = placeID;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public GenericEvent() {
        this.category="";
        this.id = 0;            //received by the server
        this.date = new Date();
        this.adminUserId = 0;   //received by the server
        this.creationDate = new Date();
        this.intCreationDate = 0;
        this.maxUsers = 0;
        this.currentUsers = 1;          //Only the creator is in the event
        this.description = "";
        this.price = 0;
        this.isPrivate = false;
        this.eventImage = "";
        this.placeID = "";
        this.longitude = 0.0;
        this.latitude = 0.0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public int getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(int adminUserId) {
        this.adminUserId = adminUserId;
    }

    public void setDate(Date date) {
        this.date = date;
        this.intDate = (int)(date.getTime()/1000);
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
        this.intCreationDate = (int)(creationDate.getTime()/1000);
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

    public Boolean getPrivate() {
        return isPrivate;
    }

    public void setPrivate(Boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public String getEventImage() {
        return eventImage;
    }

    public int getIntDate() {
        return intDate;
    }

    public void setIntDate(int intDate) {
        this.intDate = intDate;
    }

    public int getIntCreationDate() {
        return intCreationDate;
    }

    public void setIntCreationDate(int intCreationDate) {
        this.intCreationDate = intCreationDate;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }


}

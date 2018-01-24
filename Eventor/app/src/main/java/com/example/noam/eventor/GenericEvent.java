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
    //eventType missing
    //subeventType missing
    private ArrayList<Integer> friendsById;
    private String friendsIdString;
    private Boolean isPrivate;
    private String eventImage;
    private String placeID;
    private Double longitude;
    private Double latitude;

    public GenericEvent() {
        this.id = 0;
        this.date = null;
        this.adminUserId = 0;
        this.creationDate = new Date();
        this.intCreationDate = (int)(this.creationDate.getTime()/1000);
        this.date = new Date();
        this.maxUsers = 0;
        this.currentUsers = 0;
        this.description = null;
        this.price = 0;
        this.friendsById = new ArrayList<>();
        this.isPrivate = false;
        this.eventImage = null;
        this.placeID = null;
        this.longitude=0.0;
        this.latitude=0.0;

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

    public ArrayList<Integer> getFriendsById() {
        return friendsById;
    }

    public void setFriendsById(ArrayList friendsById) {
        this.friendsById = friendsById;
    }

    public void addToFriendsById(Integer userId){
        this.friendsById.add(userId);

    }

    public Integer removeFromFriendsById(int i){
        if(!friendsById.isEmpty())
            return friendsById.get(i);
        else
            return null;
    }

    public Integer getFromFriendsById(int i) {
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

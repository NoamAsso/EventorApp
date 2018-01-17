package com.example.noam.eventor;

/**
 * Created by Noam on 13/01/2018.
 */

import android.media.Image;
import android.widget.ImageView;

import java.util.Date;

/**
 * Created by Noam on 12/01/2018.
 */
enum Status {
    PAST,PRESENT,FUTURE
}

public class GenericEvent {

public static String lastId = null;
    private String id;
    private Status status;
    private String userName;
    private Date creationDate;
    private Date date;
    private int maxUsers;
    private int currentUsers;
    private String description;
    private int price;
    //eventType missing
    //subeventType missing
    private String phoneNum;
    private String email;
    private String friendsById[];
    private Boolean isPrivate;
    private ImageView eventImage;


    public GenericEvent() {
        this.id = null;
        this.status = Status.FUTURE;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getMaxUsers() {
        return maxUsers;
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

    public ImageView getEventImage() {
        return eventImage;
    }

    public void setEventImage(ImageView eventImage) {
        this.eventImage = eventImage;
    }






}

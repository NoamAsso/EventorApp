package com.example.noam.eventor;

import java.util.ArrayList;

/**
 * Created by itayrin on 1/23/2018.
 */

public class User {

    private int userId;

    private String userName;
    private String password;
    private int age;
    private String mail;
    private String phoneNum;
    private String sex;

    private ArrayList<Integer> attendingEventsIds;
    private ArrayList<Integer> createdEventsIds;
    private ArrayList<Integer> friendsIds;

    public User(int userId, String userName, String password, int age, String mail, String phoneNum, String sex) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.mail = mail;
        this.phoneNum = phoneNum;
        this.sex = sex;

        this.attendingEventsIds = new ArrayList<Integer>();
        this.createdEventsIds = new ArrayList<Integer>();
        this.friendsIds = new ArrayList<Integer>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getSex() {
        return sex;
    }

    public int getUserId() {
        return userId;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public ArrayList<Integer> getAttendingEventsIds() {
        return attendingEventsIds;
    }

    public void setAttendingEventsIds(ArrayList<Integer> attendingEventsIds) {
        this.attendingEventsIds = attendingEventsIds;
    }

    public ArrayList<Integer> getCreatedEventsIds() {
        return createdEventsIds;
    }

    public void setCreatedEventsIds(ArrayList<Integer> createdEventsIds) {
        this.createdEventsIds = createdEventsIds;
    }

    public ArrayList<Integer> getFriendsIds() {
        return friendsIds;
    }

    public void setFriendsIds(ArrayList<Integer> friendsIds) {
        this.friendsIds = friendsIds;
    }
    //#################################################

    public void addToAttendingEventsIds(Integer eventID){
        this.attendingEventsIds.add(eventID);
    }

    public Integer removeFromAttendingEventsIds(int i){
        if(!attendingEventsIds.isEmpty())
            return attendingEventsIds.get(i);
        else
            return null;
    }

    public ArrayList getFromAttendingEventsIds() {
        if(!attendingEventsIds.isEmpty())
            return attendingEventsIds;
        else
            return null;
    }

    public void clearAttendingEventsIds(){
        this.attendingEventsIds.clear();
    }
    //#################################################
    public void addToCreatedEventsIds(Integer eventID){
        this.createdEventsIds.add(eventID);
    }

    public Integer removeFromCreatedEventsIds(int i){
        if(!createdEventsIds.isEmpty())
            return createdEventsIds.get(i);
        else
            return null;
    }

    public ArrayList getFromCreatedEventsIds() {
        if(!createdEventsIds.isEmpty())
            return createdEventsIds;
        else
            return null;
    }

    public void clearCreatedEventsIds(){
        this.attendingEventsIds.clear();
    }
    //################################################# friendsIds
    public void addToFriendsIds(Integer userID){
        this.friendsIds.add(userID);
    }

    public Integer removeFromFriendsIds(int i){
        if(!friendsIds.isEmpty())
            return friendsIds.get(i);
        else
            return null;
    }

    public Integer getFromFriendsIds(int i) {
        if(!friendsIds.isEmpty())
            return friendsIds.get(i);
        else
            return null;
    }

    public void clearFriendsIds(){
        this.friendsIds.clear();
    }
    //#################################################

}

package com.example.noam.eventor;

import java.util.ArrayList;

/**
 * Created by itayrin on 1/23/2018.
 */

public class User {

    int userId;

    String userName;
    String password;
    int age;
    // TOKEN ##########################################################33
    String mail;
    String phoneNum;
    String sex;
    ArrayList<Integer> attendingEventsIds;

    ArrayList<Integer> createdEventsIds;
    ArrayList<Integer> friendsIds;
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
}

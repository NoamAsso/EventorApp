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



    public User(int userId, String userName, String password, int age, String mail, String phoneNum, String sex) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.age = age;
        this.mail = mail;
        this.phoneNum = phoneNum;
        this.sex = sex;


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


    //#################################################

}

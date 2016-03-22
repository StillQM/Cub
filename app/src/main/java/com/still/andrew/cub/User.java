package com.still.andrew.cub;

import java.util.Arrays;

/**
 * Created by AndrewStill on 3/22/16.
 */
public class User {

    private String userID;
    private String userName;
    private String userPassword;
    private String userType;


    public User(){


    }

    public User(String userID, String userName, String userPassword, String userType) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userType = userType;
        this.toString();
    }

    //<editor-fold desc="GETTERS">
    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserType() {
        return userType;
    }
    //</editor-fold>

    //<editor-fold desc="SETTERS">
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    //</editor-fold>


    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword=" + userPassword +
                ", userType='" + userType + '\'' +
                '}';
    }
}

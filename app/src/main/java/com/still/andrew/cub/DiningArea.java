package com.still.andrew.cub;

/**
 * Created by aes5638 on 4/18/16.
 */
public class DiningArea {

    private String diningAreaCoordinates;
    private String diningAreaID;
    private String diningAreaLocation;
    private String diningAreaName;

    public DiningArea(){

    }

    public DiningArea(String diningAreaCoordinates, String diningAreaID, String diningAreaLocation, String diningAreaName) {
        this.diningAreaCoordinates = diningAreaCoordinates;
        this.diningAreaID = diningAreaID;
        this.diningAreaLocation = diningAreaLocation;
        this.diningAreaName = diningAreaName;
    }

    //<editor-fold desc="GETTERS">

    public String getDiningAreaCoordinates() {
        return diningAreaCoordinates;
    }

    public String getDiningAreaID() {
        return diningAreaID;
    }

    public String getDiningAreaLocation() {
        return diningAreaLocation;
    }

    public String getDiningAreaName() {
        return diningAreaName;
    }

    //</editor-fold>

    //<editor-fold desc="SETTERS">

    public void setDiningAreaCoordinates(String diningAreaCoordinates) {
        this.diningAreaCoordinates = diningAreaCoordinates;
    }

    public void setDiningAreaID(String diningAreaID) {
        this.diningAreaID = diningAreaID;
    }

    public void setDiningAreaLocation(String diningAreaLocation) {
        this.diningAreaLocation = diningAreaLocation;
    }

    public void setDiningAreaName(String diningAreaName) {
        this.diningAreaName = diningAreaName;
    }

    //</editor-fold>


    @Override
    public String toString() {
        return "DiningArea{" +
                "diningAreaCoordinates='" + diningAreaCoordinates + '\'' +
                ", diningAreaID='" + diningAreaID + '\'' +
                ", diningAreaLocation='" + diningAreaLocation + '\'' +
                ", diningAreaName='" + diningAreaName + '\'' +
                '}';
    }
}

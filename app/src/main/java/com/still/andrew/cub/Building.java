package com.still.andrew.cub;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by aes5638 on 2/29/16.
 */
public class Building {

    private String buildingArea;
    private String buildingCoordinates;
    private String buildingID;
    private String buildingName;

    public Building(){

    }

    public Building(String buildingArea,String buildingCoordinates, String buildingID, String buildingName) {
        this.buildingArea = buildingArea;
        this.buildingCoordinates = buildingCoordinates;
        this.buildingID = buildingID;
        this.buildingName = buildingName;
    }

    public String getBuildingArea() {
        return buildingArea;
    }

    public String getBuildingCoordinates() {
        return buildingCoordinates;
    }

    public String getBuildingID() {
        return buildingID;
    }

    public String getBuildingName() {
        return buildingName;
    }

    @Override
    public String toString() {
        return "Building{" +
                "buildingArea='" + buildingArea + '\'' +
                ", buildingCoordinates='" + buildingCoordinates + '\'' +
                ", buildingID='" + buildingID + '\'' +
                ", buildingName='" + buildingName + '\'' +
                '}';
    }
}

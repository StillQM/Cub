package com.still.andrew.cub;

/**
 * Created by AndrewStill on 5/2/16.
 */
public class ScheduleItem {

    private String name;
    private String location;

    public ScheduleItem() {
    }

    public ScheduleItem(String location, String name) {
        this.location = location;
        this.name = name;
    }

    public String getItemName() {
        return name;
    }

    public String getItemLocation() {
        return location;
    }

    public void setItemName(String name) {
        this.name = name;
    }

    public void setItemLocation(String location) {
        this.location = location;
    }




}

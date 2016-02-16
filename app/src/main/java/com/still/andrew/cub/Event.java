package com.still.andrew.cub;

/**
 * Created by aes5638 on 2/10/16.
 */
public class Event {

    private String eventID;
    private String eventName;



    private String eventDescription;


    public Event(){

    }

    public Event(String eventDescription, String eventID, String eventName){
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        //System.out.println(this.eventName);

    }


    public String getEventName() {
        return eventName;
    }

    public String getEventID() {
        return eventID;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    @Override
    public String toString(){
        String theString = (getEventID() + getEventName());
        return theString;
    }


}

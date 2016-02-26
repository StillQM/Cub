package com.still.andrew.cub;

/**
 * Created by aes5638 on 2/10/16.
 */
public class Event {

    // <editor-fold desc="Variables"

    private String eventDate;
    private String eventDescription;
    private String eventID;
    private String eventName;
    private String eventPhoto;
    private String eventTime;
    private String eventType;
    private String eventVenue;

    // </editor-fold>



    public Event(){

    }

    public Event(String eventDate, String eventDescription, String eventID, String eventName, String eventPhoto, String eventTime, String eventType, String eventVenue) {
        this.eventDate = eventDate;
        this.eventDescription = eventDescription;
        this.eventID = eventID;
        this.eventName = eventName;
        this.eventPhoto = eventPhoto;
        this.eventTime = eventTime;
        this.eventType = eventType;
        this.eventVenue = eventVenue;
    }
// <editor-fold desc="GETTERS">

    public String getEventDate() {
        return eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventID() {
        return eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventPhoto() {
        return eventPhoto;
    }


    public String getEventTime() {
        return eventTime;
    }

    public String getEventType() {
        return eventType;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    // </editor-fold>



    @Override
    public String toString(){
        String theString = (getEventID() + getEventName());
        return theString;
    }


}

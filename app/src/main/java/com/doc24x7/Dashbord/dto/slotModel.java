package com.doc24x7.Dashbord.dto;

public class slotModel {
    private String id;
    private String start_time;
    private String end_time;
    private String day;

    public Boolean getBooked_status() {
        return booked_status;
    }

    public void setBooked_status(Boolean booked_status) {
        this.booked_status = booked_status;
    }

    private Boolean booked_status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}

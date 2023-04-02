package com.doc24x7.Dashbord.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GalleryListResponse {

    @SerializedName("status")
    @Expose
    private String status;

     @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<String> getFilePath() {
        return FilePath;
    }

    public void setFilePath(ArrayList<String> filePath) {
        FilePath = filePath;
    }

    ArrayList<String> FilePath;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("Data")
    @Expose
    private ArrayList<Datum> data = null;

    @SerializedName("doctors")
    @Expose
    private ArrayList<Datum> doctors = null;





    public ArrayList<Datum> getDoctors() {
        return doctors;
    }

    public void setDoctors(ArrayList<Datum> doctors) {
        this.doctors = doctors;
    }

    @SerializedName("appointment_list")
    @Expose
    private ArrayList<Datum> appointment_list = null;

    public ArrayList<Datum> getAppointment_list() {
        return appointment_list;
    }

    public void setAppointment_list(ArrayList<Datum> appointment_list) {
        this.appointment_list = appointment_list;
    }

    @SerializedName("events")
    @Expose
    private ArrayList<Datum> events = null;

    public ArrayList<Datum> getEvents() {
        return events;
    }

    public void setEvents(ArrayList<Datum> events) {
        this.events = events;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Datum> getData() {
        return data;
    }

    public void setData(ArrayList<Datum> data) {
        this.data = data;
    }

}

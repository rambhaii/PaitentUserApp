package com.doc24x7.Utils;


import com.google.gson.annotations.SerializedName;

public class ActivityActivityMessage {
    @SerializedName("message")
    private String message;
    @SerializedName("from")
    private String from;

    public ActivityActivityMessage(String message, String from) {
        this.message = message;
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {return from;}
}



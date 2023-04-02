package com.doc24x7.Utils;

import com.google.gson.annotations.SerializedName;

public class FragmentActivityMessage {
    @SerializedName("message")
    private String message;
    @SerializedName("from")
    private String from;


    public FragmentActivityMessage(String message, String from) {
        this.message = message;
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public String getFrom() {
        return from;
    }

}




package com.doc24x7.Dashbord.dto;

import com.doc24x7.Login.dto.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseReciept
{
    @SerializedName("Data")
    @Expose
    public ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}

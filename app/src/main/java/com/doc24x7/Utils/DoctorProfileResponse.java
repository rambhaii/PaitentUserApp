package com.doc24x7.Utils;

import com.doc24x7.Login.Datas;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorProfileResponse {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("OTP")
    @Expose
    private String OTP;


    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("Data")
    @Expose
    private Datas data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Datas getData() {
        return data;
    }

    public void setData(Datas data) {
        this.data = data;
    }
}

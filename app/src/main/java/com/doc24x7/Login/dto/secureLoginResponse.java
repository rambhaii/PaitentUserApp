package com.doc24x7.Login.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class secureLoginResponse
{
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    @SerializedName("url")
    @Expose
    private String url;

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

    public Integer getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Integer payment_id) {
        this.payment_id = payment_id;
    }

    private Integer payment_id;


    @SerializedName("Data")
    @Expose
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

 }

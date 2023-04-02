package com.doc24x7.Dashbord.dto;

public class SymptomData {
    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSysmobile() {
        return sysmobile;
    }

    public void setSysmobile(String sysmobile) {
        this.sysmobile = sysmobile;
    }

    public String symptoms;
    public String sysmobile;
    public  String sydate;
    public String doctor_id;
    public String statuscancel;
    public  String appointment_id;
    public  String book_date;
    public  String doctor_name;
    public  String experience;
    public  String start_time;
    public  String end_time;

    public int getPayment_id() {
        return this.payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public  int  payment_id;

    public String getActivityType() {
        return ActivityType;
    }

    public void setActivityType(String activityType) {
        ActivityType = activityType;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getEncryption_key() {
        return encryption_key;
    }

    public void setEncryption_key(String encryption_key) {
        this.encryption_key = encryption_key;
    }

    public String getToken_with_int_uid() {
        return token_with_int_uid;
    }

    public void setToken_with_int_uid(String token_with_int_uid) {
        this.token_with_int_uid = token_with_int_uid;
    }

    public String getEncryption_type() {
        return encryption_type;
    }

    public void setEncryption_type(String encryption_type) {
        this.encryption_type = encryption_type;
    }

    public String ActivityType;
 public String room;
 public String encryption_key;
 public String token_with_int_uid;
 public String encryption_type;

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String create_date;
    public String getNotidoctname() {
        return notidoctname;
    }

    public void setNotidoctname(String notidoctname) {
        this.notidoctname = notidoctname;
    }

    public String getNotimessage() {
        return notimessage;
    }

    public void setNotimessage(String notimessage) {
        this.notimessage = notimessage;
    }

    public String notidoctname;
    public String notimessage;
    public SymptomData() {
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getStatuscancel() {
        return statuscancel;
    }

    public void setStatuscancel(String statuscancel) {
        this.statuscancel = statuscancel;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getBook_date() {
        return book_date;
    }

    public void setBook_date(String book_date) {
        this.book_date = book_date;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
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

    public String getSydate() {
        return sydate;
    }

    public void setSydate(String sydate) {
        this.sydate = sydate;
    }

    public SymptomData(String symptoms, String sysmobile, String sydate) {
        this.symptoms = symptoms;
        this.sysmobile = sysmobile;
        this.sydate=sydate;
    }
}

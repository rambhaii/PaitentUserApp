package com.doc24x7.Login.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    private String resourceId;

    public Integer getPayment_id()
    {
        return payment_id;
    }

    public void setPayment_id(Integer payment_id) {
        this.payment_id = payment_id;
    }

    private Integer payment_id;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    private String sid;

    private String medicine_details;

    public String getRtm_token() {
        return rtm_token;
    }

    public void setRtm_token(String rtm_token) {
        rtm_token = rtm_token;
    }

    private String rtm_token;

    public String getMedicine_details() {
        return medicine_details;
    }

    public void setMedicine_details(String medicine_details) {
        this.medicine_details = medicine_details;
    }

    public String getPatient_details() {
        return patient_details;
    }

    public void setPatient_details(String patient_details) {
        this.patient_details = patient_details;
    }

    private String patient_details;





    @SerializedName("medicine_id")
    @Expose
    private String medicine_id;

  @SerializedName("qualification")
    @Expose
    private String qualification;

   @SerializedName("clinic_name")
    @Expose
    private String clinic_name;

    @SerializedName("preciption_img")
    @Expose
    private String preciption_img;

    public String getDigital_signature_img() {
        return digital_signature_img;
    }

    public void setDigital_signature_img(String digital_signature_img) {
        this.digital_signature_img = digital_signature_img;
    }

    @SerializedName("digital_signature_img")
    @Expose
    private String digital_signature_img;

  @SerializedName("preciption_description")
    @Expose
    private String preciption_description;


    public String getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(String medicine_id) {
        this.medicine_id = medicine_id;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getPreciption_img() {
        return preciption_img;
    }

    public void setPreciption_img(String preciption_img) {
        this.preciption_img = preciption_img;
    }

    public String getPreciption_description() {
        return preciption_description;
    }

    public void setPreciption_description(String preciption_description) {
        this.preciption_description = preciption_description;
    }

    @SerializedName("status")
    @Expose
    private String status;


    @SerializedName("message")
    @Expose
    private String message;


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

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("mobile")
    @Expose
    private String mobile;


 /*  @SerializedName("id")
    @Expose
    private String id;*/


   @SerializedName("role")
    @Expose
    private String role;

 // @SerializedName("patient_id")

 @SerializedName(value="patient_id", alternate="id")
 @Expose
    private String patient_id;


    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    @SerializedName("name")
    @Expose
    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @SerializedName("url")
     @Expose
     private String url;

   @SerializedName("email")
    @Expose
    private String email;


 @SerializedName("X-API-KEY")
    @Expose
    private String XAPIKEY;




    ////////////////////////respose///////////////


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getXAPIKEY() {
        return XAPIKEY;
    }

    public void setXAPIKEY(String XAPIKEY) {
        this.XAPIKEY = XAPIKEY;
    }


    //////////////////////////////////////////////

    @SerializedName("doctor_id")
    @Expose
    private String doctorId;
    @SerializedName("specialities")
    @Expose
    private String specialities;

    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;

    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("pincode")
    @Expose
    private Object pincode;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("image")
    @Expose
    private Object image;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getSpecialities() {
        return specialities;
    }

    public void setSpecialities(String specialities) {
        this.specialities = specialities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getPincode() {
        return pincode;
    }

    public void setPincode(Object pincode) {
        this.pincode = pincode;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

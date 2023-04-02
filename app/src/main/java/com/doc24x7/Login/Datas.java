package com.doc24x7.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datas {


    @SerializedName("status")
    @Expose
    private String status;
    private String token_with_int_uid;
    private String resourceId;

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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;

    public String getRtm_token() {
        return rtm_token;
    }

    public void setRtm_token(String rtm_token) {
        this.rtm_token = rtm_token;
    }

    private String rtm_token;

    public String getToken_with_int_uid() {
        return token_with_int_uid;
    }

    public void setToken_with_int_uid(String token_with_int_uid) {
        this.token_with_int_uid = token_with_int_uid;
    }

    public String getToken_with_user_account() {
        return token_with_user_account;
    }

    public void setToken_with_user_account(String token_with_user_account) {
        this.token_with_user_account = token_with_user_account;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getEncryption_type() {
        return encryption_type;
    }

    public void setEncryption_type(String encryption_type) {
        this.encryption_type = encryption_type;
    }

    private String token_with_user_account;
    private String room;
    private String encryption_type;

    public String getEncryption_key() {
        return encryption_key;
    }

    public void setEncryption_key(String encryption_key) {
        this.encryption_key = encryption_key;
    }

    private String encryption_key;


    public String getAssistant_name() {
        return assistant_name;
    }

    public void setAssistant_name(String assistant_name) {
        this.assistant_name = assistant_name;
    }

    public String getAssistant_mobile() {
        return assistant_mobile;
    }

    public void setAssistant_mobile(String assistant_mobile) {
        this.assistant_mobile = assistant_mobile;
    }

    public String getDoctor_fees() {
        return doctor_fees;
    }

    public void setDoctor_fees(String doctor_fees) {
        this.doctor_fees = doctor_fees;
    }

    public String getAccount_holder_name() {
        return account_holder_name;
    }

    public void setAccount_holder_name(String account_holder_name) {
        this.account_holder_name = account_holder_name;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getIfsc_code() {
        return ifsc_code;
    }

    public void setIfsc_code(String ifsc_code) {
        this.ifsc_code = ifsc_code;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }

    public String getUpi() {
        return upi;
    }

    public void setUpi(String upi) {
        this.upi = upi;
    }


    String assistant_name;
    String assistant_mobile;
    String doctor_fees;
    String account_holder_name;
    String bank_name;
    String account_no;
    String ifsc_code;
    String branch_name;
    String upi;

    public String getRegistration_no() {
        return registration_no;
    }

    public void setRegistration_no(String registration_no) {
        this.registration_no = registration_no;
    }

    String registration_no;

    public String getConsult_for() {
        return consult_for;
    }

    public void setConsult_for(String consult_for) {
        this.consult_for = consult_for;
    }

    String consult_for;
    String dr_appoinment;
    String patient_appoiment;
    String dr_comission;
    String dr_income;
    String total_patient;
    String profile_pic;
    String name;
    String specialization;
    String experience;

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }



    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }

    public String getClinic_name() {
        return clinic_name;
    }

    public void setClinic_name(String clinic_name) {
        this.clinic_name = clinic_name;
    }

    public String getDistrict_name() {
        return district_name;
    }

    public void setDistrict_name(String district_name) {
        this.district_name = district_name;
    }

    String pin;
    String state_name;
    String clinic_name;
    String district_name;

    public String getDr_appoinment() {
        return dr_appoinment;
    }

    public void setDr_appoinment(String dr_appoinment) {
        this.dr_appoinment = dr_appoinment;
    }

    public String getPatient_appoiment() {
        return patient_appoiment;
    }

    public void setPatient_appoiment(String patient_appoiment) {
        this.patient_appoiment = patient_appoiment;
    }

    public String getDr_comission() {
        return dr_comission;
    }

    public void setDr_comission(String dr_comission) {
        this.dr_comission = dr_comission;
    }

    public String getDr_income() {
        return dr_income;
    }

    public void setDr_income(String dr_income) {
        this.dr_income = dr_income;
    }

    public String getTotal_patient() {
        return total_patient;
    }

    public void setTotal_patient(String total_patient) {
        this.total_patient = total_patient;
    }

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


    @SerializedName("id")
    @Expose
    private String id;


    @SerializedName("role")
    @Expose
    private String role;





    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("join_date")
    @Expose
    private String join_date;


    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

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

    public String getDigital_signature_img() {
        return digital_signature_img;
    }

    public void setDigital_signature_img(String digital_signature_img) {
        this.digital_signature_img = digital_signature_img;
    }

    private String digital_signature_img;

    private String amount;
    private String txt_date;
    private String txt_id;
    private String payment_status;
    private String start_time;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTxt_date() {
        return txt_date;
    }

    public void setTxt_date(String txt_date) {
        this.txt_date = txt_date;
    }

    public String getTxt_id() {
        return txt_id;
    }

    public void setTxt_id(String txt_id) {
        this.txt_id = txt_id;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
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

    private String end_time;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

package com.doc24x7.Dashbord.dto;

public class OnlineDoctorRequest {
    private String UserId;
    private String DoctorId;

    public OnlineDoctorRequest(String userId, String doctorId, Boolean status) {
        UserId = userId;
        DoctorId = doctorId;
        Status = status;
    }

    private Boolean Status;

}

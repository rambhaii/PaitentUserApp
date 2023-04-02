package com.doc24x7.Dashbord.dto;

public class PatientDetailModel {
   private String PatientName;

    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getPatientMobile() {
        return PatientMobile;
    }

    public void setPatientMobile(String patientMobile) {
        PatientMobile = patientMobile;
    }

    public String getClientMobile() {
        return ClientMobile;
    }

    public void setClientMobile(String clientMobile) {
        ClientMobile = clientMobile;
    }

    public String getClientemail() {
        return Clientemail;
    }

    public void setClientemail(String clientemail) {
        Clientemail = clientemail;
    }

    public String getPatientAge() {
        return PatientAge;
    }

    public void setPatientAge(String patientAge) {
        PatientAge = patientAge;
    }

    private String PatientMobile;
    private String ClientMobile;
    private String Clientemail;

    public PatientDetailModel(String patientName, String patientMobile, String clientMobile, String clientemail, String patientAge) {
        PatientName = patientName;
        PatientMobile = patientMobile;
        ClientMobile = clientMobile;
        Clientemail = clientemail;
        PatientAge = patientAge;
    }

    private String PatientAge;
}

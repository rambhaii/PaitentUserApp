package com.doc24x7;

import java.util.ArrayList;

public class PatientDetailModel {
   private String PatientName;
    private String PatientMobile;
    private String ClientMobile;
    private String Clientemail;
    private String PatientSymption;
    private String Gender;
    private  String Type;
    private String Weight;
    private String Height;
    private String Bp;
    private String Sugar;

    ArrayList<String> ReportUrl;
    public PatientDetailModel(String patientName, String patientMobile, String clientMobile, String clientemail, String patientAge, String PatientSymptom, String gender, ArrayList<String> reporturl,String type, String weight,String height,String bp,String sugar) {
        PatientName = patientName;
        PatientMobile = patientMobile;
        ClientMobile = clientMobile;
        Clientemail = clientemail;
        PatientAge = patientAge;
        PatientSymption=PatientSymptom;
        Gender=gender;
        Type=type;
        ReportUrl=reporturl;
        Weight=weight;
        Height=height;
        Bp=bp;
        Sugar=sugar;
    }

    private String PatientAge;

}

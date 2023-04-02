package com.doc24x7.Dashbord.dto;

public class DETAIL {
    private String medicineName;
    private String medicimeFreQuency;

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicimeFreQuency() {
        return medicimeFreQuency;
    }

    public void setMedicimeFreQuency(String medicimeFreQuency) {
        this.medicimeFreQuency = medicimeFreQuency;
    }

    public String getMedicimeDays() {
        return medicimeDays;
    }

    public void setMedicimeDays(String medicimeDays) {
        this.medicimeDays = medicimeDays;
    }

    private String medicimeDays;
    private String whentouse;

    public String getWhentouse() {
        return whentouse;
    }

    public void setWhentouse(String whentouse) {
        this.whentouse = whentouse;
    }

    public String getHowtouse() {
        return howtouse;
    }

    public void setHowtouse(String howtouse) {
        this.howtouse = howtouse;
    }

    private String howtouse;

}

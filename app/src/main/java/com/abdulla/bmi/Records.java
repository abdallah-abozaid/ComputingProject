package com.example.bmiProject;

public class Records {
    String stutesRecords;
    double bmiForRecords;
    String weightRecords;
    String dateRecords;
    String timeRecords;
    String lengthRecords;
    String uId;
    public Records() {
    }

    public Records(String dateRecords, String timeRecords, String lengthRecords, String weightRecords, String uId, String stutesRecords, double bmiForRecords) {
        this.dateRecords = dateRecords;
        this.timeRecords = timeRecords;
        this.lengthRecords = lengthRecords;
        this.weightRecords = weightRecords;
        this.uId = uId;
        this.stutesRecords = stutesRecords;
        this.bmiForRecords = bmiForRecords;
    }

    public String getStutesRecords() {
        return stutesRecords;
    }


    public double getBmiForRecords() {
        return bmiForRecords;
    }


    public String getWeightRecords() {
        return weightRecords;
    }

    public void setWeightRecords(String weightRecords) {
        this.weightRecords = weightRecords;
    }

    public void setStutesRecords(String stutesRecords) {
        this.stutesRecords = stutesRecords;
    }

    public void setBmiForRecords(double bmiForRecords) {
        this.bmiForRecords = bmiForRecords;
    }

    public String getTimeRecords() {
        return timeRecords;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getDateRecords() {
        return dateRecords;
    }

    public void setDateRecords(String dateRecords) {
        this.dateRecords = dateRecords;
    }


    public void setTimeRecords(String timeRecords) {
        this.timeRecords = timeRecords;
    }

    public String getLengthRecords() {
        return lengthRecords;
    }

    public void setLengthRecords(String lengthRecords) {
        this.lengthRecords = lengthRecords;
    }
}


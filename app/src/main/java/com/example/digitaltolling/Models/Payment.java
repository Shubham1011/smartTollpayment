package com.example.digitaltolling.Models;

public class Payment {

    private String key;
    private String nID;
    private String vehicleID;
    private String plateNo;
    private String vehicleColor;
    private String vehicleName;
    private String date;
    private String time;

    public Payment() {
    }

    public Payment(String key, String nID, String vehicleID, String plateNo, String vehicleColor, String vehicleName, String time, String date) {
        this.key = key;
        this.nID = nID;
        this.vehicleID = vehicleID;
        this.plateNo = plateNo;
        this.vehicleColor = vehicleColor;
        this.vehicleName = vehicleName;
        this.date = date;
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getnID() {
        return nID;
    }

    public void setnID(String nID) {
        this.nID = nID;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(String vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

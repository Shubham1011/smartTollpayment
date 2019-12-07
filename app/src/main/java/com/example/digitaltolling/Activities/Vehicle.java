package com.example.digitaltolling.Activities;

public class Vehicle {

    private String vehicleName;
    private String plateNo;
    private String vehicleColor;
    private String id;

    public Vehicle() {
    }

    public Vehicle(String vehicleName, String plateNo, String vehicleColor, String id) {
        this.vehicleName = vehicleName;
        this.plateNo = plateNo;
        this.vehicleColor = vehicleColor;
        this.id = id;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}

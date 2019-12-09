package com.example.digitaltolling.Activities;

public class Vehicle {

    private String vehicleName;
    private String plateNo;
    private String vehicleColor;
    private String url;
    private String id;

    public Vehicle() {
    }

    public Vehicle(String vehicleName, String plateNo, String vehicleColor, String id,String url) {
        this.vehicleName = vehicleName;
        this.plateNo = plateNo;
        this.vehicleColor = vehicleColor;
        this.id=id;
        this.url=url;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

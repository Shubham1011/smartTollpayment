package com.example.digitaltolling.Models;


public class Record {
private String username;
private String vehicleimageurl;
private String vehicletype;
private String cost;
private String tollname;

    public Record(String username, String vehicleimageurl, String vehicletype, String cost,String tollname) {
        this.username = username;
        this.vehicleimageurl = vehicleimageurl;
        this.vehicletype = vehicletype;
        this.cost = cost;
        this.tollname=tollname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVehicleimageurl() {
        return vehicleimageurl;
    }

    public void setVehicleimageurl(String vehicleimageurl) {
        this.vehicleimageurl = vehicleimageurl;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}

package com.example.digitaltolling.Models;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Record {
private String username;
private String vehicleimageurl;
private String vehicletype;
private String cost;
private String tollname;
private String date;
    private String status;
    public Record(String username, String vehicleimageurl, String vehicletype, String cost,String tollname,String status) {
        this.username = username;
        this.vehicleimageurl = vehicleimageurl;
        this.vehicletype = vehicletype;
        this.cost = cost;
        this.tollname=tollname;
        this.status=status;
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a dd MMMM yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today = Calendar.getInstance().getTime();
        this.date= dateFormat.format(today);
    }
    public Record(){

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTollname() {
        return tollname;
    }

    public void setTollname(String tollname) {
        this.tollname = tollname;
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

package com.example.digitaltolling.Models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TollData {

    private String vehicleimgurl;
    private String vehiclenumber;
 private String balance;
private String vehicletype;
    private String vehiclename;
    private String cost;
    private String date;
    private String status;
private String tollname;

    public String getTollname() {
        return tollname;
    }

    public void setTollname(String tollname) {
        this.tollname = tollname;
    }

    public TollData(String vehicleimgurl, String vehiclenumber, String balance, String vehicletype, String vehiclename, String cost, String status, String tollname) {
        this.vehicleimgurl = vehicleimgurl;
        this.vehiclenumber = vehiclenumber;
        this.tollname=tollname;
        this.balance = balance;
        this.vehicletype = vehicletype;
        this.vehiclename = vehiclename;
        this.cost = cost;
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a dd MMMM yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("IST"));
        Date today = Calendar.getInstance().getTime();
        this.date= dateFormat.format(today);
        this.status = status;
    }

    public String getVehiclename() {
        return vehiclename;
    }

    public void setVehiclename(String vehiclename) {
        this.vehiclename = vehiclename;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
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

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getVehicleimgurl() {
        return vehicleimgurl;
    }

    public void setVehicleimgurl(String vehicleimgurl) {
        this.vehicleimgurl = vehicleimgurl;
    }

    public String getVehiclenumber() {
        return vehiclenumber;
    }

    public void setVehiclenumber(String vehiclenumber) {
        this.vehiclenumber = vehiclenumber;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}

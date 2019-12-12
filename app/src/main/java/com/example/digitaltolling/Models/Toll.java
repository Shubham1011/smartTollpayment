package com.example.digitaltolling.Models;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Toll {


    private String TollName;
    private String lat;
    private String lng;
    private String Lmv_price;
    private String Bus_Truck_price;
    private String Multiaxle_price;


    @NonNull
    @Override
    public String toString() {
        return this.TollName;
    }

    public Toll(){

    }
    public Toll( String tollName, String lat, String lng, String lmv_price, String bus_Truck_price, String multiaxle_price) {

       this.TollName = tollName;
        this.lat = lat;
        this.lng = lng;
        this.Lmv_price = lmv_price;
        this.Bus_Truck_price = bus_Truck_price;
        this.Multiaxle_price = multiaxle_price;

    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (this.getClass() != obj.getClass()) return false;
        Toll that = (Toll) obj;
        if (this.TollName != that.TollName) return false;
        return true;
    }


    public String getTollName() {
        return TollName;
    }

    public void setTollName(String tollName) {
        TollName = tollName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLmv_price() {
        return Lmv_price;
    }

    public void setLmv_price(String lmv_price) {
        Lmv_price = lmv_price;
    }

    public String getBus_Truck_price() {
        return Bus_Truck_price;
    }

    public void setBus_Truck_price(String bus_Truck_price) {
        Bus_Truck_price = bus_Truck_price;
    }

    public String getMultiaxle_price() {
        return Multiaxle_price;
    }

    public void setMultiaxle_price(String multiaxle_price) {
        Multiaxle_price = multiaxle_price;
    }
}

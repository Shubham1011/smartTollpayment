package com.example.digitaltolling.Activities;

import android.app.Activity;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.digitaltolling.Models.Payment;
import com.example.digitaltolling.R;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<Payment> {


    private Activity context;
    private List<Payment> paymentlist;

    public CustomAdapter( Activity context,List<Payment> paymentlist) {
        super(context, R.layout.sample_layout, paymentlist);
        this.context = context;
        this.paymentlist = paymentlist;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.sample_layout,null,true);

        Payment payment = paymentlist.get(position);

        TextView t1 = view.findViewById(R.id.vehicleNameId);
        TextView t2 = view.findViewById(R.id.plateNoId);
        TextView t3 = view.findViewById(R.id.vehicleTypeId);
        TextView t4 = view.findViewById(R.id.paidBillId);
        TextView t5 = view.findViewById(R.id.paymentKey);
        TextView t6 = view.findViewById(R.id.paymentTime);
        TextView t7 = view.findViewById(R.id.paymentDate);

        t1.setText("Vehicle Name: "+payment.getVehicleName());
        t2.setText("PlateNo. "+payment.getPlateNo());
        if(payment.getVehicleID().equals("1")){
            t3.setText("Type: Bike");
        }
        else if (payment.getVehicleID().equals("2")){

            t3.setText("Type: Auto Rickshaw");
        }
        else if (payment.getVehicleID().equals("3")){

            t3.setText("Type: Car");
        }
        else if (payment.getVehicleID().equals("4")){

            t3.setText("Type: SUV");
        }
        else if (payment.getVehicleID().equals("5")){

            t3.setText("Type: Microbus");
        }
        else if (payment.getVehicleID().equals("6")){

            t3.setText("Type: Pickup");
        }
        else if (payment.getVehicleID().equals("7")){

            t3.setText("Type: Minibus");
        }
        else if (payment.getVehicleID().equals("8")){

            t3.setText("Type: Bus");
        }
        else if (payment.getVehicleID().equals("9")){

            t3.setText("Type: Small truck");
        }
        else if (payment.getVehicleID().equals("10")){

            t3.setText("Type: Truck");
        }
        else{

            t3.setText("Type: Lorries");
        }

        if(payment.getVehicleID().equals("1")){
            t4.setText("Bill: 10 Taka");
        }
        else if(payment.getVehicleID().equals("2")){
            t4.setText("Bill: 18 Taka");
        }
        else if(payment.getVehicleID().equals("3")){
            t4.setText("Bill: 60 Taka");
        }
        else if(payment.getVehicleID().equals("4")){
            t4.setText("Bill: 70 Taka");
        }
        else if(payment.getVehicleID().equals("5")){
            t4.setText("Bill: 85 Taka");
        }
        else if(payment.getVehicleID().equals("6")){
            t4.setText("Bill: 130 Taka");
        }
        else if(payment.getVehicleID().equals("7")){
            t4.setText("Bill: 173 Taka");
        }
        else if(payment.getVehicleID().equals("8")){
            t4.setText("Bill: 260 Taka");
        }
        else if(payment.getVehicleID().equals("9")){
            t4.setText("Bill: 173 Taka");
        }
        else if(payment.getVehicleID().equals("10")){
            t4.setText("Bill: 260 Taka");
        }
        else{
            t4.setText("Bill: 345 Taka");
        }
        t5.setText("Transection ID: "+payment.getKey());
        t6.setText(payment.getTime());
        t7.setText(payment.getDate());

        return view;
    }
}

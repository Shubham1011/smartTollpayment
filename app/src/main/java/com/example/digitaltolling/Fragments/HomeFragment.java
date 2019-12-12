package com.example.digitaltolling.Fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.cardview.widget.CardView;

import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitaltolling.Activities.HistoryActivity;
import com.example.digitaltolling.Activities.MapsActivity;
import com.example.digitaltolling.Activities.PaymentActivity;
import com.example.digitaltolling.Activities.ScannerActivity;
import com.example.digitaltolling.Activities.TolllistActivity;
import com.example.digitaltolling.Activities.Vehicle;
import com.example.digitaltolling.Models.Record;
import com.example.digitaltolling.Models.Users;
import com.example.digitaltolling.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private CardView scannerCardView,tolllistCardView,historyCardView,profileCardView,mapCardViewId,paymentCardViewId;
    public static FragmentManager fragmentManager;

    DatabaseReference databaseReference,vehicleref,recordref;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Users user;
    TextView paymenttext;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
private int minbal;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_home, container, false);
final List<Record> recordList=new ArrayList<>();
        scannerCardView = view.findViewById(R.id.scannerCardViewId);
        tolllistCardView = view.findViewById(R.id.tolllistCardViewId);
        historyCardView = view.findViewById(R.id.historyCardViewId);
        profileCardView = view.findViewById(R.id.profileCardViewId);
        paymentCardViewId=view.findViewById(R.id.paymentCardViewId);
        paymenttext=view.findViewById(R.id.paymenttext);
        databaseReference=FirebaseDatabase.getInstance().getReference();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        recordref=FirebaseDatabase.getInstance().getReference().child("record").child(firebaseUser.getUid());
        vehicleref=FirebaseDatabase.getInstance().getReference().child("vehicles").child(firebaseUser.getUid());
        vehicleref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Vehicle v =dataSnapshot.getValue(Vehicle.class);
                if(v.getId().equals("1"))
                    minbal=45;
                if(v.getId().equals("2"))
                    minbal=90;
                else minbal=115;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
   databaseReference.child("Users").child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user=dataSnapshot.getValue(Users.class);
                final int value=Integer.parseInt(user.getBalance().toString());

                if(value>minbal)
                {

                    paymentCardViewId.setEnabled(false);
                    paymenttext.setText("Rs."+value);

                }
                else {

                    /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
                    new CountDownTimer(2000, 1000)
                    {

                        public void onTick(long millisUntilFinished) { final Toast toast = Toast.makeText(getContext(),"Your balance is Rs."+value+" deemed as a low balance \n " +
                                "Please Tap  Add Balance for smooth experience", Toast.LENGTH_SHORT);
                            View view = toast.getView();
                            view.setBackgroundColor(Color.RED);


                            TextView text = (TextView) view.findViewById(android.R.id.message);
                            text.setTextColor(Color.WHITE);
                            text.setGravity(Gravity.CENTER);
                        toast.show();}
                        public void onFinish() { final Toast toast = Toast.makeText(getContext(),"Your balance is Rs."+value+" deemed as a low balance \n " +
                                "Please Tap  Add Balance for smooth experience", Toast.LENGTH_SHORT);
                            View view = toast.getView();
                            view.setBackgroundColor(Color.RED);


                            TextView text = (TextView) view.findViewById(android.R.id.message);
                            text.setTextColor(Color.WHITE);
                            text.setGravity(Gravity.CENTER);
                        toast.show();
                        }


                    }.start();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        mapCardViewId=view.findViewById(R.id.mapCardViewId);
        scannerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId()== R.id.scannerCardViewId){

                    Intent intent = new Intent(getActivity(), ScannerActivity.class);
                    startActivity(intent);
                }
            }
        });

        paymentCardViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PaymentActivity.class);
                startActivity(intent);
            }
        });

        mapCardViewId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });
        tolllistCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), TolllistActivity.class);
                startActivity(intent);
            }
        });

        historyCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);

            }
        });

        profileCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.container,new ProfileFragment());
                fr.addToBackStack(null);
                fr.commit();
            }
        });






        return view;


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

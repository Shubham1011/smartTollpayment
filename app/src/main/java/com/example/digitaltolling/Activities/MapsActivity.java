package com.example.digitaltolling.Activities;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;

import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.example.digitaltolling.Models.Record;
import com.example.digitaltolling.Models.Toll;
import com.example.digitaltolling.Models.TollData;
import com.example.digitaltolling.Models.Users;
import com.example.digitaltolling.R;
import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.paytm.pgsdk.easypay.manager.PaytmAssist.getContext;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GeoQueryEventListener {
    NotificationChannel notificationChannel;
    private List<Toll> storedtolls=new ArrayList<>();
    private GoogleMap mMap;
    NotificationManager notificationManager;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Marker currentuser;
    private DatabaseReference myLocationRef;
    private DatabaseReference userReference,recordref;
    private DatabaseReference vehicleref;
    private DatabaseReference tollref;
    private DatabaseReference tolldata;
    private GeoFire geoFire;
    private List<LatLng> tolls = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    public static List<Toll> tollList=new ArrayList<>();
    HashSet<Toll> tollHashSet=new HashSet<>();
    public List<Toll> distinctlist;
    private Vehicle vehicle;
    private Users users;
    Record record;
    private static int journeysetter=1;
    private Boolean tstatus;
    private Boolean notificationstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        getuserandcar();
        recordref=FirebaseDatabase.getInstance().getReference().child("record");

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        buildlocationrequest();
                        buildlocationcallback();
                        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MapsActivity.this);
                        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(MapsActivity.this);
                        initarea();
                        settinggeofire();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        Toast.makeText(getApplicationContext(), "Need your location to function properly! Please reinstall the application", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                }).check();
    }

    private void getuserandcar() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
tolldata=FirebaseDatabase.getInstance().getReference().child("tolldata");
        userReference=FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid());
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 users=dataSnapshot.getValue(Users.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(MapsActivity.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
            }
        });
      vehicleref=FirebaseDatabase.getInstance().getReference().child("vehicles").child(user.getUid());
      tollref=FirebaseDatabase.getInstance().getReference().child("Toll");
        vehicleref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 vehicle=dataSnapshot.getValue(Vehicle.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MapsActivity.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
            }
        });
        tollref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storedtolls.clear();
                for(DataSnapshot toll:dataSnapshot.getChildren())
                {
                    storedtolls.add(toll.getValue(Toll.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void initarea() {
        /**
        tolls.add(new LatLng(19.043492, 72.824657));//bandra worli
        tolls.add(new LatLng(19.064475, 72.980455));//vashi toll
        tolls.add(new LatLng(19.153421, 72.965696));//airoli toll booth
        tolls.add(new LatLng(18.973882, 72.845407));//bpt toll
        tolls.add(new LatLng(19.310341, 72.976591));//malodi toll
        tolls.add(new LatLng(19.257073, 72.871275));//dahisar toll
        tolls.add(new LatLng(19.169120, 72.966792));//mulund toll
        tolls.add(new LatLng(19.184985, 72.955259));//lsb road thane mulund toll

**/

    }

    private void settinggeofire() {

        myLocationRef = FirebaseDatabase.getInstance().getReference("MyLocation");
        geoFire = new GeoFire(myLocationRef);

    }

    private void buildlocationcallback() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(final LocationResult locationResult) {
                if (mMap != null) {

                    geoFire.setLocation("you", new GeoLocation(locationResult.getLastLocation().getLatitude()
                            , locationResult.getLastLocation().getLongitude()), new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {


                            if (currentuser != null) currentuser.remove();
                            currentuser = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude()))
                                    .title("You"));
                            //update camera

                            mMap.animateCamera(CameraUpdateFactory.newLatLng(currentuser.getPosition()));
                        }
                    });


                }
            }
        };
    }






    private void buildlocationrequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1);
        locationRequest.setFastestInterval(1);
        locationRequest.setSmallestDisplacement(10f);


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (fusedLocationProviderClient != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    return;
            }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

        //ADD CIRCLE TO Toll AREA

        distinctlist=tollList.stream().distinct().collect(Collectors.<Toll>toList());

        for (Toll t :distinctlist
             ) {
            tolls.add(new LatLng(Double.parseDouble(t.getLat()),Double.parseDouble(t.getLng())));

        }
        for (LatLng l :
                tolls) {

            mMap.addCircle(new CircleOptions().center(l).radius(100)
                    .strokeColor(Color.BLUE).strokeWidth(5.0f).fillColor(Color.TRANSPARENT));

            GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(l.latitude, l.longitude), 0.1f);
            geoQuery.addGeoQueryEventListener(MapsActivity.this);
        }

        //writing geoquery for when user enters the fence


    }


    @Override
    protected void onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        super.onStop();
    }

    @Override
    public void onKeyEntered(String key, GeoLocation location) {
;
        Log.i("CALLED","ME");
        Toll nearresttoll=getnearesttoll(location,storedtolls);

        String costof=null;
        String vehicletype;
        Log.i("VEHICLEID",vehicle.getId());
        if (vehicle.getId().equals("1")) {
            Toast.makeText(this, "loop1", Toast.LENGTH_SHORT).show();
            vehicletype = "LMV/Car";

            costof=nearresttoll.getLmv_price();
            Log.i("VEHICLECOST",costof);
        }
        else if(vehicle.getId().equals("2")) {
            Toast.makeText(this, "loop2", Toast.LENGTH_SHORT).show();

                vehicletype = "Bus/Truck";
                costof=nearresttoll.getBus_Truck_price();
            Log.i("VEHICLECOST",costof);
            }
        else {
            Toast.makeText(this, "loopelse", Toast.LENGTH_SHORT).show();

                vehicletype = "Multiaxle";
                costof = nearresttoll.getMultiaxle_price();
            Log.i("VEHICLECOST",costof);
            }
        Log.i("VEHICLECOST",costof);
        tstatus=Integer.parseInt(users.getBalance())<Integer.parseInt(costof);

        Log.i("this",Integer.toString(journeysetter));
        if(!tstatus && journeysetter==1) {
            TollData tollData=new TollData(vehicle.getUrl(),vehicle.getPlateNo(),users.getBalance(),vehicletype,vehicle.getVehicleName(),costof,"true",nearresttoll.getTollName());
            tolldata.push().setValue(tollData);
            record=new Record(users.getName(),vehicle.getUrl(),vehicletype,costof,nearresttoll.getTollName(),"paid",vehicle.getVehicleName());
            journeysetter=2;
            sendnotofication("Good day " + users.getName() + ",you have a Toll coming up!!!", "My Algorithm says its " + nearresttoll.getTollName());
            Log.i("this",Integer.toString(journeysetter));
        }

        }

    private Toll getnearesttoll(GeoLocation location, List<Toll> storedtolls) {
      Double sdist=999999.0;
      Double newdist=0.0;
      Toll nearesttoll=new Toll();
        Log.i("HERE","out");
      for(Toll t:storedtolls)
      {newdist=meterDistanceBetweenPoints(
              location.latitude,
              location.longitude,
              Double.parseDouble(t.getLat()),
              Double.parseDouble(t.getLng()));
          Log.i("newdis",newdist.toString());
          Log.i("sdist",sdist.toString());
          Integer ans=newdist.compareTo(sdist);
          Log.i("ans",ans.toString());
          if(ans==1)
          {continue;}
          if(ans==-1)
          {
              nearesttoll=t;
              sdist=newdist;

          }

      }


        return nearesttoll;
    }
    private double meterDistanceBetweenPoints(double lat_a, double lng_a, double lat_b, double lng_b) {
        float pk = (float) (180.f / Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000 * tt;
    }
    @Override
    public void onKeyExited(String key) {

        Log.i("CALLED","ME");
        try {
            if (record.getStatus().equals("paid")) {


            }
        }catch (Exception e)
        {
            finish();
        }

        if(!tstatus && journeysetter==2) {
            int rem=Integer.parseInt(users.getBalance())-Integer.parseInt(record.getCost());
            userReference.child("balance").setValue(Integer.toString(rem));
            Log.i("this",Integer.toString(journeysetter));
            if(!users.getBalance().equals("0")){
            recordref.push().setValue(record);}
            sendnotofication("Happy Journey", String.format("Rs %s has been deducted from your account", record.getCost()));
            journeysetter=1;
notificationstatus=true;
            mMap.clear();
            finish();

        }
        Log.i("this",Integer.toString(journeysetter));

    }



    private void sendnotofication(String title, String content) {
        String NOTIFICATION_CHANNEL_ID = "tollnotification";
         notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) ;
        {
             notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notofication",
                    NotificationManager.IMPORTANCE_DEFAULT);
            //config
            notificationChannel.setDescription("channel descp");
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
            notificationChannel.enableLights(true);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        builder.setContentTitle(title);
        builder.setContentText(content)
                .setAutoCancel(false)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background));
        Notification notification = builder.build();
        notificationManager.notify(new Random().nextInt(), notification);

    }

    @Override
    public void onKeyMoved(String key, GeoLocation location) {



    }


    @Override
    public void onGeoQueryReady() {

    }

    @Override
    public void onGeoQueryError(DatabaseError error) {
        Toast.makeText(this, "Unable to process !! Please pay manually", Toast.LENGTH_SHORT).show();

    }


}

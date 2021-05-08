package com.example.mechaniconway1;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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
import com.squareup.picasso.Picasso;

import java.sql.Driver;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerMapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {

    private GoogleMap mMap;
    GoogleApiClient googleApiClient;
    Location LastLocation;
    LocationRequest locationRequest;

    private Button Logout;
    private Button SettingsButton;
    private Button CallCabCarButton;
    private Button CancelMechanic;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference CustomerDatabaseRef;
    private LatLng CustomerPickUpLocation;
    private ImageView callMechanicBtn;
    private String MechanicPhoneNumber;

    private DatabaseReference DriverAvailableRef;
    private DatabaseReference DriverLocationRef;
    private DatabaseReference DriversRef;
    private DatabaseReference callMechanicRef;
    private int radius = 1 ;

    private Boolean driverFound = false;
    private Boolean requestType = false;
    private String driverFoundID;
    private String customerID;
    Marker DriverMarker, PickUpMarker;
    GeoQuery geoQuery;

    private ValueEventListener DriverLocationRefListner;
    private ValueEventListener DriverRemoveListener;


    private TextView txtName, txtPhone;
    private CircleImageView profilePic;
    private RelativeLayout relativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_maps);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        CustomerDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Customers Request");
        DriverAvailableRef= FirebaseDatabase.getInstance().getReference().child("Mechanics Available");

        DriverLocationRef = FirebaseDatabase.getInstance().getReference().child("Mechanics Working");



        Logout = (Button) findViewById(R.id.logout_customer_btn);
        SettingsButton = (Button) findViewById(R.id.settings_customer_btn);
        CallCabCarButton =  (Button) findViewById(R.id.call_a_car_button);
        callMechanicBtn=(ImageView) findViewById(R.id.callMechanic);


        txtName = findViewById(R.id.name_driver);
        txtPhone = findViewById(R.id.phone_driver);
        //txtCarName = findViewById(R.id.car_name_driver);
        profilePic = findViewById(R.id.profile_image_driver);
        relativeLayout = findViewById(R.id.rel1);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(CustomerMapsActivity.this,SettingActivity.class);
                intent.putExtra("type", "Customers");
                startActivity(intent);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mAuth.signOut();

                LogOutUser();
            }
        });



        CallCabCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                try{
                    //Try to do something on here
                    if(requestType)
                    {
                        Toast.makeText(CustomerMapsActivity.this,"Cancelling Ride",Toast.LENGTH_SHORT).show();

                        requestType=false;
                        //geoQuery.removeAllListeners();
//error
                        //DriverLocationRef.removeEventListener(DriverLocationRefListner);


                        if(driverFound != null){

                            DriversRef =FirebaseDatabase.getInstance().getReference().child("Users").child("Mechanics").child(driverFoundID).child("CustomerRideID");

                            DriversRef.removeValue();
                            driverFoundID=null;

                        }

                        driverFound=false;
                        radius=1;
                        customerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        GeoFire geoFire = new GeoFire(CustomerDatabaseRef);

                        geoFire.removeLocation(customerID, new GeoFire.CompletionListener() {
                            @Override
                            public void onComplete(String key, DatabaseError error) {
                                if (error!=null)
                                {
                                    //   Toast.makeText(MechanicMapsActivity.this,"Unable to remove Location",Toast.LENGTH_SHORT).show();
                                }
                                //Toast.makeText(MechanicMapsActivity.this,"Location is removed",Toast.LENGTH_SHORT).show();
                            }
                        });

                        if (PickUpMarker != null){
                            PickUpMarker.remove();
                        }
                        if (DriverMarker != null){
                            DriverMarker.remove();
                        }
                        CallCabCarButton.setText("Find a Mechanic");
                        relativeLayout.setVisibility(View.GONE);
                    }
                    else {
                        requestType = true;
                        customerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        GeoFire geoFire = new GeoFire(CustomerDatabaseRef);
                        geoFire.setLocation(customerID, new GeoLocation(LastLocation.getLatitude(), LastLocation.getLongitude()), new GeoFire.CompletionListener() {
                            @Override
                            public void onComplete(String key, DatabaseError error) {
                                if (error != null) {
                                    //   Toast.makeText(MechanicMapsActivity.this,"Can't go Active",Toast.LENGTH_SHORT).show();
                                }
                                //Toast.makeText(MechanicMapsActivity.this,"You are Active",Toast.LENGTH_SHORT).show();
                            }
                        });

                        CustomerPickUpLocation = new LatLng(LastLocation.getLatitude(), LastLocation.getLongitude());
                        PickUpMarker = mMap.addMarker(new MarkerOptions().position(CustomerPickUpLocation).title("Customer Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.user)));

                        CallCabCarButton.setText("Getting your Mechanic...");

                        getClosetDriverCab();

                    }


                }catch(Exception error1) {
                    Toast.makeText(CustomerMapsActivity.this,error1.toString(),Toast.LENGTH_SHORT).show();
                }


            }




        });

        callMechanicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callMechanicRef = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child("Mechanics").child(driverFoundID);

                callMechanicRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if (dataSnapshot.exists()  )
                        {
                            //String name = dataSnapshot.child("name").getValue().toString();
                            String phone = dataSnapshot.child("phone").getValue().toString();
                            //String car = dataSnapshot.child("car").getValue().toString();


                            Intent intent = new Intent (Intent.ACTION_DIAL);
                            //intent.setData(Uri.parse("tel:03244366399"));
                            intent.setData(Uri.parse("tel:" +phone));
                            startActivity(intent);


                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        });

       /* CancelMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(CustomerMapsActivity.this,"you are cancelling ride",Toast.LENGTH_SHORT).show();
                if(requestType)
                {
                    Toast.makeText(CustomerMapsActivity.this,"Cancelling Ride",Toast.LENGTH_SHORT).show();

                    requestType=false;
                    geoQuery.removeAllListeners();

                    DriverLocationRef.removeEventListener(DriverLocationRefListner);

                    if(driverFound != null){

                        DriversRef =FirebaseDatabase.getInstance().getReference().child("Users").child("Mechanics").child(driverFoundID).child("CustomerRideID");
                        DriversRef.removeValue();
                        DriversRef.removeValue();
                        driverFoundID=null;

                    }

                    driverFound=false;
                    radius=1;
                    customerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    GeoFire geoFire = new GeoFire(CustomerDatabaseRef);

                    geoFire.removeLocation(customerID, new GeoFire.CompletionListener() {
                        @Override
                        public void onComplete(String key, DatabaseError error) {
                            if (error!=null)
                            {
                                //   Toast.makeText(MechanicMapsActivity.this,"Unable to remove Location",Toast.LENGTH_SHORT).show();
                            }
                            //Toast.makeText(MechanicMapsActivity.this,"Location is removed",Toast.LENGTH_SHORT).show();
                        }
                    });

                    if (PickUpMarker != null){
                        PickUpMarker.remove();
                    }
                    //CallCabCarButton.setText("Find a Mechanic");
                }
            }
        });*/
    }
   // @RequiresApi(api = Build.VERSION_CODES.N)
    private void getClosetDriverCab() {



        GeoFire geoFire = new GeoFire(DriverAvailableRef);

        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(CustomerPickUpLocation.latitude, CustomerPickUpLocation.longitude), radius);
        geoQuery.removeAllListeners();


        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {

            @Override
            public void onKeyEntered(String key, GeoLocation location) {


                    //anytime the driver is called this method will be called
                    //key=driverID and the location

                if (!driverFound && requestType ) {
                    Toast.makeText(CustomerMapsActivity.this, "driver found", Toast.LENGTH_SHORT).show();
                    driverFound = true;
                    driverFoundID = key;


                        //we tell driver which customer he is going to have
                    DriversRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Mechanics").child(driverFoundID);
                    HashMap driversMap = new HashMap();
                    driversMap.put("CustomerRideID", customerID);
                    DriversRef.updateChildren(driversMap);
                    Toast.makeText(CustomerMapsActivity.this, "Test 3", Toast.LENGTH_SHORT).show();




                    GettingDriverLocation();
                    CallCabCarButton.setText("Looking for Mechanic Location...");
                }
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {


                if (!driverFound) {
                    radius = radius + 1;
                    getClosetDriverCab();
                }
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }





    //and then we get to the driver location - to tell customer where is the driver
    private void GettingDriverLocation()
    {

        DriverLocationRefListner = DriverLocationRef.child(driverFoundID).child("l")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        if(dataSnapshot.exists() && requestType  )
                        {
                            List<Object> driverLocationMap = (List<Object>) dataSnapshot.getValue();
                            double LocationLat = 0;
                            double LocationLng = 0;
                            CallCabCarButton.setText("Mechanic Found");

                            relativeLayout.setVisibility(View.VISIBLE);
                            getAssignedDriverInformation();


                            if(driverLocationMap.get(0) != null)
                            {
                                LocationLat = Double.parseDouble(driverLocationMap.get(0).toString());
                            }
                            if(driverLocationMap.get(1) != null)
                            {
                                LocationLng = Double.parseDouble(driverLocationMap.get(1).toString());
                            }

                            //adding marker - to pointing where driver is - using this lat lng
                            LatLng DriverLatLng = new LatLng(LocationLat, LocationLng);
                            if(DriverMarker != null)
                            {
                                DriverMarker.remove();
                            }

                            Location location1 = new Location("");
                            location1.setLatitude(CustomerPickUpLocation.latitude);
                            location1.setLongitude(CustomerPickUpLocation.longitude);

                            Location location2 = new Location("");
                            location2.setLatitude(DriverLatLng.latitude);
                            location2.setLongitude(DriverLatLng.longitude);

                            float Distance = location1.distanceTo(location2);
                            //CallCabCarButton.setText("Mechanic Found: " + String.valueOf(Distance));
                            if (Distance < 90)
                            {
                                CallCabCarButton.setText("Mechanic's Reached");
                            }
                            else
                            {
                                CallCabCarButton.setText("Mechanic Found: " + String.valueOf(Distance));
                            }


                            DriverMarker = mMap.addMarker(new MarkerOptions().position(DriverLatLng).title("your mechanic is here").icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // now let set user location enable
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
    }















    @Override
    public void onConnected(@Nullable Bundle bundle)
    {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //

            return;
        }
        //it will handle the refreshment of the location
        //if we dont call it we will get location only once
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location)
    {
        //getting the updated location
        LastLocation = location;

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));
    }


    //create this method -- for useing apis
    protected synchronized void buildGoogleApiClient()
    {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect();
    }


    @Override
    protected void onStop()
    {
        super.onStop();
    }


    public void LogOutUser()
    {
        Intent startPageIntent = new Intent(CustomerMapsActivity.this, MainActivity.class);
        startPageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startPageIntent);
        finish();
    }


    private void getAssignedDriverInformation()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child("Mechanics").child(driverFoundID);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists()  &&  dataSnapshot.getChildrenCount() > 0)
                {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String phone = dataSnapshot.child("phone").getValue().toString();
                    //String car = dataSnapshot.child("car").getValue().toString();

                    txtName.setText(name);
                    txtPhone.setText(phone);
                    //txtCarName.setText(car);

                    if (dataSnapshot.hasChild("image"))
                    {
                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profilePic);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
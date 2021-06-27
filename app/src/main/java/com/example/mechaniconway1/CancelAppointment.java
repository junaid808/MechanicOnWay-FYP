package com.example.mechaniconway1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CancelAppointment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private TextView userName;
    private TextView userAddress;
    private TextView userEmail;
    private TextView userContact;
    private TextView userService;
    private TextView userDate;
    private TextView userTime;
    private Button CancelAppointmentButton;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private String onlineCustomerID;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appointment);




        mAuth = FirebaseAuth.getInstance();
        onlineCustomerID=FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("CustomerBookings");

        userName = findViewById(R.id.cancel_appointment1);
        userAddress=findViewById(R.id.cancel_appointment2);
        userEmail=findViewById(R.id.cancel_appointment3);
        userContact=findViewById(R.id.cancel_appointment4);
        userService=findViewById(R.id.cancel_appointment5);
        userDate=findViewById(R.id.cancel_appointment6);
        userTime=findViewById(R.id.cancel_appointment7);
        CancelAppointmentButton =findViewById(R.id.cancel_btn_booking);


        drawerLayout=findViewById(R.id.drawer_layout_cancel_appointment);
        Toolbar toolbar =  findViewById(R.id.toolbar_cancel_appointment);
        navigationView=findViewById(R.id.nav_view_cancel_appointment);

        setSupportActionBar(toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_contact);




        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    String name = dataSnapshot.child("CustomerName").getValue().toString();
                    String address = dataSnapshot.child("CustomerAddress").getValue().toString();
                    String email = dataSnapshot.child("CustomerEmail").getValue().toString();
                    String phone = dataSnapshot.child("CustomerContact").getValue().toString();
                    String service = dataSnapshot.child("CustomerService").getValue().toString();
                    String date = dataSnapshot.child("BookingDate").getValue().toString();
                    String time = dataSnapshot.child("BookingTime").getValue().toString();




                    userName.setText(name);
                    userAddress.setText(address);
                    userEmail.setText(email);
                    userContact.setText(phone);
                    userService.setText(service);
                    userDate.setText(date);
                    userTime.setText(time);





                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        CancelAppointmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference customerBookingsData = FirebaseDatabase.getInstance().getReference().child("CustomerBookings").child(onlineCustomerID);
                customerBookingsData.removeValue();
                finish();


            }
        });

    }

    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
        {super.onBackPressed();
        }
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {

        switch (menuItem.getItemId()) {

            case R.id.nav_home:
                Intent intent3 = new Intent(CancelAppointment.this, DashboardActivity.class);
                startActivity(intent3);
                break;

            case R.id.book_appointment:
                Intent intent1 = new Intent(this, BookingDetails.class);
                startActivity(intent1);
                break;

            case R.id.nav_cancel_appointment:

                break;
           /* case R.id.nav_login:
                menu.findItem(R.id.nav_logout).setVisible(true);
                menu.findItem(R.id.nav_profile).setVisible(true);
                menu.findItem(R.id.nav_login).setVisible(false);
                break;
            case R.id.nav_logout:
                menu.findItem(R.id.nav_logout).setVisible(false);
                menu.findItem(R.id.nav_profile).setVisible(false);
                menu.findItem(R.id.nav_login).setVisible(true);
                break;*/
            case R.id.nav_about_us:
                Intent intent4 =  new Intent(this, AboutUs.class);
                startActivity(intent4);
                break;
            case R.id.nav_contact:
                Intent intent5 =  new Intent(this, ContactUs.class);
                startActivity(intent5);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
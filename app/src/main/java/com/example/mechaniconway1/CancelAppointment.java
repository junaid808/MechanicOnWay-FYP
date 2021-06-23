package com.example.mechaniconway1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CancelAppointment extends AppCompatActivity {
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
}
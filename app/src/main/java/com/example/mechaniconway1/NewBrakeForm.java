package com.example.mechaniconway1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class NewBrakeForm extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference CustomerDatabaseRef;

    private static Button date, time;
    private static TextView set_date, set_time;
    private static final int Date_id = 0;
    private static final int Time_id = 1;

    // To get Data from XML
    private EditText CustomerName;
    private  EditText CustomerAddress;
    private  EditText CustomerEmail;
    private  EditText CustomerContact;
    private  Spinner CustomerService;
    private  TextView BookingDate;
    private  TextView BookingTime;

    //To Send Data to Database
    private  String CustomerNameString;
    private  String CustomerAddressString;
    private  String CustomerEmailString;
    private  String CustomerContactString;
    private  String CustomerServiceString;
    private  String BookingDateString;
    private  String BookingTimeString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_brake_form);

        String extra = getIntent().getStringExtra("extra");

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        CustomerDatabaseRef = FirebaseDatabase.getInstance().getReference().child("CustomerBookings");

        CustomerName = findViewById(R.id.nameNewBreaksForm);
        CustomerAddress = findViewById(R.id.addressNewBreaksForm);
        CustomerEmail = findViewById(R.id.emailNewBreaksForm);
        CustomerContact = findViewById(R.id.contactNewBreaksForm);
        CustomerService = findViewById(R.id.newBrakeSpinner);
        BookingDate = findViewById(R.id.set_dateNewBreaksForm);
        BookingTime = findViewById(R.id.set_timeNewBreaksForm);

        date = (Button) findViewById(R.id.selectdateNewBreaksForm);
        time = (Button) findViewById(R.id.selecttimeNewBreaksForm);
        set_date = (TextView) findViewById(R.id.set_dateNewBreaksForm);
        set_time = (TextView) findViewById(R.id.set_timeNewBreaksForm);
        Spinner dropdown = findViewById(R.id.newBrakeSpinner);
        //create a list of items for the spinner.
        String[] items = new String[]{extra};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show Date dialog
                showDialog(Date_id);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                // Show time dialog
                showDialog(Time_id);
            }
        });

    }

    protected Dialog onCreateDialog(int id) {

        // Get the calander
        Calendar c = Calendar.getInstance();

        // From calander get the year, month, day, hour, minute
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        switch (id) {
            case Date_id:

                // Open the datepicker dialog
                return new DatePickerDialog(NewBrakeForm.this, date_listener, year,
                        month, day);
            case Time_id:

                // Open the timepicker dialog
                return new TimePickerDialog(NewBrakeForm.this, time_listener, hour,
                        minute, false);

        }
        return null;
    }

    // Date picker dialog
    DatePickerDialog.OnDateSetListener date_listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // store the data in one string and set it to text
            String date1 = String.valueOf(month) + "/" + String.valueOf(day)
                    + "/" + String.valueOf(year);
            set_date.setText(date1);
        }
    };
    TimePickerDialog.OnTimeSetListener time_listener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hour, int minute) {
            // store the data in one string and set it to text
            String time1 = String.valueOf(hour) + ":" + String.valueOf(minute);
            set_time.setText(time1);
        }
    };

    public void bookAppointment(View view) {
        CustomerNameString  = CustomerName.getText().toString();
        CustomerEmailString = CustomerEmail.getText().toString();
        CustomerAddressString = CustomerAddress.getText().toString();
        CustomerContactString = CustomerContact.getText().toString();
        CustomerServiceString = CustomerService.getSelectedItem().toString();
        BookingDateString = BookingDate.getText().toString();
        BookingTimeString = BookingTime.getText().toString();


        boolean isValid = true;
        if(CustomerName.getText().toString().isEmpty()   )

        {
            CustomerName.setError("This Field is Required");





            isValid = false;

        }else if( CustomerAddress.getText().toString().isEmpty()){
            CustomerAddress.setError("This Field is Required");
            isValid = false;
        }
        else if( CustomerEmail.getText().toString().isEmpty()){
            CustomerEmail.setError("This Field is Required");
            isValid = false;
        }else if( CustomerContact.getText().toString().isEmpty()){
            CustomerContact.setError("This Field is Required");
            isValid = false;
        }else if( BookingDate.getText().toString().isEmpty()){
            BookingDate.setError("This Field is Required");
            isValid= false;

        }else if( BookingTime.getText().toString().isEmpty()){
            BookingTime.setError("This Field is Required");
            isValid=false;
        }

        else{
            createBooking(CustomerNameString,CustomerAddressString,CustomerEmailString,CustomerContactString,CustomerServiceString,BookingDateString,BookingTimeString);
            CustomerName.setText("");
            CustomerEmail.setText("");
            CustomerAddress.setText("");
            CustomerContact.setText("");
            BookingDate.setText("");
            BookingTime.setText("");
            finish();
        }

    }
    public  void createBooking(
            String CustomerName, String CustomerAddress, String CustomerEmail, String CustomerContact,String CustomerService,String BookingDate,String BookingTime
    ){
        Booking booking = new Booking(CustomerName,CustomerAddress,CustomerEmail,CustomerContact,CustomerService,BookingDate,BookingTime);
        CustomerDatabaseRef.child(currentUser.getUid()).setValue(booking);
        //CustomerDatabaseRef.child(CustomerName).setValue(booking);
        //We can use cnic/mob number for adding data
    }
}

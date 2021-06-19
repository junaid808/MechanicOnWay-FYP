package com.example.mechaniconway1;

public class Booking {
    public  String CustomerName;
    public  String CustomerAddress;
    public  String CustomerEmail;
    public  String CustomerContact;
    public  String CustomerService;
    public  String BookingDate;
    public  String BookingTime;
    public Booking() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }


    public Booking(String CustomerName, String CustomerAddress, String CustomerEmail, String CustomerContact,String CustomerService,String BookingDate,String BookingTime) {
        this.CustomerName = CustomerName;
        this.CustomerAddress = CustomerAddress;
        this.CustomerEmail = CustomerEmail;
        this.CustomerContact = CustomerContact;
        this.CustomerService = CustomerService;
        this.BookingDate = BookingDate;
        this.BookingTime = BookingTime;
    }
}

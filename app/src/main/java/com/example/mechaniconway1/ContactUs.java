package com.example.mechaniconway1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class ContactUs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        drawerLayout=findViewById(R.id.drawer_layout_contact_us);
        Toolbar toolbar =  findViewById(R.id.toolbar_contact_us);
        navigationView=findViewById(R.id.nav_view_contact_us);

        setSupportActionBar(toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_contact);






    }

    public void CallAdmin(View view) {
        Intent intent = new Intent (Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:03214205808"));
        startActivity(intent);

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
                Intent intent = new Intent(this, DashboardActivity.class);
                startActivity(intent);
                break;
            case R.id.book_appointment:
                Intent intent1 = new Intent(this, BookingDetails.class);
                startActivity(intent1);
                break;

            case R.id.nav_cancel_appointment:
                Intent intent2 = new Intent(this, CancelAppointment.class);
                startActivity(intent2);
                break;


            case R.id.nav_about_us:
                Intent intent5 = new Intent(this, AboutUs.class);
                startActivity(intent5);
                break;
            case R.id.nav_contact:
                Intent intent4 =  new Intent(this, ContactUs.class);
                startActivity(intent4);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void EmailAdmin(View view) {
        Intent i  = new Intent(this, EmailAdmin.class);
        startActivity(i);
    }
}
package com.example.mechaniconway1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Battery extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;
    String Data ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        textView=findViewById(R.id.textView);
        Toolbar toolbar =  findViewById(R.id.toolbar);



        setSupportActionBar(toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

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

    public void goToBatteryForm(View view) {
        Data="AGS @ RS:12,000";
        Intent i = new Intent(this, BatteryForm.class);
        i.putExtra("extra", Data);
        startActivity(i);
    }

    public void goToBatteryForm2(View view) {
        Data="EXIDE @ RS:15,000";
        Intent i = new Intent(this, BatteryForm.class);
        i.putExtra("extra", Data);
        startActivity(i);
    }

    public void goToBatteryForm3(View view) {
        Data="OSAKA @ RS:16,000";
        Intent i = new Intent(this, BatteryForm.class);
        i.putExtra("extra", Data);
        startActivity(i);
    }

    public void goToBatteryForm4(View view) {
        Data="PHOENIX @ RS:18,000";
        Intent i = new Intent(this, BatteryForm.class);
        i.putExtra("extra", Data);
        startActivity(i);
    }
}
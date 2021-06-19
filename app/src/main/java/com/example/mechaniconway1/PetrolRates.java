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
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class PetrolRates extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petrol_rates);
        Spinner dropdown = findViewById(R.id.spinner1);
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

//create a list of items for the spinner.
        String[] items = new String[]{"1 Litre Petrol", "2 Litre Petrol", "3 Litre Petrol", "4 Litre Petrol", "5 Litre Petrol","6 Litre Petrol","7 Litre Petrol","8 Litre Petrol","9 Litre Petrol","10 Litre Petrol"};
//create an adapter to describe how the items are displayed, adapters are used in several places in android.
//There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    public void goToBookingForm(View view) {
        Intent i = new Intent(this, EngineOilForm.class);
        startActivity(i);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
    {

        switch (menuItem.getItemId()) {

            case R.id.nav_home:
                Intent intent = new Intent(PetrolRates.this, DashboardActivity.class);
                startActivity(intent);
                break;
            case R.id.book_appointment:
                Intent intent1 = new Intent(PetrolRates.this, BookAppointment.class);
                startActivity(intent1);
                break;

            case R.id.nav_cancel_appointment:
                Intent intent2 = new Intent(PetrolRates.this, Parts.class);
                startActivity(intent2);
                break;
            /*case R.id.nav_login:
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
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show(); break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
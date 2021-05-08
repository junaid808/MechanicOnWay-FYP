package com.example.mechaniconway1;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView EngineService;
    private ImageView TyreService;
    private ImageView BrakeService;
    private ImageView PetroliumService;
    private Button UrgentAssistance;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.nav_view);
        textView=findViewById(R.id.textView);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        Button UrgentAssistance= findViewById(R.id.urgent_assitance);



        setSupportActionBar(toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);



        ImageView EngineService = (ImageView) findViewById(R.id.engine);
        EngineService.setClickable(true);
        EngineService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this,Services.class);
                startActivity(i);
            }
        });

        ImageView TyreService = (ImageView) findViewById(R.id.tyre);
        TyreService.setClickable(true);
        TyreService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this,Tyre.class);
                startActivity(i);
            }
        });

        ImageView PetroliumService = (ImageView) findViewById(R.id.petrolium);
        PetroliumService.setClickable(true);
        PetroliumService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this,Petrolium.class);
                startActivity(i);
            }
        });


        ImageView BreakService = (ImageView) findViewById(R.id.brakes);
        BreakService.setClickable(true);
        BreakService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this,Brakes.class);
                startActivity(i);
            }
        });

        UrgentAssistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(DashboardActivity.this,CustomerMapsActivity.class);
                startActivity(i);
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
            case R.id.nav_home: break;
            case R.id.nav_service:
                Intent intent = new Intent(DashboardActivity.this, Services.class);
                startActivity(intent);
                break;
            case R.id.book_appointment:
                Intent intent1 = new Intent(DashboardActivity.this, BookAppointment.class);
                startActivity(intent1);
                break;

            case R.id.nav_parts:
                Intent intent2 = new Intent(DashboardActivity.this, Parts.class);
                startActivity(intent2);
                break;
            case R.id.nav_login:
                menu.findItem(R.id.nav_logout).setVisible(true);
                menu.findItem(R.id.nav_profile).setVisible(true);
                menu.findItem(R.id.nav_login).setVisible(false);
                break;
            case R.id.nav_logout:
                menu.findItem(R.id.nav_logout).setVisible(false);
                menu.findItem(R.id.nav_profile).setVisible(false);
                menu.findItem(R.id.nav_login).setVisible(true);
                break;
            case R.id.nav_about_us:
                Intent intent3 = new Intent(DashboardActivity.this,AboutUs.class);
                startActivity(intent3); break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
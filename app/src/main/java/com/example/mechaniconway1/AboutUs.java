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

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;

public class AboutUs extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Button buttonCall;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        drawerLayout=findViewById(R.id.drawer_layout_about_us);
        Toolbar toolbar =  findViewById(R.id.toolbar_about_us);
        navigationView=findViewById(R.id.nav_view_about_us);


        setSupportActionBar(toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_about_us);


        /*buttonCall = findViewById(R.id.btnCall);
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:03244366399"));
                startActivity(intent);

            }
        });*/

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
                Intent intent3 = new Intent(AboutUs.this, DashboardActivity.class);
                startActivity(intent3);
                break;

            case R.id.book_appointment:
                Intent intent1 = new Intent(AboutUs.this, BookAppointment.class);
                startActivity(intent1);
                break;

            case R.id.nav_cancel_appointment:
                Intent intent2 = new Intent(AboutUs.this, Parts.class);
                startActivity(intent2);
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
                 break;
            case R.id.nav_contact:
                Intent intent4 =  new Intent(this, ContactUs.class);
                startActivity(intent4);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
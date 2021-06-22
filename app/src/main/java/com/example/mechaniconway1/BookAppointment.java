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
import android.widget.TextView;
import android.widget.Toast;

import android.view.View;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;

public class BookAppointment extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    EditText name, address, contact, email;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        contact  = findViewById(R.id.contact);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        textView = findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);


        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);
    }
    public void bookAppointment(View view) {
        email.setText("ahmed486rvns@gmail.com");
        name.setText("Ahmed");
        address.setText("Township,Lahore");
        contact.setText("03244366399");

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

                case R.id.book_appointment:
                    Intent intent1 = new Intent(BookAppointment.this, BookAppointment.class);
                    startActivity(intent1);
                    break;

                case R.id.nav_cancel_appointment:
                    Intent intent2 = new Intent(BookAppointment.this, Parts.class);
                    startActivity(intent2);
                    break;
/*  case R.id.nav_login:
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

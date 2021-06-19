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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Parts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView EngineService;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Menu menu;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parts);
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

        navigationView.setCheckedItem(R.id.nav_cancel_appointment);



        //ImageView EngineService = (ImageView) findViewById(R.id.engine);
        //EngineService.setClickable(true);
        // EngineService.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //public void onClick(View v) {
        //  Intent i = new Intent(DashboardActivity.this,ActivityOne.class);
        //startActivity(i);
        //}
        //   });

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
                Intent intent0 = new Intent(Parts.this, DashboardActivity.class);
                startActivity(intent0);
                break;

            case R.id.book_appointment:
                Intent intent1 = new Intent(Parts.this, BookAppointment.class);
                startActivity(intent1);
                break;

            case R.id.nav_cancel_appointment:
                Intent intent2 = new Intent(Parts.this, Parts.class);
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
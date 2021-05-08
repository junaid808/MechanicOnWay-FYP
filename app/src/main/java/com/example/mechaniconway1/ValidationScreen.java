package com.example.mechaniconway1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ValidationScreen extends AppCompatActivity {

    private Button CustomerValidationBtn;
    private Button MechanicValidationBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validation_screen);

        CustomerValidationBtn =findViewById(R.id.customer_validation_btn);
        MechanicValidationBtn=findViewById(R.id.mechanic_validation_btn);

        CustomerValidationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ValidationScreen.this,MainActivity.class);
                startActivity(i);
            }
        });
        MechanicValidationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ValidationScreen.this,MechanicLogIn.class);
                startActivity(i);
            }
        });
    }
}
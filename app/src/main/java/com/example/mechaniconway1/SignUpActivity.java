package com.example.mechaniconway1;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText emailEt,passwordEt1,passwordEt2,userNameEt;
    private Button SignUpButton;
    private TextView SignInTv;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    private DatabaseReference customerDatabaseRef;
    private String onlineCustomerID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        firebaseAuth=FirebaseAuth.getInstance();

        userNameEt =findViewById(R.id.inputUsername);
        emailEt=findViewById(R.id.inputEmail);
        passwordEt1=findViewById(R.id.inputPassword);
        passwordEt2=findViewById(R.id.inputConformPassword);
        SignUpButton=findViewById(R.id.btnRegister);
        progressDialog=new ProgressDialog(this);
        SignInTv=findViewById(R.id.alreadyHaveAccount);
        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        SignInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Register(){
        //Take data from XML file
        String userName=userNameEt.getText().toString();
        String email=emailEt.getText().toString();
        String password1=passwordEt1.getText().toString();
        String password2=passwordEt2.getText().toString();

        if(TextUtils.isEmpty(userName)){
            userNameEt.setError("Enter your username");
            return;
        }
        else if(TextUtils.isEmpty(email)){
            emailEt.setError("Enter your email");
            return;
        }
        else if(TextUtils.isEmpty(password1)){
            passwordEt1.setError("Enter your password");
            return;
        }
        else if(TextUtils.isEmpty(password2)){
            passwordEt2.setError("Confirm your password");
            return;
        }
        else if(!password1.equals(password2)){
            passwordEt2.setError("Different password");
            return;
        }
        else if(password1.length()<4){
            passwordEt1.setError("Length should be > 4");
            return;
        }
        else if(!isVallidEmail(email)){
            emailEt.setError("invalid email");
            return;
        }
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    onlineCustomerID=firebaseAuth.getCurrentUser().getUid();
                    customerDatabaseRef= FirebaseDatabase.getInstance().getReference().child("Users").child("Customers").child(onlineCustomerID);
                    customerDatabaseRef.setValue(true);




                    Intent intent=new Intent(SignUpActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    Toast.makeText(SignUpActivity.this,"Successfully registered",Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    Toast.makeText(SignUpActivity.this,"Sign up fail!",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
        });
    }
    private Boolean isVallidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}

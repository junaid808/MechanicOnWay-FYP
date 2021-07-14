package com.example.mechaniconway1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MechanicLogIn extends AppCompatActivity {
    private EditText emailEt,passwordEt;
    private Button SignInBtn;
    private Button mechanicLoginButton;
    private EditText mechanicEmail;
    private EditText mechanicPassword;
    private FirebaseAuth Myauth;
    private ProgressDialog loadingBar;
    private TextView resetPasswordMechanic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_log_in);

        Myauth = FirebaseAuth.getInstance();

        mechanicLoginButton = (Button) findViewById(R.id.login_mechanic_btn);
        mechanicEmail = (EditText) findViewById(R.id.email_mechanic);
        mechanicPassword =(EditText) findViewById(R.id.password_mechanic);
        resetPasswordMechanic = findViewById(R.id.forgotPassword1);
        loadingBar = new ProgressDialog(this);
        /*SignUpMechanic= findViewById(R.id.signUp);*/






        mechanicLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email =  mechanicEmail.getText().toString();
                String password = mechanicPassword.getText().toString();

                loginMechanic(email,password);
            }
        });

        resetPasswordMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ? ");
                passwordResetDialog.setMessage("Enter your Email to receive Reset Link. ");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail = resetMail.getText().toString();
                        Myauth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MechanicLogIn.this,"Reset Link Sent to your Email. ",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MechanicLogIn.this,"Error ! Resent Link si not Sent. "+ e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.create().show();

            }
        });
        /*SignUpMechanic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MechanicLogIn.this,SignUpMechanic.class);
                startActivity(intent);
                finish();
            }
        });*/


    }

    private void loginMechanic(String email, String password) {

        if(TextUtils.isEmpty(email))
            Toast.makeText(MechanicLogIn.this, "Please Enter valid Email address", Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(password))
            Toast.makeText(MechanicLogIn.this, "Please Enter valid Password", Toast.LENGTH_SHORT).show();
        else
        {
            loadingBar.setTitle("Mechanic Login");
            loadingBar.setMessage("Please Wait ..... While we check your creditentials ..!");
            loadingBar.show();
            Myauth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MechanicLogIn.this,"Mechanic LogInSuccessfully",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent driverIntent = new Intent(MechanicLogIn.this,MechanicMapsActivity.class);
                                startActivity(driverIntent);
                            }
                            else{
                                Toast.makeText(MechanicLogIn.this,"LogIn Failed .....Try again",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });
        }

    }

    /*private void RegisterMechanic(String email, String password) {

        if(TextUtils.isEmpty(email))
            Toast.makeText(MechanicLoginRegisterActivity.this, "Please Enter valid Email address", Toast.LENGTH_SHORT).show();
        if(TextUtils.isEmpty(password))
            Toast.makeText(MechanicLoginRegisterActivity.this, "Please Enter valid Password", Toast.LENGTH_SHORT).show();
        else
        {
            loadingBar.setTitle("Mechanic Registration");
            loadingBar.setMessage("Please Wait ..... While we register your data ..!");
            loadingBar.show();
            Myauth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MechanicLoginRegisterActivity.this,"Mechanic Registered Successfully",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent driverIntent = new Intent(MechanicLoginRegisterActivity.this,MechanicMapsActivity.class);
                                startActivity(driverIntent);
                            }
                            else{
                                Toast.makeText(MechanicLoginRegisterActivity.this,"Registeration Failed .....Try again",Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });
        }

    }*/
}


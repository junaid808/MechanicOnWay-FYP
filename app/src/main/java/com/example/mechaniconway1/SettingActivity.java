package com.example.mechaniconway1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;



import java.util.HashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;


import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class    SettingActivity extends AppCompatActivity {
    private String getType;

    private CircleImageView profileImageView;
    private EditText nameEditText, phoneEditText, driverCarName;
    private ImageView closeButton, saveButton;
    private TextView profileChangeBtn;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private String checker = "";
    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        getType = getIntent().getStringExtra("type");
        Toast.makeText(this, getType, Toast.LENGTH_SHORT).show();


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(getType);
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Profile Pictures");



        profileImageView = findViewById(R.id.profile_image);

        nameEditText = findViewById(R.id.name);
        phoneEditText = findViewById(R.id.phone_number);

        driverCarName = findViewById(R.id.driver_car_name);
        if (getType.equals("Customers"))
        {
            driverCarName.setVisibility(View.VISIBLE);
        }

        closeButton = findViewById(R.id.close_button);
        saveButton = findViewById(R.id.save_button);

        profileChangeBtn = findViewById(R.id.change_picture_btn);



        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (getType.equals("Mechanics"))
                {
                    startActivity(new Intent(SettingActivity.this, MechanicMapsActivity.class));
                }
                else
                {
                    startActivity(new Intent(SettingActivity.this, CustomerMapsActivity.class));
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (checker.equals("clicked"))
                {
                    validateControllers();
                }
                else
                {
                    validateAndSaveOnlyInformation();
                }
            }
        });

        profileChangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                checker = "clicked";

                CropImage.activity()
                        .setAspectRatio(1, 1)
                        .start(SettingActivity.this);
            }
        });

        getUserInformation();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }
        else
        {
            if (getType.equals("Mechanics"))
            {
                startActivity(new Intent(SettingActivity.this, MechanicMapsActivity.class));
            }
            else
            {
                startActivity(new Intent(SettingActivity.this, CustomerMapsActivity.class));
            }

            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();
        }
    }


    private void validateControllers()
    {
        if (TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your name.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your phone number.", Toast.LENGTH_SHORT).show();
        }
        else if (getType.equals("Customers")  &&  TextUtils.isEmpty(driverCarName.getText().toString()))
        {
            Toast.makeText(this, "Please provide your car Name.", Toast.LENGTH_SHORT).show();
        }
        else if (checker.equals("clicked"))
        {
            uploadProfilePicture();
        }
    }




    private void uploadProfilePicture()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Settings Account Information");
        progressDialog.setMessage("Please wait, while we are settings your account information");
        progressDialog.show();

        if (imageUri != null)
        {
            final StorageReference fileRef = storageProfilePicsRef
                    .child(mAuth.getCurrentUser().getUid()  +  ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception
                {
                    if (!task.isSuccessful())
                    {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();


                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task)
                {
                    if (task.isSuccessful())
                    {
                        Uri downloadUrl = task.getResult();
                        myUrl = downloadUrl.toString();


                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("uid", mAuth.getCurrentUser().getUid());
                        userMap.put("name", nameEditText.getText().toString());
                        userMap.put("phone", phoneEditText.getText().toString());
                        userMap.put("image", myUrl);

                        if (getType.equals("Customers"))
                        {
                            userMap.put("car", driverCarName.getText().toString());
                        }

                        databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);

                        progressDialog.dismiss();

                        if (getType.equals("Mechanics"))
                        {
                            startActivity(new Intent(SettingActivity.this, MechanicMapsActivity.class));
                        }
                        else
                        {
                            startActivity(new Intent(SettingActivity.this, CustomerMapsActivity.class));
                        }
                    }
                }
            });
        }
        else
        {
            Toast.makeText(this, "Image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }



    private void validateAndSaveOnlyInformation()
    {
        if (TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your name.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Please provide your phone number.", Toast.LENGTH_SHORT).show();
        }
        else if (getType.equals("Drivers")  &&  TextUtils.isEmpty(driverCarName.getText().toString()))
        {
            Toast.makeText(this, "Please provide your car Name.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            HashMap<String, Object> userMap = new HashMap<>();
            userMap.put("uid", mAuth.getCurrentUser().getUid());
            userMap.put("name", nameEditText.getText().toString());
            userMap.put("phone", phoneEditText.getText().toString());

            if (getType.equals("Customers"))
            {
                userMap.put("car", driverCarName.getText().toString());
            }

            databaseReference.child(mAuth.getCurrentUser().getUid()).updateChildren(userMap);

            if (getType.equals("Mechanics"))
            {
                startActivity(new Intent(SettingActivity.this, MechanicMapsActivity.class));
            }
            else
            {
                startActivity(new Intent(SettingActivity.this, CustomerMapsActivity.class));
            }
        }
    }


    private void getUserInformation() {
        databaseReference.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String phone = dataSnapshot.child("phone").getValue().toString();

                    nameEditText.setText(name);
                    phoneEditText.setText(phone);

                    if (getType.equals("Customers")) {
                        String car = dataSnapshot.child("car").getValue().toString();
                        driverCarName.setText(car);
                    }


                    if (dataSnapshot.hasChild("image")) {
                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(profileImageView);
                        //Picasso.get().load(image).into(profileImageView);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }}
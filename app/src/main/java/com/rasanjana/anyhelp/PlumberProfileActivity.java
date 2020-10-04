package com.rasanjana.anyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class PlumberProfileActivity extends AppCompatActivity {

    private static final String TAG = "PlumberProfileActivity";
    TextView txtName, txtProfession, txtLoction, txtPhoneNo, txtServiceCategory, txtGender, txtAvailableTime, txtQualifications, txtDescription;
    Button btnUpdate, btnCheckAppoinments, btnDelete;
    DatabaseReference dbRef;
    final static String key="";
    Plumber plumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_profile);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txtName = findViewById(R.id.TvName);
        txtProfession = findViewById(R.id.TvProfession);
        txtLoction = findViewById(R.id.TvLocation);
        txtPhoneNo = findViewById(R.id.TvPhoneNo);
        txtServiceCategory = findViewById(R.id.TvServiceCategory);
        txtGender = findViewById(R.id.TvGender);
        txtAvailableTime = findViewById(R.id.TvAvailableTime);
        txtQualifications = findViewById(R.id.TvQualification);
        txtDescription = findViewById(R.id.TvDescription);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnCheckAppoinments = findViewById(R.id.btnCheckAppoinment);
        btnDelete = findViewById(R.id.btnDelete);

        btnCheckAppoinments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlumberProfileActivity.this, MyAppointments.class);
                String id = plumber.getKey();
                Log.i(TAG, "id: "+id);
                intent.putExtra(key, id);
                startActivity(intent);
                Log.i(TAG, "key: "+key);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference deRef = FirebaseDatabase.getInstance().getReference().child("Plumber");
                deRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //  if(dataSnapshot.hasChild("-MHa9R5ak68ac1Swlgs3")){
                        dbRef = FirebaseDatabase.getInstance().getReference().child("Plumber").child("-MIOBqOAmnQhpH6E5dEM");
                        dbRef.removeValue();

                        Toast.makeText(getApplicationContext(),"Data Deleted Successfully", Toast.LENGTH_SHORT).show();


//                        }else{
//                            Toast.makeText(getApplicationContext(),"No Source to Delete", Toast.LENGTH_SHORT).show();
//                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                //move next page
                Intent myIntent = new Intent(PlumberProfileActivity.this, PlumberCareerCreate.class);
                startActivity(myIntent);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(PlumberProfileActivity.this, PlumberCareerUpdateFormActivity.class);
                String id = plumber.getKey();
                Log.i(TAG, "id: "+id);
                myIntent.putExtra(key, id);
                Log.i(TAG, "key: "+ key);
                startActivity(myIntent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Plumber").child("-MIhkMkdH1nCmTWcBV2y");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i(TAG, "onDataChange: ");

                if(dataSnapshot.hasChildren()){
                    plumber = dataSnapshot.getValue(Plumber.class);
                    plumber.setKey(dataSnapshot.getKey());
                    txtName.setText(plumber.getName().toString());
                    txtProfession.setText("Plumber");
                    txtLoction.setText(plumber.getLocation().toString());
                    txtPhoneNo.setText(plumber.getContactNo().toString());
                    txtAvailableTime.setText(plumber.getAvailableTime().toString());
                    txtQualifications.setText(plumber.getQualifications().toString());
                    txtDescription.setText(plumber.getDescription().toString());


                }else{
                    Toast.makeText(getApplicationContext(),"No source to display", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: ");
            }
        });
    }
}
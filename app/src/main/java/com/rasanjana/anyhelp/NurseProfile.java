package com.rasanjana.anyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NurseProfile extends AppCompatActivity {
    TextView location,qualifications,description, serviceCategories,gender,availableTime;
    Button UpdateBtn,CheckAppintments,DeleteButton;
    final static String key="";
    Nurse nurse;

    private static final String TAG = "NurseProfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_profile);
        location = findViewById(R.id.textView27);
        qualifications = findViewById(R.id.textView28);
        description = findViewById(R.id.textView54);
        serviceCategories = findViewById(R.id.textV);
        gender = findViewById(R.id.textV2);
        availableTime = findViewById(R.id.availableTimeN);
        UpdateBtn = findViewById(R.id.button4);
        CheckAppintments = findViewById(R.id.button5);
        DeleteButton = findViewById(R.id.button6);

        CheckAppintments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NurseProfile.this, NurseAppointmentsActivity.class);
                String id = nurse.getKey();
                Log.i(TAG, "id: " + id);
                intent.putExtra(key, id);
                startActivity(intent);
                Log.i(TAG, "key: " + key);
            }
        });
        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(NurseProfile.this, NurseProfileUpdate.class);
                String id = nurse.getKey();
                myIntent.putExtra(key, id);
                startActivity(myIntent);
            }
        });
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference deRef = FirebaseDatabase.getInstance().getReference().child("Nurse");
                deRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Nurse").child("-MImhg679NsHJYlpMnND");
                        dbRef.removeValue();
                        Toast.makeText(getApplicationContext(),"Data Deleted successfully",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
            FirebaseDatabase.getInstance().getReference().child("nurse");
            DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Nurse").child("-MIhy29l6wbUCfb6_gIf");
            readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i(TAG, "onDataChange: ");
                    if (snapshot.hasChildren()) {
                        nurse = snapshot.getValue(Nurse.class);
                        nurse.setKey(snapshot.getKey());
                        ArrayList<String> serviceCategoriesList = (ArrayList<String>) snapshot.child("serviceCategories").getValue();
                        String text = "";
                        for (String serviceCategory : serviceCategoriesList) {
                            text += serviceCategory + " ";
                        }
                        Log.i(TAG, "onDataChange: subjects = " + serviceCategoriesList);
                        serviceCategories.setText(text);
                        location.setText(snapshot.child("location").getValue().toString());
                        availableTime.setText(snapshot.child("availableTime").getValue().toString());
                        qualifications.setText(snapshot.child("qualifications").getValue().toString());
                        description.setText(snapshot.child("description").getValue().toString());
                        gender.setText(snapshot.child("gender").getValue().toString());


                    } else {
                        Toast.makeText(getApplicationContext(), "No source", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            ImageView ivBack = findViewById(R.id.ivBack);
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }

}


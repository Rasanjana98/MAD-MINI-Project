package com.rasanjana.anyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NurseProfileUpdate extends AppCompatActivity {
    EditText description,qualification;
    Spinner locationSpinner,availableTimeSpinner,genderSpinner;
    CheckBox cBabyCare,cFirstAid,cWoundDressing,cInjections,cElderlyCare,cVigilantObservation;
    Button NurseProfileUpdate;
    Nurse nurse;
    DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nurse_profile_update);

        description=findViewById(R.id.edit_text4);
        qualification=findViewById(R.id.edit_text3);
        locationSpinner=findViewById(R.id.planets_spinner4);
        availableTimeSpinner=findViewById(R.id.planets_spinner3);
        genderSpinner=findViewById(R.id.planets_spinner5);
        cBabyCare=findViewById(R.id.checkBox20);
        cFirstAid=findViewById(R.id.checkBox21);
        cWoundDressing=findViewById(R.id.checkBox17);
        cInjections=findViewById(R.id.checkBox14);
        cElderlyCare=findViewById(R.id.checkBox23);
        cVigilantObservation=findViewById(R.id.checkBox19);
        NurseProfileUpdate=findViewById(R.id.button7);

        DatabaseReference upadateRef = FirebaseDatabase.getInstance().getReference().child("Nurse").child("-MIO6nLzkkDXjRsUdP8N");
        upadateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> serviesList=(ArrayList<String>) snapshot.child("serviceCategories").getValue();
                String text="";
                for (String services:serviesList){
                    text += services+" ";
                    if (services.equals("Baby Care")){
                        cBabyCare.setChecked(true);
                    }
                    if (services.equals("Wound Dressing")){
                        cWoundDressing.setChecked(true);
                    }
                    if (services.equals("First Aid")){
                        cFirstAid.setChecked(true);
                    }
                    if (services.equals("Elderly Care")){
                        cElderlyCare.setChecked(true);
                    }
                    if (services.equals("Vigilant Observation")){
                        cVigilantObservation.setChecked(true);
                    }
                    if (services.equals("Injections")){
                        cInjections.setChecked(true);
                    }
                }
                description.setText(snapshot.child("description").getValue().toString());
                qualification.setText(snapshot.child("qualifications").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        NurseProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nurse=new Nurse();

                nurse.setDescription(description.getText().toString().trim());
                nurse.setQualifications(qualification.getText().toString().trim());

                dbref=FirebaseDatabase.getInstance().getReference().child("Nurse").child("-MIO6nLzkkDXjRsUdP8N");
                dbref.setValue(nurse);

                Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
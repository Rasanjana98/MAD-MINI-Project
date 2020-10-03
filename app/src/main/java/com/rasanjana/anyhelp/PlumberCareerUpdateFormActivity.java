package com.rasanjana.anyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PlumberCareerUpdateFormActivity extends AppCompatActivity {

    private static final String TAG = "PlumberCareerUpdateForm";

    EditText txtName, txtContactNo, txtQualifications, txtDescription;
    Spinner spLocation, spAvailableTime;
    Button btnUpdate;
    String key;
    DatabaseReference upRef;
    Plumber plumber ;

    ArrayAdapter<CharSequence> locationAdapter;
    ArrayAdapter<CharSequence> availableTimeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber_career_update_form);

        Intent intent = getIntent();
        key = intent.getStringExtra(PlumberProfileActivity.key);

        txtName = findViewById(R.id.EtName);
        txtContactNo = findViewById(R.id.EtConNo);
        txtQualifications = findViewById(R.id.EtQualification);
        txtDescription = findViewById(R.id.EtDescription);
        spLocation = findViewById(R.id.SpLocation);
        spAvailableTime = findViewById(R.id.SpAvailableTime);
        btnUpdate = findViewById(R.id.btnUpdate);

        locationAdapter = ArrayAdapter.createFromResource(PlumberCareerUpdateFormActivity.this,
                R.array.addAppointment_location_arrays, android.R.layout.simple_spinner_item);
        spLocation.setAdapter(locationAdapter);

        availableTimeAdapter = ArrayAdapter.createFromResource(PlumberCareerUpdateFormActivity.this,
                R.array.plumber_career_availableTime_arrays, android.R.layout.simple_spinner_item);
        spAvailableTime.setAdapter(availableTimeAdapter);

        upRef = FirebaseDatabase.getInstance().getReference().child("Plumber").child(key);
        upRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i(TAG, "onDataChange: ");

                Plumber plumber = dataSnapshot.getValue(Plumber.class);

                txtName.setText(plumber.getName());
                txtContactNo.setText(String.valueOf(plumber.getContactNo()));
                txtQualifications.setText(plumber.getQualifications());
                txtDescription.setText(plumber.getDescription());

                Log.i(TAG, "onDataChange: location = "+plumber.getLocation());
                int index = locationAdapter.getPosition(plumber.getLocation());
                spLocation.setSelection(index);

                int index2 = availableTimeAdapter.getPosition(plumber.getAvailableTime());
                spAvailableTime.setSelection(index2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i(TAG, "onCancelled: ");
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: ");
                plumber = new Plumber();

                plumber.setName(txtName.getText().toString().trim());
                plumber.setContactNo(Integer.parseInt(txtContactNo.getText().toString().trim()));
                plumber.setLocation(spLocation.getSelectedItem().toString().trim());
                plumber.setAvailableTime(spAvailableTime.getSelectedItem().toString().trim());
                plumber.setQualifications(txtQualifications.getText().toString().trim());
                plumber.setDescription(txtDescription.getText().toString().trim());

                upRef.setValue(plumber);

                //Feedback to the user via toast
                Toast.makeText(getApplicationContext(),"Data Updated Successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
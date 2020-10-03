package com.rasanjana.anyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AddAppointment extends AppCompatActivity {

    private static final String TAG = "AddAppointment";

    final static String Key="";
    final static String Profession="";
    String key;
    String profession;
    CalendarView calendarView;
    Spinner spLocation;
    EditText etTask;
    Button btnNext;
    DatabaseReference dbRef;
    Plumber plumber;
    Teacher teacher;
    Nurse nurse;
    Mechanic mechanic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        Intent intent = getIntent();
        key = intent.getStringExtra(CareerProfileActivity.key);
        profession = intent.getStringExtra(CareerProfileActivity.profession);

        dbRef = FirebaseDatabase.getInstance().getReference().child(profession).child(key);
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    switch (profession){
                        case "Plumber":
                            plumber = snapshot.getValue(Plumber.class);
                            if(plumber != null){
                                btnNext.setEnabled(true);
                            }
                        break;
                        case "Teacher":
                            teacher = snapshot.getValue(Teacher.class);
                            if(teacher != null){
                                btnNext.setEnabled(true);
                            }
                         break;
                        case "Nurse":
                            nurse = snapshot.getValue(Nurse.class);
                            if(nurse != null){
                                btnNext.setEnabled(true);
                            }
                         break;
                        case "Mechanic":
                            mechanic = snapshot.getValue(Mechanic.class);
                            if(mechanic != null){
                                btnNext.setEnabled(true);
                            }
                         break;
                    }

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

        calendarView = findViewById(R.id.calendarView);
        spLocation = findViewById(R.id.SpLocation);
        etTask = findViewById(R.id.etTask);
        btnNext = findViewById(R.id.btnNext);

        //final List<Appoinment> appoinments = new ArrayList<>();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           //     dbRef = FirebaseDatabase.getInstance().getReference().child(profession).child(key);
                switch (profession){
                    case "Plumber":
                        Appoinment appoinment = new Appoinment();
                        appoinment.setDate(new Date(calendarView.getDate()));
                        appoinment.setLocation(spLocation.getSelectedItem().toString());
                        appoinment.setTask(etTask.getText().toString());

                        plumber.getAppoinments().add(appoinment);
                        dbRef.setValue(plumber);

                        Toast.makeText(AddAppointment.this, "Added", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case "Teacher":
                        Appoinment appoinment1 = new Appoinment();
                        appoinment1.setDate(new Date(calendarView.getDate()));
                        appoinment1.setLocation(spLocation.getSelectedItem().toString());
                        appoinment1.setTask(etTask.getText().toString());

                        teacher.getAppoinments().add(appoinment1);
                        dbRef.setValue(teacher);

                        Toast.makeText(AddAppointment.this, "Added", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case "Nurse":
                        Appoinment appoinment2 = new Appoinment();
                        appoinment2.setDate(new Date(calendarView.getDate()));
                        appoinment2.setLocation(spLocation.getSelectedItem().toString());
                        appoinment2.setTask(etTask.getText().toString());

                        nurse.getAppoinments().add(appoinment2);
                        dbRef.setValue(nurse);

                        Toast.makeText(AddAppointment.this, "Added", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case "Mechanic":
                        Appoinment appoinment3 = new Appoinment();
                        appoinment3.setDate(new Date(calendarView.getDate()));
                        appoinment3.setLocation(spLocation.getSelectedItem().toString());
                        appoinment3.setTask(etTask.getText().toString());

                        mechanic.getAppoinments().add(appoinment3);
                        dbRef.setValue(mechanic);

                        Toast.makeText(AddAppointment.this, "Added", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }

            }
        });
    }
}
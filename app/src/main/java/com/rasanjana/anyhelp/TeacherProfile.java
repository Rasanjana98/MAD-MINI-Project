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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherProfile extends AppCompatActivity {
    Button button,AcntDltBtn,updateBtn;
    TextView location,qualifications,description, subjects,grade,availableTime;
    DatabaseReference dbRef;
    final static String key="";

    private static final String TAG = "TeacherProfile";
    Teacher teacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        button=findViewById(R.id.button5);
        updateBtn=findViewById(R.id.button4);
        AcntDltBtn=findViewById(R.id.DltAcntBtn);
        subjects=findViewById(R.id.textV);
        grade=findViewById(R.id.textV2);
        location=findViewById(R.id.textView48);
       availableTime=findViewById(R.id.availableTimeN);
        qualifications=findViewById(R.id.textView28);
        description=findViewById(R.id.textView51);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TeacherProfile.this, TeacherAppoinmentsActivity.class);
                String id = teacher.getKey();
                Log.i(TAG, "id: "+id);
                intent.putExtra(key, id);
                startActivity(intent);
                Log.i(TAG, "key: "+key);
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(TeacherProfile.this, TeacherProfileUpdate.class);
                String id = teacher.getKey();
                Log.i(TAG, "id: "+id);
                myIntent.putExtra(key, id);
                Log.i(TAG, "key: "+ key);
                startActivity(myIntent);
            }
        });
        AcntDltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference deRef = FirebaseDatabase.getInstance().getReference().child("Teacher");
                deRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dbRef = FirebaseDatabase.getInstance().getReference().child("Teacher").child(key);
                        dbRef.removeValue();
                        Toast.makeText(getApplicationContext(),"Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                //move next page
                Intent myIntent = new Intent(TeacherProfile.this, TeacherCareerCreate.class);
                startActivity(myIntent);
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
    @Override
    protected void onResume() {
        super.onResume();
        FirebaseDatabase.getInstance().getReference().child("teacher");
        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Teacher").child("-MInfJLwrpCgn04Eg-nr");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i(TAG, "onDataChange: ");
                if (snapshot.hasChildren()) {
                    teacher = snapshot.getValue(Teacher.class);

                    teacher.setKey(snapshot.getKey());
                    ArrayList<String> subjectList = (ArrayList<String>) snapshot.child("subjects").getValue();
                    String text = "";
                    for (String subject : subjectList) {
                        text += subject + " ";
                    }
                    ArrayList<String> gradeList = (ArrayList<String>) snapshot.child("grades").getValue();
                    String text1 = "";
                    for (String grade : gradeList) {
                        text1 += grade + " ";
                    }
                    Log.i(TAG, "onDataChange: subjects = " + subjectList);
                    subjects.setText(text);
                    Log.i(TAG, "OnDataChange: grades =" + gradeList);
                    grade.setText(text1);
                    location.setText(snapshot.child("location").getValue().toString());
                    availableTime.setText(snapshot.child("time").getValue().toString());
                    qualifications.setText(snapshot.child("qualifications").getValue().toString());
                    description.setText(snapshot.child("description").getValue().toString());

                } else {
                    Toast.makeText(getApplicationContext(), "No source", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG, "onCancelled: ");
            }
        });


    }
}
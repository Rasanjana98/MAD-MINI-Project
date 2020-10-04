package com.rasanjana.anyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
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
import java.util.List;

public class TeacherProfileUpdate extends AppCompatActivity {
    private  static String TAG ="TeacherProfileUpdate";
    EditText description,qualifications;
    Spinner locationSpinner,availableTimeSpinner;
    CheckBox cbSinhala,cbEnglish,cbScience,cbPhysics,cbTamil,cbBiology,cbMathematics,cbChemistry,cbAccounting,cbEconomics,cbPrimary,cbSixToOl,cbAL;
    Button TeacherProfileUpdate;
    Teacher teacher;

    DatabaseReference updateRef;
    String key;

    ArrayAdapter<CharSequence> locationAdapter;
    ArrayAdapter<CharSequence> availableTimeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile_update);

        Intent intent =getIntent();
        key=intent.getStringExtra(TeacherProfile.key);

        description=findViewById(R.id.edit_text2);
        qualifications=findViewById(R.id.edit_text);
        locationSpinner=findViewById(R.id.planets_spinner);
        availableTimeSpinner=findViewById(R.id.planets_spinner2);
        TeacherProfileUpdate=findViewById(R.id.TeacherUpdateBtn);
        cbEnglish=findViewById(R.id.c2);
        cbSinhala=findViewById(R.id.c1);
        cbScience=findViewById(R.id.c3);
        cbPhysics=findViewById(R.id.c4);
        cbTamil=findViewById(R.id.c5);
        cbBiology=findViewById(R.id.c6);
        cbMathematics=findViewById(R.id.c7);
        cbChemistry=findViewById(R.id.c8);
        cbAccounting=findViewById(R.id.c9);
        cbEconomics=findViewById(R.id.c10);
        cbPrimary=findViewById(R.id.checkBox9);
        cbSixToOl=findViewById(R.id.checkBox10);
        cbAL=findViewById(R.id.checkBox11);

        locationAdapter=ArrayAdapter.createFromResource(TeacherProfileUpdate.this,R.array.Location_arrays,android.R.layout.simple_spinner_item);
        locationSpinner.setAdapter(locationAdapter);

        availableTimeAdapter=ArrayAdapter.createFromResource(TeacherProfileUpdate.this,R.array.Time_arrays,android.R.layout.simple_spinner_item);
        availableTimeSpinner.setAdapter(availableTimeAdapter);
         updateRef = FirebaseDatabase.getInstance().getReference().child("Teacher").child(key);
        updateRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> subjectList=(ArrayList<String>) snapshot.child("subjects").getValue();
                String text ="";

                ArrayList<String> gradeList=(ArrayList<String>) snapshot.child("grades").getValue();
                String text2="";

                for (String grade:gradeList){
                    text2 += grade+" ";
                    if (grade.equals("Primary")){
                        cbPrimary.setChecked(true);
                    }
                    if (grade.equals("Six-OL")){
                        cbSixToOl.setChecked(true);
                    }
                    if (grade.equals("A/L")){
                        cbAL.setChecked(true);
                    }
                }
                for (String subject :subjectList){
                    text += subject+" ";
                    if (subject.equals("Sinhala")){
                        cbSinhala.setChecked(true);
                    }
                     if (subject.equals("English")){
                        cbEnglish.setChecked(true);
                    }
                    if (subject.equals("Science")){
                        cbScience.setChecked(true);
                    }
                    if (subject.equals("Physics")){
                        cbPhysics.setChecked(true);
                    }
                    if (subject.equals("Tamil")){
                        cbTamil.setChecked(true);
                    }
                    if (subject.equals("Biology")){
                        cbBiology.setChecked(true);
                    }
                    if (subject.equals("Mathematics")){
                        cbMathematics.setChecked(true);
                    }
                    if (subject.equals("Chemistry")){
                        cbChemistry.setChecked(true);
                    }
                    if (subject.equals("Accounting")){
                        cbAccounting.setChecked(true);
                    }
                    if (subject.equals("Economics")){
                        cbEconomics.setChecked(true);
                    }

                }
                description.setText(snapshot.child("description").getValue().toString());
                qualifications.setText(snapshot.child("qualifications").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i(TAG,"onCancelled");
            }
        });
        TeacherProfileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG,"onClick");
                teacher=new Teacher();

                List<String> subjects =new ArrayList<>();


                if(cbSinhala.isChecked()){
                    subjects.add("Sinhala");
                }
                if(cbEnglish.isChecked()){
                    subjects.add("English");
                }
                if(cbTamil.isChecked()){
                    subjects.add("Tamil");
                }
                if(cbScience.isChecked()){
                    subjects.add("Science");
                }
                if(cbPhysics.isChecked()){
                    subjects.add("Physics");
                }
                if(cbBiology.isChecked()){
                    subjects.add("Biology");
                }
                if(cbMathematics.isChecked()){
                    subjects.add("Mathematics");
                }
                if(cbChemistry.isChecked()){
                    subjects.add("Chemistry");
                }
                if(cbAccounting.isChecked()){
                    subjects.add("Accounting");
                }
                if(cbEconomics.isChecked()){
                    subjects.add("Economics");
                }

                List<String> grades = new ArrayList<>();

                if(cbPrimary.isChecked()){
                    grades.add("Primary");
                }
                if(cbSixToOl.isChecked()){
                    grades.add("Six-OL");
                }
                if(cbAL.isChecked()){
                    grades.add("A/L");
                }


                teacher.setDescription(description.getText().toString().trim());
                teacher.setQualifications(qualifications.getText().toString().trim());
                teacher.setLocation(locationSpinner.getSelectedItem().toString().trim());
                teacher.setTime(availableTimeSpinner.getSelectedItem().toString().trim());

                updateRef.setValue(teacher);
                Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_SHORT).show();

            }
        });
    }
}
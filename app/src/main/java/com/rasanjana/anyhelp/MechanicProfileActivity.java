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
import java.util.List;

public class MechanicProfileActivity extends AppCompatActivity {
    private static final String TAG = "MechanicProfileActivity";
    final static String key ="";
    private Button button1, button2, button3;
    private ImageView imageArrow, imageProfile;
    TextView textViewNOut, textViewProfOut, textViewLocOut, textViewPhoneOut, textViewFieldsOut, textViewTimeOut, textViewQualiOut, textViewDescOut;
    DatabaseReference dbRef;
    Mechanic mech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanic_profile);

//         textViewNOut = findViewById(R.id.textViewNOut);
        textViewProfOut = findViewById(R.id.textViewProfOut);
        textViewLocOut =  findViewById(R.id.textViewLocOut);
//         textViewPhoneOut = findViewById(R.id.textViewPhoneOut);
        textViewFieldsOut = findViewById(R.id.textViewFieldsOut);
        textViewTimeOut =  findViewById(R.id.textViewTimeOut);
        textViewQualiOut = findViewById(R.id.textViewQualiOut);
        textViewDescOut = findViewById(R.id.textViewDescOut);

        button1 = (Button) findViewById(R.id.buttonUpdate);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMechanicUpdate();
                Toast.makeText(getApplicationContext(), "Redirecting to update page...",
                        Toast.LENGTH_LONG).show();
            }
        });

        button2 = (Button) findViewById(R.id.buttonCheck);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAppointments();
                Toast.makeText(getApplicationContext(), "Redirecting to Appointment page... ",
                        Toast.LENGTH_LONG).show();
            }
        });

        button3 = (Button) findViewById(R.id.btnDelete);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToRegistration();
                DatabaseReference showRef = FirebaseDatabase.getInstance().getReference().child("Mechanic");
                showRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("-MIoHaH1FaGsPLdhy_t4")) {
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Mechanic").child("--MIoGg-FF-dplTzEuy5Q");
                            dbRef.removeValue();

                            Toast.makeText(getApplicationContext(), "Data Successfully Deleted", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(), "No Source to Delete", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        dbRef = FirebaseDatabase.getInstance().getReference().child("Mechanic").child("-MIoHaH1FaGsPLdhy_t4");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChildren()) {
                    mech = dataSnapshot.getValue(Mechanic.class);
                    mech.setKey(dataSnapshot.getKey());

//                    Add name and profession
//mechanic

//                    textViewNOut.setText(mech.getName().toString());

                    textViewProfOut.setText("Mechanic");
                    textViewLocOut.setText(mech.getLocation());

//                    Add Phone Number
//                    textViewPhoneOut.setText(mech.getContactNo().toString());

                    List<String> fieldsList = mech.getFields();
                    if(fieldsList!=null){
                        String text = "";
                        for (String textViewFieldsOut : fieldsList ) {
                            text += textViewFieldsOut + " ";
                        }
                        textViewFieldsOut.setText(text);
                    }

                    textViewTimeOut.setText(dataSnapshot.child("time").getValue().toString());
                    textViewQualiOut.setText(dataSnapshot.child("qualifications").getValue().toString());
                    textViewDescOut.setText(dataSnapshot.child("description").getValue().toString());

                }else {
                    Toast.makeText(getApplicationContext(), "Details Not Displayed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageArrow = (ImageView) findViewById(R.id.ivBack);
        imageArrow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                goBack();
            }
        });
    }

    private void openMechanicUpdate() {
        Intent intent1 = new Intent(this, MechanicEditProfile.class);
        String id = mech.getKey();
        intent1.putExtra(key, id);
        startActivity(intent1);
    }
    private void openAppointments() {
        Intent intent2 = new Intent(this, MechanicAppointment.class);
        String id = mech.getKey();
        intent2.putExtra(key, id);
        startActivity(intent2);
    }
    private void goBack() {
        Intent intent3 = new Intent(this, MechanicRegistration.class); //Have to put a dynamic onClick
        startActivity(intent3);
    }
    private void navigateToRegistration() {
        Intent intent = new Intent(this, MechanicRegistration.class);
        startActivity(intent);
    }
}
package com.rasanjana.anyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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

import javax.security.auth.login.LoginException;

public class CareerProfileActivity extends AppCompatActivity {

    private static final String TAG = "CareerProfileActivity";
    private String key;
    private String profession;
    String phone;


    TextView txtTitle, txtName, txtProfession, txtLocation, txtDescription;
    Button btnCall, btnAddAppoinment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_profile_activity);

        txtTitle = findViewById(R.id.txt_title);
        txtName = findViewById(R.id.Tvname);
        txtProfession = findViewById(R.id.TvProfession);
        txtLocation = findViewById(R.id.TvLocation);
        txtDescription =findViewById(R.id.TvDescription);

        btnCall = findViewById(R.id.btnCall);
        btnAddAppoinment = findViewById(R.id.btnAddAppoinment);

        Intent intent = getIntent();
        key = intent.getStringExtra(UserMenuActivity.key);
        profession = intent.getStringExtra(UserMenuActivity.profession);

        Log.i(TAG, "key: "+key);
        Log.i(TAG, "profession: "+profession);


        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child(profession).child(key);
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    phone = snapshot.child("contactNo").getValue().toString();
//                    txtTitle.setText(snapshot.child("name").getValue().toString());
 //                   txtName.setText(snapshot.child("name").getValue().toString());
                    txtProfession.setText(profession);
                    txtLocation.setText(snapshot.child("location").getValue().toString());
                    txtDescription.setText(snapshot.child("description").getValue().toString());
                }else{
                    Toast.makeText(getApplicationContext(),"No source to display", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.i(TAG, "btnCall: "+phone);
                String s = "tel:" + phone;
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(s));
                startActivity(intent);
            }
        });

        btnAddAppoinment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CareerProfileActivity.this, AddAppointment.class);
                startActivity(intent);

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
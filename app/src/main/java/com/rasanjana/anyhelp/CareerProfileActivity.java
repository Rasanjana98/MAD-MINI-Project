package com.rasanjana.anyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

public class CareerProfileActivity extends AppCompatActivity {

    private List<Appoinment> appoinments;
    private static final String TAG = "CareerProfileActivity";
    public static String key;
    public static String profession;
    String phone;


    TextView txtTitle, txtName, txtProfession, txtLocation, txtDescription;
    Button btnCall, btnAddAppoinment;
    TableLayout tableLayout;

    //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_career_profile_activity);

        appoinments = new ArrayList<>();

        txtTitle = findViewById(R.id.txt_title);
        txtName = findViewById(R.id.Tvname);
        txtProfession = findViewById(R.id.TvProfession);
        txtLocation = findViewById(R.id.TvLocation);
        txtDescription =findViewById(R.id.TvDescription);

        btnCall = findViewById(R.id.btnCall);
        btnAddAppoinment = findViewById(R.id.btnAddAppoinment);

        Intent intent = getIntent();
        key = intent.getStringExtra(AddAppointment.Key);
        key = intent.getStringExtra(UserMenuActivity.key);
        profession = intent.getStringExtra(AddAppointment.Profession);
        profession = intent.getStringExtra(UserMenuActivity.profession);

        Log.i(TAG, "key: "+key);
        Log.i(TAG, "profession: "+profession);

        tableLayout = findViewById(R.id.appointmentTable);
        tableLayout.setStretchAllColumns(true);
        tableLayout.bringToFront();

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
                intent.putExtra(key, key);
                intent.putExtra(profession, profession);
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

    private void showAppointments(final List<Appoinment> appoinments){
        tableLayout.removeAllViews();
        TableRow header =  new TableRow(this);
        TextView h1 = new TextView(this);
        h1.setText("Date");
        TextView h2 = new TextView(this);
        h2.setText("Location");
        TextView h3 = new TextView(this);
        h3.setText("Task");

        header.addView(h1);
        header.addView(h2);
        header.addView(h3);

        h1.setBackgroundColor(Color.LTGRAY);
        h2.setBackgroundColor(Color.LTGRAY);
        h3.setBackgroundColor(Color.LTGRAY);

        h1.setPadding(20, 20 , 20, 20);
        h2.setPadding(20, 20 , 20, 20);
        h3.setPadding(20, 20 , 20, 20);

        tableLayout.addView(header);

        for (int i=0; i < appoinments.size(); i++) {
            Appoinment appoinment = appoinments.get(i);
            Log.i(TAG, "shoeAppointments: appointment = "+appoinment);

            TableRow tr =  new TableRow(CareerProfileActivity.this);
            TextView c1 = new TextView(CareerProfileActivity.this);
            c1.setText(formatter.format(appoinment.getDate()));
            TextView c2 = new TextView(CareerProfileActivity.this);
            c2.setText(appoinment.getLocation());
            TextView c3 = new TextView(CareerProfileActivity.this);
            c3.setText(appoinment.getTask());
//            ImageView c4 = new ImageView(this);
//            c4.setImageResource(R.drawable.close);
//            c4.setTag(i);
//            c4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Log.i(TAG, "onClick: delete click index = "+view.getTag());
//                    int index = (int) view.getTag();
//                    appoinments.remove(index);
//                    //plumber.save
//
//                }
//            });

            tr.addView(c1);
            tr.addView(c2);
            tr.addView(c3);
           // tr.addView(c4);

            c1.setPadding(20, 20 , 20, 20);
            c2.setPadding(20, 20 , 20, 20);
            c3.setPadding(20, 20 , 20, 20);
          //  c4.setPadding(20, 20 , 20, 20);

            if (i % 2 == 1) {
                c1.setBackgroundColor(Color.LTGRAY);
                c2.setBackgroundColor(Color.LTGRAY);
                c3.setBackgroundColor(Color.LTGRAY);
             //   c4.setBackgroundColor(Color.LTGRAY);
            }

            tableLayout.addView(tr);
            tableLayout.requestLayout();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume: ");
        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child(profession).child(key);
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    //phone = snapshot.child("contactNo").getValue().toString();
                    //txtTitle.setText(snapshot.child("name").getValue().toString());
                   // txtName.setText(snapshot.child("name").getValue().toString());
                    txtProfession.setText(profession);
                    txtLocation.setText(snapshot.child("location").getValue().toString());
                    txtDescription.setText(snapshot.child("description").getValue().toString());

                    switch (profession){
                        case "Plumber":
                            Plumber plumber = snapshot.getValue(Plumber.class);
                            if(plumber!=null) {
                                showAppointments(plumber.getAppoinments());
                            }
                            break;
                        case "Teacher":
                            Teacher teacher = snapshot.getValue(Teacher.class);
                            if(teacher!=null) {
                                showAppointments(teacher.getAppoinments());
                            }
                            break;
                        case "Nurse":
                            Nurse nurse = snapshot.getValue(Nurse.class);
                            if(nurse!=null) {
                                showAppointments(nurse.getAppoinments());
                            }
                            break;
                        case "Mechanic":
                            Mechanic mechanic = snapshot.getValue(Mechanic.class);
                            if(mechanic!=null) {
                                showAppointments(mechanic.getAppoinments());
                            }
                            break;
                    }


                }else{
                    Toast.makeText(getApplicationContext(),"No source to display", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.rasanjana.anyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.List;

public class MechanicAppointment extends AppCompatActivity {

        private static final String TAG = "MechanicAppointment";
        String key;
        TableLayout tableLayout;
        Mechanic mechanic;
        DatabaseReference dbRef;

        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                Log.i(TAG, "onCreate: ");
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_mechanic_appointment);

                Intent intent = getIntent();
                key = intent.getStringExtra(MechanicProfileActivity.key);
                Log.i(TAG, "onCreate: key " + key);

                tableLayout = findViewById(R.id.appointmentTable);
                tableLayout.setStretchAllColumns(true);
                tableLayout.bringToFront();

                if(key != null) {
                        dbRef = FirebaseDatabase.getInstance().getReference().child("Mechanic").child(key);
                        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Log.i(TAG, "onDataChange: ");
                                        if (snapshot.hasChildren()) {
                                                mechanic = snapshot.getValue(Mechanic.class);
                                                if (mechanic != null) {
                                                        Log.i(TAG, "onDataChange: has mechanic++");
                                                        showAppointments(mechanic.getAppoinments());

                                                }
                                        }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                        });
                }
        }
        private void showAppointments(final List<Appoinment> appoinments){
                Log.i(TAG, "showAppointments: ");
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

                        TableRow tr =  new TableRow(MechanicAppointment.this);
                        TextView c1 = new TextView(MechanicAppointment.this);
                        c1.setText(formatter.format(appoinment.getDate()));
                        TextView c2 = new TextView(MechanicAppointment.this);
                        c2.setText(appoinment.getLocation());
                        TextView c3 = new TextView(MechanicAppointment.this);
                        c3.setText(appoinment.getTask());
                        ImageView c4 = new ImageView(this);
                        c4.setImageResource(R.drawable.close);
                        c4.setTag(i);
                        c4.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                        Log.i(TAG, "onClick: delete click index = "+view.getTag());
                                        int index = (int) view.getTag();
                                        appoinments.remove(index);
                                        mechanic.setAppoinments(appoinments);
                                        dbRef.setValue(mechanic);

                                        tableLayout.removeViewAt(index+1);

                                }
                        });

                        tr.addView(c1);
                        tr.addView(c2);
                        tr.addView(c3);
                        tr.addView(c4);

                        c1.setPadding(20, 20 , 20, 20);
                        c2.setPadding(20, 20 , 20, 20);
                        c3.setPadding(20, 20 , 20, 20);
                        c4.setPadding(20, 20 , 20, 20);

                        tableLayout.addView(tr);
                        tableLayout.requestLayout();
                }
        }
}
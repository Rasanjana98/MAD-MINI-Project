package com.rasanjana.anyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserMenuActivity extends AppCompatActivity {

    private static final String TAG = "UserMenuActivity";

    TextView txtTitle;
    DatabaseReference dbRef;
    private List<Plumber> plumbers;
    private List<Teacher> teachers;
    private List<Mechanic> mechanics;
    private List<Nurse> nurses;
    public static String profession = "";
    public static String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        txtTitle = findViewById(R.id.txt_title);

        plumbers = new ArrayList<>();
        teachers = new ArrayList<>();
        mechanics = new ArrayList<>();
        nurses = new ArrayList<>();

//get buttons from appHome
        Intent intent = getIntent();
      profession = intent.getStringExtra(AppHomePage.profession);

            Log.i(TAG, "profession: "+ profession);

        final PlumberUserMenuAdapter plumberAdapter = new PlumberUserMenuAdapter(this);
        plumberAdapter.setPlumbers(plumbers);
        plumberAdapter.setOnItemClickListener(new PlumberUserMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.i(TAG, "onItemClicked: position = "+position);
                Plumber plumber = plumbers.get(position);
                plumber.setProfession(profession);
                Log.i(TAG, "onItemClicked: plumber key = "+plumber.getKey());

                Intent intent =  new Intent(UserMenuActivity.this, CareerProfileActivity.class);
                String Key = plumber.getKey();
                intent.putExtra(key, Key);
                intent.putExtra(profession, profession);
                startActivity(intent);
                Log.i(TAG, "key: "+Key);
                Log.i(TAG, "profession: "+profession);
            }
        });
        final TeacherUserMenuAdapter teacherAdapter = new TeacherUserMenuAdapter(this);
        teacherAdapter.setTeachers(teachers);
        teacherAdapter.setOnItemClickListener(new TeacherUserMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.i(TAG, "onItemClicked: position"+position);
                Teacher teacher = teachers.get(position);
                teacher.setProfession(profession);
                Log.i(TAG, "onItemClicked: plumber key = "+teacher.getKey());

                Intent intent =  new Intent(UserMenuActivity.this, CareerProfileActivity.class);
                String Key = teacher.getKey();
                intent.putExtra(key, Key);
                intent.putExtra(profession, profession);
                startActivity(intent);
                Log.i(TAG, "key: "+Key);
                Log.i(TAG, "profession: "+profession);

            }
        });
        final MechanicUserMenuAdapter mechanicAdapter = new MechanicUserMenuAdapter(this);
        mechanicAdapter.setMechanics(mechanics);
        mechanicAdapter.setOnItemClickListener(new MechanicUserMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.i(TAG, "onItemClicked: position"+position);
                Mechanic mechanic = mechanics.get(position);
                mechanic.setProfession(profession);
                Log.i(TAG, "onItemClicked: plumber key = "+mechanic.getKey());

                Intent intent =  new Intent(UserMenuActivity.this, CareerProfileActivity.class);
                String Key = mechanic.getKey();
                intent.putExtra(key, Key);
                intent.putExtra(profession, profession);
                startActivity(intent);
                Log.i(TAG, "key: "+Key);
                Log.i(TAG, "profession: "+profession);
            }
        });
        final NurseUserMenuAdapter nurseAdapter = new NurseUserMenuAdapter(this);
        nurseAdapter.setNurses(nurses);
        nurseAdapter.setOnItemClickListener(new NurseUserMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.i(TAG, "onItemClicked: position"+position);
                Nurse nurse = nurses.get(position);
                nurse.setProfession(profession);
                Log.i(TAG, "onItemClicked: plumber key = "+nurse.getKey());

                Intent intent =  new Intent(UserMenuActivity.this, CareerProfileActivity.class);
                String Key = nurse.getKey();
                intent.putExtra(key, Key);
                intent.putExtra(profession, profession);
                startActivity(intent);
                Log.i(TAG, "key: "+Key);
                Log.i(TAG, "profession: "+profession);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(UserMenuActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);

        switch (profession){
            case "Teacher":
                recyclerView.setAdapter(teacherAdapter);
                txtTitle.setText(profession);
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Teacher");
                readRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.i(TAG ,"Count = " + dataSnapshot.getChildrenCount());
                        teachers.clear();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Log.i(TAG, "onDataChange: key = "+postSnapshot.getKey());
                            Teacher teacher = postSnapshot.getValue(Teacher.class);
                            teacher.setKey(postSnapshot.getKey());
                            teachers.add(teacher);
                        }

                        teacherAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

            case "Nurse":
                recyclerView.setAdapter(nurseAdapter);
                txtTitle.setText(profession);
                DatabaseReference readRef2 = FirebaseDatabase.getInstance().getReference().child("Nurse");
                readRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.i(TAG ,"Count = " + dataSnapshot.getChildrenCount());
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Log.i(TAG, "onDataChange: key = "+postSnapshot.getKey());
                            Nurse nurse = postSnapshot.getValue(Nurse.class);
                            nurse.setKey(postSnapshot.getKey());
                            nurses.add(nurse);
                        }

                        nurseAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;

            case "Mechanic":
                recyclerView.setAdapter(mechanicAdapter);
            txtTitle.setText(profession);
                DatabaseReference readRef3 = FirebaseDatabase.getInstance().getReference().child("Mechanic");
                readRef3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.i(TAG ,"Count = " + dataSnapshot.getChildrenCount());
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Log.i(TAG, "onDataChange: key = "+postSnapshot.getKey());
                            Mechanic mechanic = postSnapshot.getValue(Mechanic.class);
                            mechanics.add(mechanic);
                        }

                        mechanicAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
            case "Plumber":
                recyclerView.setAdapter(plumberAdapter);
                txtTitle.setText(profession);
                DatabaseReference readRef4 = FirebaseDatabase.getInstance().getReference().child("Plumber");
                readRef4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.i(TAG ,"Count = " + dataSnapshot.getChildrenCount());
                        plumbers.clear();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            Log.i(TAG, "onDataChange: key = "+postSnapshot.getKey());
                            Plumber plumber = postSnapshot.getValue(Plumber.class);
                            plumber.setKey(postSnapshot.getKey());
                            plumbers.add(plumber);
                        }

                        plumberAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                break;
        }
    }
}
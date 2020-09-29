package com.rasanjana.anyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserMenuActivity extends AppCompatActivity {

    private static final String TAG = "UserMenuActivity";

    DatabaseReference dbRef;
    private List<Plumber> plumbers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

       /* ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/

        plumbers = new ArrayList<>();
        /*Plumber plumber1 = new Plumber();
        plumber1.setName("Hashan");
        plumber1.setLocation("Gampaha");
        plumber1.setAvailableTime("Day");

        Plumber plumber2 = new Plumber();
        plumber2.setName("Shashika");
        plumber2.setLocation("Colombo");
        plumber2.setAvailableTime("Night");

        Plumber plumber3 = new Plumber();
        plumber3.setName("iii");
        plumber3.setLocation("Kaluthara");
        plumber3.setAvailableTime("Day/night");

        plumbers.add(plumber1);
        plumbers.add(plumber2);
        plumbers.add(plumber3);*/

        final PlumberUserMenuAdapter adapter = new PlumberUserMenuAdapter(this);
        adapter.setPlumbers(plumbers);
        adapter.setOnItemClickListener(new PlumberUserMenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.i(TAG, "onItemClicked: position = "+position);
            }
        });

        RecyclerView rvPlumbers = findViewById(R.id.rvPlumbers);
        rvPlumbers.setAdapter(adapter);
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(UserMenuActivity.this, LinearLayoutManager.VERTICAL, false);
        rvPlumbers.setLayoutManager(horizontalLayoutManager);

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Plumber");
        readRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i(TAG ,"Count = " + dataSnapshot.getChildrenCount());
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Log.i(TAG, "onDataChange: key = "+postSnapshot.getKey());
                    Plumber plumber = postSnapshot.getValue(Plumber.class);
                    plumbers.add(plumber);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
package com.rasanjana.anyhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ChooseRegisterType extends AppCompatActivity {
    Button button,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_register_type);
        button=findViewById(R.id.customerBtn);
        button2=findViewById(R.id.button4);

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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent myIntent =new Intent(ChooseRegisterType.this,UserRegister.class);
                startActivity(myIntent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent =new Intent(ChooseRegisterType.this,ServiceProviderRegister.class);
                startActivity(myIntent);
            }
        });
    }
}
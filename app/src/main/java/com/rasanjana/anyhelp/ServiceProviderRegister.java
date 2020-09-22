package com.rasanjana.anyhelp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ServiceProviderRegister extends AppCompatActivity {
    EditText fName,lName,email,pNumber,password,cPassword;
    Button button;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_register);

        fName=findViewById(R.id.firstName);
        lName=findViewById(R.id.lastName);
        email=findViewById(R.id.Email);
        pNumber=findViewById(R.id.pNumber);
        password=findViewById(R.id.password);
        cPassword=findViewById(R.id.cPassowrdS);
        button=findViewById(R.id.registerBtn2);
        spinner=findViewById(R.id.profSpinner);





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
        String [] professions =new String[]{"Teacher","Plumber","Visiting Nurse","Mechanic"};
        ArrayAdapter<String> adapter =new ArrayAdapter< >( ServiceProviderRegister.this, android.R.layout.simple_spinner_dropdown_item,professions);
        spinner.setAdapter(adapter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent= new Intent(ServiceProviderRegister.this,TeacherCareerCreate.class);
                startActivity(myIntent);
            }
        });
    }
}
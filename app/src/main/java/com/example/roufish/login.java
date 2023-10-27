package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FloatingActionButton backToMain = findViewById(R.id.backToMain);

        EditText password = (EditText) findViewById(R.id.input_Password);
        CheckBox checkPassword  = (CheckBox) findViewById(R.id.tampilkanPassword);

        checkPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else{
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(login.this, MainActivity.class);

                startActivity(registerIntent);

            }
        });
    }
}
package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FloatingActionButton backToMain = findViewById(R.id.backToMain);
        EditText editTextUsername = findViewById(R.id.input_Username);
        EditText editTextpassword =  findViewById(R.id.input_Password);
        CheckBox checkPassword  = (CheckBox) findViewById(R.id.tampilkanPassword);
        AppCompatButton login = findViewById(R.id.btn_login);

        checkPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    editTextpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else{
                    editTextpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
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

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username,password = null;

                username = String.valueOf(editTextUsername.getText());
                password = String.valueOf(editTextpassword.getText());


                if (TextUtils.isEmpty(username)){
                    Toast.makeText(login.this,"Masukkan username",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(login.this,"Masukkan password",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent registerIntent = new Intent(login.this, product.class);

                startActivity(registerIntent);
            }
        });
    }
}
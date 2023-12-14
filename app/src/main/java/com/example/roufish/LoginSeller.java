package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class LoginSeller extends AppCompatActivity {

    FloatingActionButton btnBack;
    CheckBox tampilPassword;

    EditText inputPasswordSeller, inputUsernameSeller;

    AppCompatButton loginSeller;



    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_seller);

        btnBack = findViewById(R.id.tmbl_kembaliSeller);
        tampilPassword = findViewById(R.id.tampilkanPasswordSeller);
        inputUsernameSeller = findViewById(R.id.inputSellerId);
        inputPasswordSeller= findViewById(R.id.input_PasswordSeller);
        loginSeller = findViewById(R.id.btn_loginSeller);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent btnBackIntent = new Intent(LoginSeller.this, LoginPage.class);
                startActivity(btnBackIntent);
            }
        });

        tampilPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (!isChecked) {
                    inputPasswordSeller.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    inputPasswordSeller.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        loginSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = null;
                String password = null;
            }
        });
















    }
}
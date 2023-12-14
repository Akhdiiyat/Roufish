package com.example.roufish;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.roufish.activities.*;

public class Homepage extends Activity {
    Button buttonLogin, buttonRegister;
    TextView loginSeller;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin =findViewById(R.id.buttonLogin);
        loginSeller = findViewById(R.id.login_seller);


        loginSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sellerIntent = new Intent(MainActivity.this, login_seller.class);
                startActivity(sellerIntent);
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Homepage.this, RegisterBuyer.class);
                startActivity(registerIntent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(Homepage.this, login.class);
                startActivity(loginIntent);
            }
        });


    }
}


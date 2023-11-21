package com.example.roufish;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.roufish.activities.login;
import com.example.roufish.forgetPassword.lupapwd;

public class MainActivity extends Activity {
    Button buttonLogin, buttonRegister;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogin =findViewById(R.id.buttonLogin);
        TextView lupaPassword = findViewById(R.id.lupaPassword);
        lupaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lupaPassIntent = new Intent(MainActivity.this, lupapwd.class);
                startActivity(lupaPassIntent);
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, register.class);
                startActivity(registerIntent);
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(MainActivity.this, login.class);
                startActivity(loginIntent);
            }
        });

    }
}


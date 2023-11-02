package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class lupapwd extends AppCompatActivity {

    Button buttonRegister, buttonSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapwd);

        buttonRegister = findViewById(R.id.btn_register_pwd);
        buttonSend = findViewById(R.id.btn_send_code);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(lupapwd.this, register.class); // Adjust to your Register activity class name
                startActivity(registerIntent);
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(lupapwd.this, lupapwd_code.class);
                startActivity(sendIntent);
            }
        });
    }
}

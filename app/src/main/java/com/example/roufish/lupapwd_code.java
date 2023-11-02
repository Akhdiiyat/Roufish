package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class lupapwd_code extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapwd_code);

        FloatingActionButton backToMain = findViewById(R.id.backToMainREG); // Ensure the ID is correct
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lupaPwIntent = new Intent(lupapwd_code.this, lupapwd.class);
                startActivity(lupaPwIntent);
            }
        });
    }
}

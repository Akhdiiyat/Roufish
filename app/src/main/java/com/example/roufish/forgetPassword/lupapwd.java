package com.example.roufish.forgetPassword;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roufish.MainActivity;
import com.example.roufish.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class lupapwd extends AppCompatActivity {

    Button buttonSend;
    EditText emailInput ;;
    //TextInputEditText emailTextInput = findViewById(R.id.emailTextInput);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapwd);
        emailInput = findViewById(R.id.emailInput);
        //emailTextInput = findViewById(R.id.emailTextInput);
        FloatingActionButton backToMain = findViewById(R.id.backToMainREG);
        buttonSend = findViewById(R.id.btn_send_code);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lupaPwIntent = new Intent(lupapwd.this, MainActivity.class);
                startActivity(lupaPwIntent);
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email ;
                email = String.valueOf(emailInput.getText());
                if (TextUtils.isEmpty(email)) {
                    // Email field is empty, show an error message
                    Toast.makeText(lupapwd.this,"Masukkan username",Toast.LENGTH_SHORT).show();
                    return;
                }

                    // Proceed with sending the code
                    Intent sendIntent = new Intent(lupapwd.this, lupapwd_code.class);
                    startActivity(sendIntent);

            }
        });
    }
}

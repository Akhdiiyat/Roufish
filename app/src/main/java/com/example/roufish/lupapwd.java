package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class lupapwd extends AppCompatActivity {

    Button buttonSend;
    TextInputLayout emailInputLayout = findViewById(R.id.emailInputLayout);;
    TextInputEditText emailTextInput = findViewById(R.id.emailTextInput);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapwd);

        buttonSend = findViewById(R.id.btn_send_code);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTextInput.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    // Email field is empty, show an error message
                    emailInputLayout.setError("Email is required");
                } else {
                    // Email field is not empty, remove any previous error message
                    emailInputLayout.setError(null);

                    // Proceed with sending the code
                    Intent sendIntent = new Intent(lupapwd.this, lupapwd_code.class);
                    startActivity(sendIntent);
                }
            }
        });
    }
}

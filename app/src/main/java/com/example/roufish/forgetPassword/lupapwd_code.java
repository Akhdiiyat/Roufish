package com.example.roufish.forgetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.roufish.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class lupapwd_code extends AppCompatActivity {

    private EditText editTextCode;
    private Button buttonVerif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupapwd_code);

        editTextCode = findViewById(R.id.editTextText6); // Update with the correct ID
        buttonVerif = findViewById(R.id.btn_verifikasi);

        FloatingActionButton backToMain = findViewById(R.id.backToMainREG);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lupaPwIntent = new Intent(lupapwd_code.this, lupapwd.class);
                startActivity(lupaPwIntent);
            }
        });

        buttonVerif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent verifIntent = new Intent(lupapwd_code.this, lupapwd_verif.class);
                startActivity(verifIntent);
            }
        });

        editTextCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this example
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this example
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Check the length of the entered text and allow only 4 characters
                if (editable.length() == 4) {
                    editTextCode.setError(null);
                    buttonVerif.setEnabled(true);

                } else {
                    editTextCode.setError("Enter exactly 4 digits");
                    buttonVerif.setEnabled(false);
                }
            }
        });
    }
}

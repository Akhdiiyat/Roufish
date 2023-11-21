package com.example.roufish;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import com.example.roufish.activities.ProductActivity;

public class login extends AppCompatActivity {

    FirebaseAuth mAuth;
    FloatingActionButton backToMain ;
    EditText editTextUsername ;
    EditText editTextpassword ;
    CheckBox checkPassword ;
    AppCompatButton loginBtn ;
    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://roufish-database-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        backToMain = findViewById(R.id.backToMain);
        editTextUsername = findViewById(R.id.input_Username);
        editTextpassword = findViewById(R.id.input_Password);
        checkPassword = (CheckBox) findViewById(R.id.tampilkanPassword);
        loginBtn = findViewById(R.id.btn_login);


        checkPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    editTextpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
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

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = null;
                String password = null;

                email = editTextUsername.getText().toString().trim();
                password = editTextpassword.getText().toString().trim();
                //check empty
                if(TextUtils.isEmpty(email)){
                    editTextUsername.setError("Masukkan Email");
                    return;
                }else if(TextUtils.isEmpty(password)){
                    editTextpassword.setError("Masukkan password");
                    return;
                }

                if(password.length() < 6) {
                    editTextpassword.setError("password harus lebih dari 6 karakter");
                    return;
                }

                //sign in process

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(login.this,"Loginn berhasil",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),ProductActivity.class));
                        }else {
                            Toast.makeText(login.this,"Login Gagal" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    });
                }


            });
        };

    }

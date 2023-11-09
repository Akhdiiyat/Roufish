package com.example.roufish;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    //FirebaseAuth mAuth;
    FloatingActionButton backToMain ;
    EditText editTextUsername ;
    EditText editTextpassword ;
    CheckBox checkPassword ;
    AppCompatButton login ;
    DatabaseReference database = FirebaseDatabase.getInstance().getReferenceFromUrl("https://roufish-database-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //mAuth = FirebaseAuth.getInstance();
        backToMain = findViewById(R.id.backToMain);
        editTextUsername = findViewById(R.id.input_Username);
        editTextpassword =  findViewById(R.id.input_Password);
        checkPassword  = (CheckBox) findViewById(R.id.tampilkanPassword);
        login = findViewById(R.id.btn_login);



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
                String username,password;

                //username = String.valueOf(editTextUsername.getText());
                //password = String.valueOf(editTextpassword.getText());

                username = editTextUsername.getText().toString();
                password = editTextpassword.getText().toString();
                //database = FirebaseDatabase.getInstance().getReference("users");

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(login.this,"Masukkan Data yang Kosong",Toast.LENGTH_SHORT).show();

                }
                else {
                    database.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        //check username ada atau tidak di database
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(username)){
                                final String getpassword = snapshot.child(username).child("password").getValue(String.class);

                                if(getpassword.equals(password)){
                                    Toast.makeText(login.this,"Loggin Sukses",Toast.LENGTH_SHORT).show();

                                    String usernameDB = snapshot.child(username).child("username").getValue(String.class);
                                    String emailDB = snapshot.child(username).child("email").getValue(String.class);
                                    String noHPDB = snapshot.child(username).child("noHP").getValue(String.class);

                                    Intent loginIntent = new Intent(login.this, product.class);
                                    loginIntent.putExtra("username", usernameDB);
                                    loginIntent.putExtra("email",emailDB );
                                    loginIntent.putExtra("noHP",noHPDB );
                                    startActivity(loginIntent);
                                    finish();
                                }
                                else {
                                    Toast.makeText(login.this,"Password Salah",Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(login.this,"Masukkan Data yang Benar",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }



            }
        });
    }
}
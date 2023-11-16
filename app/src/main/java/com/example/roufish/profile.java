package com.example.roufish;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.DataCollectionDefaultChange;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {

    //FirebaseAuth auth;
    Button logout;

    //FirebaseUser user;

    //FirebaseAuth auth;

    TextView profilUsername, profilEmail, profilNoHP;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        logout = findViewById(R.id.btn_logout);
        profilEmail = (TextView) findViewById(R.id.profilEmail);
        profilUsername = (TextView) findViewById(R.id.profilNama);
        profilNoHP = (TextView) findViewById(R.id.profilNoHP);
       /* auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();*/
        reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://roufish-database-default-rtdb.firebaseio.com/");

        FloatingActionButton roufish = findViewById(R.id.roufish);

        showUserData();


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });

        roufish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent roufishintent = new Intent(profile.this, product.class);
                startActivity(roufishintent);
            }
        });


    }

    public void showUserData() {



        String username = "username";
        reference.child("users").child(username).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        // Assuming your user data has fields like "username", "email", "noHP"
                        String retrievedUsername = dataSnapshot.child("username").getValue(String.class);
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String noHP = dataSnapshot.child("noHP").getValue(String.class);


                        // Set the values to the TextView elements
                        profilUsername.setText(retrievedUsername);
                        profilEmail.setText(email);
                        profilNoHP.setText(noHP);

                        // Now you can use these values as needed, for example, log them
                        Log.d("firebase", retrievedUsername);
                        Log.d("firebase",  email);
                        Log.d("firebase", noHP);
                    } else {
                        Log.d("firebase", "No such document");
                    }
                }
            }


            /*Intent intent = getIntent();

            String namaUser = intent.getStringExtra("username");
            String emailUser = intent.getStringExtra("email");
            String noHPUser = intent.getStringExtra("NoHP");

            profilUsername.setText(namaUser);
            profilEmail.setText(emailUser);
            profilNoHP.setText(noHPUser);*/
        });
        /*if (user != null){
            //String username = String.valueOf(database.getRef());

            database.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {
                        String namaUser = snapshot.child("username").getValue(String.class);
                        String emailUser = snapshot.child("email").getValue(String.class);
                        String noHPUser = snapshot.child("NoHP").getValue(String.class);

                        profilUsername.setText(namaUser);
                        profilEmail.setText(emailUser);
                        profilNoHP.setText(noHPUser);
                    } else {
                        // Handle the case where user data doesn't exist
                        Toast.makeText(profile.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle the error
                    Toast.makeText(profile.this, "Database error", Toast.LENGTH_SHORT).show();
                }
            });
         else {
        // Handle the case where the user is not authenticated
            Toast.makeText(profile.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }*/
    }


}






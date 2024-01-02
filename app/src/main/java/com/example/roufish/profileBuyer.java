package com.example.roufish;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roufish.activities.MainPageBuyer;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class profileBuyer extends AppCompatActivity {


    Button logout, editProfile;
    FloatingActionButton back;
    FirebaseFirestore firestore;

    FirebaseAuth mAuth;

    FirebaseUser user;

    TextView profileUsername, profileEmail, profileName, profilePassword, titleName, titleUsername;
    TextView profileNoHP;
    String userId;
    ImageView profileImg;
    StorageReference storageReference;
    StorageReference profileRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        back = findViewById(R.id.tombolkmbl);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(profileBuyer.this, MainPageBuyer.class);
                startActivity(backIntent);
            }
        });
        logout = findViewById(R.id.btn_logout);


        profileEmail = (TextView) findViewById(R.id.profileEmail);
        profileUsername = (TextView) findViewById(R.id.profileUsername);
        titleName =  (TextView) findViewById(R.id.titleName);
        titleUsername = (TextView) findViewById(R.id.titleUsername);
        profileNoHP = (TextView) findViewById(R.id.profileNoHP);
        profileImg = (ImageView) findViewById(R.id.profileImg);

        editProfile = (Button) findViewById(R.id.editProfile);
        mAuth =FirebaseAuth.getInstance();
        firestore =FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        userId = mAuth.getCurrentUser().getUid();
        profileRef = storageReference.child("users/" + mAuth.getCurrentUser().getUid()+ "/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImg);
            }
        });

        editProfile.setOnClickListener((v) ->{

            Intent i = new Intent(v.getContext(),EditProfile.class);
            i.putExtra("username", profileUsername.getText().toString());
            i.putExtra("email", profileEmail.getText().toString());
            i.putExtra("noHP", profileNoHP.getText().toString());
            startActivity(i);

        });

        //FloatingActionButton roufish = findViewById(R.id.roufish);

        showUserData();


        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });*/



    }





    public  void logOut(View view){
        mAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Homepage.class));
        finish();
    }

    public void showUserData() {

        DocumentReference documentReference = firestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()){
                    profileUsername.setText(value.getString("username"));
                    titleUsername.setText(value.getString("username"));
                    profileEmail.setText(value.getString("email"));
                    profileNoHP.setText(value.getString("noHP"));
                }

            }
        });
    }



}






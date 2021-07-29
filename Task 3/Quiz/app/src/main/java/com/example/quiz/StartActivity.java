package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    Button start, bookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        start=findViewById(R.id.start_start_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, CategoriesActivity.class));
            }
        });

        bookmark = findViewById(R.id.start_start_btn2);
        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,BookmarkActivity.class));
            }
        });

//        name=findViewById(R.id.idShowName);
//        email=findViewById(R.id.idShowEmail);
//
//        GoogleSignInAccount mSignInAccount= GoogleSignIn.getLastSignedInAccount(MainActivity.this);
//        if(mSignInAccount!=null){
//            name.setText(mSignInAccount.getDisplayName());
//            email.setText(mSignInAccount.getEmail());
//        }
//        FirebaseAuth mAuth=FirebaseAuth.getInstance();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser!=null){
//            name.setText(currentUser.getDisplayName());
//            email.setText(currentUser.getEmail());
//        }
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}
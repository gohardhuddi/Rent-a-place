package com.example.Rentup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class About extends AppCompatActivity {
    TextView email,aboutus,profile,logout;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference UserRef;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        email=findViewById(R.id.email);
        aboutus=findViewById(R.id.aboutus);
        logout=findViewById(R.id.logout);
        id=FirebaseAuth.getInstance().getCurrentUser().getUid();
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();
        UserRef= FirebaseDatabase.getInstance().getReference().child("Registration");
        retriveData();
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(About.this,About_us.class);
                startActivity(i);
            }
        });
        Paper.init(this);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Paper.book().delete(Remembermeinfosaver.PaperEmail);
                Paper.book().delete(Remembermeinfosaver.PaperPassword);
                Intent i=new Intent(About.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void retriveData() {
        UserRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.hasChild("Email"))
                {
                    String UserEmail=snapshot.child("Email").getValue().toString();
                    Toast.makeText(About.this, ""+UserEmail, Toast.LENGTH_SHORT).show();
                    email.setText(UserEmail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.example.Rentup;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {
    Button btngetcode;
    EditText etemail;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        btngetcode=findViewById(R.id.btngetcode);
        etemail=findViewById(R.id.etemail);
        firebaseAuth=FirebaseAuth.getInstance();
        btngetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username= etemail.getText().toString().trim();
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(Forgot.this, "Enter your Registered Email", Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth.sendPasswordResetEmail(username).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(Forgot.this, "Email Sent", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }

                            else{
                                Toast.makeText(Forgot.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });
    }
}
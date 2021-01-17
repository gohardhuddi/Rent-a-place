package com.example.rentup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText etemail, etpassword;
    private Button btnLogin,btnregister;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        btnLogin = findViewById(R.id.btnlogin);
        btnregister=findViewById(R.id.btnregister);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password,test;
                email=etemail.getText().toString().trim();
                password=etpassword.getText().toString().trim();
                progressDialog.setMessage("Loging..........");
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    Intent i= new Intent(MainActivity.this,Decesion.class);
                                    startActivity(i);
                                }
                                else
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Invalid username or password try again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this, Profile.class);
                startActivity(i);
            }
        });
    }
}

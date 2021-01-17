package com.example.rentup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {
    EditText etfirstname,etlastname,etemail,etpassword,etconfpassword,etph;
    Button btnregister;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myref;
    FirebaseAuth firebaseAuth;
    String Firstname,Lastname,Email,Password,Confpassword,Ph;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etfirstname=findViewById(R.id.etfirstname);
        etlastname=findViewById(R.id.etlaname);
        etemail=findViewById(R.id.etemail);
        etpassword=findViewById(R.id.etpassword);
        etconfpassword=findViewById(R.id.eetconfpassword);
        etph=findViewById(R.id.etph);
        progressDialog=new ProgressDialog(this);
        FirebaseAuth.getInstance();
        btnregister=findViewById(R.id.btnregistration);
        myref=FirebaseDatabase.getInstance().getReference("Registration");
        firebaseAuth=FirebaseAuth.getInstance();
        //Registration is my support class,bythis method Registration node will be created in db to store entries
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Registring..........");
                Firstname=etfirstname.getText().toString().trim();
                Lastname=etlastname.getText().toString().trim();
                Email=etemail.getText().toString().trim();
                Password=etpassword.getText().toString().trim();
                Confpassword=etconfpassword.getText().toString().trim();
                Ph=etph.getText().toString().trim();
                if (Firstname.isEmpty())
                {
                    etfirstname.setError("Please Enter Your Name");
                    etfirstname.requestFocus();
                }
                else if (Lastname.isEmpty())
                {
                    etlastname.setError("Enter Your Last Name");
                    etlastname.requestFocus();
                }
                else if (Email.isEmpty())
                {
                    etemail.setError("Enter Email Address");
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches())
                {
                    etemail.setError("Enter a Valid Email");
                    Toast.makeText(Profile.this, "someone@gmail.com", Toast.LENGTH_SHORT).show();
                }
                else if (Password.isEmpty())
                {
                    etpassword.setError("Enteer Password");
                }
                else if(Password.length()<8)
                {
                    etpassword.setError("Password should contain 8 or more characters");
                }
                else if(!Confpassword.equals(Password))
                {
                    etconfpassword.setError("Password don't match");
                }
                else if (Ph.isEmpty())
                {
                    etph.setError("Enter Phone Number");
                }
                else if (Ph.length()!=11)
                {
                    etph.setError("Enter Correct Phonenumber");
                    Toast.makeText(Profile.this, "e.g 03001234567", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Profile.this, "email"+Password, Toast.LENGTH_SHORT).show();
                    firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                            .addOnCompleteListener(Profile.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        progressDialog.dismiss();
                                        registration registration= new registration(Firstname,Lastname,Email,Password,Confpassword,Ph);

                                        FirebaseDatabase.getInstance().getReference("Registration")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(registration).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(Profile.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        });
                                    }
                                    else
                                    {
                                        Toast.makeText(Profile.this, "Fail", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                            });
                }
                //for phone authentication
                Intent intent=new Intent(Profile.this,PhoneAuthnetication.class);
                intent.putExtra("Ph",Ph);
                startActivity(intent);

            }
        });
    }
}

package com.example.Rentup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Profile extends AppCompatActivity {
    EditText etfirstname,etlastname,etemail,etpassword,etconfpassword,etph;
    Button btnregister;
    FirebaseDatabase firebaseDatabase;//use to use db.
    DatabaseReference myref;//maun calss contain quries
    //use it when we need database
    FirebaseAuth firebaseAuth;
    String Firstname,Lastname,Email,Password,Confpassword,Ph,emailRegEx;
    ProgressDialog progressDialog;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        etfirstname=findViewById(R.id.etfirstname);
        etlastname=findViewById(R.id.etlaname);
        etemail=findViewById(R.id.etemail);
        etpassword=findViewById(R.id.etpassword);
        etconfpassword=findViewById(R.id.eetconfpassword);
        etph=findViewById(R.id.etph);
        progressDialog=new ProgressDialog(this);
        FirebaseAuth.getInstance();//get current instance mean state
        btnregister=findViewById(R.id.btnregistration);
        myref=FirebaseDatabase.getInstance().getReference("Registration");
        //this line crete a node with name as Reference in online db.
        firebaseAuth=FirebaseAuth.getInstance();
        //Registration is my support class,bythis method Registration node will be created in db to store entries
        etph.setText("+92");
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputValidation();
                }

            private void FirebaseUserCreation() {
                progressDialog.show();
                progressDialog.setMessage("Please wait");
                //toast is use to display someting on screen
                Toast.makeText(Profile.this, "email"+Password, Toast.LENGTH_SHORT).show();
                firebaseAuth.createUserWithEmailAndPassword(Email,Password)
                        //this method is use to creat user in auth sec of d.b
                        .addOnCompleteListener(Profile.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull final Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    ProfileHelper registration= new ProfileHelper(Firstname,Lastname,Email,Password,Confpassword,Ph);
                                    //registartion is my support class and registartion()is constructer with parameters
                                    //Firstname......etc are veriable name in registration constructor

                                    FirebaseDatabase.getInstance().getReference("Registration")
                                            //creat node Registration
                                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                            //creat child for each user
                                            .setValue(registration).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Profile.this, "Registration successful", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(Profile.this,decesion.class);
                                            progressDialog.dismiss();
                                            intent.putExtra("Ph",Ph);
                                            //put extra is use to send data from one activit to another
                                            startActivity(intent);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Profile.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                if (!task.isSuccessful() &&
                                task.getException() instanceof FirebaseAuthUserCollisionException)
                                {
                                    FirebaseAuthUserCollisionException exception =
                                            (FirebaseAuthUserCollisionException)task.getException();
                                    Toast.makeText(Profile.this, "Email already exists ", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                    etemail.requestFocus();
                                }
                            }
                        });
            }

            private void InputValidation() {
                progressDialog.setMessage("Registring..........");
                Firstname=etfirstname.getText().toString().trim();
                Lastname=etlastname.getText().toString().trim();
                Email=etemail.getText().toString().trim();
                Password=etpassword.getText().toString().trim();
                Confpassword=etconfpassword.getText().toString().trim();
                Ph=etph.getText().toString().trim();
                emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
                Pattern pattern = Pattern.compile(emailRegEx);
                final Matcher matcher = pattern.matcher(Email);
                    if (Firstname.isEmpty())
                    {
                        etfirstname.setError("Plese Enter valid name");
                        etfirstname.requestFocus();
                    }
                    else if(Firstname.length()<3)
                    {
                        etfirstname.setError("Plese Enter valid name");
                        etfirstname.requestFocus();
                    }
                    else if (Lastname.isEmpty())
                    {
                        etlastname.setError("Plese Enter valid last name");
                        etlastname.requestFocus();
                    }
                    else if (Lastname.length()<3)
                    {
                        etlastname.setError("Plese Enter valid name");
                        etlastname.requestFocus();
                    }
                    else if (Email.isEmpty())
                    {
                        etemail.setError("Plese Enter valid Email");
                        etemail.requestFocus();
                    }
                    else if (!matcher.find())
                    {
                        etemail.setError("Enter vaild email");
                        etemail.requestFocus();
                    }
                    else if (Password.isEmpty())
                    {
                        etpassword.setError("Enter Password of 8 characters");
                        etpassword.requestFocus();
                    }
                    else if(Password.length()<8)
                    {
                        etpassword.setError("Enter Password of 8 characters");
                        etpassword.requestFocus();
                    }
                    else if(Confpassword.isEmpty())
                    {
                        etconfpassword.setError("Re_enter password");
                        etconfpassword.requestFocus();
                    }
                    else if(!Confpassword.equals(Password))
                    {
                        etpassword.setError("Password donot match ");
                        etpassword.requestFocus();
                    }
                    else if (etph.length()!=13)
                    {

                        etph.setError("enter valid phone number\n3031234567");
                        etph.requestFocus();
                        int length=etph.length();
                        Toast.makeText(Profile.this, ""+length, Toast.LENGTH_SHORT).show();
                    }
                    else {
                        FirebaseUserCreation();
                    }
            }
        });
    }
}

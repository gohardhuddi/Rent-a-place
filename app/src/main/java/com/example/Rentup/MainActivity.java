//login activity
package com.example.Rentup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity  {
    //all element in xml are java classes in java so we call that class and make object
    //as shown here
    private EditText etemail, etpassword;
    private Button btnLogin;
    TextView btnregister,forget;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;//use for firebase authentication
    Animation fromtop;
    CheckBox cbRememberme;
    String emailRegEx,email,password;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etemail = findViewById(R.id.etemail);
        etpassword = findViewById(R.id.etpassword);
        btnLogin = findViewById(R.id.btnlogin);
        btnregister=findViewById(R.id.btnregister);
        cbRememberme=findViewById(R.id.cbrememberme);
        forget=findViewById(R.id.tvforgot);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        fromtop= AnimationUtils.loadAnimation(this,R.anim.toptobottom);
        btnLogin.setAnimation(fromtop);
        btnregister.setAnimation(fromtop);
        etemail.setAnimation(fromtop);
        etpassword.setAnimation(fromtop);
        Paper.init(this);
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {//use for what happen when button was clicked
                final String email, password,test;//veriables
                email=etemail.getText().toString().trim();
                Pattern pattern = Pattern.compile(emailRegEx);
                Matcher matcher = pattern.matcher(email);
                //getText() use to get text from edittext.
                //to string() is use to contert any data type to string
                //trim() is use that donot take space on start in edit text
                password=etpassword.getText().toString().trim();
                if (email.isEmpty()){
                    Toast.makeText(MainActivity.this, "Fill the Fields", Toast.LENGTH_SHORT).show();
                    etemail.requestFocus();
                }
                else if(!matcher.find())
                {
                    Toast.makeText(MainActivity.this, "Enter a Valid Email", Toast.LENGTH_SHORT).show();
                    etemail.requestFocus();

                }
                else if(password.isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                    etpassword.requestFocus();
                }
                else {
                    progressDialog.setMessage("Please Wait..........");
                    progressDialog.show();
                    firebaseAuth.fetchSignInMethodsForEmail(etemail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                @Override
                                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                    boolean Check=!task.getResult().getSignInMethods().isEmpty();
                                    if (!Check)
                                    {//not fond
                                        progressDialog.dismiss();
                                        Snackbar snackbar=Snackbar.make(v,"User doesn't exist",Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                        snackbar.setAction("Register", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent i=new Intent(MainActivity.this,Profile.class);
                                                startActivity(i);
                                            }
                                        });
                                    }
                                    else{
                                        progressDialog.setMessage("Please Wait..........");//setting the error messege in edit text
                                        progressDialog.show();//Show() use to show something
                                        firebaseAuth.signInWithEmailAndPassword(email,password)
                                                //Function of FirebaseAuth class use to Sign in user in user is registered.
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {//called when task complete to inform us
                                                        if (task.isSuccessful())
                                                        {
                                                            Intent i= new Intent(MainActivity.this,decesion.class);
                                                            startActivity(i);
                                                        }
                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(MainActivity.this, "Invalid username or password try again", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            });

                    if (cbRememberme.isChecked())
                    {
                        Paper.book().write(Remembermeinfosaver.PaperEmail,email);
                        Paper.book().write(Remembermeinfosaver.PaperPassword,password);                    }
                }

            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,Profile.class);
                startActivity(i);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forg=new Intent(MainActivity.this,Forgot.class);
                startActivity(forg);
            }
        });
    }



}

package com.example.Rentup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.net.HttpURLConnection;

import io.paperdb.Paper;

public class Splash extends AppCompatActivity {//extends mean inhertence in java
    //AppcomatActivity is s super class whic contain main methods.
    //splash is subclass of appcompatactivity
    //like on creat and find view by id method are part of AppcompatActivity and we use them in our sub class
ImageView imageView;//class Imageview with object
    Animation frombottom;
//    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {//save instance & it is the method of appcompatActivity
        super.onCreate(savedInstanceState);//method of aca
        setContentView(R.layout.activity_splash);//connecting xml to java
        imageView=findViewById(R.id.logo);//connecting xml to java
        frombottom= AnimationUtils.loadAnimation(this,R.anim.bottomup);
        imageView.setAnimation(frombottom);
  //      progressBar=findViewById(R.id.progressbar);
       firebaseAuth= FirebaseAuth.getInstance();
       Paper.init(this);
        //retrive data from paper
        String PaperEmail= Paper.book().read(Remembermeinfosaver.PaperEmail);
        String PaperPassword=Paper.book().read(Remembermeinfosaver.PaperPassword);

        //Toast.makeText(this, "1" +PaperEmail, Toast.LENGTH_SHORT).show();
        if (isnetworkavailable())
        {
            if (PaperEmail!=null && PaperPassword!=null)
            {
                if (!TextUtils.isEmpty(PaperEmail) && !TextUtils.isEmpty(PaperPassword))
                {
                    AllowAccess(PaperEmail,PaperPassword);
                    Toast.makeText(this, "2"+PaperEmail, Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Thread myThread=new Thread(){
                    @Override
                    public void run() {//method use to automatically jumo to next activity after time sleep.
                        try {
                            sleep(5000);//time in mili seconds
                            Intent i=new Intent(Splash.this,MainActivity.class);//method use to go to one activity to other
                            startActivity(i);
                            overridePendingTransition(R.anim.bottomup,R.anim.bottomup);
                            finish();//function of inflator class return boolen value
                            //inflator is java class it attach to activity after reading xml file
                        } catch (InterruptedException e) {
                            e.printStackTrace();//method in java use to handle error and ecceptions
                        }
                    }
                };
                myThread.start();//start the thread

            }
        }
        else {
            Toast.makeText(this, "No internet Access", Toast.LENGTH_SHORT).show();
        }


    }
    private void AllowAccess(String paperemail, String paperpassword) {
        //Toast.makeText(this, ""+paperemail+"\n"+paperpassword, Toast.LENGTH_SHORT).show();
        firebaseAuth.signInWithEmailAndPassword(paperemail,paperpassword)
                //Function of FirebaseAuth class use to Sign in user in user is registered.
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {//called when task complete to inform us
                        if (task.isSuccessful())
                        {
                            Intent i= new Intent(Splash.this,decesion.class);
                            startActivity(i);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Splash.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public boolean isnetworkavailable()
    {
        try {
            ConnectivityManager cm =
                    (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork != null &&
                    activeNetwork.isConnectedOrConnecting();
        }
        catch (NullPointerException e){
            return false;
        }
    }

}

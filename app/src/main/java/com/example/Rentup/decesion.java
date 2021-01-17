package com.example.Rentup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class decesion extends AppCompatActivity {
Button btnTenant,btnOwner;
ImageView about;
  //  ImageButton imgbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decesion);
        btnTenant=findViewById(R.id.btntenant);
        btnOwner=findViewById(R.id.btnowner);
        about=findViewById(R.id.about);
       // imgbtn=findViewById(R.id.btnprofile);
        btnTenant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(decesion.this,query_selection.class);
                startActivity(i);
            }
        });
        btnOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(decesion.this,Owner.class);
                startActivity(i);

            }
        });
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(decesion.this,About.class);
                startActivity(i);
            }
        });
      //  Paper.init(this);
//        imgbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Paper.book().delete(Remembermeinfosaver.PaperEmail);
//                Paper.book().delete(Remembermeinfosaver.PaperPassword);
//                Intent i=new Intent(decesion.this,MainActivity.class);
//                startActivity(i);
//            }
//        });
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}

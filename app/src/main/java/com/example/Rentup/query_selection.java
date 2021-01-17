package com.example.Rentup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class query_selection extends AppCompatActivity {
    Button btnHouse,btnOffice,btnHostle,btnShop;
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_selection);
        btnHostle=findViewById(R.id.btnhostle);
        btnHouse=findViewById(R.id.btnhouse);
        btnOffice=findViewById(R.id.btnoffice);
        btnShop=findViewById(R.id.btnShop);
        btnHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String easyPuzzle  = "House";

                Intent i = new Intent(query_selection.this,Available_places.class);
                i.putExtra("epuzzle", easyPuzzle);
                startActivity(i);
            }
        });
        btnOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String easyPuzzle  = "Office";

                Intent i = new Intent(query_selection.this,Available_places.class);
                i.putExtra("epuzzle", easyPuzzle);
                startActivity(i);
            }
        });
        btnHostle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String easyPuzzle  = "Hostle";

                Intent i = new Intent(query_selection.this,Available_places.class);
                i.putExtra("epuzzle", easyPuzzle);
                startActivity(i);
            }
        });
        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String easyPuzzle  = "Shope";

                Intent i = new Intent(query_selection.this,Available_places.class);
                i.putExtra("epuzzle", easyPuzzle);
                startActivity(i);
            }
        });


    }
}
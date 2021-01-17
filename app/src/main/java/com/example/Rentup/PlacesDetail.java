package com.example.Rentup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PlacesDetail extends AppCompatActivity {
    private String recUseerId;
    TextView Colony,Address,Description,Phone,Rent;
    ImageView MainImage,callno;
    private DatabaseReference UserRef;
    String mmPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_detail);
        Colony=findViewById(R.id.tvcolony);
        Address=findViewById(R.id.tvaddress);
        Description=findViewById(R.id.tvdescription);
        Phone=findViewById(R.id.tvphone);
        Rent=findViewById(R.id.tvrent);
        MainImage=findViewById(R.id.mainimage);
        callno=findViewById(R.id.callno);
        recUseerId=getIntent().getExtras().get("visted").toString();
        Toast.makeText(this, "User Id"+recUseerId, Toast.LENGTH_SHORT).show();
        UserRef= FirebaseDatabase.getInstance().getReference().child("Description");
        RetriveUserinfo();
    }

    private void RetriveUserinfo() {
        UserRef.child(recUseerId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.hasChild("mImageUrl"))
                {
                    String UserImage=snapshot.child("mImageUrl").getValue().toString();
                    String mAddress=snapshot.child("mAddress").getValue().toString();
                    String mColony=snapshot.child("mColony").getValue().toString();
                    String mDescription=snapshot.child("mDescription").getValue().toString();
                    String mPhoneNumber=snapshot.child("mPhoneNumber").getValue().toString();
                    String mRent=snapshot.child("mRent").getValue().toString();
                    mmPhoneNumber=mPhoneNumber;
                    Picasso.get().load(UserImage).into(MainImage);
                    Colony.setText(mColony);
                    Address.setText(mAddress);
                    Description.setText(mDescription);
                    Phone.setText(mPhoneNumber);
                    Rent.setText(mRent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        callno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+mmPhoneNumber));
                startActivity(intent);
            }
        });
    }
}
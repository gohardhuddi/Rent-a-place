package com.example.Rentup;

import  androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.model.Model;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import io.paperdb.Paper;

public class Available_places extends AppCompatActivity {
    FirebaseAuth mFirebaseAuth;
    DatabaseReference mDatabaseReference;
    TextView tst;
    Button btnLoad;
    String uid, name, colony, rent,search;
    RecyclerView recyclerViewlist;
    ImageButton imgbtn;
    Query query;
    ProgressDialog progressDialog;
    Button House,Office,Shop,Hostle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_places);
       // imgbtn=findViewById(R.id.btnprofile);
       // btnLoad = findViewById(R.id.btnLoad);
        recyclerViewlist = findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerViewlist.setHasFixedSize(true);
        recyclerViewlist.setLayoutManager(layoutManager);
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent intent = getIntent();
        final String easyPuzzle = intent.getExtras().getString("epuzzle");
        Toast.makeText(this, "id=  " + uid, Toast.LENGTH_SHORT).show();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Description");
        String fur="Furnished";
        query =FirebaseDatabase.getInstance().getReference().child("Description").orderByChild("mPropertyty2").equalTo(easyPuzzle);
//        House.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                query =FirebaseDatabase.getInstance().getReference().child("Description").orderByChild("mPropertyty2").equalTo(easyPuzzle);
//                Toast.makeText(Available_places.this, "House", Toast.LENGTH_SHORT).show();
//            }
//        });
//        Office.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                query =FirebaseDatabase.getInstance().getReference().child("Description").orderByChild("mPropertyty2").equalTo("Office");
//                Toast.makeText(Available_places.this, "Office", Toast.LENGTH_SHORT).show();
//            }
//        });
//        Shop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                query =FirebaseDatabase.getInstance().getReference().child("Description").orderByChild("mPropertyty2").equalTo("Shope");
//                Toast.makeText(Available_places.this, "Shope", Toast.LENGTH_SHORT).show();
//            }
//        });
//        Hostle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                query =FirebaseDatabase.getInstance().getReference().child("Description").orderByChild("mPropertyty2").equalTo("Hostle");
//                Toast.makeText(Available_places.this, "Hostle", Toast.LENGTH_SHORT).show();
//            }
//        });
        //Query query=FirebaseDatabase.getInstance().getReference().child("Description").orderByChild("mColony").equalTo("x");
//        btnLoad.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDatabaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        name = dataSnapshot.child("imageUrl").getValue().toString();
//                        rent = dataSnapshot.child("mRent").getValue().toString();
//                        clony = dataSnapshot.child("mColony").getValue().toString();
//                        Toast.makeText(Available_places.this, "" + " " + colony + " " + rent, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Toast.makeText(Available_places.this, "" + databaseError, Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
        Paper.init(this);
//        imgbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Paper.book().delete(Remembermeinfosaver.PaperEmail);
//                Paper.book().delete(Remembermeinfosaver.PaperPassword);
//                Intent i=new Intent(Available_places.this,MainActivity.class);
//                startActivity(i);
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<AvailableplacesHelper>options=
                new FirebaseRecyclerOptions.Builder<AvailableplacesHelper>()
                        .setQuery(query,AvailableplacesHelper.class)
                        .build();
        FirebaseRecyclerAdapter<AvailableplacesHelper,AvailViewHolder> adapter
                =new FirebaseRecyclerAdapter<AvailableplacesHelper, AvailViewHolder>(options) {


            @Override
            protected void onBindViewHolder(@NonNull AvailViewHolder holder, final int position, @NonNull AvailableplacesHelper model) {
                holder.tvRent.setText(model.getmRent());
                holder.tvColony.setText(model.getmColony());
                Picasso.get().load(model.getImageUrl()).into(holder.ivimage);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String visted= getRef(position).getKey();
                        Intent ProfileIntent=new Intent(Available_places.this,PlacesDetail.class);
                        ProfileIntent.putExtra("visted",visted);
                        startActivity(ProfileIntent);
                    }
                });

            }

            @NonNull
            @Override
            public AvailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,parent,false);
                AvailViewHolder viewHolder=new AvailViewHolder(view);
                return viewHolder;
            }
        };
        recyclerViewlist.setAdapter(adapter);
        adapter.startListening();
    }
    public static class AvailViewHolder extends RecyclerView.ViewHolder{
        TextView tvRent,tvColony;
        ImageView ivimage;
        public AvailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRent=itemView.findViewById(R.id.tvrent);
            tvColony=itemView.findViewById(R.id.tvcolony);
            ivimage=itemView.findViewById(R.id.ivimage);
        }
    }
}
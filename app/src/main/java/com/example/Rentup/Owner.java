package com.example.Rentup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import io.paperdb.Paper;

public class Owner extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //calling classes with obj
    ImageView Ivmain, Ivfirst, Ivsecond;
    Button btnupload;
    Spinner etColony;
    TextView test;
    private ProgressDialog progressDialog;
    EditText  etAddress, etDescription, etRent, etPhone;
    RadioGroup radioGroup,radioGrouptype;//for radio button we need a seperate function which return us value that which btn is pressend
    RadioButton rbFurnished, rbUnfurnished,rbHome,rbOffice,rbShop,rbHostle;
    CheckBox cbFamily;
    Uri mImageUri;
    String mImageUrl;
    //this is use to get image uri;
    String PlaceType = "";
    String Family = "";
    String Colony,Address,Description,Rent,PhoneNumber,Propertyty1,Propertyty2,Propertyty3,Clon;
    //final String spinnerdata;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference mDatabasrReferance;
    StorageReference mStorageReference;
   // ProgressBar progressBar;
    ImageButton imgbtn;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //connection
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
       Ivmain=findViewById(R.id.imageuploada1);
       test=findViewById(R.id.test);
        etColony=findViewById(R.id.etcolony);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.Colony_Name, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etColony.setAdapter(adapter);
        etColony.setOnItemSelectedListener(this);
        etAddress = findViewById(R.id.etaddress);
        etDescription = findViewById(R.id.etdescription);
        etRent = findViewById(R.id.etrent);
        etPhone = findViewById(R.id.etph);
        btnupload = findViewById(R.id.btnUpload);
        radioGroup = findViewById(R.id.rgradiogroup);
        radioGrouptype=findViewById(R.id.rgradiogrouptype);
        rbFurnished = findViewById(R.id.rbtnfurnished);
        rbUnfurnished = findViewById(R.id.rbtnunfurnished);
        rbHome=findViewById(R.id.rbHouse);
        rbHostle=findViewById(R.id.rbHostle);
        rbOffice=findViewById(R.id.rboffice);
        rbShop=findViewById(R.id.rbshope);
        cbFamily = findViewById(R.id.cbforfamily);
       // imgbtn=findViewById(R.id.btnprofile);





        ////////////////////creating ref in db and storage
        mDatabasrReferance=FirebaseDatabase.getInstance().getReference("Description");
        mStorageReference= FirebaseStorage.getInstance().getReference("Description");
        FirebaseDatabase.getInstance();
        FirebaseAuth.getInstance();
        /////////////////////////////////chose image
        Ivmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileChoser();
            }
        });

        //upload btn
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getcolonyname();
                progressDialog.setMessage("Uploading..........");
                progressDialog.show();
                //getting data from input fields and stors into veriables
                 //etColony.getText().toString().trim();
                Address = etAddress.getText().toString().trim();
                if (Address.isEmpty())
                {
                    etAddress.setError("Please enter Address");
                    etAddress.requestFocus();
                }
                Description = etDescription.getText().toString().trim();
                if (Description.isEmpty())
                {
                    etDescription.setError("provide description");
                    etDescription.requestFocus();
                }
                Rent = etRent.getText().toString().trim();
                if (Rent.isEmpty())
                {
                    etRent.setError("Provide Rent");
                    etRent.requestFocus();
                }
                PhoneNumber = etPhone.getText().toString().trim();
                if(PhoneNumber.length()!=11)
                {
                    etPhone.setError("Enter valid Phone number");
                    etPhone.requestFocus();
                }
                else {
                    UploadPlceData();
                }

                //it is the function below
                if (radioGroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(Owner.this, "Poperty Type Require", Toast.LENGTH_SHORT).show();
                }
                else {
                    int radioid1=radioGroup.getCheckedRadioButtonId();
                    rbFurnished=findViewById(radioid1);
                    Propertyty1=rbFurnished.getText().toString();
                }
                if (radioGrouptype.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(Owner.this, "Poperty Type Require", Toast.LENGTH_SHORT).show();
                }
                else{
                    int radioid2=radioGrouptype.getCheckedRadioButtonId();
                    rbHome=findViewById(radioid2);
                    Propertyty2=rbHome.getText().toString();
                }
                    //code to check if this checkbox is checked!
                if (cbFamily.isChecked())
                {
                    Toast.makeText(Owner.this, "Checked", Toast.LENGTH_SHORT).show();
                    Propertyty3="For Family";
                }
                else
                {
                    Propertyty3="null";

                }

            }
        });
        //signout
        Paper.init(this);//paper is db
//        imgbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseAuth.getInstance().signOut();
//                Paper.book().delete(Remembermeinfosaver.PaperEmail);
//                Paper.book().delete(Remembermeinfosaver.PaperPassword);
//                Intent i=new Intent(Owner.this,MainActivity.class);
//                startActivity(i);
//            }
//        });
    }


    ////////////get ext of file for d.s
    private String getFileExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));

    }
    public void UploadPlceData(){
        if (mImageUri!=null)
        {
            final StorageReference fileReference=mStorageReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()+"."
                    +getFileExtension(mImageUri));
            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            mImageUrl=uri.toString();
                            mDatabasrReferance.child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .child("imageUrl")
                                    .setValue(mImageUrl).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Owner.this, "s", Toast.LENGTH_SHORT).show();
                                    uploaddata();
                                }
                            });
                            //Toast.makeText(Owner.this, ""+uri, Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(Owner.this, ""+mImageUrl, Toast.LENGTH_SHORT).show();
                    Handler handler=new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                      //      progressBar.setProgress(0);
                        }
                    },500);
                    Toast.makeText(Owner.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(Owner.this,decesion.class);
                    startActivity(i);
                }

                private void uploaddata() {
                    Colony=Clon;
                    OwnerHelper ownerHelper=new OwnerHelper(Colony,Address,Description,Rent,PhoneNumber,Propertyty1,Propertyty2,Propertyty3,mImageUrl);
                    FirebaseDatabase.getInstance().getReference("Description")
                            .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                            .setValue(ownerHelper).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Owner.this, "Successful", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Owner.this, "f"+e, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Owner.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
//                    progressBar.setProgress((int)progress);
                }
            });
        }
        else {
            Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
        }


        ////for image upload
    }
    /////////////file chose/////////////
    private void FileChoser(){
        Intent i= new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,1);
    }
//file choser
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            mImageUri=data.getData();
            Picasso .get().load(mImageUri).into(Ivmain);
        }
    }
//spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Clon=parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),Clon, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

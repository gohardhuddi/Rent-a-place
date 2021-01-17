package com.example.Rentup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneAuthentication extends AppCompatActivity {
    TextView textView;
    EditText editText;
    private String verificationId;
    Button btn;
    FirebaseAuth firebaseAuth;//it is for manual authentication

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_authentication);
        textView = findViewById(R.id.para);
        editText = findViewById(R.id.etcode);
        btn = findViewById(R.id.btnregister);
        textView.setText("Please enter the code here\nReceived by the SMS\nCode will received within one Minute");
        firebaseAuth = FirebaseAuth.getInstance();
        String Ph = getIntent().getStringExtra("Ph");
        Toast.makeText(this, "no"+Ph, Toast.LENGTH_SHORT).show();
        sendVerificationCode(Ph);
        firebaseAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String code=editText.getText().toString().trim();
                verifycode(code);//functing listed below

            }
        });
    }
    private void verifycode(String code){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);//buit in method should be written as it is
    }
    private void signInWithCredential(PhoneAuthCredential credential){
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent=new Intent(PhoneAuthentication.this,decesion.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(PhoneAuthentication.this, "Error bachy", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void sendVerificationCode(String number){
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(number)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallBack)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
//        PhoneAuthProvider.getInstance().verifyPhoneNumber(//build in method
//                number,//phno of user
//                60,//timeout delay
//                TimeUnit.SECONDS,//unit of timeout delay
//                (Activity) TaskExecutors.MAIN_THREAD,//build in function
//                mCallBack//functing listed below
   //     );
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId=s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null)
            {
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(PhoneAuthentication.this,e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

}
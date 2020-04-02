package com.example.otpverify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    Button next;
    FirebaseAuth fAuth;
    FirebaseFirestore fBase;
    EditText phone,otp;
    TextView state,resend;
    ProgressBar pBar;
    String verificationID;
    PhoneAuthProvider.ForceResendingToken Token;
    Boolean verificationInProgress=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fBase=FirebaseFirestore.getInstance();
        next=findViewById(R.id.nextBtn);
        fAuth=FirebaseAuth.getInstance();
        phone=findViewById(R.id.phone);
        otp=findViewById(R.id.codeEnter);
        state=findViewById(R.id.state);
        pBar=findViewById(R.id.progressBar);
        resend=findViewById(R.id.resendOtpBtn);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(!phone.getText().toString().isEmpty() && phone.getText().toString().length()==10)
            {
                if(!verificationInProgress)
                {
                    next.setEnabled(false);
                    String pno="+91"+phone.getText().toString();
                    Log.d(TAG,"onClick: Phone number: "+pno);
                    pBar.setVisibility(View.VISIBLE);
                    state.setText("Sending OTP");
                    state.setVisibility(View.VISIBLE);
                    verifyNum(pno);

                }
                else
                {
                    next.setEnabled(false);
                    otp.setVisibility(View.GONE);
                    pBar.setVisibility(View.VISIBLE);
                    state.setText("Logging In");
                    state.setVisibility(View.VISIBLE);
                    String sentOtp=otp.getText().toString();
                    if(sentOtp.isEmpty())
                    {
                        otp.setError("Required");
                        return;
                    }
                    PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationID,sentOtp);
                    verifyAuth(credential);
                }
            }
            else
            {
                    otp.setError("Enter Valid OTP");
            }
            }
        });

    }
    private void verifyNum(String phoneno)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneno, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeAutoRetrievalTimeOut(String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                //Toast.makeText(MainActivity.this,"OTP Expired,Re-Request OTP",Toast.LENGTH_SHORT).show();
                resend.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verificationID=s;
                Token=forceResendingToken;
                pBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                otp.setVisibility(View.VISIBLE);
                next.setText("Verify");
                next.setEnabled(true);
                verificationInProgress=true;
            }



            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                verifyAuth(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e)
            {
                Toast.makeText(MainActivity.this, "Error Sending OTP "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


private void verifyAuth(PhoneAuthCredential credential) {

    fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {

            if(task.isSuccessful())
            {
                //Checking if user exists or not
                Toast.makeText(MainActivity.this, "Phone Verified", Toast.LENGTH_SHORT).show();
                checkUserProfile();;
            }
            else
            {
                pBar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
            }
        }
    });
    }
    @Override
    protected void onStart() {
        super.onStart();

        if(fAuth.getCurrentUser() != null){
            pBar.setVisibility(View.VISIBLE);
            state.setText("Checking..");
            state.setVisibility(View.VISIBLE);
            checkUserProfile();
        }
    }
    private void checkUserProfile()
    {
        DocumentReference docRef=fBase.collection("users").document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                if(documentSnapshot.exists())
                {
                    startActivity(new Intent(getApplicationContext(),dashboard.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(),Details.class));
                    finish();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"Profile Does not Exist",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

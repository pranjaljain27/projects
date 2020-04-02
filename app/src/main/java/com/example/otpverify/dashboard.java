package com.example.otpverify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

import javax.annotation.Nullable;

public class dashboard extends AppCompatActivity {
    TextView oName, bName, email, add, local, cit, phoneNo, states, categor;
    FirebaseAuth fAuth;
    Button log;
    FirebaseFirestore fBase;
    String userID;
    private static final String TAG ="TAG" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        fAuth = FirebaseAuth.getInstance();
        fBase = FirebaseFirestore.getInstance();
        oName = findViewById(R.id.name);
        bName = findViewById(R.id.bname);
        email = findViewById(R.id.Email);
        add = findViewById(R.id.address);
        local = findViewById(R.id.loc);
        cit = findViewById(R.id.city);
        phoneNo = findViewById(R.id.phone);
        states = findViewById(R.id.state);
        categor = findViewById(R.id.category);
        log=findViewById(R.id.logmeout);
        userID= Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });

        DocumentReference docRef= fBase.collection("users").document(userID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists())
                {
                    String ph=fAuth.getCurrentUser().getPhoneNumber();

                oName.setText(documentSnapshot.getString("Owner"));
                    bName.setText(documentSnapshot.getString("BusinessName"));
                    email.setText(documentSnapshot.getString("Email"));
                    add.setText(documentSnapshot.getString("Address"));
                    local.setText(documentSnapshot.getString("Locality"));
                    cit.setText(documentSnapshot.getString("City"));
                    states.setText(documentSnapshot.getString("State"));
                    categor.setText(documentSnapshot.getString("Category"));
                    phoneNo.setText(ph);
            }
                else
                {
                    Log.d(TAG, "Profile Not Found");
                }
        }
        });
}
    }


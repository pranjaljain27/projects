package com.example.otpverify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Update extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fBase;
    Button logBaby,upd;
    Spinner feel;
    TextView val;
    ImageView back;
    ProgressBar pBarr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        fAuth=FirebaseAuth.getInstance();
        fBase=FirebaseFirestore.getInstance();
        final DocumentReference docRef=fBase.collection("users").document(fAuth.getCurrentUser().getUid());
        logBaby=findViewById(R.id.firselogout);
        feel=(Spinner)findViewById(R.id.field);
        val=findViewById(R.id.value);
        back=findViewById(R.id.backarrow);
        upd=findViewById(R.id.updatebutton);
        pBarr=findViewById(R.id.progressBar2);
        logBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),dashboard.class));
            }
        });
        upd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pBarr.setVisibility(View.VISIBLE);
                String data= val.getText().toString();
                if(TextUtils.isEmpty(data))
                {
                    val.setError("Field Cannot Be Empty");
                    return;
                }
                final String change=feel.getSelectedItem().toString();
                Map<String,Object> user=new HashMap<>();
                user.put(change,data);
                docRef.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Update.this,"Data Successfully Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),dashboard.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Update.this,"Error Updating Data",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),dashboard.class));
                    }
                });
            }
        });
    }
}

package com.example.otpverify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.opencensus.trace.unsafe.ContextUtils;

public class Details extends AppCompatActivity{
    EditText Oname,Bname,email,add,local,city;
    Button submit;
    CheckBox own,correct;
    Spinner category,state;
    FirebaseFirestore fBase;
    FirebaseAuth fAuth;
    String userID;
    String text1,text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Oname=findViewById(R.id.owner);
        Bname=findViewById(R.id.bname);
        email=findViewById(R.id.mail);
        add=findViewById(R.id.address);
        local=findViewById(R.id.locality);
        city=findViewById(R.id.city);
        submit=findViewById(R.id.Submit);
        own=findViewById(R.id.checkBox1);
        correct=findViewById(R.id.checkBox2);
        fBase=FirebaseFirestore.getInstance();
        fAuth=FirebaseAuth.getInstance();
        category=(Spinner)findViewById(R.id.categories);
        state=(Spinner)findViewById(R.id.states);
        userID= Objects.requireNonNull(fAuth.getCurrentUser()).getUid();

        submit.setOnClickListener(new View.OnClickListener() {
            private static final String TAG = "TAG";

            @Override
            public void onClick(View v) {
                String pName=Oname.getText().toString();
                String busName=Bname.getText().toString();
                String mail=email.getText().toString().trim();
                String address=add.getText().toString();
                String citY=city.getText().toString();
                String locality=local.getText().toString();
                String cat=category.getSelectedItem().toString();
                String sta=state.getSelectedItem().toString();
                if(TextUtils.isEmpty(pName))
                {
                    Oname.setError("Owner Name Cannot Be Empty");
                    return;
                }
                if(TextUtils.isEmpty(busName))
                {
                    Bname.setError("Business Name Cannot Be Empty");
                    return;
                }
                if(TextUtils.isEmpty(mail))
                {
                    email.setError("Email Cannot Be Empty");
                    return;
                }
                if(TextUtils.isEmpty(address))
                {
                    add.setError("Address Cannot Be Empty");
                    return;
                }
                if(TextUtils.isEmpty(citY))
                {
                    city.setError("City Cannot Be Empty");
                    return;
                }
                if(TextUtils.isEmpty(locality))
                {
                    local.setError("Locality Cannot Be Empty");
                    return;
                }
                if(!own.isChecked())
                {
                    own.setError("This Field Should Be Ticked");
                    return;
                }
                if(!correct.isChecked()) {
                    correct.setError("This Field Should Be Ticked");
                    return;
                }

                DocumentReference docRef=fBase.collection("users").document(userID);
                Map<String,Object> user=new HashMap<>();
                user.put("Owner",pName);
                user.put("BusinessName",busName);
                user.put("Email",mail);
                user.put("Address",address);
                user.put("Locality",locality);
                user.put("City",citY);
                user.put("State",sta);
                user.put("Category",cat);

                docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            startActivity(new Intent(getApplicationContext(),dashboard.class));
                        }
                        else
                        {
                            Toast.makeText(Details.this,"Data Cannot Be Inserted",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}

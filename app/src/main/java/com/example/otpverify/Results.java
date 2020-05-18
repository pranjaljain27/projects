package com.example.otpverify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.otpverify.Model.Detail;
import com.example.otpverify.RecyclerAdapter.RecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class Results extends AppCompatActivity {

    FirebaseAuth.AuthStateListener mAuthListener;
    private RecyclerView recyclerView;
    private ArrayList<Detail> detailArrayList=new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private DocumentSnapshot queriedDocument;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getDetails();
    }

    private void getDetails() {
        FirebaseFirestore db=FirebaseFirestore.getInstance();
        CollectionReference dataCollection=db.collection("users");
        dataCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                for(DocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult()))
                {
                    Detail detail=new Detail(querySnapshot.getString("Owner"),
                                                querySnapshot.getString("BusinessName"),
                                                querySnapshot.getString("Email"),
                                                querySnapshot.getString("Address"),
                                                querySnapshot.getString("Locality"),
                                                querySnapshot.getString("City"),
                                                querySnapshot.getString("State"),
                                                querySnapshot.getString("Category"));
                    detailArrayList.add(detail);
                }
                recyclerAdapter=new RecyclerAdapter(Results.this,detailArrayList);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });
    }

}

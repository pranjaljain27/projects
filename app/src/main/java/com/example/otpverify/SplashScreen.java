package com.example.otpverify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.otpverify.Model.Detail;
import com.example.otpverify.RecyclerAdapter.RecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class SplashScreen extends AppCompatActivity {
    private SearchView searchView;
    FirebaseAuth.AuthStateListener mAuthListener;
    private RecyclerView recyclerView;
    private ArrayList<Detail> detailArrayList=new ArrayList<>();
    private RecyclerAdapter recyclerAdapter;
    private DocumentSnapshot queriedDocument;
    Button signButton;
    Button catBtn;
    private static int SPLASH_TIME_OUT =4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        searchView=findViewById(R.id.search_view);
        recyclerView=findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getDetails();

        signButton=findViewById(R.id.btnSignUp);
        catBtn=findViewById(R.id.btnCategory);
        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        catBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreen.this,Activity_Category.class));
            }
        });

        //View searchPlate = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });

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
                recyclerAdapter=new RecyclerAdapter(SplashScreen.this,detailArrayList);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });
    }
}



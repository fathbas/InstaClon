package com.fatihb.instaclon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    ArrayList<String>userE;
    ArrayList<String>userC;
    ArrayList<String>userU;
    reAdaptor reAdaptor;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.upload){
            Intent intentToUp = new Intent(FeedActivity.this,Upload.class);
            startActivity(intentToUp);
        }else if (item.getItemId() == R.id.signOut){

            firebaseAuth.signOut();

            Intent intentOut = new Intent(FeedActivity.this,SignUpActivity.class);
            startActivity(intentOut);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        userC = new ArrayList<>();
        userE = new ArrayList<>();
        userU = new ArrayList<>();

        firebaseAuth=FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        getData();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reAdaptor = new reAdaptor(userE,userC,userU);
        recyclerView.setAdapter(reAdaptor);
    }

    public void getData(){
        CollectionReference collectionReference = firestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    Toast.makeText(FeedActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
                }else {
                 if (queryDocumentSnapshots != null){
                     for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                         Map<String,Object> data = documentSnapshot.getData();
                         String comment = (String)data.get("comment");
                         String email = (String)data.get("email");
                         String downloadUrl = (String)data.get("dowUrl");
                         userC.add(comment);
                         userE.add(email);
                         userU.add(downloadUrl);

                         reAdaptor.notifyDataSetChanged();
                     }
                  }
                }
            }
        });
    }
}

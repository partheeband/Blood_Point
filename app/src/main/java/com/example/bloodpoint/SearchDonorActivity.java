package com.example.bloodpoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchDonorActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SearchDonorAdapter adapter;
    private List<UserInformation> DonorList;
    DatabaseReference dbDonor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_donor);
        getSupportActionBar().hide();

        recyclerView= findViewById(R.id.recyclerViewDonorDetails);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DonorList = new ArrayList<>();
        adapter = new SearchDonorAdapter(this, DonorList);
        recyclerView.setAdapter(adapter);

        //1. SELECT * FROM Artists
        dbDonor = FirebaseDatabase.getInstance().getReference("userprofile") ;
        dbDonor.addListenerForSingleValueEvent(valueEventListener);
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            DonorList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserInformation donordetails = snapshot.getValue(UserInformation.class);
                    DonorList.add(donordetails);
                }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        UserInformation donordetails = snapshot.getValue(UserInformation.class);
                        DonorList.add(donordetails);
                    }
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserInformation donordetails = snapshot.getValue(UserInformation.class);
                    DonorList.add(donordetails);
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}

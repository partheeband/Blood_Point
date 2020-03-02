package com.example.bloodpoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private List<UserInformationHelper> DonorList;
    DatabaseReference dbDonor;
    FirebaseUser user;
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

        user = FirebaseAuth.getInstance().getCurrentUser();
        //1. SELECT * FROM Artists
        dbDonor = FirebaseDatabase.getInstance().getReference("userprofile");
        dbDonor.addListenerForSingleValueEvent(valueEventListener);
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            DonorList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserInformationHelper donordetails = snapshot.getValue(UserInformationHelper.class);
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

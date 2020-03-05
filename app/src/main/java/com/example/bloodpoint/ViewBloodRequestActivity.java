package com.example.bloodpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewBloodRequestActivity extends AppCompatActivity implements View.OnClickListener  {
    private RecyclerView recyclerView;
    private ImageView buttonBack;
    private ViewBloodRequestAdapter adapter;
    private List<BloodRequestsHelper> BloodRequestList;
    Query dbBloodRequests,myRef;
    FirebaseUser user;
    UserInformationHelper userinformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blood_request);

        getSupportActionBar().hide();

        buttonBack=(ImageView)findViewById(R.id.imageViewBack);
        buttonBack.setOnClickListener(this);

        recyclerView= findViewById(R.id.recyclerViewBloodRequests);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BloodRequestList = new ArrayList<>();
        adapter = new ViewBloodRequestAdapter(this, BloodRequestList);
        recyclerView.setAdapter(adapter);

        //to retrieve current user blood type
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRef=FirebaseDatabase.getInstance().getReference("userprofile/"+user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userinformation = dataSnapshot.getValue(UserInformationHelper.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
//TO Access blood request from database
        dbBloodRequests = FirebaseDatabase.getInstance().getReference("bloodrequests");
        dbBloodRequests.addListenerForSingleValueEvent(valueEventListener);
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            BloodRequestList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    BloodRequestsHelper bloodrequestdetails = snapshot.getValue(BloodRequestsHelper.class);
                    if (!bloodrequestdetails.userid.equals(user.getUid())) {
//                        if (bloodrequestdetails.bloodgroup.equals(bloodDonationMapping(userinformation.bloodgroup))) {
//                            BloodRequestList.add(bloodrequestdetails);
//                        }
                        for (Object bloodtype:bloodDonationMapping(userinformation.bloodgroup))
                        {
                            if (bloodrequestdetails.bloodgroup.equals(bloodtype)){
                                BloodRequestList.add(bloodrequestdetails);
                            }
                        }
                        //BloodRequestList.add(bloodrequestdetails);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    @Override
    public void onClick(View view) {
        if (view==buttonBack)
        {
            Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
            startActivity(intent);
            finish();
        }
    }
    private Object[] bloodDonationMapping(String bloodType)
    {
        ArrayList<String> canDonateBloodTo =new ArrayList<String>();
        switch(bloodType)
        {
            case "O+":
            {
                canDonateBloodTo.add("O+");
                canDonateBloodTo.add("A+");
                canDonateBloodTo.add("B+");
                canDonateBloodTo.add("AB+");
                break;
            }
            case "A+":
            {
                canDonateBloodTo.add("A+");
                canDonateBloodTo.add("AB+");
                break;
            }
            case "B+":
            {
                canDonateBloodTo.add("B+");
                canDonateBloodTo.add("AB+");
                break;
            }
            case "AB+":
            {
                canDonateBloodTo.add("AB+");
                break;
            }
            case "O-":
            {
                canDonateBloodTo.add("O+");
                canDonateBloodTo.add("A+");
                canDonateBloodTo.add("B+");
                canDonateBloodTo.add("AB+");
                canDonateBloodTo.add("O-");
                canDonateBloodTo.add("A-");
                canDonateBloodTo.add("B-");
                canDonateBloodTo.add("AB-");
                break;
            }
            case "A-":
            {
                canDonateBloodTo.add("A-");
                canDonateBloodTo.add("A+");
                canDonateBloodTo.add("AB-");
                canDonateBloodTo.add("AB+");
                break;
            }
            case "B-":
            {
                canDonateBloodTo.add("B-");
                canDonateBloodTo.add("B+");
                canDonateBloodTo.add("AB-");
                canDonateBloodTo.add("AB+");
                break;
            }
            case "AB-":
            {
                canDonateBloodTo.add("AB-");
                canDonateBloodTo.add("AB+");
                break;
            }
        }
        return canDonateBloodTo.toArray();
    }
}

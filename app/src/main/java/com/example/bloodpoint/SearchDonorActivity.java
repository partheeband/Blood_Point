package com.example.bloodpoint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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

public class SearchDonorActivity extends AppCompatActivity implements View.OnClickListener ,SearchDonorAdapter.onNoteListener{
    private RecyclerView recyclerView;
    ImageView buttonBack,buttonSearch;
    private SearchDonorAdapter adapter;
    private List<UserInformationHelper> DonorList= new ArrayList<>();
    DatabaseReference dbDonor;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_donor);
        getSupportActionBar().hide();

        recyclerView= findViewById(R.id.recyclerViewBloodRequests);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        buttonBack=(ImageView)findViewById(R.id.imageViewBack);
        buttonSearch=(ImageView)findViewById(R.id.imageViewSearch);

        buttonBack.setOnClickListener(this);
        buttonSearch.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        //1. SELECT * FROM Artists
        dbDonor = FirebaseDatabase.getInstance().getReference("userprofile");
        dbDonor.addListenerForSingleValueEvent(valueEventListener);

        adapter = new SearchDonorAdapter(this, DonorList,this);
        recyclerView.setAdapter(adapter);
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            DonorList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserInformationHelper donordetails = snapshot.getValue(UserInformationHelper.class);
                    if (donordetails.isDonor &&!user.getEmail().equals(donordetails.email))
                    {
                        DonorList.add(donordetails);
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
        else if (view==buttonSearch)
        {
            AlertDialog.Builder mBuilder=new AlertDialog.Builder(SearchDonorActivity.this);
            View mView=getLayoutInflater().inflate(R.layout.alertdialog_searchdonor_with_filter,null);

            ImageView buttonSearchFilter=(ImageView) mView.findViewById(R.id.imageViewSearchFilter);

            final Spinner spinnerBloodGroup=(Spinner)mView.findViewById(R.id.spinnerBloodGroup);
            String[] bloodgroupArray={"BloodGroup","A+","A-","B+","B-","O+","O-","AB+","AB-"};
            ArrayAdapter<String> bloodgroupAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bloodgroupArray);
            spinnerBloodGroup.setAdapter(bloodgroupAdapter);

            mBuilder.setView(mView);
            final AlertDialog dialog=mBuilder.create();
            dialog.show();

            buttonSearchFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int spinner_bloodgroup_position = spinnerBloodGroup.getSelectedItemPosition();
                    String bloodGroupSelected=computeBloodgroup(spinner_bloodgroup_position);
                    Toast.makeText(SearchDonorActivity.this, bloodGroupSelected, Toast.LENGTH_SHORT).show();


                    if (!bloodGroupSelected.equals("All")) {

                        recyclerView= findViewById(R.id.recyclerViewBloodRequests);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        DonorList = new ArrayList<>();

                        //1. SELECT * FROM userprofile where bloodgroup=selectedbloodgroup
                        Query query = FirebaseDatabase.getInstance().getReference("userprofile")
                                .orderByChild("bloodgroup")
                                .equalTo(bloodGroupSelected);
                        query.addListenerForSingleValueEvent(valueEventListener);

                        adapter = new SearchDonorAdapter(getApplicationContext(), DonorList,SearchDonorActivity.this);
                        recyclerView.setAdapter(adapter);
                    }
                    else
                    {
                        recyclerView= findViewById(R.id.recyclerViewBloodRequests);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        DonorList = new ArrayList<>();

                        //1. SELECT * FROM userprofile where bloodgroup=selectedbloodgroup
                        Query query = FirebaseDatabase.getInstance().getReference("userprofile");
                        query.addListenerForSingleValueEvent(valueEventListener);

                        adapter = new SearchDonorAdapter(getApplicationContext(), DonorList,SearchDonorActivity.this);
                        recyclerView.setAdapter(adapter);
                    }

                    dialog.dismiss();
                }
            });
        }

    }
    private String computeBloodgroup(int spinner_bloodgroup_position) {
        String bloodgroup="";
        switch(spinner_bloodgroup_position){
            case 1:
            {
                bloodgroup="A+";
                break;
            }
            case 2:
            {
                bloodgroup="A-";
                break;
            }
            case 3:
            {
                bloodgroup="B+";
                break;
            }
            case 4:
            {
                bloodgroup="B-";
                break;
            }
            case 5:
            {
                bloodgroup="O+";
                break;
            }
            case 6:
            {
                bloodgroup="O-";
                break;
            }
            case 7:
            {
                bloodgroup="AB+";
                break;
            }
            case 8:
            {
                bloodgroup="AB-";
                break;
            }
            default:
                bloodgroup="All";
        }
        return bloodgroup;
    }

    @Override
    public void onNoteClick(int position) {
        UserInformationHelper userInformation=DonorList.get(position);
        Toast.makeText(this, "Calling "+userInformation.phoneno, Toast.LENGTH_SHORT).show();

        String phone =userInformation.phoneno;
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
        Log.d("cardviewPosition", String.valueOf(position));
    }
}

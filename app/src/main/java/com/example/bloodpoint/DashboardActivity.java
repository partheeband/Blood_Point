package com.example.bloodpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView UserInfo,FindDonor,SaveALife,RequestBlood;
    Button Logout;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().hide(); // hide the title bar
        UserInfo=(ImageView)findViewById(R.id.imageViewUserInfo);
        FindDonor=(ImageView)findViewById(R.id.imageViewSearchDonor);
        SaveALife=(ImageView)findViewById(R.id.imageViewSaveALife);
        RequestBlood=(ImageView)findViewById(R.id.imageViewRequestBlood);
        Logout=(Button)findViewById(R.id.buttonLogout);

        UserInfo.setOnClickListener(this);
        FindDonor.setOnClickListener(this);
        SaveALife.setOnClickListener(this);
        RequestBlood.setOnClickListener(this);
        Logout.setOnClickListener(this);



        firebaseAuth = FirebaseAuth.getInstance();
    }
    @Override
    public void onClick(View view) {
        if (view==UserInfo){
            Intent intent=new Intent(getApplicationContext(),ViewProfileActivity.class);
            startActivity(intent);

        }
        else if (view==FindDonor){
            Intent intent=new Intent(getApplicationContext(),SearchDonorActivity.class);
            startActivity(intent);
        }
        else if (view==RequestBlood){
            Intent intent=new Intent(getApplicationContext(),RequestBloodActivity.class);
            startActivity(intent);
        }
        else if (view==SaveALife){
            Intent intent=new Intent(getApplicationContext(),DashboardActivity.class);
            //startActivity(intent);
        }
        else if (view==Logout){
            firebaseAuth.signOut();
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

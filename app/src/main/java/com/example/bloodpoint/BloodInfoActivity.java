package com.example.bloodpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class BloodInfoActivity extends AppCompatActivity {
    ImageView linkedin1,linkedin2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_info);

        getSupportActionBar().hide(); // hide the title bar

        linkedin1=(ImageView)findViewById(R.id.imageViewlinkedIn);
        linkedin2=(ImageView)findViewById(R.id.imageViewlinkedIn1);

        linkedin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),DeveloperInfoActivity.class);
                startActivity(intent);
            }
        });
        linkedin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),DeveloperInfoActivity.class);
                startActivity(intent);
            }
        });

    }
}

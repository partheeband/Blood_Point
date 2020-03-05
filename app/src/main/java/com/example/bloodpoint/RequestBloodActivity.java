package com.example.bloodpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RequestBloodActivity extends AppCompatActivity implements View.OnClickListener{
EditText editTextPatientName,editTextHospitalName,editTextHospitalLocation,editTextContactNo,editTextReasonForRequest;
ImageView buttonCancel,buttonUpdate;
Spinner spinnerGender,spinnerBloodGroup;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_blood);
        getSupportActionBar().hide(); // hide the title bar

        editTextPatientName=(EditText)findViewById(R.id.editTextPatientName);
        editTextHospitalName=(EditText)findViewById(R.id.editTextHospitalName);
        editTextHospitalLocation=(EditText)findViewById(R.id.editTextHospitalCity);
        editTextContactNo=(EditText)findViewById(R.id.editTextContactNo);
        editTextReasonForRequest=(EditText)findViewById(R.id.editTextReasonForRequest);
        buttonCancel=(ImageView) findViewById(R.id.imageViewCancelRequest);
        buttonUpdate=(ImageView) findViewById(R.id.imageViewUpdateRequest);
        spinnerGender=(Spinner)findViewById(R.id.spinnerGender);
        spinnerBloodGroup=(Spinner)findViewById(R.id.spinnerBloodGroup);

        progressDialog= new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();
        databaseReference= FirebaseDatabase.getInstance().getReference();

        String[] genderArray={"Select Gender","Female","Male"};
        String[] bloodgroupArray={"BloodGroup","A+","A-","B+","B-","O+","O-","AB+","AB-"};

        ArrayAdapter<String> genderAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,genderArray);
        spinnerGender.setAdapter(genderAdapter);

        ArrayAdapter<String> bloodgroupAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,bloodgroupArray);
        spinnerBloodGroup.setAdapter(bloodgroupAdapter);



        buttonCancel.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view==buttonCancel){
            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
            finish();
        }
        else if (view==buttonUpdate){
            PostRequest();
        }
    }

    private void PostRequest() {
        final String patientName=editTextPatientName.getText().toString().trim();
        final String gender,bloodgroup;
        final String hospitalName=editTextHospitalName.getText().toString().trim();
        final String hospitalLocation=editTextHospitalLocation.getText().toString().trim();
        final String contactNo = editTextContactNo.getText().toString().trim();
        final String reasonForRequest = editTextReasonForRequest.getText().toString().trim();

        int spinner_gender_position = spinnerGender.getSelectedItemPosition();
        int spinner_bloodgroup_position = spinnerBloodGroup.getSelectedItemPosition();

        if (TextUtils.isEmpty(patientName))
        {
            Toast.makeText(this, "Please enter Patient Name", Toast.LENGTH_LONG).show();
            return;
        }
        if (spinner_gender_position == 0)
        {
            Toast.makeText(this, "Please select gender", Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            gender=computeGender(spinner_gender_position);
        }
        if (spinner_bloodgroup_position == 0)
        {
            Toast.makeText(this, "Please select Blood Group", Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            bloodgroup=computeBloodgroup(spinner_bloodgroup_position);
        }
        if (TextUtils.isEmpty(hospitalName))
        {
            Toast.makeText(this, "Please enter Hospital Name", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(hospitalLocation))
        {
            Toast.makeText(this, "Please enter Hospital Location", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(contactNo))
        {
            Toast.makeText(this, "Please enter Contact Number", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(reasonForRequest))
        {
            Toast.makeText(this, "Please enter Reason For Request", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Posting Request Please Wait...");
        progressDialog.show();
        String currentDateTime=getCurrentTimeStamp();


        FirebaseUser user=firebaseAuth.getCurrentUser();
        BloodRequestsHelper postBloodRequest=new BloodRequestsHelper(patientName,hospitalName,hospitalLocation,gender,bloodgroup,contactNo,reasonForRequest,currentDateTime,user.getUid());

        databaseReference.child("bloodrequests").child(currentDateTime).setValue(postBloodRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(RequestBloodActivity.this, "Blood Request Has been Posted successfully", Toast.LENGTH_SHORT).show();
                        //start Dashboard activity
                        finish();
                        startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                }
                });
    }

    private static String getCurrentTimeStamp(){
        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String currentDateTime = dateFormat.format(new Date()); // Find todays date

            return currentDateTime;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
    private String computeGender(int spinner_gender_position) {
        String gender="";
        if(spinner_gender_position==1)
        {
            gender="FEMALE";
        }
        else if (spinner_gender_position==2)
        {
            gender="MALE";
        }
        return gender;
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
                throw new IllegalStateException("Unexpected value: " + spinner_bloodgroup_position);
        }
        return bloodgroup;
    }
}

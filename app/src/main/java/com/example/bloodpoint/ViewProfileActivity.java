package com.example.bloodpoint;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ViewProfileActivity extends AppCompatActivity implements View.OnClickListener{
    TextView textViewName,textViewEmail,textViewBloodGroup,textViewGender,textViewTotalDonation,textViewLastDonationDate;
    EditText editTextCity,editTextPhoneNo;
    RadioButton radioButtonDonor,radioButtonUser;
    ImageView buttonEditPersonalDetails,buttonEditAchievements,buttonUpdatePersonalDetails,buttonHome;

    private ProgressDialog progressDialog;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef,myRefAchievements,databaseReference_update;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        getSupportActionBar().hide(); // hide the title bar

        textViewName=(TextView)findViewById(R.id.textViewUserName);
        textViewEmail=(TextView)findViewById(R.id.textViewEmail);
        textViewBloodGroup=(TextView)findViewById(R.id.textViewBloodGroup);
        textViewGender=(TextView)findViewById(R.id.textViewGender);
        textViewTotalDonation=(TextView)findViewById(R.id.textViewTotalDonation);
        textViewLastDonationDate=(TextView)findViewById(R.id.textViewLastDonationDate);
        editTextPhoneNo=(EditText)findViewById(R.id.editTextPhoneNo);
        editTextCity=(EditText)findViewById(R.id.editTextCity);

        buttonEditPersonalDetails=(ImageView) findViewById(R.id.imageViewEditUserInfo);
        buttonEditAchievements=(ImageView) findViewById(R.id.imageViewEditAchievements);
        //block for update buttons
        {
            buttonUpdatePersonalDetails = (ImageView) findViewById(R.id.imageViewUpdatePersonalDetails);
            //initially disable this button
            buttonUpdatePersonalDetails.setVisibility(View.INVISIBLE);

        }
        buttonHome=(ImageView) findViewById(R.id.imageViewhome);

        radioButtonDonor=(RadioButton)findViewById(R.id.radioButton);
        radioButtonUser=(RadioButton)findViewById(R.id.radioButton2);

        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        buttonEditPersonalDetails.setOnClickListener(this);
        buttonEditAchievements.setOnClickListener(this);
        buttonHome.setOnClickListener(this);
        buttonUpdatePersonalDetails.setOnClickListener(this);


        //this to prevent editing in viewing userinfo
        {
            editTextCity.setFocusable(false);
            editTextPhoneNo.setFocusable(false);


            radioButtonDonor.setClickable(false);
            radioButtonUser.setClickable(false);
        }


//firebase stuffs
        firebaseDatabase=FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();


        String userEmail = user.getEmail();
        textViewEmail.setText(userEmail);

        //retrieve user data from firebase
        myRef=firebaseDatabase.getReference("userprofile/"+user.getUid());

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                UserInformation userinformation = dataSnapshot.getValue(UserInformation.class);

                Log.d("data", "Value is: " + userinformation);
                //Toast.makeText(getApplicationContext(), userinformation.name+" "+userinformation.city+" "+userinformation.phoneno+" "+userinformation.gender+" "+userinformation.bloodgroup+" "+userinformation.isDonor, Toast.LENGTH_SHORT).show();

                textViewName.setText(userinformation.name);
                textViewBloodGroup.setText(userinformation.bloodgroup);
                textViewGender.setText(userinformation.gender);
                editTextCity.setText(userinformation.city);
                editTextPhoneNo.setText(userinformation.phoneno);
                textViewTotalDonation.setText(String.valueOf(userinformation.totalDonations));
                textViewLastDonationDate.setText(userinformation.lastDonationDate);

                //to stop donating more than one time a day.
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Calendar c = Calendar.getInstance();
                String date = sdf.format(c.getTime());
                String getLastDonationDate=textViewLastDonationDate.getText().toString().trim();
                String totalDonation=textViewTotalDonation.getText().toString().trim();
                if (userinformation.isDonor==true)
                {
                    radioButtonDonor.setChecked(true);
                }
                else {
                    radioButtonUser.setChecked(true);
                }
                progressDialog.dismiss();
                if (getLastDonationDate.equals("Nil")&&totalDonation.equals("0")){
                    buttonEditAchievements.setVisibility(View.VISIBLE);
                }
                else
                {
                    if (dateDifference(date,getLastDonationDate)<90)
                    {
                        buttonEditAchievements.setVisibility(View.GONE);
                    }
                    else
                        buttonEditAchievements.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("data", "Failed to read value.", error.toException());
                Toast.makeText(getApplicationContext(), "Failed to read value.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private float dateDifference(String date, String getLastDonationDate) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dateBeforeString = getLastDonationDate;
        String dateAfterString = date;

        try {
            Date dateBefore = myFormat.parse(dateBeforeString);
            Date dateAfter = myFormat.parse(dateAfterString);
            long difference = dateAfter.getTime() - dateBefore.getTime();
            float daysBetween = (difference / (1000*60*60*24));
            /* You can also convert the milliseconds to days using this method
             * float daysBetween =
             *         TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS)
             */
            return daysBetween;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void onClick(View view) {
        if(view==buttonEditPersonalDetails)
        {
            buttonEditPersonalDetails.setVisibility(View.INVISIBLE);
            buttonUpdatePersonalDetails.setVisibility(View.VISIBLE);

            textViewName.setBackgroundResource(R.drawable.textbox_low_gradient);
            textViewEmail.setBackgroundResource(R.drawable.textbox_low_gradient);
            textViewBloodGroup.setBackgroundResource(R.drawable.textbox_low_gradient);
            textViewGender.setBackgroundResource(R.drawable.textbox_low_gradient);
            editTextCity.setBackgroundResource(R.drawable.textbox_high_gradient);
            editTextPhoneNo.setBackgroundResource(R.drawable.textbox_high_gradient);

            editTextCity.setFocusableInTouchMode(true);
            editTextCity.requestFocus();
            editTextPhoneNo.setFocusableInTouchMode(true);
            radioButtonDonor.setClickable(true);
            radioButtonUser.setClickable(true);

        }
        else if (view==buttonEditAchievements)
        {
            final String strTotalDonation= textViewTotalDonation.getText().toString().trim();

            AlertDialog.Builder mBuilder=new AlertDialog.Builder(ViewProfileActivity.this);
            View mView=getLayoutInflater().inflate(R.layout.alertdialog_achievements,null);
            Button buttonYes=(Button) mView.findViewById(R.id.buttonYes);
            Button buttonNo=(Button) mView.findViewById(R.id.buttonNo);
            mBuilder.setView(mView);
            final AlertDialog dialog=mBuilder.create();
            dialog.show();

            buttonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar c = Calendar.getInstance();
                    String date = sdf.format(c.getTime());
                    int totalDonation=Integer.parseInt(strTotalDonation);
                    totalDonation++;
                    UserInformation userInformation=new UserInformation(totalDonation,date);

                    firebaseDatabase=FirebaseDatabase.getInstance();
                    user = FirebaseAuth.getInstance().getCurrentUser();
                    databaseReference_update= FirebaseDatabase.getInstance().getReference();
                    Map<String,Object> taskMap = new HashMap<String,Object>();
                    taskMap.put("totalDonations",userInformation.totalDonations);
                    taskMap.put("lastDonationDate",userInformation.lastDonationDate);
                    databaseReference_update.child("userprofile").child(user.getUid()).updateChildren(taskMap);
                    dialog.dismiss();

                }
            });
            buttonNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

        }
        else if (view==buttonUpdatePersonalDetails)
        {
            if (editTextPhoneNo.getText().toString().trim().length() == 10 && editTextCity.getText().toString().trim().length() != 0)
            {
                UserInformation userInformation_PersonalDetails = new UserInformation(editTextCity.getText().toString().trim(),editTextPhoneNo.getText().toString().trim(), radioButtonDonor.isChecked());
                firebaseDatabase = FirebaseDatabase.getInstance();
                user = FirebaseAuth.getInstance().getCurrentUser();
                databaseReference_update = FirebaseDatabase.getInstance().getReference();
                Map<String,Object> taskMap = new HashMap<String,Object>();
                taskMap.put("city",userInformation_PersonalDetails.city);
                taskMap.put("phoneno",userInformation_PersonalDetails.phoneno);
                taskMap.put("isDonor",userInformation_PersonalDetails.isDonor);
                databaseReference_update.child("userprofile").child(user.getUid()).updateChildren(taskMap);
            }
        }
        else if(view==buttonHome)
        {
            Intent intent =new Intent(getApplicationContext(),DashboardActivity.class);
            startActivity(intent);
            finish();
        }

    }
}

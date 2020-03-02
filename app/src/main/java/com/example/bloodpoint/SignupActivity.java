package com.example.bloodpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    Spinner spinner_gender,spinner_bloodgroup;
    EditText Name,City,Contactno,Email,Password,ConfirmPassword;
    Button signup;
    TextView signin;

    private ProgressDialog progressDialog;
       private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen



        spinner_gender=(Spinner)findViewById(R.id.spinner_gender);
        spinner_bloodgroup=(Spinner)findViewById(R.id.spinner_bloodgroup);

        signup=(Button)findViewById(R.id.buttonSignup);
        signin=(TextView)findViewById(R.id.textViewSignin);

        Name=(EditText) findViewById(R.id.editTextName);
        Contactno=(EditText) findViewById(R.id.editTextContact);
        City=(EditText) findViewById(R.id.editTextCity);
        Email=(EditText) findViewById(R.id.editTextEmail);
        Password=(EditText) findViewById(R.id.editTextPassword);
        ConfirmPassword=(EditText) findViewById(R.id.editTextConfirmPassword);

        progressDialog= new ProgressDialog(this);

      firebaseAuth=FirebaseAuth.getInstance();
      databaseReference= FirebaseDatabase.getInstance().getReference();


        String[] genderArray={"Select Gender","Female","Male"};
        String[] bloodgroupArray={"Select BloodGroup","A+","A-","B+","B-","O+","O-","AB+","AB-"};

        ArrayAdapter<String> genderAdapter=new ArrayAdapter<String>(this,R.layout.spinner_item_gender,R.id.spinner_textview_gender,genderArray);
        spinner_gender.setAdapter(genderAdapter);

        ArrayAdapter<String> bloodgroupAdapter=new ArrayAdapter<String>(this,R.layout.spinner_item_bloodgroup,R.id.spinner_textview_bloodgroup,bloodgroupArray);
        spinner_bloodgroup.setAdapter(bloodgroupAdapter);



        signup.setOnClickListener(this);
        signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==signup){
            registerUser();

        }
        else if (view==signin){
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();

        }
    }
    private void registerUser() {

        //getting email and password from edit texts
        final String name=Name.getText().toString().trim();
        final String gender,bloodgroup;
        final String city=City.getText().toString().trim();
        final String phoneno=Contactno.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String confirmpassword = ConfirmPassword.getText().toString().trim();
        final Boolean isDonor=true;
        final int totalDonation=0;
        final String lastDonationDate="Nil";

        int spinner_gender_position = spinner_gender.getSelectedItemPosition();
        int spinner_bloodgroup_position = spinner_bloodgroup.getSelectedItemPosition();


        //checking if email and passwords are empty
        if (TextUtils.isEmpty(name))
        {
            Toast.makeText(this, "Please enter name", Toast.LENGTH_LONG).show();
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
        if (spinner_bloodgroup_position==0)
        {
            Toast.makeText(this, "Please select Blood group", Toast.LENGTH_LONG).show();
            return;
        }
        else
        {
            bloodgroup=computeBloodgroup(spinner_bloodgroup_position);
        }
        if (TextUtils.isEmpty(city)) {
            Toast.makeText(this, "Please enter address", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(phoneno)) {
            Toast.makeText(this, "Please enter phone no", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(confirmpassword)) {
            Toast.makeText(this, "Please enter Confirm password", Toast.LENGTH_LONG).show();
            return;
        }
        if (!password.equals(confirmpassword))
        {
            Toast.makeText(this, "password mismatch", Toast.LENGTH_LONG).show();
            return;
        }



        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //display some message here
                            Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_LONG).show();
                            UserInformationHelper userInformation=new UserInformationHelper(name,city,phoneno,gender,bloodgroup,isDonor,totalDonation,lastDonationDate);


                            FirebaseUser user=firebaseAuth.getCurrentUser();
                            databaseReference.child("userprofile").child(user.getUid()).setValue(userInformation);
                            //start profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                        } else {
                            //display some message here
                            Toast.makeText(getApplicationContext(), "Registration Error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
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

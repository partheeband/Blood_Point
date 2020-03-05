package com.example.bloodpoint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button signin;
    EditText Email,Password;
    TextView signup;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide(); // hide the title bar

        firebaseAuth=FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser()!=null)
        {
            //start profile activity
            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
            finish();
        }


        signin=(Button)findViewById(R.id.buttonSignin);
        signup=(TextView)findViewById(R.id.textViewSignup);

        Email=(EditText) findViewById(R.id.editTextUserName);
        Password=(EditText) findViewById(R.id.editTextPassword);

        progressDialog= new ProgressDialog(this);

        firebaseAuth=FirebaseAuth.getInstance();

        signup.setOnClickListener(this);
        signin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view==signin){
            //check internet status
            if(!haveNetworkConnection())
            {
                Toast.makeText(this, "Please check your internet connection...", Toast.LENGTH_SHORT).show();
                return;
            }
            //login user
            loginUser();

        }
        else if (view==signup){
            Intent intent=new Intent(getApplicationContext(),SignupActivity.class);
            startActivity(intent);
            finish();

        }

    }

    private void loginUser() {
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage(" Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            //start profile activity
                            progressDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
                            finish();
                        }
                        else
                        {   progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Invalid username or Password", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}

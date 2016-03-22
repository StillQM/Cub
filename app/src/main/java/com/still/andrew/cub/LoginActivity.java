package com.still.andrew.cub;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.BinderThread;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final String FIREBASE_URL = "https://glaring-heat-9011.firebaseio.com/user";
    private String password;
    private String email;
    private boolean isAuthenticated;
    //private String userName;
    //private String userPassword;
    private User[] userArray = new User[3];
    private int userCounter = 0;

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        Firebase.setAndroidContext(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });

        new Firebase(FIREBASE_URL)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot){
                        for(DataSnapshot userSnapshot : snapshot.getChildren()) {
                            String userID = (String) userSnapshot.child("user_id").getValue();
                            String userName = (String) userSnapshot.child("user_name").getValue();
                            String userPassword = (String) userSnapshot.child("user_password").getValue();
                            String userType = (String) userSnapshot.child("user_type").getValue();
                            User user = new User(userID, userName, userPassword, userType);
                            user.toString();
                            System.out.println(userCounter);
                            userArray[userCounter] = user;
                            userCounter++;
                        }
                    }

                    public void onCancelled(FirebaseError firebaseError){

                    }
                });

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        email = _emailText.getText().toString();
        password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        //<editor-fold desc="Firebase"
        /*
        new Firebase(FIREBASE_URL)
                .addValueEventListener(new ValueEventListener() {
                   @Override
                public void onDataChange(DataSnapshot snapshot){
                       for(DataSnapshot userSnapshot : snapshot.getChildren()) {
                           String userID = (String) userSnapshot.child("user_id").getValue();
                           String userName = (String) userSnapshot.child("user_name").getValue();
                           String userPassword = (String) userSnapshot.child("user_password").getValue();
                           String userType = (String) userSnapshot.child("user_type").getValue();
                           User user = new User(userID, userName, userPassword, userType);
                           if(userID.equals(email) && userPassword.equals(password)){
                               isAuthenticated = true;
                               System.out.println(isAuthenticated);
                           } else {
                               isAuthenticated = false;
                               System.out.println(isAuthenticated);
                           }
                       }
                   }

                public void onCancelled(FirebaseError firebaseError){

                   }
                });
                */
        //</editor-fold>

        for(int i = 0; i < userCounter; i++){
            if(userArray[i].getUserName().equals(this.email) && userArray[i].getUserPassword().equals(this.password)){
                isAuthenticated = true;
                System.out.println(isAuthenticated);
                return;
            } else {
                isAuthenticated = false;
                System.out.println(isAuthenticated);
            }
        }



        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        if(isAuthenticated) {
                            onLoginSuccess();
                        } else {
                            onLoginFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}


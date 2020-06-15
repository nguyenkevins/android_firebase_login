package com.outbit.circle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    // Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    ProgressDialog processDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use activity_signup.xml as the layout for this class
        setContentView(R.layout.activity_signup);

        // initialize the FirebaseAuth instance.
        mAuth = FirebaseAuth.getInstance();
        processDialog = new ProgressDialog(this);
        final EditText usernameSignup = findViewById(R.id.usernameSIGNUP);
        final EditText passwordSignup = findViewById(R.id.passwordSIGNUP);
        final EditText confirmPasswordSignup = findViewById(R.id.confirmPasswordSIGNUP);
        Button activitySignupButton = findViewById(R.id.buttonSIGNUP);
        TextView textBackLoginLink = findViewById(R.id.backToLoginTextSIGNUP);
        getSupportActionBar().hide();

        processDialog.setMessage("Signing up...");

        activitySignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameSignup.getText().toString().trim();
                String password = passwordSignup.getText().toString().trim();
                String confirmPassword = confirmPasswordSignup.getText().toString().trim();

                // Make sure password and confirm password is the same before moving on
                if(password.equals(confirmPassword)) {
                    if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                        usernameSignup.setError("Invalid Email");
                        usernameSignup.setFocusable(true);
                    } else if(password.length() < 7) {
                        passwordSignup.setError("Password needs to be more than 7 in length");
                        passwordSignup.setFocusable(true);
                    } else {
                        registerUser(username, password);
                    }
                } else {
                    passwordSignup.setError("Password and Confirm Password mismatch");
                    passwordSignup.setFocusable(true);
                }
            }


        });

        textBackLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    private void registerUser(String username, String password) {
        processDialog.show();
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            processDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(SignupActivity.this, MainActivityMessage.class));
                            finish();
                        } else {
                            processDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignupActivity.this, "Failed registration!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                processDialog.dismiss();
                Toast.makeText(SignupActivity.this, ""+e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
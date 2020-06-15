package com.outbit.circle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    ProgressDialog processDialog;
    static SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();



        mAuth = FirebaseAuth.getInstance();
        processDialog = new ProgressDialog(this);
        sharedpreferences=getApplicationContext().getSharedPreferences("Preferences", 0);
        String login = sharedpreferences.getString("LOGIN", null);
        if (login != null) {
            startActivity(new Intent(LoginActivity.this, MainActivityMessage.class));
            //finish();
        } else {

        }

        processDialog.setMessage("Signing up...");

        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        Button activityLoginButton = findViewById(R.id.buttonLOGIN);
        TextView textCreateAccountLink = findViewById(R.id.newAccountText);

        activityLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameL = username.getText().toString().trim();
                String passwordL = password.getText().toString().trim();
                if(!Patterns.EMAIL_ADDRESS.matcher(usernameL).matches()){
                    username.setError("Invalid Email");
                    username.setFocusable(true);
                } else {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString("LOGIN", usernameL);
                    editor.commit();
                    loginUser(usernameL, passwordL);
                }
            }
        });

        textCreateAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

    }

    private void loginUser(String username, String password) {
        processDialog.show();

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            processDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(LoginActivity.this, MainActivityMessage.class));
                            //finish();
                        } else {
                            processDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Failed login!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                processDialog.dismiss();
                Toast.makeText(LoginActivity.this, ""+e.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

}
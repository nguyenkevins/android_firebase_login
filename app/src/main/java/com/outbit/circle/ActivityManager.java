package com.outbit.circle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
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

import static com.outbit.circle.LoginActivity.sharedpreferences;

public class ActivityManager extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    ProgressDialog processDialog;
    static SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        getSupportActionBar().hide();



        firebaseAuth = FirebaseAuth.getInstance();
        sharedpreferences=getApplicationContext().getSharedPreferences("Preferences", 0);
        String login = sharedpreferences.getString("LOGIN", null);
    }

    @Override
    protected void onStart() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null) {
            startActivity(new Intent(ActivityManager.this, MainActivityMessage.class));
            finish();
        } else {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove("LOGIN");
            editor.commit();
            startActivity(new Intent(ActivityManager.this, LoginActivity.class));
            finish();
        }
        super.onStart();
    }

}
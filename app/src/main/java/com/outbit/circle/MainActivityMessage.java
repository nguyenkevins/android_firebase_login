package com.outbit.circle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.outbit.circle.LoginActivity.sharedpreferences;

public class MainActivityMessage extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_message);

        firebaseAuth = FirebaseAuth.getInstance();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Message");
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#222222'>Message"));
    }

    @Override
    protected void onStart() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null) {

        } else {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.remove("LOGIN");
            editor.commit();
            startActivity(new Intent(MainActivityMessage.this, LoginActivity.class));
            finish();
        }
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int chosenID = item.getItemId();
        if(chosenID == R.id.logoutAction) {
            firebaseAuth.signOut();
            startActivity(new Intent(MainActivityMessage.this, LoginActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
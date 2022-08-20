package com.example.smartheater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartheater.data.HeaterContract.HeaterEntry;

public class SignIn extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);



        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(getApplicationContext());
        if(sessionManager.getLogIn()==true){
            Intent i = new Intent(this,SystemType.class);
            startActivity(i);
            finish();
        }

        mEmailEditText = (EditText) findViewById(R.id.user_email_edit_view);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.notifications:
                // TODO
                return true;
            case R.id.share:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Smart Heater application is an application that allows you to control the heater remotely from your smart device";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Smart Heater");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void openSignUp(View view){
        Intent i = new Intent(this,SignUp.class);
        startActivity(i);
    }
    public void openReset(View view){
        Intent i = new Intent(this,Reset.class);
        startActivity(i);
    }

    public void openHome(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void signIn(View view){
        String userEmail = mEmailEditText.getText().toString().trim();
        String userPassword = mPasswordEditText.getText().toString().trim();

        String[] projection ={
                HeaterEntry._ID,
                HeaterEntry.COLUMN_USER_NAME
        };
        String selection = HeaterEntry.COLUMN_USER_EMAIL + " = ? AND " + HeaterEntry.COLUMN_USER_PASSWORD + " = ? ";

        String [] selectionArg ={
                userEmail,
                userPassword
        };
       Cursor cursor = getContentResolver().query(HeaterEntry.CONTENT_URI,projection,selection,selectionArg,null);
       try {
            if (cursor.getCount()==0){
                Toast.makeText(this, "Wrong email or password", Toast.LENGTH_LONG).show();

            }
            else {
                sessionManager.setLogIn(true);
                sessionManager.setHours(0);
                sessionManager.setMinutes(0);
                Intent i = new Intent(this, SystemType.class);
                startActivity(i);
            }
       }
       finally {
           // Always close the cursor when you're done reading from it. This releases all its
           // resources and makes it invalid.
           cursor.close();
       }

    }

}
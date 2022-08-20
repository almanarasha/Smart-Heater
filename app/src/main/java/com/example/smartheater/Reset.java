package com.example.smartheater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartheater.data.HeaterContract;

public class Reset extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mSecurtiyQuestion;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

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
        mSecurtiyQuestion = (EditText) findViewById(R.id.security_question);
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


    public void openHome(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void recover(View view){
        String userEmail = mEmailEditText.getText().toString().trim();
        String securityQuestion = mSecurtiyQuestion.getText().toString().trim();

        String[] projection ={
                HeaterContract.HeaterEntry._ID,
                HeaterContract.HeaterEntry.COLUMN_USER_PASSWORD
        };
        String selection = HeaterContract.HeaterEntry.COLUMN_USER_EMAIL + " = ? AND " + HeaterContract.HeaterEntry.COLUMN_USER_QUESTION + " = ? ";

        String [] selectionArg ={
                userEmail,
                securityQuestion
        };
        Cursor cursor = getContentResolver().query(HeaterContract.HeaterEntry.CONTENT_URI,projection,selection,selectionArg,null);
        try {
            if (cursor.getCount()==0){
                Toast.makeText(this, "There is no match result", Toast.LENGTH_LONG).show();

            }
            else {
                int passwordColumnIndex = cursor.getColumnIndex(HeaterContract.HeaterEntry.COLUMN_USER_PASSWORD);
                if(cursor.moveToFirst()) {
                    String userPassword = cursor.getString(passwordColumnIndex);
                    Toast.makeText(this, "Your password is:\n" + userPassword , Toast.LENGTH_LONG).show();
                }


            }
        }
        finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }
}
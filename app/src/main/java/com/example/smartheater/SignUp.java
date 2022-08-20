package com.example.smartheater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartheater.data.HeaterContract;
import com.example.smartheater.data.HeaterContract.HeaterEntry;
import com.example.smartheater.data.HeaterDbHelper;

public class SignUp extends AppCompatActivity {

    private EditText mNameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mSecurtiyQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mNameEditText = (EditText) findViewById(R.id.user_name_edit_view);
        mEmailEditText = (EditText) findViewById(R.id.user_email_edit_view);
        mPasswordEditText = (EditText) findViewById(R.id.password_edit_view);
        mSecurtiyQuestion =(EditText) findViewById(R.id.security_question);


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


    public void addUser(View view){
     String userName = mNameEditText.getText().toString().trim();
      String userEmail = mEmailEditText.getText().toString().trim();
      String userPassword = mPasswordEditText.getText().toString().trim();
      String securityQuestion = mSecurtiyQuestion.getText().toString().trim();

      ContentValues values = new ContentValues();
      values.put(HeaterEntry.COLUMN_USER_NAME,userName);
      values.put(HeaterEntry.COLUMN_USER_EMAIL,userEmail);
      values.put(HeaterEntry.COLUMN_USER_PASSWORD,userPassword);
      values.put(HeaterEntry.COLUMN_USER_QUESTION,securityQuestion);

        Uri newUri = getContentResolver().insert(HeaterEntry.CONTENT_URI,values);
        if (newUri==null) {
            Toast.makeText(this, "Something wrong, please try again", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, "Your account created successfully", Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, SignIn.class);
            startActivity(i);
        }

    }
}

package com.example.smartheater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SystemType extends AppCompatActivity {
    TextView systemTrue;
    TextView systemFalse;
    TextView systemError;
    Button autoButton;
    ImageView checkIcon;
    ImageView xIcon;
    String systemStatus = "on";
    String url ="http://homeassistant.local:1880/endpoint/systemtrue";
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_type);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(getApplicationContext());

        systemTrue = findViewById(R.id.auto_text_view_on);
        systemFalse = findViewById(R.id.auto_text_view_off);
        systemError = findViewById(R.id.error_text_view);
        autoButton = findViewById(R.id.auto_button);
        checkIcon = findViewById(R.id.check);
        xIcon = findViewById(R.id.xicon);
        autoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(SystemType.this);


// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(systemStatus=="on"){
                                    systemStatus="off";
                                    url="http://homeassistant.local:1880/endpoint/systemfalse";
                                    systemError.setVisibility(View.GONE);
                                    systemFalse.setVisibility(View.GONE);
                                    xIcon.setVisibility(View.GONE);
                                    checkIcon.setVisibility(View.VISIBLE);
                                    systemTrue.setVisibility(View.VISIBLE);
                                }
                                else {
                                    systemStatus="on";
                                    url="http://homeassistant.local:1880/endpoint/systemtrue";
                                    systemError.setVisibility(View.GONE);
                                    systemTrue.setVisibility(View.GONE);
                                    checkIcon.setVisibility(View.GONE);
                                    xIcon.setVisibility(View.VISIBLE);
                                    systemFalse.setVisibility(View.VISIBLE);
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        systemError.setVisibility(View.VISIBLE);
                    }
                });

// Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });

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


    public void openControl(View view) {
        Intent i = new Intent(this, Control.class);
        startActivity(i);
    }
    public void openHome(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void logOut(View view){

        sessionManager.sessionLogOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}
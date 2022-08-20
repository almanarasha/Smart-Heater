package com.example.smartheater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Control extends AppCompatActivity {
    TextView tempValue;
    TextView turnedOnMsg;
    TextView turnedOffMsg;
    TextView errorMsg;
    //int seekBarMinValue;
    SessionManager sessionManager;
    Button onOffButton;
    String heaterStatus ="on";
    String url ="http://homeassistant.local:1880/endpoint/on";
    String temp_url;
    int hours;
    int minutes;
    int delay;
    Handler handler = new Handler(Looper.getMainLooper());
    boolean stop = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tempValue = findViewById(R.id.temp_value);
        turnedOnMsg = findViewById(R.id.turned_on_text_view);
        turnedOffMsg = findViewById(R.id.turned_off_text_view);
        errorMsg = findViewById(R.id.error_text_view);
        onOffButton = findViewById(R.id.powre_icone);

        sessionManager = new SessionManager(getApplicationContext());

        hours = sessionManager.getHours();
        minutes = sessionManager.getMinutes();

       delay = (((hours*60)*60)*1000) + (((minutes*10)*60)*1000);


        onOffButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(Control.this);


// Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                if(heaterStatus == "on"){
                                    stop = true;
                                   heaterStatus = "off";
                                   url ="http://homeassistant.local:1880/endpoint/off";
                                   errorMsg.setVisibility(View.GONE);
                                   turnedOffMsg.setVisibility(View.GONE);
                                    turnedOnMsg.setVisibility(View.VISIBLE);
                                    stopHeaterOn(delay);
                                }
                                else {
                                    stop = false;
                                    heaterStatus = "on";
                                    url ="http://homeassistant.local:1880/endpoint/on";
                                    errorMsg.setVisibility(View.GONE);
                                    turnedOnMsg.setVisibility(View.GONE);
                                    turnedOffMsg.setVisibility(View.VISIBLE);
                                    stopHeaterOn(100);
                                }


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        turnedOnMsg.setVisibility(View.GONE);
                        turnedOffMsg.setVisibility(View.GONE);
                        errorMsg.setVisibility(View.VISIBLE);
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);



            }
        });



        //SeekBar seekBar = findViewById(R.id.seekbar);
        //int temperatureValue = sessionManager.getTemperature();
        //seekBar.setProgress(temperatureValue - 20);

/*
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarMinValue = progress+20;
                textView.setText(String.valueOf(seekBarMinValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sessionManager.setTemperature(seekBarMinValue);
            }
        });*/



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


    public void openRepeatScheduled(View view){
        Intent i = new Intent(this, TimeScheduling.class);
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

    @Override
    protected void onStart() {
        super.onStart();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(Control.this);
        temp_url ="http://homeassistant.local:1880/endpoint/temp";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, temp_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tempValue.setText(response);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               tempValue.setText(R.string.value30);
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void stopHeaterOn(int delay) {

        // off request
        if(delay != 0) {

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Instantiate the RequestQueue.
                    RequestQueue queue = Volley.newRequestQueue(Control.this);
                    url = "http://homeassistant.local:1880/endpoint/off";

// Request a string response from the provided URL.
                    StringRequest stringRequestOff = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    errorMsg.setVisibility(View.GONE);
                                    turnedOnMsg.setVisibility(View.GONE);
                                    turnedOffMsg.setVisibility(View.VISIBLE);


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            turnedOnMsg.setVisibility(View.GONE);
                            turnedOffMsg.setVisibility(View.GONE);
                            errorMsg.setVisibility(View.VISIBLE);
                        }
                    });

// Add the request to the RequestQueue.
                    queue.add(stringRequestOff);
                }

            }, delay);
        }
    }


    }

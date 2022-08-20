package com.example.smartheater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

public class TimeScheduling extends AppCompatActivity {
    TextView textView;
    NumberPicker hoursNumberPicker;
    NumberPicker minNumberPicker;
    SessionManager sessionManager;
    int newHour;
    int newMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_scheduling);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(getApplicationContext());

        hoursNumberPicker = findViewById(R.id.hours_numpicker);
        minNumberPicker = findViewById(R.id.min_numpicker);

        hoursNumberPicker.setMinValue(0);
        hoursNumberPicker.setMaxValue(12);

        hoursNumberPicker.setValue(sessionManager.getHours());
        newHour = sessionManager.getHours();



        hoursNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                newHour = i1;

            }
        });




        int maxValue = 5;
        int step = 10;
        String[] numberValue = new String[maxValue+1];
        for(int i =0; i<=maxValue;i++){
            numberValue[i]=String.valueOf((0+i) * step);

        }


        minNumberPicker.setMaxValue(maxValue);
        minNumberPicker.setDisplayedValues(numberValue);

        minNumberPicker.setValue(sessionManager.getMinutes());
        newMinute = sessionManager.getMinutes();

        minNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                newMinute =i1;
            }
        });


        


/*
       textView = findViewById(R.id.max_temp_value);
        SeekBar seekBar = findViewById(R.id.max_seekbar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int seekBarMinValue = progress+20;
                textView.setText(String.valueOf(seekBarMinValue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
       */

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
                startActivity(Intent.createChooser(sharingIntent, "Share via"));                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void openHome(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void saveTime(View view){
        sessionManager.setHours(newHour);
        sessionManager.setMinutes(newMinute);
        Toast.makeText(this,"Saved Successfully",Toast.LENGTH_LONG).show();
        Intent i = new Intent(this, Control.class);
        startActivity(i);
    }
    public void logOut(View view){

        sessionManager.sessionLogOut();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
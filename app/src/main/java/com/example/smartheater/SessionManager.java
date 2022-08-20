package com.example.smartheater;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SessionManager {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;
    private static final String PREFERENCE_FILE_NAME = "preference";
    int PRIVATE_MODE = 0;

    private static final String LOGGED_IN_STATUS = "loggedInStatus";
    private static final String TEMPERATURE = "temperature";
    private static final String HOURS = "hours";
    private static final String MINUTES = "minutes";


    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME,PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void setLogIn(boolean isLoggedIn){
        editor.putBoolean(LOGGED_IN_STATUS,isLoggedIn);
        editor.commit();
    }

    public boolean getLogIn(){
        return sharedPreferences.getBoolean(LOGGED_IN_STATUS,false);
    }

    /*public void setTemperature(int temperatureValue){
        editor.putInt(TEMPERATURE,temperatureValue);
        editor.commit();
    }

    public int getTemperature(){

        return sharedPreferences.getInt(TEMPERATURE,20);

    }*/
    public void setHours(int hours){
        editor.putInt(HOURS,hours);
        editor.commit();
    }

    public int getHours(){

        return sharedPreferences.getInt(HOURS,0);

    }
    public void setMinutes(int minutes){
        editor.putInt(MINUTES,minutes);
        editor.commit();
    }

    public int getMinutes(){

        return sharedPreferences.getInt(MINUTES,0);

    }


public void sessionLogOut(){

    editor.clear();
    editor.commit();
}
}

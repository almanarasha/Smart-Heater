package com.example.smartheater.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.smartheater.data.HeaterContract.HeaterEntry;

import androidx.annotation.Nullable;

public class HeaterDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "smart_heater.db";
    public static final int DATABASE_VERSION = 1;

    public HeaterDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_USERS_TABLE = "CREATE TABLE "+ HeaterEntry.TABLE_NAME+"("
                + HeaterEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + HeaterEntry.COLUMN_USER_NAME + " TEXT NOT NULL,"
                + HeaterEntry.COLUMN_USER_EMAIL + " TEXT UNIQUE NOT NULL,"
                + HeaterEntry.COLUMN_USER_PASSWORD + " TEXT NOT NULL,"
                + HeaterEntry.COLUMN_USER_QUESTION + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_USERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}

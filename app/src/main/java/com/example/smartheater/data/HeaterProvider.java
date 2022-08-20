package com.example.smartheater.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import com.example.smartheater.data.HeaterContract.HeaterEntry;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HeaterProvider extends ContentProvider {
    private static final int USER = 100;
    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static {
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.
        sUriMatcher.addURI(HeaterContract.CONTENT_AUTHORITY, HeaterContract.PATH_USERS, USER);

    }

    private HeaterDbHelper mDbHelper;

    /**
     * Initialize the provider and the database helper object.
     */

    @Override
    public boolean onCreate() {
        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new HeaterDbHelper(getContext());
        return true;
    }




    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = null;
        final int match = sUriMatcher.match(uri);
        switch (match){
            case USER:
                cursor = database.query(HeaterEntry.TABLE_NAME,projection,selection,selectionArgs,null,null,null);
                break;
            default:
                throw new IllegalArgumentException("query not supported"+uri);//ask
        }
        return cursor;
    }


    @Override
    public String getType(Uri uri) {
        return null;
    }


    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match){
            case USER:
                return insertUser(uri,contentValues);
            default:
                throw new IllegalArgumentException("Insertion not supported"+uri);
        }


    }

    private Uri insertUser(Uri uri,ContentValues values){

        String userName = values.getAsString(HeaterEntry.COLUMN_USER_NAME);
        String userEmail = values.getAsString(HeaterEntry.COLUMN_USER_EMAIL);
        String usePassword = values.getAsString(HeaterEntry.COLUMN_USER_PASSWORD);
        String securityQuestion = values.getAsString(HeaterEntry.COLUMN_USER_QUESTION);
        if(TextUtils.isEmpty(userName)){
            throw new IllegalArgumentException("Name required");
        }
        if(TextUtils.isEmpty(userEmail)){
            throw new IllegalArgumentException("Email required");
        }
        if(TextUtils.isEmpty(usePassword)){
            throw new IllegalArgumentException("Password required");
        }
        if(TextUtils.isEmpty(securityQuestion)){
            throw new IllegalArgumentException("Security Question required");
        }

        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        long id = database.insert(HeaterEntry.TABLE_NAME,null,values);
        if(id==-1){
            return null;
        }
            return ContentUris.withAppendedId(uri,id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

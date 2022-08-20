package com.example.smartheater.data;

import android.net.Uri;
import android.provider.BaseColumns;

public final class HeaterContract {
    public static final String CONTENT_AUTHORITY ="com.example.smartheater";
    public static final String PATH_USERS ="users";
    public static final Uri BASE_CONTENT_URI =Uri.parse("content://"+ CONTENT_AUTHORITY );
    private HeaterContract(){

    }

    public static class HeaterEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USERS);
        public static final String TABLE_NAME = "users";
        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_USER_EMAIL = "email";
        public static final String COLUMN_USER_PASSWORD = "password";
        public static final String COLUMN_USER_QUESTION = "question";
    }
}

package com.pipalapipapalapi.smartplaces.database;

import android.provider.BaseColumns;

public class ReminderTable implements BaseColumns {

    public static final String TABLE_NAME = "reminder";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_TRIGGER = "trigger";
    public static final String COLUMN_MESSAGE = "message";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                        TABLE_NAME + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                        "," +
                        COLUMN_ID + " INTEGER UNIQUE NOT NULL" +
                        "," +
                        COLUMN_STATUS + " INTEGER" +
                        "," +
                        COLUMN_LATITUDE + " REAL" +
                        "," +
                        COLUMN_LONGITUDE + " REAL" +
                        "," +
                        COLUMN_TRIGGER + " INTEGER" +
                        "," +
                        COLUMN_MESSAGE + " TEXT" +
                        ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
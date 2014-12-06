package com.pipalapipapalapi.smartplaces.database;

import android.provider.BaseColumns;

public class NotificationTable implements BaseColumns {

    public static final String TABLE_NAME = "notification";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_ISREAD = "isRead";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                        TABLE_NAME + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                        "," +
                        COLUMN_ID + " INTEGER UNIQUE NOT NULL" +
                        "," +
                        COLUMN_TIMESTAMP + " INTEGER" +
                        "," +
                        COLUMN_TYPE + " INTEGER" +
                        "," +
                        COLUMN_TITLE + " TEXT" +
                        "," +
                        COLUMN_MESSAGE + " TEXT" +
                        "," +
                        COLUMN_ISREAD + " INTEGER" +
                        ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
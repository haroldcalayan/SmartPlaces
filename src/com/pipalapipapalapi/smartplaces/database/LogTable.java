package com.pipalapipapalapi.smartplaces.database;

import android.provider.BaseColumns;

public class LogTable implements BaseColumns {

    public static final String TABLE_NAME = "log";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MODULE = "module";
    public static final String COLUMN_DATETIMEINMILLIS = "dateTimeInMillis";
    public static final String COLUMN_LOCATION = "location";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
                        TABLE_NAME + "(" +
                        _ID + " INTEGER PRIMARY KEY AUTOINCREMENT" +
                        "," +
                        COLUMN_ID + " INTEGER UNIQUE NOT NULL" +
                        "," +
                        COLUMN_MODULE + " TEXT" +
                        "," +
                        COLUMN_DATETIMEINMILLIS + " INTEGER" +
                        "," +
                        COLUMN_LOCATION + " TEXT" +
                        ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
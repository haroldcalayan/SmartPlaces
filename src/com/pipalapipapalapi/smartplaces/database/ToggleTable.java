package com.pipalapipapalapi.smartplaces.database;

import android.provider.BaseColumns;

public class ToggleTable implements BaseColumns {

    public static final String TABLE_NAME = "toggle";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_TRIGGER = "trigger";
    public static final String COLUMN_WIFISTATE = "wifiState";
    public static final String COLUMN_MOBILEDATASTATE = "mobileDataState";
    public static final String COLUMN_RINGERSTATE = "ringerState";
    public static final String COLUMN_BLUETOOTHSTATE = "bluetoothState";
    public static final String COLUMN_AIRPLANEMODESTATE = "airplaneModeState";

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
                        COLUMN_WIFISTATE + " INTEGER" +
                        "," +
                        COLUMN_MOBILEDATASTATE + " INTEGER" +
                        "," +
                        COLUMN_RINGERSTATE + " INTEGER" +
                        "," +
                        COLUMN_BLUETOOTHSTATE + " INTEGER" +
                        "," +
                        COLUMN_AIRPLANEMODESTATE + " INTEGER" +
                        ");";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

}
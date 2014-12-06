package com.pipalapipapalapi.smartplaces.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SampleDatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SampleDatabase.db";

    private static final int DATABASE_VERSION = 1;

    private static volatile SampleDatabaseOpenHelper mInstance;

    private static volatile SQLiteDatabase mDatabase;

    public static SampleDatabaseOpenHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (SampleDatabaseOpenHelper.class) {
                if (mInstance == null) {
                    mInstance = new SampleDatabaseOpenHelper(context.getApplicationContext());
                    mDatabase = mInstance.getWritableDatabase();
                }
            }
        }
        return mInstance;
    }

    private SampleDatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public synchronized SQLiteDatabase getCachedDatabase() {
        if (mDatabase == null || !mDatabase.isOpen()) {
            mDatabase = getWritableDatabase();
        }
        return mDatabase;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(LogTable.CREATE_TABLE);
        db.execSQL(MessageTable.CREATE_TABLE);
        db.execSQL(NotificationTable.CREATE_TABLE);
        db.execSQL(ReminderTable.CREATE_TABLE);
        db.execSQL(ToggleTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(LogTable.DROP_TABLE);
        db.execSQL(MessageTable.DROP_TABLE);
        db.execSQL(NotificationTable.DROP_TABLE);
        db.execSQL(ReminderTable.DROP_TABLE);
        db.execSQL(ToggleTable.DROP_TABLE);
        onCreate(db);
    }


    public Cursor rawQuery(final String sql, final String[] selectionArgs) {
    	return getWritableDatabase().rawQuery(sql, selectionArgs);
    }

    public void rawQuery(final String sql, final String[] selectionArgs, final DAOListener<Cursor> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final Cursor cursor = rawQuery(sql, selectionArgs);
				if (listener != null)  {
					MainThreadExecutor.getInstance().execute(new Runnable() {

						@Override public void run() {
							listener.onFinish(cursor);
						}
					});
				}
			}
		});
    }
}

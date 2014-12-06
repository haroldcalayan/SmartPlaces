package com.pipalapipapalapi.smartplaces.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.pipalapipapalapi.smartplaces.model.Reminder;


public class ReminderDAO {

    private SQLiteDatabase mDatabase;

    private static volatile ReminderDAO mInstance;

    public static ReminderDAO getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ReminderDAO.class) {
                if (mInstance == null) {
                    mInstance = new ReminderDAO(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private ReminderDAO(Context context) {
        mDatabase = SampleDatabaseOpenHelper.getInstance(context).getCachedDatabase();
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return mDatabase.query(ReminderTable.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public void query(final String[] columns, final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final DAOListener<Cursor> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final Cursor cursor = query(columns, selection, selectionArgs, groupBy, having, orderBy);
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

    public List<Reminder> query(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Cursor cursor = mDatabase.query(ReminderTable.TABLE_NAME, null, selection, selectionArgs, groupBy, having, orderBy);
        return toReminderList(cursor);
    }

    public void query(final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final DAOListener<List<Reminder>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Reminder> ob = query(selection, selectionArgs, groupBy, having, orderBy);
				if (listener != null)  {
					MainThreadExecutor.getInstance().execute(new Runnable() {

						@Override public void run() {
							listener.onFinish(ob);
						}
					});
				}
			}
		});
    }

    public List<Reminder> queryAll(String orderBy) {
        Cursor cursor = mDatabase.query(ReminderTable.TABLE_NAME, null, null, null, null, null, orderBy);
        return toReminderList(cursor);
    }

    public void queryAll(final String orderBy, final DAOListener<List<Reminder>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Reminder> ob = queryAll(orderBy);
				if (listener != null)  {
					MainThreadExecutor.getInstance().execute(new Runnable() {

						@Override public void run() {
							listener.onFinish(ob);
						}
					});
				}
			}
		});
    }

    public List<Reminder> queryByField(String field, String args, String orderBy) {
        Cursor cursor = mDatabase.query(ReminderTable.TABLE_NAME, null, field + " = ?", new String[] { args }, null, null, orderBy);
        return toReminderList(cursor);
    }

    public void queryByField(final String field, final String args, final String orderBy, final DAOListener<List<Reminder>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Reminder> ob = queryByField(field, args, orderBy);
				if (listener != null)  {
					MainThreadExecutor.getInstance().execute(new Runnable() {

						@Override public void run() {
							listener.onFinish(ob);
						}
					});
				}
			}
		});
    }

    public Reminder queryById(String value) {
        Cursor cursor = mDatabase.query(ReminderTable.TABLE_NAME, null, ReminderTable.COLUMN_ID + " = ?", new String[] { value }, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Reminder ob = toReminder(cursor);
            cursor.close();
            return ob;
        }
        return null;
    }

    public long insert(Reminder reminder) {
        return mDatabase.insert(ReminderTable.TABLE_NAME, null, asContentValues(reminder));
    }

    public int insert(List<Reminder> list) {
        int count = 0;
        try {
            mDatabase.beginTransaction();
            for (Reminder reminder : list) {
                long id = insert(reminder);
                if (id != -1) count += 1;
                mDatabase.yieldIfContendedSafely();
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return count;
    }

    public void insert(final List<Reminder> list, final DAOListener<Integer> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final int count = insert(list);
				if (listener != null)  {
					MainThreadExecutor.getInstance().execute(new Runnable() {

						@Override public void run() {
							listener.onFinish(count);
						}
					});
				}
			}
		});
    }

    public int update(ContentValues contentValues, String whereClause, String[] whereArgs) {
        return mDatabase.update(ReminderTable.TABLE_NAME, contentValues, whereClause, whereArgs);
    }

    public void update(final ContentValues contentValues, final String whereClause, final String[] whereArgs, final DAOListener<Integer> listener) {
        BackgroundThreadExecutor.getInstance().execute(new Runnable() {

    	    @Override public void run() {
    		    final int count = update(contentValues, whereClause, whereArgs);
    		    if (listener != null)  {
    		        MainThreadExecutor.getInstance().execute(new Runnable() {

    			        @Override public void run() {
    				        listener.onFinish(count);
    			        }
    		        });
    	        }
    		}
    	});
    }

    public int updateByField(String field, ContentValues contentValues, String value) {
    	return mDatabase.update(ReminderTable.TABLE_NAME, contentValues, field + " = ?", new String[] { value });
    }

    public int updateById(ContentValues contentValues, String id) {
    	return mDatabase.update(ReminderTable.TABLE_NAME, contentValues, ReminderTable.COLUMN_ID + " = ?", new String[] { id });
    }

    public int updateOrInsertById(List<Reminder> list) {
        int count = 0;
        try {
        mDatabase.beginTransaction();
        for (Reminder reminder : list) {
            int affected = update(asContentValues(reminder), ReminderTable.COLUMN_ID + " = ?", new String[] { reminder.getId() + "" });
            if (affected == 0) insert(reminder);
            count += affected;
            mDatabase.yieldIfContendedSafely();
        }
        mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return count;
    }

    public void updateOrInsertById(final List<Reminder> list, final DAOListener<Integer> listener) {
        	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

    			@Override
    			public void run() {
    				final int count = updateOrInsertById(list);
    				if (listener != null)  {
    					MainThreadExecutor.getInstance().execute(new Runnable() {

    						@Override public void run() {
    							listener.onFinish(count);
    						}
    					});
    				}
    			}
    		});
        }

    public int delete(String whereClause, String[] whereArgs) {
        return mDatabase.delete(ReminderTable.TABLE_NAME, whereClause, whereArgs);
    }

    public int deleteAll() {
        return mDatabase.delete(ReminderTable.TABLE_NAME, null, null);
    }

    public int deleteByField(String field, String value) {
        return mDatabase.delete(ReminderTable.TABLE_NAME, field + " = ?", new String[] { value });
    }

    public int deleteById(String value) {
        return mDatabase.delete(ReminderTable.TABLE_NAME, ReminderTable.COLUMN_ID + " = ?", new String[] {value});
    }

    public ContentValues asContentValues(Reminder reminder) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ReminderTable.COLUMN_ID, reminder.getId());
        contentValues.put(ReminderTable.COLUMN_STATUS, reminder.getStatus());
        contentValues.put(ReminderTable.COLUMN_LATITUDE, reminder.getLatitude());
        contentValues.put(ReminderTable.COLUMN_LONGITUDE, reminder.getLongitude());
        contentValues.put(ReminderTable.COLUMN_TRIGGER, reminder.getTrigger());
        contentValues.put(ReminderTable.COLUMN_MESSAGE, reminder.getMessage());
        return contentValues;
    }

    private List<Reminder> toReminderList(Cursor cursor) {
        List<Reminder> list = new ArrayList<Reminder>();
        if (cursor == null) return list;
        if (cursor.moveToFirst()) {
            do {
                list.add(toReminder(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    private Reminder toReminder(Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0) return null;
        Reminder reminder = new Reminder();
        reminder.setId(cursor.getInt(cursor.getColumnIndex(ReminderTable.COLUMN_ID)));
        reminder.setStatus(cursor.getInt(cursor.getColumnIndex(ReminderTable.COLUMN_STATUS)));
        reminder.setLatitude(cursor.getDouble(cursor.getColumnIndex(ReminderTable.COLUMN_LATITUDE)));
        reminder.setLongitude(cursor.getDouble(cursor.getColumnIndex(ReminderTable.COLUMN_LONGITUDE)));
        reminder.setTrigger(cursor.getInt(cursor.getColumnIndex(ReminderTable.COLUMN_TRIGGER)));
        reminder.setMessage(cursor.getString(cursor.getColumnIndex(ReminderTable.COLUMN_MESSAGE)));
        return reminder;
    }
}

package com.pipalapipapalapi.smartplaces.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.pipalapipapalapi.smartplaces.model.Log;


public class LogDAO {

    private SQLiteDatabase mDatabase;

    private static volatile LogDAO mInstance;

    public static LogDAO getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LogDAO.class) {
                if (mInstance == null) {
                    mInstance = new LogDAO(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private LogDAO(Context context) {
        mDatabase = SampleDatabaseOpenHelper.getInstance(context).getCachedDatabase();
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return mDatabase.query(LogTable.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
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

    public List<Log> query(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Cursor cursor = mDatabase.query(LogTable.TABLE_NAME, null, selection, selectionArgs, groupBy, having, orderBy);
        return toLogList(cursor);
    }

    public void query(final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final DAOListener<List<Log>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Log> ob = query(selection, selectionArgs, groupBy, having, orderBy);
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

    public List<Log> queryAll(String orderBy) {
        Cursor cursor = mDatabase.query(LogTable.TABLE_NAME, null, null, null, null, null, orderBy);
        return toLogList(cursor);
    }

    public void queryAll(final String orderBy, final DAOListener<List<Log>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Log> ob = queryAll(orderBy);
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

    public List<Log> queryByField(String field, String args, String orderBy) {
        Cursor cursor = mDatabase.query(LogTable.TABLE_NAME, null, field + " = ?", new String[] { args }, null, null, orderBy);
        return toLogList(cursor);
    }

    public void queryByField(final String field, final String args, final String orderBy, final DAOListener<List<Log>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Log> ob = queryByField(field, args, orderBy);
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

    public Log queryById(String value) {
        Cursor cursor = mDatabase.query(LogTable.TABLE_NAME, null, LogTable.COLUMN_ID + " = ?", new String[] { value }, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Log ob = toLog(cursor);
            cursor.close();
            return ob;
        }
        return null;
    }

    public long insert(Log log) {
        return mDatabase.insert(LogTable.TABLE_NAME, null, asContentValues(log));
    }

    public int insert(List<Log> list) {
        int count = 0;
        try {
            mDatabase.beginTransaction();
            for (Log log : list) {
                long id = insert(log);
                if (id != -1) count += 1;
                mDatabase.yieldIfContendedSafely();
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return count;
    }

    public void insert(final List<Log> list, final DAOListener<Integer> listener) {
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
        return mDatabase.update(LogTable.TABLE_NAME, contentValues, whereClause, whereArgs);
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
    	return mDatabase.update(LogTable.TABLE_NAME, contentValues, field + " = ?", new String[] { value });
    }

    public int updateById(ContentValues contentValues, String id) {
    	return mDatabase.update(LogTable.TABLE_NAME, contentValues, LogTable.COLUMN_ID + " = ?", new String[] { id });
    }

    public int updateOrInsertById(List<Log> list) {
        int count = 0;
        try {
        mDatabase.beginTransaction();
        for (Log log : list) {
            int affected = update(asContentValues(log), LogTable.COLUMN_ID + " = ?", new String[] { log.getId() + "" });
            if (affected == 0) insert(log);
            count += affected;
            mDatabase.yieldIfContendedSafely();
        }
        mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return count;
    }

    public void updateOrInsertById(final List<Log> list, final DAOListener<Integer> listener) {
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
        return mDatabase.delete(LogTable.TABLE_NAME, whereClause, whereArgs);
    }

    public int deleteAll() {
        return mDatabase.delete(LogTable.TABLE_NAME, null, null);
    }

    public int deleteByField(String field, String value) {
        return mDatabase.delete(LogTable.TABLE_NAME, field + " = ?", new String[] { value });
    }

    public int deleteById(String value) {
        return mDatabase.delete(LogTable.TABLE_NAME, LogTable.COLUMN_ID + " = ?", new String[] {value});
    }

    public ContentValues asContentValues(Log log) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(LogTable.COLUMN_ID, log.getId());
        contentValues.put(LogTable.COLUMN_MODULE, log.getModule());
        contentValues.put(LogTable.COLUMN_DATETIMEINMILLIS, log.getDateTimeInMillis());
        contentValues.put(LogTable.COLUMN_LOCATION, log.getLocation());
        return contentValues;
    }

    private List<Log> toLogList(Cursor cursor) {
        List<Log> list = new ArrayList<Log>();
        if (cursor == null) return list;
        if (cursor.moveToFirst()) {
            do {
                list.add(toLog(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    private Log toLog(Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0) return null;
        Log log = new Log();
        log.setId(cursor.getInt(cursor.getColumnIndex(LogTable.COLUMN_ID)));
        log.setModule(cursor.getString(cursor.getColumnIndex(LogTable.COLUMN_MODULE)));
        log.setDateTimeInMillis(cursor.getInt(cursor.getColumnIndex(LogTable.COLUMN_DATETIMEINMILLIS)));
        log.setLocation(cursor.getString(cursor.getColumnIndex(LogTable.COLUMN_LOCATION)));
        return log;
    }
}

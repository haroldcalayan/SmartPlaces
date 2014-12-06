package com.pipalapipapalapi.smartplaces.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.pipalapipapalapi.smartplaces.model.Notification;


public class NotificationDAO {

    private SQLiteDatabase mDatabase;

    private static volatile NotificationDAO mInstance;

    public static NotificationDAO getInstance(Context context) {
        if (mInstance == null) {
            synchronized (NotificationDAO.class) {
                if (mInstance == null) {
                    mInstance = new NotificationDAO(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private NotificationDAO(Context context) {
        mDatabase = SampleDatabaseOpenHelper.getInstance(context).getCachedDatabase();
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return mDatabase.query(NotificationTable.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
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

    public List<Notification> query(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Cursor cursor = mDatabase.query(NotificationTable.TABLE_NAME, null, selection, selectionArgs, groupBy, having, orderBy);
        return toNotificationList(cursor);
    }

    public void query(final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final DAOListener<List<Notification>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Notification> ob = query(selection, selectionArgs, groupBy, having, orderBy);
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

    public List<Notification> queryAll(String orderBy) {
        Cursor cursor = mDatabase.query(NotificationTable.TABLE_NAME, null, null, null, null, null, orderBy);
        return toNotificationList(cursor);
    }

    public void queryAll(final String orderBy, final DAOListener<List<Notification>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Notification> ob = queryAll(orderBy);
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

    public List<Notification> queryByField(String field, String args, String orderBy) {
        Cursor cursor = mDatabase.query(NotificationTable.TABLE_NAME, null, field + " = ?", new String[] { args }, null, null, orderBy);
        return toNotificationList(cursor);
    }

    public void queryByField(final String field, final String args, final String orderBy, final DAOListener<List<Notification>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Notification> ob = queryByField(field, args, orderBy);
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

    public Notification queryById(String value) {
        Cursor cursor = mDatabase.query(NotificationTable.TABLE_NAME, null, NotificationTable.COLUMN_ID + " = ?", new String[] { value }, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Notification ob = toNotification(cursor);
            cursor.close();
            return ob;
        }
        return null;
    }

    public long insert(Notification notification) {
        return mDatabase.insert(NotificationTable.TABLE_NAME, null, asContentValues(notification));
    }

    public int insert(List<Notification> list) {
        int count = 0;
        try {
            mDatabase.beginTransaction();
            for (Notification notification : list) {
                long id = insert(notification);
                if (id != -1) count += 1;
                mDatabase.yieldIfContendedSafely();
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return count;
    }

    public void insert(final List<Notification> list, final DAOListener<Integer> listener) {
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
        return mDatabase.update(NotificationTable.TABLE_NAME, contentValues, whereClause, whereArgs);
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
    	return mDatabase.update(NotificationTable.TABLE_NAME, contentValues, field + " = ?", new String[] { value });
    }

    public int updateById(ContentValues contentValues, String id) {
    	return mDatabase.update(NotificationTable.TABLE_NAME, contentValues, NotificationTable.COLUMN_ID + " = ?", new String[] { id });
    }

    public int updateOrInsertById(List<Notification> list) {
        int count = 0;
        try {
        mDatabase.beginTransaction();
        for (Notification notification : list) {
            int affected = update(asContentValues(notification), NotificationTable.COLUMN_ID + " = ?", new String[] { notification.getId() + "" });
            if (affected == 0) insert(notification);
            count += affected;
            mDatabase.yieldIfContendedSafely();
        }
        mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return count;
    }

    public void updateOrInsertById(final List<Notification> list, final DAOListener<Integer> listener) {
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
        return mDatabase.delete(NotificationTable.TABLE_NAME, whereClause, whereArgs);
    }

    public int deleteAll() {
        return mDatabase.delete(NotificationTable.TABLE_NAME, null, null);
    }

    public int deleteByField(String field, String value) {
        return mDatabase.delete(NotificationTable.TABLE_NAME, field + " = ?", new String[] { value });
    }

    public int deleteById(String value) {
        return mDatabase.delete(NotificationTable.TABLE_NAME, NotificationTable.COLUMN_ID + " = ?", new String[] {value});
    }

    public ContentValues asContentValues(Notification notification) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotificationTable.COLUMN_ID, notification.getId());
        contentValues.put(NotificationTable.COLUMN_TIMESTAMP, notification.getTimestamp());
        contentValues.put(NotificationTable.COLUMN_TYPE, notification.getType());
        contentValues.put(NotificationTable.COLUMN_TITLE, notification.getTitle());
        contentValues.put(NotificationTable.COLUMN_MESSAGE, notification.getMessage());
        contentValues.put(NotificationTable.COLUMN_ISREAD, notification.getIsRead());
        return contentValues;
    }

    private List<Notification> toNotificationList(Cursor cursor) {
        List<Notification> list = new ArrayList<Notification>();
        if (cursor == null) return list;
        if (cursor.moveToFirst()) {
            do {
                list.add(toNotification(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    private Notification toNotification(Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0) return null;
        Notification notification = new Notification();
        notification.setId(cursor.getInt(cursor.getColumnIndex(NotificationTable.COLUMN_ID)));
        notification.setTimestamp(cursor.getInt(cursor.getColumnIndex(NotificationTable.COLUMN_TIMESTAMP)));
        notification.setType(cursor.getInt(cursor.getColumnIndex(NotificationTable.COLUMN_TYPE)));
        notification.setTitle(cursor.getString(cursor.getColumnIndex(NotificationTable.COLUMN_TITLE)));
        notification.setMessage(cursor.getString(cursor.getColumnIndex(NotificationTable.COLUMN_MESSAGE)));
        notification.setIsRead(cursor.getInt(cursor.getColumnIndex(NotificationTable.COLUMN_ISREAD)));
        return notification;
    }
}

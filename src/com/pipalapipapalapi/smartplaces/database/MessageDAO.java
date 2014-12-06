package com.pipalapipapalapi.smartplaces.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.pipalapipapalapi.smartplaces.model.Message;


public class MessageDAO {

    private SQLiteDatabase mDatabase;

    private static volatile MessageDAO mInstance;

    public static MessageDAO getInstance(Context context) {
        if (mInstance == null) {
            synchronized (MessageDAO.class) {
                if (mInstance == null) {
                    mInstance = new MessageDAO(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private MessageDAO(Context context) {
        mDatabase = SampleDatabaseOpenHelper.getInstance(context).getCachedDatabase();
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return mDatabase.query(MessageTable.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
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

    public List<Message> query(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Cursor cursor = mDatabase.query(MessageTable.TABLE_NAME, null, selection, selectionArgs, groupBy, having, orderBy);
        return toMessageList(cursor);
    }

    public void query(final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final DAOListener<List<Message>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Message> ob = query(selection, selectionArgs, groupBy, having, orderBy);
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

    public List<Message> queryAll(String orderBy) {
        Cursor cursor = mDatabase.query(MessageTable.TABLE_NAME, null, null, null, null, null, orderBy);
        return toMessageList(cursor);
    }

    public void queryAll(final String orderBy, final DAOListener<List<Message>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Message> ob = queryAll(orderBy);
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

    public List<Message> queryByField(String field, String args, String orderBy) {
        Cursor cursor = mDatabase.query(MessageTable.TABLE_NAME, null, field + " = ?", new String[] { args }, null, null, orderBy);
        return toMessageList(cursor);
    }

    public void queryByField(final String field, final String args, final String orderBy, final DAOListener<List<Message>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Message> ob = queryByField(field, args, orderBy);
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

    public Message queryById(String value) {
        Cursor cursor = mDatabase.query(MessageTable.TABLE_NAME, null, MessageTable.COLUMN_ID + " = ?", new String[] { value }, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Message ob = toMessage(cursor);
            cursor.close();
            return ob;
        }
        return null;
    }

    public long insert(Message message) {
        return mDatabase.insert(MessageTable.TABLE_NAME, null, asContentValues(message));
    }

    public int insert(List<Message> list) {
        int count = 0;
        try {
            mDatabase.beginTransaction();
            for (Message message : list) {
                long id = insert(message);
                if (id != -1) count += 1;
                mDatabase.yieldIfContendedSafely();
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return count;
    }

    public void insert(final List<Message> list, final DAOListener<Integer> listener) {
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
        return mDatabase.update(MessageTable.TABLE_NAME, contentValues, whereClause, whereArgs);
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
    	return mDatabase.update(MessageTable.TABLE_NAME, contentValues, field + " = ?", new String[] { value });
    }

    public int updateById(ContentValues contentValues, String id) {
    	return mDatabase.update(MessageTable.TABLE_NAME, contentValues, MessageTable.COLUMN_ID + " = ?", new String[] { id });
    }

    public int updateOrInsertById(List<Message> list) {
        int count = 0;
        try {
        mDatabase.beginTransaction();
        for (Message message : list) {
            int affected = update(asContentValues(message), MessageTable.COLUMN_ID + " = ?", new String[] { message.getId() + "" });
            if (affected == 0) insert(message);
            count += affected;
            mDatabase.yieldIfContendedSafely();
        }
        mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return count;
    }

    public void updateOrInsertById(final List<Message> list, final DAOListener<Integer> listener) {
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
        return mDatabase.delete(MessageTable.TABLE_NAME, whereClause, whereArgs);
    }

    public int deleteAll() {
        return mDatabase.delete(MessageTable.TABLE_NAME, null, null);
    }

    public int deleteByField(String field, String value) {
        return mDatabase.delete(MessageTable.TABLE_NAME, field + " = ?", new String[] { value });
    }

    public int deleteById(String value) {
        return mDatabase.delete(MessageTable.TABLE_NAME, MessageTable.COLUMN_ID + " = ?", new String[] {value});
    }

    public ContentValues asContentValues(Message message) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(MessageTable.COLUMN_ID, message.getId());
        contentValues.put(MessageTable.COLUMN_STATUS, message.getStatus());
        contentValues.put(MessageTable.COLUMN_LATITUDE, message.getLatitude());
        contentValues.put(MessageTable.COLUMN_LONGITUDE, message.getLongitude());
        contentValues.put(MessageTable.COLUMN_TRIGGER, message.getTrigger());
        contentValues.put(MessageTable.COLUMN_RECIPIENTNUMBER, message.getRecipientNumber());
        contentValues.put(MessageTable.COLUMN_MESSAGE, message.getMessage());
        return contentValues;
    }

    private List<Message> toMessageList(Cursor cursor) {
        List<Message> list = new ArrayList<Message>();
        if (cursor == null) return list;
        if (cursor.moveToFirst()) {
            do {
                list.add(toMessage(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    private Message toMessage(Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0) return null;
        Message message = new Message();
        message.setId(cursor.getInt(cursor.getColumnIndex(MessageTable.COLUMN_ID)));
        message.setStatus(cursor.getInt(cursor.getColumnIndex(MessageTable.COLUMN_STATUS)));
        message.setLatitude(cursor.getDouble(cursor.getColumnIndex(MessageTable.COLUMN_LATITUDE)));
        message.setLongitude(cursor.getDouble(cursor.getColumnIndex(MessageTable.COLUMN_LONGITUDE)));
        message.setTrigger(cursor.getInt(cursor.getColumnIndex(MessageTable.COLUMN_TRIGGER)));
        message.setRecipientNumber(cursor.getString(cursor.getColumnIndex(MessageTable.COLUMN_RECIPIENTNUMBER)));
        message.setMessage(cursor.getString(cursor.getColumnIndex(MessageTable.COLUMN_MESSAGE)));
        return message;
    }
}

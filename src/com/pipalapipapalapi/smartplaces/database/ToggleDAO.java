package com.pipalapipapalapi.smartplaces.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.pipalapipapalapi.smartplaces.model.Toggle;


public class ToggleDAO {

    private SQLiteDatabase mDatabase;

    private static volatile ToggleDAO mInstance;

    public static ToggleDAO getInstance(Context context) {
        if (mInstance == null) {
            synchronized (ToggleDAO.class) {
                if (mInstance == null) {
                    mInstance = new ToggleDAO(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    private ToggleDAO(Context context) {
        mDatabase = SampleDatabaseOpenHelper.getInstance(context).getCachedDatabase();
    }

    public Cursor query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return mDatabase.query(ToggleTable.TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
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

    public List<Toggle> query(String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        Cursor cursor = mDatabase.query(ToggleTable.TABLE_NAME, null, selection, selectionArgs, groupBy, having, orderBy);
        return toToggleList(cursor);
    }

    public void query(final String selection, final String[] selectionArgs, final String groupBy, final String having, final String orderBy, final DAOListener<List<Toggle>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Toggle> ob = query(selection, selectionArgs, groupBy, having, orderBy);
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

    public List<Toggle> queryAll(String orderBy) {
        Cursor cursor = mDatabase.query(ToggleTable.TABLE_NAME, null, null, null, null, null, orderBy);
        return toToggleList(cursor);
    }

    public void queryAll(final String orderBy, final DAOListener<List<Toggle>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Toggle> ob = queryAll(orderBy);
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

    public List<Toggle> queryByField(String field, String args, String orderBy) {
        Cursor cursor = mDatabase.query(ToggleTable.TABLE_NAME, null, field + " = ?", new String[] { args }, null, null, orderBy);
        return toToggleList(cursor);
    }

    public void queryByField(final String field, final String args, final String orderBy, final DAOListener<List<Toggle>> listener) {
    	BackgroundThreadExecutor.getInstance().execute(new Runnable() {

			@Override
			public void run() {
				final List<Toggle> ob = queryByField(field, args, orderBy);
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

    public Toggle queryById(String value) {
        Cursor cursor = mDatabase.query(ToggleTable.TABLE_NAME, null, ToggleTable.COLUMN_ID + " = ?", new String[] { value }, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Toggle ob = toToggle(cursor);
            cursor.close();
            return ob;
        }
        return null;
    }

    public long insert(Toggle toggle) {
        return mDatabase.insert(ToggleTable.TABLE_NAME, null, asContentValues(toggle));
    }

    public int insert(List<Toggle> list) {
        int count = 0;
        try {
            mDatabase.beginTransaction();
            for (Toggle toggle : list) {
                long id = insert(toggle);
                if (id != -1) count += 1;
                mDatabase.yieldIfContendedSafely();
            }
            mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return count;
    }

    public void insert(final List<Toggle> list, final DAOListener<Integer> listener) {
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
        return mDatabase.update(ToggleTable.TABLE_NAME, contentValues, whereClause, whereArgs);
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
    	return mDatabase.update(ToggleTable.TABLE_NAME, contentValues, field + " = ?", new String[] { value });
    }

    public int updateById(ContentValues contentValues, String id) {
    	return mDatabase.update(ToggleTable.TABLE_NAME, contentValues, ToggleTable.COLUMN_ID + " = ?", new String[] { id });
    }

    public int updateOrInsertById(List<Toggle> list) {
        int count = 0;
        try {
        mDatabase.beginTransaction();
        for (Toggle toggle : list) {
            int affected = update(asContentValues(toggle), ToggleTable.COLUMN_ID + " = ?", new String[] { toggle.getId() + "" });
            if (affected == 0) insert(toggle);
            count += affected;
            mDatabase.yieldIfContendedSafely();
        }
        mDatabase.setTransactionSuccessful();
        } finally {
            mDatabase.endTransaction();
        }
        return count;
    }

    public void updateOrInsertById(final List<Toggle> list, final DAOListener<Integer> listener) {
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
        return mDatabase.delete(ToggleTable.TABLE_NAME, whereClause, whereArgs);
    }

    public int deleteAll() {
        return mDatabase.delete(ToggleTable.TABLE_NAME, null, null);
    }

    public int deleteByField(String field, String value) {
        return mDatabase.delete(ToggleTable.TABLE_NAME, field + " = ?", new String[] { value });
    }

    public int deleteById(String value) {
        return mDatabase.delete(ToggleTable.TABLE_NAME, ToggleTable.COLUMN_ID + " = ?", new String[] {value});
    }

    public ContentValues asContentValues(Toggle toggle) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ToggleTable.COLUMN_ID, toggle.getId());
        contentValues.put(ToggleTable.COLUMN_STATUS, toggle.getStatus());
        contentValues.put(ToggleTable.COLUMN_LATITUDE, toggle.getLatitude());
        contentValues.put(ToggleTable.COLUMN_LONGITUDE, toggle.getLongitude());
        contentValues.put(ToggleTable.COLUMN_TRIGGER, toggle.getTrigger());
        contentValues.put(ToggleTable.COLUMN_WIFISTATE, toggle.getWifiState());
        contentValues.put(ToggleTable.COLUMN_MOBILEDATASTATE, toggle.getMobileDataState());
        contentValues.put(ToggleTable.COLUMN_RINGERSTATE, toggle.getRingerState());
        contentValues.put(ToggleTable.COLUMN_BLUETOOTHSTATE, toggle.getBluetoothState());
        contentValues.put(ToggleTable.COLUMN_AIRPLANEMODESTATE, toggle.getAirplaneModeState());
        return contentValues;
    }

    private List<Toggle> toToggleList(Cursor cursor) {
        List<Toggle> list = new ArrayList<Toggle>();
        if (cursor == null) return list;
        if (cursor.moveToFirst()) {
            do {
                list.add(toToggle(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    private Toggle toToggle(Cursor cursor) {
        if (cursor == null || cursor.getCount() <= 0) return null;
        Toggle toggle = new Toggle();
        toggle.setId(cursor.getInt(cursor.getColumnIndex(ToggleTable.COLUMN_ID)));
        toggle.setStatus(cursor.getInt(cursor.getColumnIndex(ToggleTable.COLUMN_STATUS)));
        toggle.setLatitude(cursor.getDouble(cursor.getColumnIndex(ToggleTable.COLUMN_LATITUDE)));
        toggle.setLongitude(cursor.getDouble(cursor.getColumnIndex(ToggleTable.COLUMN_LONGITUDE)));
        toggle.setTrigger(cursor.getInt(cursor.getColumnIndex(ToggleTable.COLUMN_TRIGGER)));
        toggle.setWifiState(cursor.getInt(cursor.getColumnIndex(ToggleTable.COLUMN_WIFISTATE)));
        toggle.setMobileDataState(cursor.getInt(cursor.getColumnIndex(ToggleTable.COLUMN_MOBILEDATASTATE)));
        toggle.setRingerState(cursor.getInt(cursor.getColumnIndex(ToggleTable.COLUMN_RINGERSTATE)));
        toggle.setBluetoothState(cursor.getInt(cursor.getColumnIndex(ToggleTable.COLUMN_BLUETOOTHSTATE)));
        toggle.setAirplaneModeState(cursor.getInt(cursor.getColumnIndex(ToggleTable.COLUMN_AIRPLANEMODESTATE)));
        return toggle;
    }
}

package com.bitty.notifyme;

import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class NotifyMeDBAdapter
{
	private static final String TAG = "NotifyMeDBAdapter";
	
	private static final String DATABASE_NAME = "notifyMe.db";
	private static final String DATABASE_TABLE = "notifyItems";
	private static final int DATABASE_VERSION = 2;

	public static final String KEY_ID = "_id";
	public static final String KEY_NOTIFY_DAY= "day";
	public static final String KEY_NOTIFY_LINES = "lines";
	public static final String KEY_NOTIFY_HOUR = "hour";
	public static final String KEY_NOTIFY_MINUTES = "minutes";

	private SQLiteDatabase db;
	private final Context context;
	private notifyDBOpenHelper dbHelper;

	public NotifyMeDBAdapter(Context _context) {
		this.context = _context;
		dbHelper = new notifyDBOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.w(TAG, "NotifyMeDBAdapter contructor");
	}

	public void close() {
		db.close();
	}

	public void open() {
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbHelper.getReadableDatabase();
		}
	}

	// Insert a new task
	public long insertTask(NotifyMeItem _notify) {
		// Create a new row of values to insert
		ContentValues newTaskValues = new ContentValues();
		// Assign values for each row
		newTaskValues.put(KEY_NOTIFY_LINES, _notify.getLines());
		newTaskValues.put(KEY_NOTIFY_DAY, _notify.getDay());
		newTaskValues.put(KEY_NOTIFY_HOUR, _notify.getHour());
		newTaskValues.put(KEY_NOTIFY_MINUTES, _notify.getMinutes());
		// Insert the row
		return db.insert(DATABASE_TABLE, null, newTaskValues);

	}

	// Remove a task based on its index
	public boolean removeTask(long _rowIndex) {
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + _rowIndex, null) > 0;
	}

	// Update a task
	public boolean updateTask(long _rowIndex, String _notify) {
		ContentValues newValue = new ContentValues();
		newValue.put(KEY_NOTIFY_DAY, _notify);
		return db.update(DATABASE_TABLE, newValue, KEY_ID + "=" + _rowIndex, null) > 0;
	}

	public Cursor getAllNotifyItemsCursor() {
		return db.query(DATABASE_TABLE,
				new String[] { KEY_ID, KEY_NOTIFY_DAY, KEY_NOTIFY_HOUR, KEY_NOTIFY_LINES }, null, null, null, null,
				null);
	}

	public Cursor setCursorNotifyItem(long _rowIndex) throws SQLException {
		Cursor result = db.query(true, DATABASE_TABLE, new String[] { KEY_ID, KEY_NOTIFY_HOUR, KEY_NOTIFY_DAY, KEY_NOTIFY_LINES},
				KEY_ID + "=" + _rowIndex, null, null, null, null, null);

		// Checks if row exits
		if ((result.getCount() == 0) || !result.moveToFirst()) {
			throw new SQLException("No to do item found for row: " + _rowIndex);
		}
		return result;
	}

	public NotifyMeItem getNotifyItem(long _rowIndex) throws SQLException {
		Cursor cursor = db.query(true, DATABASE_TABLE, new String[] { KEY_ID, KEY_NOTIFY_LINES, KEY_NOTIFY_DAY, KEY_NOTIFY_HOUR, KEY_NOTIFY_MINUTES},
				KEY_ID + "=" + _rowIndex, null, null, null, null, null);

		if ((cursor.getCount() == 0) || !cursor.moveToFirst()) {
			throw new SQLException("No to do item found for row: " + _rowIndex);
		}

		String lines = cursor.getString(cursor.getColumnIndex(KEY_NOTIFY_LINES));
		int day = cursor.getInt(cursor.getColumnIndex(KEY_NOTIFY_DAY));
		int hour = cursor.getInt(cursor.getColumnIndex(KEY_NOTIFY_HOUR));
		int minutes = cursor.getInt(cursor.getColumnIndex(KEY_NOTIFY_MINUTES));

		NotifyMeItem result = new NotifyMeItem(lines, day, hour, minutes);
		return result;
	}

	// ///////////////////////////////////////////////////
	// SQLite Helper Class
	// ///////////////////////////////////////////////////

	private static class notifyDBOpenHelper extends SQLiteOpenHelper
	{
		private static final String TAG = "notifyDBOpenHelper";
		
		public notifyDBOpenHelper(Context context, String name, CursorFactory factory,
				int version) {
			super(context, name, factory, version);
			Log.w(TAG, "Create DB");
		}

		// SQL Statement to create a new database
		private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE
				+ " (" + KEY_ID + " integer primary key autoincrement, " + KEY_NOTIFY_DAY
				+ " int, " + KEY_NOTIFY_LINES + " text not null, " 
				+ KEY_NOTIFY_HOUR + " int, " + KEY_NOTIFY_MINUTES + " int);";

		@Override
		public void onCreate(SQLiteDatabase _db) {
			Log.w(TAG, "Create DB");
			_db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
			Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion + " to "
					+ _newVersion + ", which will destroy all old data");

			// Drop the old table.
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			// Create a new one
			onCreate(_db);
		}
	}
}

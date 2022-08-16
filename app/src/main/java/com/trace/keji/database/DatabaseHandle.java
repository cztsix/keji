package com.trace.keji.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHandle {
	
	private SQLiteDatabase db;
	
	public void insertDatabase(String dateStr,String keyStr,String valueStr,MyDatabaseHelper dbHelper){
		db= dbHelper.getWritableDatabase();
		ContentValues contentvalues = new ContentValues();
		contentvalues.put("DateTime", dateStr);
		contentvalues.put("Key", keyStr);
		contentvalues.put("Value", valueStr);
		db.insert(MyDatabaseHelper.tableName, null, contentvalues);
	}
	
	public Cursor seleteDatabase(String whereStr,String[] parameter){
		String tiaojiao = whereStr+"=?";
		Cursor cursor = db.query(MyDatabaseHelper.tableName, null, tiaojiao, parameter, null, null, null);
		return cursor;
	}
}

package com.trace.keji.database;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.trace.keji.model.DatabaseModel;

/**
 * 数据库操作管理类
 * 
 * @author Administrator
 * 
 */
@SuppressLint("SimpleDateFormat")
public class DBManager {

	public static final String DB_NAME = "information_table";
	public static SQLiteDatabase db = null;

	public Context context;
	public static DatabaseHelper databaseHelper;

	public static Integer sqliteLock = 1000;
	public static DBManager dbManager;

	public DBManager(Context context) {
		this.context = context;
	}

	public static DBManager getDBManagerInit(Context context) {
		if (dbManager == null) {
			dbManager = new DBManager(context);
		}
		if (databaseHelper == null) {
			databaseHelper = new DatabaseHelper(context, DB_NAME, null, 1);
		}
		if (db == null) {
			db = databaseHelper.getWritableDatabase();
		}
		return dbManager;
	}

	public void closedatabase() {
		if (db != null) {
			db.close();
			db = null;
		}
	}
	
	
	/**
	 * 保存key value键值对到数据库
	 * @param key
	 * @param value
	 */
	public void addContent(String key, String value) {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("Key", key);
		values.put("Value", value);
		values.put("DateTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		db.insert(DB_NAME, null, values);
	}
	
	
	/**
	 * 通过key获取所有Value
	 * @param key
	 * @return
	 */
	public List<DatabaseModel> getModelListByKey(String key) {
		// TODO Auto-generated method stub
		List<DatabaseModel> list = new ArrayList<DatabaseModel>();
		DatabaseModel model; 
		
		String sql  = "select * from " +DB_NAME+ " where Key= '"+key + "' order by id desc";
		Cursor c = db.rawQuery(sql, null);
		if (c != null) {
			while (c.moveToNext()) {
				model = new DatabaseModel();
				model.key = c.getString(c.getColumnIndex("Key"));
				model.value = c.getString(c.getColumnIndex("Value"));
				model.time = c.getString(c.getColumnIndex("DateTime"));
				list.add(model);
			}
		}
		closeCursor(c);
		return list;
	}
	

	/**
	 * 获取所有Key Value
	 * @return
	 */
	public List<DatabaseModel> getModelList() {
		// TODO Auto-generated method stub
		List<DatabaseModel> list = new ArrayList<DatabaseModel>();
		DatabaseModel model; 
		
		String sql  = "select * from " +DB_NAME + " where 1=1 " + " order by id desc";
		Cursor c = db.rawQuery(sql, null);
		if (c != null) {
			while (c.moveToNext()) {
				model = new DatabaseModel();
				model.key = c.getString(c.getColumnIndex("Key"));
				model.value = c.getString(c.getColumnIndex("Value"));
				model.time = c.getString(c.getColumnIndex("DateTime"));
				list.add(model);
			}
		}
		closeCursor(c);
		return list;
	}


	
	
	public DatabaseModel seleteData(String whereStr, String[] parameter){
		DatabaseModel mDatabaseModel = null;
		
		Cursor cursor = db.query(MyDatabaseHelper.tableName, null, whereStr+"=?", parameter, null, null, null);

		if (cursor != null) {
			while (cursor.moveToNext()) {
				mDatabaseModel = new DatabaseModel();
				mDatabaseModel.time = cursor.getString(cursor.getColumnIndex("DateTime"));
				mDatabaseModel.key= cursor.getString(cursor.getColumnIndex("Key"));
				mDatabaseModel.value = cursor.getString(cursor.getColumnIndex("Value"));
			}
		}
		return mDatabaseModel;
	}
	
	
	
	/**
	 * 删除所有
	 */
	public void delAllCollect() {
		String sql = "delete from " +DB_NAME ;
		db.execSQL(sql);
	}

	
	/**
	 * 关闭游标
	 * 
	 * @param cursor
	 */
	public void closeCursor(Cursor cursor) {
		if (cursor != null) {
			cursor.close();
			cursor = null;
		}
	}
	
}

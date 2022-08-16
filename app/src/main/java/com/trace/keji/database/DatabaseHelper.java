package com.trace.keji.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * 
 * 创建数据库
 * 
 * @author yinmenglei
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {

	public static Integer sqliteLock = 1100;

	public DatabaseHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, null, version);
	}

	public void onCreate(SQLiteDatabase db) {
		
		String TABLENAME = "create table if not exists "
				+ DBManager.DB_NAME
				+ " (id integer primary key autoincrement,"
				+ "DateTime varchar(20),"
				+ "Key varchar(100),"
				+ "Value varchar(1000))";

		db.execSQL(TABLENAME);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// db.execSQL("drop table if exists callTick");
		onCreate(db);
	}

}

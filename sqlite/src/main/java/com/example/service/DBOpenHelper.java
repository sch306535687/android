package com.example.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		super(context, "mySQLite", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL("create table person(id Integer primary key autoincrement,name,age)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("insert into person(name,age) values(?,?)", new Object[]{"ÕÅÈý","18"});

	}

}

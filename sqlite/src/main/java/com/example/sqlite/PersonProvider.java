package com.example.sqlite;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.service.DBOpenHelper;

public class PersonProvider extends ContentProvider {

	DBOpenHelper dbop;
	SQLiteDatabase db;
	// 定义uri匹配类
	private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	private static final int PERSON = 1;
	private static final int PERSONS = 2;
	// 给常量赋值
	static {
		MATCHER.addURI("com.example.sqlite.personprovider", "person", PERSON);
		MATCHER.addURI("com.example.sqlite.personprovider/#", "person", PERSONS);
	}

	@Override
	public boolean onCreate() {
		this.dbop = new DBOpenHelper(this.getContext());
		this.db = dbop.getWritableDatabase();
		return true;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {

		int num = 0;
		switch (MATCHER.match(uri)) {
			case 1:

				num = db.delete("pseron", selection, selectionArgs);

				break;
			case 2:
				//得到传入的id值
				long id = ContentUris.parseId(uri);
				String where = "id="+id;
				//判断外部是否传入条件语句
				if(selection!=null&&"".equals(selection.trim())){
					where+=" and "+selection;
				}
				num = db.delete("person", where, selectionArgs);

				break;

			default:
				throw new IllegalArgumentException("this is unknown uri"+uri);
		}

		return num;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {

		switch (MATCHER.match(uri)) {
			case 1:

				long rowid = db.insert("person", null, values);
				// 定义uri
				Uri newuri = Uri
						.parse("content://com.example.sqlite.personprovider/person"
								+ rowid);
				return newuri;

			default:
				throw new IllegalArgumentException("this is unknown uri"+uri);
		}

	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
						String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
					  String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}

package com.example.service;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bean.Person;

public class SQLiteMethods {

	private DBOpenHelper openHelper;

	public SQLiteMethods(Context context) {
		this.openHelper = new DBOpenHelper(context);
	}

	/**
	 * 添加数据
	 * @param person person对象
	 */
	public void add(Person person) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		db.execSQL("insert into person(name,age) values(?,?)", new Object[]{person.getName(),person.getAge()});
	}
	/**
	 * 删除数据
	 * @param id 对象id
	 */
	public void delete(int id) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		db.execSQL("delete from person where id=?", new Object[]{id});
	}
	/**
	 * 更新数据
	 * @param person person对象
	 */
	public void update(Person person) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		db.execSQL("update person set name=?,age=?", new Object[]{person.getName(),person.getAge()});
	}
	/**
	 * 查询数据
	 * @param id 对象id
	 * @return 返回用户对象
	 */
	public Person select(String id) {
		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from person where id=?", new String[]{id});
		Person person = null;
		if(cursor.moveToFirst()){
			person = new Person();
			person.setId(cursor.getInt(cursor.getColumnIndex("id")));
			person.setName(cursor.getString(cursor.getColumnIndex("name")));
			person.setAge(cursor.getInt(cursor.getColumnIndex("age")));

		}
		return person;
	}

	public List<Person> getALL(){
		List<Person> persons = new ArrayList<Person>();;

		SQLiteDatabase db = openHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from person", null);

		while(cursor.moveToNext()){
			Person person = new Person();
			person.setId(cursor.getInt(cursor.getColumnIndex("id")));
			person.setName(cursor.getString(cursor.getColumnIndex("name")));
			person.setAge(cursor.getInt(cursor.getColumnIndex("age")));
			persons.add(person);
		}

		return persons;

	}

}

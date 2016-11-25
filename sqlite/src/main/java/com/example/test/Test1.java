package com.example.test;

import java.util.List;

import android.test.AndroidTestCase;
import android.util.Log;

import com.example.bean.Person;
import com.example.service.DBOpenHelper;
import com.example.service.SQLiteMethods;

public class Test1 extends AndroidTestCase {
	
	private final String TAG="Test1";

	public void test() throws Exception{

		DBOpenHelper db = new DBOpenHelper(getContext());
		db.getWritableDatabase();
		
	}
	
	public void testAdd() throws Exception{

		SQLiteMethods method = new SQLiteMethods(getContext());
		for(int i=0;i<50;i++){
			
			Person person = new Person("уенч╪и"+i,i+1);
			method.add(person);
			
		}
		
	}
	
	public void testGetAll() throws Exception{

		SQLiteMethods method = new SQLiteMethods(getContext());
		List<Person> persons = method.getALL();
		for (Person person : persons) {
			Log.i(TAG,person.toString());
		}
		
	}
	
}

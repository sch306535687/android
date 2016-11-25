package com.example.studentservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class StudentService extends Service {

	private String[] students = {"张三","李四","王五"};
	IBinder binder = new studentBinder();
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	public String query(int num){
		if(num>0&&num<4){

			return students[num-1];

		}
		return null;

	}

	private class studentBinder extends Binder implements studentMethod{

		@Override
		public String queryStudent(int num) {

			return query(num);
		}

	}

}

package com.example.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.content.Context;

public class SaveService {

	private Context context;
	
	public SaveService(Context context) {
		this.context = context;
	}
	
	public void save(String filename,String content) throws Exception{
		
		//��ȡ���������
		FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
		//д���ļ�
		fos.write(content.getBytes());
		fos.close();
		
		
	}
	
}

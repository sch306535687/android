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
		
		//获取输出流对象
		FileOutputStream fos = context.openFileOutput(filename, Context.MODE_PRIVATE);
		//写入文件
		fos.write(content.getBytes());
		fos.close();
		
		
	}
	
}

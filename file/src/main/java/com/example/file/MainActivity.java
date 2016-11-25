package com.example.file;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.service.SaveService;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 获取按钮
		Button button = (Button) findViewById(R.id.btn);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获取文件名称
				EditText filename = (EditText) findViewById(R.id.text);
				final String text = filename.getText().toString();
				// 获取文件名称
				EditText content = (EditText) findViewById(R.id.content);
				final String contentText = filename.getText().toString();

				SaveService saveService = new SaveService(MainActivity.this);
				try {
					saveService.save(text,contentText);
					Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_LONG).show();
				} catch (Exception e) {
					Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

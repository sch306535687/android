package com.example.studentservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView text;
	private TextView name;
	private ServiceConnection conn = new StudentServiceConnection();
	private studentMethod method;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		text = (TextView)this.findViewById(R.id.number);
		Button button = (Button)this.findViewById(R.id.btn);
		name = (TextView)this.findViewById(R.id.name);
		
		Intent service = new Intent(this,StudentService.class);
		//�󶨷���
		bindService(service, conn, BIND_AUTO_CREATE);
		button.setOnClickListener(new StudentOnclick());
		
	}
	
	public final class StudentServiceConnection implements ServiceConnection{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			
			method = (studentMethod)service;
			
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			
			method=null;
			
		}
		
	}
	
	@Override
	protected void onDestroy() {
		unbindService(conn);
		super.onDestroy();
	}
	
	private final class StudentOnclick implements View.OnClickListener{

		@Override
		public void onClick(View v) {
		
			String studentNumber = text.getText().toString();
			String nameText = method.queryStudent(Integer.valueOf(studentNumber));
			name.setText(nameText);
			
		}
		
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

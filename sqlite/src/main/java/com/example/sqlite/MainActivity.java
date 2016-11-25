package com.example.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.bean.Person;
import com.example.service.SQLiteMethods;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取ListView空间对象
		ListView listView = (ListView) this.findViewById(R.id.listView);
		// 添加事件
		listView.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				ListView view = (ListView) parent;
				HashMap<String, Object> map = (HashMap) view.getItemAtPosition(position);
				Toast.makeText(getApplicationContext(), map.get("id").toString(), 1)
						.show();

			}
		});
		// 获取数据
		SQLiteMethods method = new SQLiteMethods(this);
		List<Person> persons = method.getALL();

		// 方法1 SimpleAdapter适配器
		List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();

		for (Person person : persons) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("id", person.getId());
			map.put("name", person.getName());
			map.put("age", person.getAge());
			data.add(map);
		}
		//
		// 获取适配器
		SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item,
				new String[] { "id", "name", "age" }, new int[] { R.id.id,
						R.id.name, R.id.age });

		listView.setAdapter(adapter);

		
		 //方法2 自定义适配器 
		/*PersonAdapter adapter = new PersonAdapter(this,persons,R.layout.item);
		 listView.setAdapter(adapter);*/
		 
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

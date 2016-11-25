package com.example.adapt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bean.Person;
import com.example.sqlite.R;

import java.util.List;

public class PersonAdapter extends BaseAdapter {

	private List<Person> persons;
	private int resource;
	private LayoutInflater flate;
	
	public PersonAdapter(Context context, List<Person> persons, int resource) {
		this.persons = persons;
		this.resource = resource;
		flate = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return persons.size();
	}

	@Override
	public Object getItem(int position) {
		return persons.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView==null){
			
			convertView = flate.inflate(resource, null);
			
		}
		
		//������
		TextView id = (TextView)convertView.findViewById(R.id.id);
		TextView name = (TextView)convertView.findViewById(R.id.name);
		TextView age = (TextView)convertView.findViewById(R.id.age);
		Person person = persons.get(position);
		id.setText(person.getId());
		name.setText(person.getName());
		age.setText(person.getAge());
		
		return convertView;
		
	}

}

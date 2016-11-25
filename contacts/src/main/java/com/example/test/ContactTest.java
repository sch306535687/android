package com.example.test;

import java.util.ArrayList;


import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

public class ContactTest extends AndroidTestCase {

	public void testAddContact() throws Exception {

		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = getContext().getContentResolver();
		// 第一个参数：内容提供者的主机名
		// 第二个参数：要执行的操作
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();

		// 操作1.添加Google账号，这里值为null，表示不添加
		ContentProviderOperation operation = ContentProviderOperation
				.newInsert(uri).withValue("account_name", null)// account_name:Google账号
				.build();

		// 操作2.添加data表中name字段
		uri = Uri.parse("content://com.android.contacts/data");
		ContentProviderOperation operation2 = ContentProviderOperation
				.newInsert(uri)
						// 第二个参数int previousResult:表示上一个操作的位于operations的第0个索引，
						// 所以能够将上一个操作返回的raw_contact_id作为该方法的参数
				.withValueBackReference("raw_contact_id", 0)
				.withValue("mimetype", "vnd.android.cursor.item/name")
				.withValue("data2", "张三").build();

		// 操作3.添加data表中phone字段
		uri = Uri.parse("content://com.android.contacts/data");
		ContentProviderOperation operation3 = ContentProviderOperation
				.newInsert(uri).withValueBackReference("raw_contact_id", 0)
				.withValue("mimetype", "vnd.android.cursor.item/phone_v2")
				.withValue("data2", "2").withValue("data1", "15099144117")
				.build();

		// 操作4.添加data表中的Email字段
		uri = Uri.parse("content://com.android.contacts/data");
		ContentProviderOperation operation4 = ContentProviderOperation
				.newInsert(uri).withValueBackReference("raw_contact_id", 0)
				.withValue("mimetype", "vnd.android.cursor.item/email_v2")
				.withValue("data2", "2")
				.withValue("data1", "zhouguoping@qq.com").build();

		operations.add(operation);
		operations.add(operation2);
		operations.add(operation3);
		operations.add(operation4);

		try {
			resolver.applyBatch("com.android.contacts", operations);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void testGetContact() throws Exception {
		final String TAG = "testGetContact";

		Uri uri = Uri.parse("content://com.android.contacts/contacts"); // 访问所有联系人

		ContentResolver resolver = getContext().getContentResolver();
		Cursor cursor = resolver.query(uri, new String[]{"_id"}, null, null, null);
		while(cursor.moveToNext()){
			int contactsId = cursor.getInt(0);
			StringBuilder sb = new StringBuilder("contactsId=");
			sb.append(contactsId);
			uri = Uri.parse("content://com.android.contacts/contacts/" + contactsId + "/data"); //某个联系人下面的所有数据
			Cursor dataCursor = resolver.query(uri, new String[]{"mimetype", "data1", "data2"}, null, null, null);
			while(dataCursor.moveToNext()){
				String data = dataCursor.getString(dataCursor.getColumnIndex("data1"));
				String type = dataCursor.getString(dataCursor.getColumnIndex("mimetype"));
				if("vnd.android.cursor.item/name".equals(type)){    // 如果他的mimetype类型是name
					sb.append(", name=" + data);
				} else if("vnd.android.cursor.item/email_v2".equals(type)){ // 如果他的mimetype类型是email
					sb.append(", email=" + data);
				} else if("vnd.android.cursor.item/phone_v2".equals(type)){ // 如果他的mimetype类型是phone
					sb.append(", phone=" + data);
				}
			}
			Log.i(TAG, sb.toString());
		}
	}
}

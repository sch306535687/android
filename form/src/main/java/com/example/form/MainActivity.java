package com.example.form;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// ��ȡ��ť
		Button button = (Button) this.findViewById(R.id.btn);
		// ��ȡ�ؼ�

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {
					public void run() {
						try { // �ύ���������
							String path = "http://192.168.8.24:8888/android/servlet/formServlet?username=sun&password=33333";
							// ��ȡURL
							URL url = new URL(path);
							// ��ȡ���Ӷ���
							HttpURLConnection con = (HttpURLConnection) url
									.openConnection();
							// ��������ʱʱ��
							con.setConnectTimeout(3000);
							// ��������ʽ
							con.setRequestMethod("GET");
							// ͨ��״̬���ж��Ƿ�ɹ�
							if (con.getResponseCode() == 200) {

								Toast.makeText(getApplicationContext(), "�ύ�ɹ�",
										1).show();
							}
						} catch (Exception e) {
							Toast.makeText(getApplicationContext(), "�ύʧ��", 1)
									.show();
							e.printStackTrace();
						}
					}
				}).start();

				/*
				 * // post ��ʽ String path = "username=sun&password=33333";
				 * byte[] data = path.getBytes();
				 * 
				 * URL url; try { url = new URL(
				 * "http://192.168.8.24:8888/android/servlet/formServlet");
				 * HttpURLConnection conn = (HttpURLConnection)
				 * url.openConnection(); conn.setConnectTimeout(3000); //
				 * ��������ʽΪPOST conn.setRequestMethod("POST"); // ����post�����Ҫ������ͷ
				 * conn.setRequestProperty("Content-Type",
				 * "application/x-www-form-urlencoded"); // ����ͷ, ��������
				 * conn.setRequestProperty("Content-Length", data.length + "");
				 * // ע�����ֽڳ���, // �����ַ��
				 * 
				 * conn.setDoOutput(true); // ׼��д��
				 * conn.getOutputStream().write(data); // д�����
				 * 
				 * if (conn.getResponseCode() == 200) {
				 * Toast.makeText(getApplicationContext(), "�ύ�ɹ�", 1).show(); }
				 * } catch (Exception e) {
				 * Toast.makeText(getApplicationContext(), "�ύʧ��", 1).show();
				 * e.printStackTrace(); }
				 */

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

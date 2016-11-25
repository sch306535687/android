package com.example.getimage;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.os.Message;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.os.Handler;

public class MainActivity extends Activity {

	private EditText text;
	private static ImageView image;
	static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			image.setImageBitmap((Bitmap)msg.obj);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// 获取按钮
		Button button = (Button) this.findViewById(R.id.btn);
		// 获取文本值
		text = (EditText) this.findViewById(R.id.image);
		// 获取显示图片控件
		image = (ImageView) this.findViewById(R.id.showImage);

		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				new Thread(new Runnable() {
					public void run() {

						String path = text.getText().toString();
						try {
							// 获取URL
							URL url = new URL(path);
							// 获取连接对象
							HttpURLConnection con = (HttpURLConnection) url
									.openConnection();
							// 设置请求超时时间
							con.setConnectTimeout(3000);
							// 设置请求方式
							con.setRequestMethod("GET");
							// 通过状态码判断是否成功
							if (con.getResponseCode() == 200) {

								// 获取输入流
								InputStream is = con.getInputStream();
								int len = 0;
								byte[] b = new byte[1024];
								ByteArrayOutputStream bos = new ByteArrayOutputStream();
								while ((len = is.read(b)) != -1) {
									bos.write(b, 0, len);
								}

								byte[] data = bos.toByteArray();
								// 字节数组放入位图对象
								final Bitmap bitmap = BitmapFactory.decodeByteArray(data,
										0, data.length);
								// 把位图对象发送到主线程
								handler.sendMessage(handler.obtainMessage(1, bitmap));

								//Toast.makeText(getApplicationContext(), "提交成功", Toast.LENGTH_SHORT).show();

							}
						} catch (Exception e) {
							handler.sendMessage(handler.obtainMessage(0));
							//Toast.makeText(getApplicationContext(), "提交失败", Toast.LENGTH_SHORT).show();
							e.printStackTrace();
						}

					}
				}).start();

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

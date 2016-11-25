package com.example.webservice;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends Activity {

    private EditText text;
    private static TextView showText;
    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //��������
            showText.setText((String) msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ��ȡ��ť
        final Button button = (Button) this.findViewById(R.id.btn);
        // ��ȡ����
        text = (EditText) this.findViewById(R.id.phone);
        // ��ȡ��ʾ�ؼ�
        showText = (TextView) this.findViewById(R.id.show);

        //����¼�
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //���������߳�
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {

                            String number = text.getText().toString();
                            String address = query(number);
                            //�������ݵ����߳�
                            handler.sendMessage(handler.obtainMessage(1, address));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }).start();

            }

        });

    }

    public String query(String num) throws Exception {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("soap.xml");
        int len = 0;
        byte[] b = new byte[1024];
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = in.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        byte[] data = bos.toByteArray();
        String xml = new String(data);
        //�滻
        xml = xml.replace("#", num);
        byte[] sendData = xml.getBytes("UTF-8");
        //���͵�����ĵ�ַ��
        URL url = new URL("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
        conn.setRequestProperty("Content-Length", String.valueOf(sendData.length));
        //�������xml���ͳ�ȥ
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.getOutputStream().write(sendData);
        //��ȡ�ӷ�����������������
        if (conn.getResponseCode() == 200)
            return parse(conn.getInputStream());
        return null;
    }

    //�������õ�getMobileCodeInfoResult�е�����
    private String parse(InputStream inputStream) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inputStream, "UTF-8");
        //����getMobileCodeInfoResult��ǩ����ȡ��ǩ�е�����
        for (int event = parser.getEventType(); event != XmlPullParser.END_DOCUMENT; event = parser.next())
            switch (event) {
                case XmlPullParser.START_TAG:
                    if ("getMobileCodeInfoResult".equals(parser.getName()))
                        return parser.nextText();
            }

        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

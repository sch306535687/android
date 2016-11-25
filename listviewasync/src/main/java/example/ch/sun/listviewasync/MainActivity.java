package example.ch.sun.listviewasync;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

import sun.ch.bean.Person;
import sun.ch.utils.XmlwebData;


public class MainActivity extends Activity {

    private ArrayList<Person> persons;
    private File SDCardFile;
    private ListView listView;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取SDCard目录
        SDCardFile = new File(Environment.getExternalStorageDirectory(),"cache");
        if(!SDCardFile.exists()){
            SDCardFile.mkdirs();
        }

        //获取ListView
       listView = (ListView) this.findViewById(R.id.listView);
        //封装xml数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Thread.sleep(4000);
                    persons = XmlwebData.getData("http://192.168.8.24:8888/android/person.xml");
                    handler.sendMessage(handler.obtainMessage(1, persons));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
       handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                //适配器
                listView.setAdapter(new PersonAdapt(MainActivity.this, (ArrayList<Person>)msg.obj, R.layout.item, SDCardFile));

            }
        };
    }

    @Override
    protected void onDestroy() {

        Log.i("AAA",SDCardFile+"");
        for(File file : SDCardFile.listFiles()){
            file.delete();
        }
        SDCardFile.delete();
        super.onDestroy();
    }

}

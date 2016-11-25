package example.ch.sun.threaddownload;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity {
    //定义执行了多少线程
    int thread = 0;
    //定义线程数
    int threadCount = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取按钮
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //定义下载路径
                        String path = "http://192.168.8.24:8888/android/down.exe";
                        //定义文件长度
                        int leng = 0;
                        //定义每条线程下载量
                        int size = 0;

                        try {
                            URL url = new URL(path);
                            HttpURLConnection con = (HttpURLConnection) url.openConnection();
                            con.setRequestMethod("GET");
                            con.setConnectTimeout(5000);
                            con.setReadTimeout(5000);
                            if( con.getResponseCode() == 200 ){
                                //获取文件长度
                                leng = con.getContentLength();
                                //获取每条线程下载量
                                size = leng/threadCount;
                                //设置文件保存位置
                                String filename = path.substring(path.lastIndexOf("/")+1);
                                File file = new File(Environment.getExternalStorageDirectory(),filename);
                                RandomAccessFile raf = new RandomAccessFile(file,"rwd");
                                //设置文件大小
                                raf.setLength(leng);
                                //开启线程
                                for( int i=0;i<threadCount;i++ ){
                                    //定义开始位置
                                    int startIndex = i*size;
                                    //定义结束位置
                                    int endIndex = (i+1)*size - 1;
                                    //如果是最后一条线程，结束位置为下载文件的总长度
                                    if( i == threadCount-1 ){
                                        endIndex = leng;
                                    }
                                    //开线程
                                    new MyThread(i,startIndex, endIndex, path, file ).start();
                                }
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });


    }

    public class MyThread extends Thread {
        int i;
        int startIndex;
        int endIndex;
        String path;
        File file;
        public MyThread(int i,int startIndex, int endIndex, String path, File file) {
            super();
            this.i = i;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.path = path;
            this.file = file;
        }

        @Override
        public void run() {

            try {
                //开始下载
                URL url = new URL(path);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setConnectTimeout(5000);
                con.setReadTimeout(5000);
                //判断记录下载量的文件是否存在
                File f = new File(Environment.getExternalStorageDirectory(),i+".txt");
                if( f.exists() ){
                    FileInputStream fis = new FileInputStream(f);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                    int doneTotal = Integer.parseInt(br.readLine());
                    startIndex += doneTotal;
                }
                //设置请求头
                con.setRequestProperty("Range","bytes="+startIndex+"-"+endIndex);
                //拿到随机生成文件
                RandomAccessFile raf = new RandomAccessFile(file,"rwd");
                //设置开始位置
                raf.seek(startIndex);
                if( con.getResponseCode() == 206 ){
                    //获取输入流
                    InputStream is = con.getInputStream();
                    //定义当前线程已下载量
                    int total = 0;
                    //读取流到随机文件中
                    int len = 0;
                    byte[] b = new byte[1024];
                    while( (len=is.read(b)) != -1 ){
                        raf.write(b,0,len);
                        total+=len;
                        //把当前线程下载量保存到文件中
                        File file = new File(Environment.getExternalStorageDirectory(),i+".txt");
                        RandomAccessFile ProgressRaf = new RandomAccessFile(file,"rwd");
                        ProgressRaf.write((total+"").getBytes());
                        ProgressRaf.close();
                        System.out.println("线程"+i+"已下载"+total);
                    }
                    raf.close();
                    //判断线程是否执行完毕
                    synchronized (path){
                        thread++;
                        if( thread == threadCount ){
                            //删除用来存储上次已下载量文件
                            for( int i=0;i<thread;i++ ){
                                File file = new File(Environment.getExternalStorageDirectory(),i+".txt");
                                file.delete();
                            }
                            thread=0;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}

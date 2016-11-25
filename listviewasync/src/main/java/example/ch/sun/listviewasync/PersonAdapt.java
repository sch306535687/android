package example.ch.sun.listviewasync;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import sun.ch.bean.Person;
import sun.ch.utils.md5;

/**
 * Created by Administrator on 2016/11/3.
 */
public class PersonAdapt extends BaseAdapter {
    private Context context;
    private ArrayList<Person> persons;
    private int item;
    private File cache;
    private LayoutInflater inflater;
    public PersonAdapt(Context context,ArrayList<Person> persons,int item,File cache) {
        this.context = context;
        this.persons = persons;
        this.item = item;
        this.cache = cache;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return persons.size();
    }

    @Override
    public Object getItem(int i) {
        return persons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PersonWraper personWraper;
        TextView name;
        TextView sex;
        TextView age;
        ImageView imageView;
        if(view==null){
            view = inflater.inflate(item,null);
             name = (TextView)view.findViewById(R.id.name);
             sex = (TextView)view.findViewById(R.id.sex);
             age = (TextView)view.findViewById(R.id.age);
            imageView = (ImageView)view.findViewById(R.id.imageView);
            personWraper = new PersonWraper(name,sex,age,imageView);
            view.setTag(personWraper);
        }else{
            personWraper = (PersonWraper)view.getTag();
             name = personWraper.getName();
             sex = personWraper.getSex();
             age = personWraper.getAge();
            imageView = personWraper.getImageView();
        }
        Person person = persons.get(i);
        name.setText(person.getName());
        sex.setText(person.getSex());
        age.setText(String.valueOf(person.getAge()));
        //设置图片Uri
        //setImage(person.getPath(),imageView);
        LoadImage(person.getPath(),imageView);

        return view;
    }
    private void LoadImage(String path,ImageView img)
    {
        //异步加载图片资源
        AsyncTaskImageLoad async=new AsyncTaskImageLoad(img);
        //执行异步加载，并把图片的路径传送过去
        async.execute(path);

    }
    private final class AsyncTaskImageLoad extends AsyncTask<String, Integer, Uri> {

        private ImageView imageView;

        public AsyncTaskImageLoad(ImageView img)
        {
            imageView=img;
        }
        //运行在子线程中
        protected Uri doInBackground(String... params) {
            try
            {
                return getUri(params[0]);

            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Uri uri)
        {
            if(imageView!=null && uri!=null)
            {
                imageView.setImageURI(uri);
            }
        }
        /*private ImageView Image=null;

        public AsyncTaskImageLoad(ImageView img)
        {
            Image=img;
        }
        //运行在子线程中
        protected Bitmap doInBackground(String... params) {
            try
            {
                URL url=new URL(params[0]);
                HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setConnectTimeout(5000);
                if(conn.getResponseCode()==200)
                {
                    InputStream input=conn.getInputStream();
                    Bitmap map= BitmapFactory.decodeStream(input);
                    return map;
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Bitmap result)
        {
            if(Image!=null && result!=null)
            {
                Image.setImageBitmap(result);
            }

            super.onPostExecute(result);
        }*/
    }

    private Uri getUri(String path) {

        String imageName = md5.MD5(path) + path.substring(path.lastIndexOf("."));
        File file = new File(cache,imageName);

        if( file.exists() ){
            return Uri.fromFile(file);
        }else{
            try {
                FileOutputStream fos = new FileOutputStream(file);
                HttpURLConnection conn = (HttpURLConnection)new URL(path).openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                if( conn.getResponseCode() == 200 ){
                    InputStream is = conn.getInputStream();
                    int len = 0;
                    byte[] b = new byte[1024];
                    while( (len=is.read(b)) != -1 ){
                        fos.write(b,0,len);
                    }
                    fos.close();
                    is.close();
                    return Uri.fromFile(file);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private class PersonWraper {
        private TextView name;
        private TextView sex;
        private TextView age;
        private ImageView imageView;

        public PersonWraper(TextView name, TextView sex, TextView age,ImageView imageView) {
            this.name = name;
            this.sex = sex;
            this.age = age;
            this.imageView = imageView;
        }

        public TextView getName() {
            return name;
        }

        public TextView getSex() {
            return sex;
        }

        public TextView getAge() {
            return age;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}

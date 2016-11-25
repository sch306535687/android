package example.ch.sun.popupwindow;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;


public class MainActivity extends Activity {

    private Button button;
    private  PopupWindow pop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取填充服务
        View popview = getLayoutInflater().inflate(R.layout.popup,null);
        //创建泡泡框
        pop = new PopupWindow(popview, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //点击空白处隐藏
        pop.setBackgroundDrawable(new BitmapDrawable());
        //取得焦点
        pop.setFocusable(true);
         button = (Button)this.findViewById(R.id.button);
        View view = this.findViewById(R.id.self);

         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                pop.showAtLocation(view, Gravity.BOTTOM,0,0);

             }
         });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

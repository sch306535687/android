package example.ch.sun.widget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/11/1.
 */
public class TimeService extends Service {
    private Timer timer ;
    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SimpleDateFormat simpleformat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = simpleformat.format(new Date());
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.appwidget);
                views.setTextViewText(R.id.text, date);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
                appWidgetManager.updateAppWidget(new ComponentName(getApplication(), WidgetProvider.class), views);
            }
        },0,1000);

    }

    @Override
    public void onDestroy() {
        timer.cancel();
        timer = null;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

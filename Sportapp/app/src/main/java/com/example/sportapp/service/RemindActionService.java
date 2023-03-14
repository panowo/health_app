package com.example.sportapp.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.sportapp.pages.MainActivity;
import com.example.sportapp.R;

public class RemindActionService extends Service {
    private Context mContext;
    private NotificationManager notificationManager;
    private Notification.Builder mBuilder;
    private Notification notification;
    private NotificationManager mManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new Notification.Builder(mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "channelId";
            String channelName = "这是用来测试的notification";
            createNotificationChannel(channelId, channelName, NotificationManagerCompat.IMPORTANCE_HIGH);
        }
        mManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent intent2=new Intent();
        intent2.setClass(this, MainActivity.class);//点击通知需要跳转的activity
        PendingIntent contentIntent = PendingIntent.getActivity(mContext,0, intent2,
                PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = "channelId";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = "这是用来测试的notification";
            createNotificationChannel(channelId, channelName, NotificationManagerCompat.IMPORTANCE_HIGH);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
        builder.setContentTitle("运动健康打卡")
                .setContentText("运动健康打卡小助手提醒您打卡！")
                .setContentIntent(contentIntent)
                //指定通知被创建的时间
                .setWhen(System.currentTimeMillis());

        Notification notification = builder.build();
        mManager.notify(1, notification);

        return START_REDELIVER_INTENT;
    }
}
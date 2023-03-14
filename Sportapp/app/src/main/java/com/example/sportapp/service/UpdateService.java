package com.example.sportapp.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateService extends Service {
    int TIME_INTERVAL = 5000; // 这是5s
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter(TEST_ACTION);
        registerReceiver(receiver, intentFilter);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setAction(TEST_ACTION);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0低电量模式需要使用该方法触发定时任务
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4以上 需要使用该方法精确执行时间
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } else {//4。4一下 使用老方法
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), TIME_INTERVAL, pendingIntent);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static final String TEST_ACTION = "XXX.XXX.XXX" + "_TEST_ACTION";

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TEST_ACTION.equals(action)) {
                CreateNewHealthRecord();
                CreateNewSportRecord();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + TIME_INTERVAL, pendingIntent);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + TIME_INTERVAL, pendingIntent);
                }
            }
        }
    };

    //okhttp方法
    public void CreateNewHealthRecord(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sp = getSharedPreferences("record", Context.MODE_PRIVATE);

                    String userid=sp.getString("userid", null);
                    String datestring=sp.getString("date", null);
                    String timestring=sp.getString("time", null);
                    String place=sp.getString("place", null);
                    String sta=sp.getString("state", null);
                    String te=sp.getString("tem", null);

                    String TAG = "RESPONSE";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("ID", userid).add("DATE", datestring)
                            .add("TIME",timestring).add("PLACE",place)
                            .add("STATE",sta).add("TEM",te)
                            .build();
                    //Log.d("connect","123");
                    Request request = new Request.Builder().url("http://10.0.2.2:8080/HttpClient/NewHealthRecord").post(formBody).build();
                    Call requestcall = okHttpClient.newCall(request);
                    requestcall.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful())
                            {
                                String responsestr=response.body().string();
//                                Message message=new Message();
//                                //message.what=USER_LOGIN;
//                                message.what=0;
//                                message.obj=responsestr;
//                                handler.sendMessage(message);//使用Message传递消息给线程
                                Log.i(TAG,"HealthResponse:"+responsestr);
                            }

                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //okhttp方法
    public void CreateNewSportRecord(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sp = getSharedPreferences("sport_record", Context.MODE_PRIVATE);

                    String userid=sp.getString("userid", null);
                    String datestring=sp.getString("date", null);
                    String timestring=sp.getString("time", null);
                    String place=sp.getString("place", null);
                    String dur_time=sp.getString("dur_time",null);

                    String TAG = "RESPONSE";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("ID", userid).add("DATE", datestring)
                            .add("TIME",timestring).add("PLACE",place).add("DUR_TIME",dur_time)
                            .build();
                    //Log.d("connect","123");
                    Request request = new Request.Builder().url("http://10.0.2.2:8080/HttpClient/NewSportRecord").post(formBody).build();
                    Call requestcall = okHttpClient.newCall(request);
                    requestcall.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful())
                            {
                                String responsestr=response.body().string();
//                                Message message=new Message();
//                                //message.what=USER_LOGIN;
//                                message.what=0;
//                                message.obj=responsestr;
//                                handler.sendMessage(message);//使用Message传递消息给线程
                                Log.i(TAG,"SportResponse:"+responsestr);
                            }

                        }
                    });
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
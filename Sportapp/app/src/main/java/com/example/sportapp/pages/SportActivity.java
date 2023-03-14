package com.example.sportapp.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportapp.R;
import com.example.sportapp.ui.UserAcitivity;

public class SportActivity extends AppCompatActivity {

    private int seconds = 0;
    private boolean running = false; //计时状态
    private boolean wasRunning = false; //保存running的状态

    //app进入后台，暂停计时
    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    //重新进入app，开始计时
    @Override
    protected void onStart() {
        super.onStart();
        if(wasRunning) running = true;
    }

    //失去焦点(如分屏)，暂停计时
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    //获得焦点,重新开始计时
    @Override
    protected void onResume() {
        super.onResume();
        if(wasRunning) running = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport);
        //获取保存的状态
        if(savedInstanceState!=null){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTime();
    }

    /**
     *保存状态
     */
    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {

        super.onSaveInstanceState(saveInstanceState);
        saveInstanceState.putInt("seconds",seconds);
        saveInstanceState.putBoolean("running",running);
        saveInstanceState.putBoolean("wasRunning",wasRunning);
    }
    /**
     * 响应button的onClick事件
     * 方法名和onClick的值一致
     */
    public void onClickStart(View button){
        running = true;
    }
    public void onClickStop(View button){
        running = false;
        Intent intent = getIntent();
        String userid=intent.getStringExtra("userid");
        String datestring=intent.getStringExtra("date");
        String timestring=intent.getStringExtra("time");
        String place=intent.getStringExtra("place");

        final TextView textView = findViewById(R.id.timeView);
        String dur_time = textView.getText().toString();

        SharedPreferences sp1 = this.getSharedPreferences("sport_record", Context.MODE_PRIVATE);
        Log.i("SPORT_RECORD", userid+"-"+datestring+"-"+timestring+"-"+place+"-"+dur_time);
        sp1.edit()
                .putString("userid",userid)
                .putString("date",datestring)
                .putString("time",timestring)
                .putString("place",place)
                .putString("dur_time",dur_time)
                .apply();
        JumpUserActivity();
        Toast.makeText(this, "运动打卡结束", Toast.LENGTH_SHORT).show();

    }
    public void onClickReset(View button){
        running = false;
        seconds = 0;
    }

    /**
     * 注意 ui线程不能被堵塞，因此不能在ui线程中调用sleep方法
     * 只允许ui线程更新界面，不能在后台线程更新界面
     *
     * ** 使用ui线程的Handler定时更新 **
     * 将任务封装到 Runnable的run方法中 ，通过Handler的
     * post(立即提交任务)或postDelayed(实现定时调度)方法提交到ui线程
     */
    private void runTime(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
                         @Override
                         public void run() {
                             final TextView textView = findViewById(R.id.timeView);
                             int hour = seconds /3600%24;
                             int minute = seconds%3600/60;
                             String time = String.format("%02d:%02d:%02d",hour,minute,seconds%60);
                             textView.setText(time);
                             if(running) seconds++;
                             handler.postDelayed(this,1000);
                         }
                     }
        );

    }
    public void JumpUserActivity()
    {
        Intent  intent = new Intent();
        intent.setClass(this, UserAcitivity.class);
        this.startActivity(intent);
    }

}
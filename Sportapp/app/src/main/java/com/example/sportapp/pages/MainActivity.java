package com.example.sportapp.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.text.TextUtils;
import android.widget.Toast;
import android.widget.CheckBox;


import java.io.IOException;
import java.util.Calendar;

import android.os.Handler;

import com.example.sportapp.R;
import com.example.sportapp.service.RemindActionService;
import com.example.sportapp.ui.FourFragment.MyFragment;
import com.example.sportapp.ui.UserAcitivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStart = this.findViewById(R.id.btn_login);
        //要通过id找到对应的按钮
        Button btnStart3 = this.findViewById(R.id.btn_register);
        //要通过id找到对应的按钮
        TextView mEtuser = this.findViewById(R.id.et_user);
        //获取TextView的句柄
        TextView mEtpassword = this.findViewById(R.id.et_password);

        CheckBox remember;  //定义记住密码
        remember = (CheckBox) findViewById(R.id.remember);

        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);

        boolean isRemember = sp.getBoolean("remember_password",false);

        //保存密码
        Boolean loginstate=sp.getBoolean("state", false);

//        int id = getIntent().getIntExtra("id", 0);
//        if (id == 1) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.mobile_navigation,new MyFragment())
//                    .addToBackStack(null)
//                    .commit();
//        }

        //如果之前没有退出
        if(loginstate)
            JumpUserActivity();

        //如果之前选择了记住密码
        if (isRemember)
        {
            mEtuser.setText(sp.getString("username", null));
            mEtpassword.setText(sp.getString("password", null));
            sp.edit().putBoolean("remember_password",true).apply();
            remember.setChecked(true);
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            //JumpServerActivity();
                                            JumpLoginActivity();
                                        }
                                        //打开另一个函数？
                                    }

        );
        btnStart3.setOnClickListener(
                //点击事件监听
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        JumpNewActivity();
                    }
                    //打开另一个函数？
                }

        );
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        long start = System.currentTimeMillis() + 5 * 1000;
        Intent intent = new Intent(this, RemindActionService.class);
        intent.putExtra("id", 10);
        intent.putExtra("title", "测试");
        intent.putExtra("contentText", "测试本地通知");
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am= (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        //am.set(AlarmManager.RTC_WAKEUP, start , pendingIntent);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , pendingIntent);

    }

    //Intent实现多个Activity之间的跳转和数据传递，打开SecondActivity
    public void JumpLoginActivity()
    {
        //
        TextView mEtuser = this.findViewById(R.id.et_user);
        //获取TextView的句柄
        TextView mEtpassword = this.findViewById(R.id.et_password);

        String TAG="LOGIN";
        String username = mEtuser.getText().toString();
        String password = mEtpassword.getText().toString();

        Log.d(TAG,username);
//        //用来打印日志
//        boolean name = username.equals("CSDN");
//        boolean pass = password.equals("12345678");
//
//        if (TextUtils.isEmpty(mEtuser.getText().toString()) || TextUtils.isEmpty(mEtpassword.getText().toString()))
//        {
//            Toast.makeText(MainActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
//        } else if (name&&pass)
//        {
//            Intent intent = new Intent(MainActivity.this, ServerActivity.class);
//            intent.putExtra("userName", username);
//            intent.putExtra("password", password);
//            startActivity(intent);
//        }
//        else{
//            Toast.makeText(MainActivity.this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
//        }
        if (TextUtils.isEmpty(mEtuser.getText().toString()) || TextUtils.isEmpty(mEtpassword.getText().toString()))
        {
            Toast.makeText(MainActivity.this, "用户名或者密码不能为空", Toast.LENGTH_SHORT).show();
        } else
        {
            SendByOKHttp(username,password);
        }
    }
//httpclient方法
//    public void SendByHttpClient(final String id, final String pw){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    HttpClient httpclient=new DefaultHttpClient();
//                    //HttpPost httpPost=new HttpPost("http://localhost:8080/HttpClientDemo/Login");
//                    HttpPost httpPost=new HttpPost("http://10.0.2.2:8080/HttpClient/LoginServlet");
//                    //HttpPost httpPost=new HttpPost("http://192.168.191.1:8080/HttpClientDemo/Login");//服务器地址，指向Servlet
//                    //Log.d("connect","123");
//                    List<NameValuePair> params=new ArrayList<NameValuePair>();//将id和pw装入list
//                    params.add(new BasicNameValuePair("ID",id));
//                    params.add(new BasicNameValuePair("PW",pw));
//                    final UrlEncodedFormEntity entity=new UrlEncodedFormEntity(params,"utf-8");//以UTF-8格式发送
//                    httpPost.setEntity(entity);
//                    HttpResponse httpResponse= httpclient.execute(httpPost);
//                    if(httpResponse.getStatusLine().getStatusCode()==200)//在200毫秒之内接收到返回值
//                    {
//                        HttpEntity entity1=httpResponse.getEntity();
//                        String response=EntityUtils.toString(entity1, "utf-8");//以UTF-8格式解析
//                        Message message=new Message();
//                        //message.what=USER_LOGIN;
//                        message.what=0;
//                        message.obj=response;
//                        handler.sendMessage(message);//使用Message传递消息给线程
//                    }
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }

    //okhttp方法
    public void SendByOKHttp(final String id, final String pw){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String TAG = "RESPONSE";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("ID", id).add("PW", pw).build();
                    //Log.d("connect","123");
                    Request request = new Request.Builder().url("http://10.0.2.2:8080/HttpClient/LoginServlet").post(formBody).build();
                    Call requestcall = okHttpClient.newCall(request);
                    requestcall.enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            if(response.isSuccessful())
                            {
                                CheckBox remember;  //定义记住密码
                                remember = (CheckBox) findViewById(R.id.remember);
                                //SharedPreferences 保存数据
                                //如果选择了记住密码
                                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                                if(remember.isChecked())
                                {
                                    sp.edit()
                                            .putString("username", id)
                                            .putString("password", pw)
                                            .putBoolean("state",true)
                                            .putBoolean("remember_password",true)
                                            .apply();
                                }
                                else
                                    {
                                        sp.edit()
                                                .putString("username", id)
                                                .putString("password", pw)
                                                .putBoolean("state",true)
                                                .putBoolean("remember_password",false)
                                                .apply();
                                }

                                sp.edit().apply();

                               String responsestr=response.body().string();
                        Message message=new Message();
                        //message.what=USER_LOGIN;
                        message.what=0;
                        message.obj=responsestr;
                        handler.sendMessage(message);//使用Message传递消息给线程
                                Log.i(TAG,"Login state:"+responsestr);
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

    public static final int SHOW_RESPONSE=1;
    public Handler handler=new Handler() {
        public void handleMessage(Message msg)
        {
            switch (msg.what){
                case 0:
                    String response=(String)msg.obj;
                    if (response.equals("true"))
                    {Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                        JumpUserActivity();}
                    else
                    {
                        Toast.makeText(MainActivity.this, "用户名或者密码错误", Toast.LENGTH_SHORT).show();
                        SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                        sp.edit()
                                .putBoolean("state",false)
                                .putBoolean("remember_password",false)
                                .apply();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public void JumpNewActivity()
    {
        Intent  intent = new Intent();
        intent.setClass(this,RegisterActivity.class);
        this.startActivity(intent);
    }
    public void JumpUserActivity()
    {
        Intent  intent = new Intent();
        intent.setClass(this, UserAcitivity.class);
        this.startActivity(intent);
    }
}
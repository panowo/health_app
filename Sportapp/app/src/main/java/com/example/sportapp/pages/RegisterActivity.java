package com.example.sportapp.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.sportapp.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnStart = this.findViewById(R.id.btn_daka);
        //要通过id找到对应的按钮
        btnStart.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Register_Act();
                                        }
                                        //打开另一个函数？
                                    }

        );
    }


    public void Register_Act()
    {
        //TextView mEtcode = this.findViewById(R.id.eTuserlocation);
        TextView mEtid = this.findViewById(R.id.eTusertime);
        TextView mEtpassword = this.findViewById(R.id.eTusertem);
        TextView mEtpassword2 = this.findViewById(R.id.eTuserstate);

        String username =mEtid.getText().toString().trim();
        //String code =mEtcode.getText().toString().trim();
        String password =mEtpassword.getText().toString().trim();
        String password2 =mEtpassword2.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password2)) {
            Toast.makeText(RegisterActivity.this, "各项均不能为空", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.equals(password, password2)) {
                //执行注册操作
                //SaveInfo.SaveInformation(RegisterActivity.this,username,password,password2,mail);
                RegisterByOKHttp(username,password);
                //Toast.makeText(RegisterActivity.this,"注册成功,请返回登录",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(RegisterActivity.this, "两次输入的密码不一样", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    public void RegisterByHttpClient(final String id, final String pw){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    HttpClient httpclient=new DefaultHttpClient();
//                    //HttpPost httpPost=new HttpPost("http://localhost:8080/HttpClientDemo/Login");
//                    HttpPost httpPost=new HttpPost("http://10.0.2.2:8080/HttpClientDemo/Register");
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
//                        String response= EntityUtils.toString(entity1, "utf-8");//以UTF-8格式解析
//                        Message message=new Message();
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

    public void RegisterByOKHttp(final String id, final String pw){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String TAG = "RESPONSE";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("ID", id).add("PW", pw).build();
                    //Log.d("connect","123");
                    Request request = new Request.Builder().url("http://10.0.2.2:8080/HttpClient/RegisterServlet").post(formBody).build();
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
                                Message message=new Message();
                                //message.what=USER_LOGIN;
                                message.what=0;
                                message.obj=responsestr;
                                handler.sendMessage(message);//使用Message传递消息给线程
                                Log.i(TAG,"SYNCPOST:"+responsestr);
                                SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                                sp.edit()
                                        .putBoolean("state",false)
                                        .putBoolean("remember_password",false)
                                        .apply();
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
                    {Toast.makeText(RegisterActivity.this, "注册成功,请返回登录", Toast.LENGTH_SHORT).show();
                    JumpBacktoMain();}
                    else
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    public void JumpBacktoMain()
    {
        Intent  intent = new Intent();
        intent.setClass(this,MainActivity.class);
        this.startActivity(intent);
    }
}
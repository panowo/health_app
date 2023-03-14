package com.example.sportapp.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sportapp.recordtool.HealthRecord;
import com.example.sportapp.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowHealthActivity extends AppCompatActivity {

    private ArrayList<HealthRecord> HealthRecordslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_health);
        ShowHealthByOKHttp();
    }

    //okhttp方法
    public void ShowHealthByOKHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sp = getSharedPreferences("login", Context.MODE_PRIVATE);
                    String userid=sp.getString("username", null);
                    String TAG = "RESPONSE";
                    OkHttpClient okHttpClient = new OkHttpClient();
                    FormBody formBody = new FormBody.Builder().add("ID", userid).build();
                    //Log.d("connect","123");
                    Request request = new Request.Builder().url("http://10.0.2.2:8080/HttpClient/ShowHealth").post(formBody).build();
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
    public static final int SHOW_RESPONSE=1;
    public Handler handler=new Handler() {
        public void handleMessage(Message msg)
        {
            switch (msg.what){
                case 0:
                    String response=(String)msg.obj;
                    //Log.i("SHOW", responsestr);
                    //接收数据
                    HealthRecordslist = new ArrayList<>();
                    String[] split=response.split("\n");
                    for (String str_record: split)
                    {
                        //Log.i("SHOW1", split[0]);
                        String[] split_str=str_record.split("\t");
                        List<String> label = new ArrayList<>();
                        for(int i=0;i<split_str.length;i++)
                        {
                            label.add(split_str[i]);
//                                        System.out.println(i+" "+split_str[i]);//输出结果 1 2 3
                        }
//                                    int len=split.length;
//                                    System.out.println(len);
//                                    for(String str: split_str)
//                                    {
//                                        Log.i("SHOW1", str);
                        if(label!=null&&label.size()>1)
                        {
                            String id = split_str[0];
                            String date = split_str[1];
                            String time = split_str[2];
                            String place=split_str[3];
                            String tem=split_str[4];
                            String state=split_str[5];
                            HealthRecord st = new HealthRecord(id,date,time,place,state,tem);
                            HealthRecordslist.add(st);
                        }
                    }

                    ListView lv = (ListView)findViewById(R.id.list_health);
                    lv.setAdapter(new BaseAdapter() {
                        /*
                         * 为ListView设置一个适配器
                         * getCount()返回数据个数
                         * getView()为每一行设置一个条目
                         * */
                        @Override
                        public int getCount() {
                            return HealthRecordslist.size();
                        }

                        @Override
                        public Object getItem(int position) {
                            // return studentlist.get(position);
                            return null;
                        }

                        @Override
                        public long getItemId(int position) {

                            // return position;
                            return 0;
                        }

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {


                            View view ;
                            /**对ListView的优化，convertView为空时，创建一个新视图；
                             * convertView不为空时，代表它是滚出,
                             * 放入Recycler中的视图,若需要用到其他layout，
                             * 则用inflate(),同一视图，用fiindViewBy()
                             * **/
                            if(convertView == null )
                            {
                                LayoutInflater inflater = ShowHealthActivity.this.getLayoutInflater();
                                view = inflater.inflate(R.layout.healthitem,null);
                                //view = View.inflate(getBaseContext(),R.layout.item,null);
                            }
                            else
                            {
                                view = convertView;
                            }

                            //从studentlist中取出一行数据，position相当于数组下标,可以实现逐行取数据
                            HealthRecord st = HealthRecordslist.get(position);
                            TextView date = (TextView)view.findViewById(R.id.health_date);
                            TextView time  = (TextView)view.findViewById(R.id.health_time);
                            TextView place = (TextView)view.findViewById(R.id.health_place);
                            TextView state = (TextView)view.findViewById(R.id.health_state);
                            TextView tem = (TextView)view.findViewById(R.id.health_tem);

                            date.setText(st.getDate());
                            time.setText(st.getTime());
                            place.setText(st.getPlace());
                            System.out.println(st.getPlace());
                            if (st.getState().equals("0"))
                                state.setText("正常");
                            else
                                state.setText("异常");

                            if (st.getTem().equals("0"))
                                tem.setText("正常");
                            else
                                tem.setText("异常");

                            return view;
                        }
                    });
//                    if (response.equals("true"))
//                        Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
//                    else
//                        Toast.makeText(getActivity(), "用户名或者密码错误", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };
}
package com.example.sportapp.ui.Sport;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.sportapp.placetool.City;
import com.example.sportapp.placetool.DiQu;
import com.example.sportapp.placetool.ParserTool;
import com.example.sportapp.placetool.Province;
import com.example.sportapp.R;
import com.example.sportapp.pages.SportActivity;
import com.example.sportapp.service.UpdateService;
import com.example.sportapp.databinding.FragmentSportBinding;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SportFragment extends Fragment {

    private SportModel sportModel;
    private FragmentSportBinding binding;

    private Spinner sp_prov,sp_city,sp_diQu;
    private ArrayAdapter<Province> prov_adapter;
    private ArrayAdapter<City> city_adapter;
    private ArrayAdapter<DiQu> diQu_adapter;

    private List<Province> provs;
    private int prov_position;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sport, container, false);
        sp_prov = view.findViewById(R.id.sheng1);
        sp_city = view.findViewById(R.id.shi1);
        sp_diQu = view.findViewById(R.id.xian1);
        //第二步，得到解析后的数据
        InputStream in = getResources().openRawResource(R.raw.citys);
        try
        {
            provs = ParserTool.parserProvince(in);
        } catch (XmlPullParserException | IOException e)
        {
            e.printStackTrace();
        }
        //第三步，设置省的适配器
        prov_adapter = new ArrayAdapter<Province>(getContext(), android.R.layout.simple_spinner_item, provs);
        sp_prov.setAdapter(prov_adapter);

        sp_prov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                prov_position = position;
                //设置市的适配器
                city_adapter = new ArrayAdapter<City>(getContext(), android.R.layout.simple_spinner_item, provs.get(position).getCitys());
                sp_city.setAdapter(city_adapter);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                //设置地区的适配器
                diQu_adapter = new ArrayAdapter<DiQu>(getContext(), android.R.layout.simple_spinner_item, provs.get(prov_position).getCitys().get(position).getDiQus());
                sp_diQu.setAdapter(diQu_adapter);


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });
        sp_diQu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id)
            {
                String pro= sp_prov.getSelectedItem().toString();
                String ccity= sp_city.getSelectedItem().toString();
                String diqu= sp_diQu.getSelectedItem().toString();
                TextView TV=getActivity().findViewById(R.id.t_v1);
                TV.setText(pro+"-"+ccity+"-"+diqu);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        return view;
    }
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //获取时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss

        Date date = new Date(System.currentTimeMillis());
        //显示时间
        TextView tv_time = (TextView) getActivity().findViewById(R.id.textView_time1);
        tv_time.setText(simpleDateFormat.format(date));

        //健康打卡按钮
        Button button1 = (Button) getActivity().findViewById(R.id.btn_da1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "进入运动打卡", Toast.LENGTH_SHORT).show();
                JumpSportActivity();
            }
        });

        Button button = (Button) getActivity().findViewById(R.id.btn_update1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                JumpClockActivity();
                CreateNewSportRecord();
            }
        });


    }
    //okhttp方法
    public void CreateNewSportRecord(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sp = getActivity().getSharedPreferences("sport_record", Context.MODE_PRIVATE);

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
                                Message message=new Message();
                                //message.what=USER_LOGIN;
                                message.what=0;
                                message.obj=responsestr;
                                handler.sendMessage(message);//使用Message传递消息给线程
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

    public static final int SHOW_RESPONSE=1;
    public Handler handler=new Handler() {
        public void handleMessage(Message msg)
        {
            switch (msg.what){
                case 0:
                    String response=(String)msg.obj;
                    if (response.equals("true"))
                        Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "上传失败", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    public void JumpSportActivity()
    {
        Intent intent = new Intent();
        intent.setClass(getActivity(), SportActivity.class);

        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss

        TextView TV=getActivity().findViewById(R.id.t_v1);
        //分别获取时间日期
        String datestring=dateFormat.format(date);
        String timestring=timeFormat.format(date);


        String place=TV.getText().toString();

        SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String username=sp.getString("username", null);

        Log.i("RECORD", username+"-"+datestring+"-"+timestring+"-"+place);

        intent.putExtra("userid",username)
                .putExtra("date",datestring)
                .putExtra("time",timestring)
                .putExtra("place",place);
        //传递信息


        this.startActivity(intent);
    }

    public void JumpClockActivity()
    {
        Intent intent = new Intent(getActivity(), UpdateService.class);
        getActivity().startService(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
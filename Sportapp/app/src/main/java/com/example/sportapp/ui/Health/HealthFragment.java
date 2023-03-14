package com.example.sportapp.ui.Health;

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

import com.example.sportapp.pages.MainActivity;
import com.example.sportapp.R;
import com.example.sportapp.databinding.FragmentHealthBinding;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.example.sportapp.placetool.City;
import com.example.sportapp.placetool.DiQu;
import com.example.sportapp.placetool.Province;
import com.example.sportapp.placetool.ParserTool;

import org.jetbrains.annotations.NotNull;
import org.xmlpull.v1.XmlPullParserException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HealthFragment extends Fragment {

    private HealthViewModel healthViewModel;
    private FragmentHealthBinding binding;

    private Spinner sp_prov,sp_city,sp_diQu;
    private ArrayAdapter<Province> prov_adapter;
    private ArrayAdapter<City> city_adapter;
    private ArrayAdapter<DiQu> diQu_adapter;

    private List<Province> provs;
    private int prov_position;

//    Spinner s_sheng;
//    ArrayAdapter<String> adapter_sheng;
//    ArrayAdapter<String> adapter_shi;
//    ArrayAdapter<String> adapter_xian;
//    Spinner s_shi;
//    Spinner s_xian;
//    int sheng_postion;
//    int shi_postion;
//    String seletced_sheng;
//    String seletced_shi;
//    String seletced_xian;
//    TextView t_v;
//    String[] arr_sheng={"北京市","天津市","上海市","广东省","河南省","重庆市","河北省","山西省","辽宁省","吉林省","黑龙江省","江苏省","浙江省", "安徽省","福建省",
//            "江西省","山东省","湖北省","湖南省","海南省","四川省","贵州省","云南省","陕西省", "甘肃省","青海省","台湾省"};
//    String [][]arr_shi=new String[][] {
//            {"北京市"},// 北京市
//            {"天津市"},//天津市
//            {"上海市"},//上海市
//            {"广州", "深圳", "韶关", "珠海", "汕头", "佛山", "湛江", "肇庆", "江门", "茂名", "惠州", "梅州",
//                    "汕尾", "河源", "阳江", "清远", "东莞", "中山", "潮州", "揭阳", "云浮"},//广州市
//            {"郑州市","开封市","洛阳市","平顶山市","许昌市"
//            },//河南省
//
//    };
//    String[][][]arr_xian= new String [][][]{
//            {//北京市
//                    {"东城区", "西城区", "崇文区", "宣武区", "朝阳区", "海淀区", "丰台区", "石景山区", "门头沟区",
//                            "房山区", "通州区", "顺义区", "大兴区", "昌平区", "平谷区", "怀柔区", "密云县", "延庆县"}
//            },
//            {//深圳
//                    {"和平区", "河东区", "河西区", "南开区", "河北区", "红桥区", "塘沽区", "汉沽区", "大港区", "东丽区"}
//            },
//            {//上海
//                    {"长宁区", "静安区", "普陀区", "闸北区", "虹口区"}
//            },
//            {//广东
//                    {"海珠区", "荔湾区", "越秀区", "白云区", "萝岗区", "天河区", "黄埔区", "花都区", "从化市", "增城市", "番禺区", "南沙区"}, //广州
//                    {"宝安区", "福田区", "龙岗区", "罗湖区", "南山区", "盐田区"}, //深圳
//                    {"武江区", "浈江区", "曲江区", "乐昌市", "南雄市", "始兴县", "仁化县", "翁源县", "新丰县", "乳源县"},//韶关
//                    {"无"}
//            },
//            {//河南
//                    {"中原区","二七区","管城区","金水区","上街区","惠济区","巩义市","荥阳市","新密市","新郑市" ,"登封市","中牟县" },
//                    {"鼓楼区","龙亭区","顺河区","禹王台","金明区", "杞县","通许县", "尉氏县", "开封县" ,"兰考县"},
//                    {"西工区","老城区","瀍河区","涧西区","吉利区","洛龙区","偃师市","孟津县","新安县","栾川县","嵩县","汝阳县", "宜阳县","洛宁县","伊川县"},
//                    {"新华区","卫东区","湛河区","石龙区","舞钢市","汝州市","宝丰县","叶 县","鲁山县","郏县"},
//                    {"魏都区","禹州市","长葛市","许昌县","鄢陵县", "襄城县"}
//            },
//
//
//    };
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_health, container, false);
        sp_prov = view.findViewById(R.id.sheng);
        sp_city = view.findViewById(R.id.shi);
        sp_diQu = view.findViewById(R.id.xian);
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
                TextView TV=getActivity().findViewById(R.id.t_v);
                TV.setText(pro+"-"+ccity+"-"+diqu);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });


//        TV.setText(pro+"-"+ccity+"-"+diqu);
//        s_sheng = view.findViewById(R.id.sheng);
//        s_shi = view.findViewById(R.id.shi);
//        s_xian =view.findViewById(R.id.xian);
//        adapter_sheng = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arr_sheng);
//        adapter_shi = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arr_shi[0]);
//        adapter_xian = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arr_xian[0][0]);
//        s_sheng.setAdapter(adapter_sheng);
//        s_sheng.setSelection(0);
//        s_shi.setAdapter(adapter_shi);
//        s_shi.setSelection(0);
//        s_xian.setAdapter(adapter_xian);
//        s_xian.setSelection(0);
//        s_sheng.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                sheng_postion=i;
//                adapter_shi=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arr_shi[i]);
//                s_shi.setAdapter(adapter_shi);
//                seletced_sheng=arr_sheng[i];
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        s_shi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                shi_postion=i;
//                adapter_xian=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, arr_xian[sheng_postion][shi_postion]);
//                s_xian.setAdapter(adapter_xian);
//                seletced_shi=arr_shi[sheng_postion][i];
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        s_xian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                seletced_xian=arr_xian[sheng_postion][shi_postion][i];
//                t_v.setText(seletced_sheng+"-"+seletced_shi+"-"+seletced_xian);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        final TextView textView = binding.textDashboard;
//        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        s_shi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                shi_postion=i;
//                adapter_xian=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, arr_xian[sheng_postion][shi_postion]);
//                s_xian.setAdapter(adapter_xian);
//                seletced_shi=arr_shi[sheng_postion][i];
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        s_xian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                seletced_xian=arr_xian[sheng_postion][shi_postion][i];
//                t_v.setText(seletced_sheng+"-"+seletced_shi+"-"+seletced_xian);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        //获取时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss

        Date date = new Date(System.currentTimeMillis());
        //显示时间
        TextView tv_time = (TextView) getActivity().findViewById(R.id.textView_time);
        tv_time.setText(simpleDateFormat.format(date));

        TextView TV=getActivity().findViewById(R.id.t_v);

        //健康打卡按钮
        Button button1 = (Button) getActivity().findViewById(R.id.btn_da);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //分别获取时间日期
                String datestring=dateFormat.format(date);
                String timestring=timeFormat.format(date);


                String place=TV.getText().toString();

                //获取两个状态
                Spinner state=getActivity().findViewById(R.id.spinner_state);
                Spinner tem=getActivity().findViewById(R.id.spinner_gender);

                String restate= state.getSelectedItem().toString();
                String retem= tem.getSelectedItem().toString();

                Boolean sta=false;
                Boolean te=false;
                if (state.equals("37.3℃以上"))
                    sta=true;
                if(te.equals("是"))
                    te=true;


                SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                String username=sp.getString("username", null);

                Log.i("RECORD", username+"-"+datestring+"-"+timestring+"-"+place+"-"+sta.toString()+"-"+te.toString());

                SharedPreferences sp1 = getActivity().getSharedPreferences("record", Context.MODE_PRIVATE);
                sp1.edit()
                        .putString("userid",username)
                        .putString("date",datestring)
                        .putString("time",timestring)
                        .putString("place",place)
                        .putString("state",sta.toString())
                        .putString("tem",te.toString())
                        .apply();
                Toast.makeText(getActivity(), "打卡成功", Toast.LENGTH_SHORT).show();
            }
        });

        Button button = (Button) getActivity().findViewById(R.id.btn_update);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateNewHealthRecord();
            }
        });


//        Button button = (Button) getActivity().findViewById(R.id.logout);
//
//        SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
//
//
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
//                sp.edit()
//                        .putBoolean("state",false)
//                        .apply();
////                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
//            }
//        });
    }
    //okhttp方法
    public void CreateNewHealthRecord(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SharedPreferences sp = getActivity().getSharedPreferences("record", Context.MODE_PRIVATE);

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

    public void JumpMainActivity()
    {
        Intent  intent = new Intent();
        intent.setClass(getActivity(), MainActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
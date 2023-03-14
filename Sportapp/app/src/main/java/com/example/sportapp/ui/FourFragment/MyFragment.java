package com.example.sportapp.ui.FourFragment;

import static android.content.Context.NOTIFICATION_SERVICE;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportapp.pages.EditActivity;
import com.example.sportapp.pages.MainActivity;
import com.example.sportapp.R;
import com.example.sportapp.service.UpdateService;
import com.example.sportapp.databinding.MyFragmentBinding;

public class MyFragment extends Fragment {

    private MyViewModel mViewModel;
    private MyFragmentBinding binding;
//    private NotificationManager mManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(MyViewModel.class);

        binding = MyFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String channelId = "channelId";
//            String channelName = "这是用来测试的notification";
//            createNotificationChannel(channelId, channelName, NotificationManagerCompat.IMPORTANCE_HIGH);
//        }
//        mManager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);

//        final TextView textView = binding.textMy;
//        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager manager = (NotificationManager) getActivity().getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences sp1 = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);

        TextView textView_id = (TextView) getActivity().findViewById(R.id.textView_id);
        String s1="账号:";
        String s2=sp1.getString("username", null);
        String s3=s1+s2;
        textView_id.setText(s3);

        SharedPreferences sp = getActivity().getSharedPreferences(s2, Context.MODE_PRIVATE);

        TextView textView_gender = (TextView) getActivity().findViewById(R.id.textView_gender);
        String s11="性别:";
        String s12=sp.getString("gender", null);
        String s13=s11+s12;
        textView_gender.setText(s13);

        TextView textView_age = (TextView) getActivity().findViewById(R.id.textView_age);
        String s21="年龄:";
        String s22=sp.getString("age", null);
        String s23=s21+s22;
        textView_age.setText(s23);

        TextView textView_name = (TextView) getActivity().findViewById(R.id.textView_name);
        String s31="昵称:";
        String s32=sp.getString("name", null);
        String s33=s31+s32;
        textView_name.setText(s33);

        TextView textView_height = (TextView) getActivity().findViewById(R.id.textView_height);
        String s41="身高:";
        String s42=sp.getString("height", null);
        String s43=s41+s42;
        textView_height.setText(s43);

        TextView textView_weight = (TextView) getActivity().findViewById(R.id.textView_weight);
        String s51="体重:";
        String s52=sp.getString("weight", null);
        String s53=s51+s52;
        textView_weight.setText(s53);

        Button button = (Button) getActivity().findViewById(R.id.logout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                sp.edit()
                        .putBoolean("state",false)
                        .apply();
                JumpMainActivity();
//                Toast.makeText(getActivity(), "Clicked", Toast.LENGTH_LONG).show();
            }
        });

        Button button1 = (Button) getActivity().findViewById(R.id.btn_timeupdate);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),UpdateService.class);
                getActivity().startService(intent);
               Toast.makeText(getActivity(), "开启定时上传", Toast.LENGTH_LONG).show();
//                sendNotification();

            }
        });

        Button button2 = (Button) getActivity().findViewById(R.id.btn_edit);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpEditActivity(s2);
                //Toast.makeText(getActivity(), "开启定时上传", Toast.LENGTH_LONG).show();

            }
        });

    }

//    private void sendNotification() {
//        String channelId = "channelId";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            String channelName = "这是用来测试的notification";
//            createNotificationChannel(channelId, channelName, NotificationManagerCompat.IMPORTANCE_HIGH);
//        }
//
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), channelId);
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        //builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo));
//        builder.setContentTitle("运动健康打卡");
//        builder.setContentText("运动健康打卡小助手提醒您打卡！")
//                //指定通知被创建的时间
//                .setWhen(System.currentTimeMillis());
//
//        Notification notification = builder.build();
//        mManager.notify(1, notification);
//    }

    public void JumpMainActivity()
    {
        Intent  intent = new Intent();
        intent.setClass(getActivity(), MainActivity.class);
        this.startActivity(intent);
    }

    public void JumpEditActivity(String s2)
    {
        Intent  intent = new Intent();
        intent.setClass(getActivity(), EditActivity.class);
        intent.putExtra("id",s2);
        this.startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
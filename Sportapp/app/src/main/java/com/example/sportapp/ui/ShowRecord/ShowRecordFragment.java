package com.example.sportapp.ui.ShowRecord;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sportapp.R;
import com.example.sportapp.pages.ShowHealthActivity;
import com.example.sportapp.pages.ShowSportActivity;
import com.example.sportapp.databinding.FragmentShowrecordBinding;

public class ShowRecordFragment extends Fragment {

    private ShowRecordViewModel showRecordViewModel;
    private FragmentShowrecordBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        showRecordViewModel =
                new ViewModelProvider(this).get(ShowRecordViewModel.class);

        binding = FragmentShowrecordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //运动打卡按钮
        Button button1 = (Button) getActivity().findViewById(R.id.btn_sport);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "运动打卡记录", Toast.LENGTH_SHORT).show();
                JumpSportActivity();
//                ShowSportByOKHttp();
            }
        });
        //运动打卡按钮
        Button button2 = (Button) getActivity().findViewById(R.id.btn_health);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "健康打卡记录", Toast.LENGTH_SHORT).show();
                JumpHealthActivity();
//                ShowSportByOKHttp();
            }
        });


    }

    public void JumpSportActivity()
    {
        Intent intent = new Intent(getActivity(), ShowSportActivity.class);
        this.startActivity(intent);
    }

    public void JumpHealthActivity()
    {
        Intent intent = new Intent(getActivity(), ShowHealthActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
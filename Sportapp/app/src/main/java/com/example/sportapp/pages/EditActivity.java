package com.example.sportapp.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportapp.R;
import com.example.sportapp.pages.MainActivity;
import com.example.sportapp.ui.FourFragment.MyFragment;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent intent = getIntent();
        String id =intent.getStringExtra("id");

        TextView mEtname = this.findViewById(R.id.et_name);
        TextView mEtage = this.findViewById(R.id.et_age);
        TextView mEtweight= this.findViewById(R.id.et_weight);

        Spinner Sgender=this.findViewById(R.id.spinner_gender);

        TextView mEtheight= this.findViewById(R.id.et_height);

        SharedPreferences sp = this.getSharedPreferences(id, Context.MODE_PRIVATE);

        mEtname.setText(sp.getString("name", null));
        mEtage.setText(sp.getString("age", null));
        mEtweight.setText(sp.getString("weight", null));
        mEtheight.setText(sp.getString("height", null));
        String s=sp.getString("gender", null);
//        if (s.equals("女"))
//        {
//            Sgender.setit(2,true);
//        }



        Button button = (Button) this.findViewById(R.id.btn_updateper);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit(id);
                JumpMyActivity();
            }
        });


    }

    private void edit(String id)
    {
        TextView mEtname = this.findViewById(R.id.et_name);
        TextView mEtage = this.findViewById(R.id.et_age);
        TextView mEtweight= this.findViewById(R.id.et_weight);

        Spinner Sgender=this.findViewById(R.id.spinner_gender);

        TextView mEtheight= this.findViewById(R.id.et_height);


        String name =mEtname.getText().toString().trim();
        String age =mEtage.getText().toString().trim();
        String gender= Sgender.getSelectedItem().toString();
        String height =mEtheight.getText().toString().trim();
        String weight =mEtweight.getText().toString().trim();


        SharedPreferences sp = getSharedPreferences(id, Context.MODE_PRIVATE);
        sp.edit()
                .putString("name", name)
                .putString("age", age)
                .putString("gender",gender)
                .putString("height",height)
                .putString("weight",weight)
                .apply();
        //Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show();

    }

    public void JumpMyActivity()
    {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.putExtra("id",1);
//        startActivity(intent);
        Intent  intent = new Intent();
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);

    }
}
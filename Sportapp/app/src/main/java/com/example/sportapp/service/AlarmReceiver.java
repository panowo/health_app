package com.example.sportapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.sportapp.service.UpdateService;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, UpdateService.class);
        context.startService(i);
    }
}
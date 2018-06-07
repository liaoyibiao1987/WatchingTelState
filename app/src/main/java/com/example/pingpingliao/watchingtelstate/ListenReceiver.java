package com.example.pingpingliao.watchingtelstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ListenReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context,BackGroupService.class));//收到广播时开启监听电话的服务
    }
}

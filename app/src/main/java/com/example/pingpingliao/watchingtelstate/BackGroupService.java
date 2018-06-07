package com.example.pingpingliao.watchingtelstate;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class BackGroupService extends Service {
    NotificationManager manager; //通知管理器，用于发送通知Notification对象
    @Override
    public void onCreate() {
        super.onCreate();
        manager =(NotificationManager)getSystemService(Service.NOTIFICATION_SERVICE);
        btn_no_common();
    }

    private void btn_no_common(){
        //1、创建通知对象

        NotificationCompat.Builder nb = new NotificationCompat.Builder(this);

        //2、设置通知对象的各种信息

        //注意：以下6点信息的设置，必须要写小图标，其余的可选择性省略

        nb.setContentTitle("设置标题"+System.currentTimeMillis());

        nb.setContentText("内容文本部分");

        //设置info信息，即设置显示在时间右下角的文字

        nb.setContentInfo("info信息");

        //设置通知时间

        nb.setWhen(System.currentTimeMillis());
        nb.setTicker("设置滚动提示的文字");

        //不能手动移除，模态，需要代码控制

        //nb.setOngoing(true);
        int num = 2323;
        num++;

        manager.notify(num,nb.build());   //发送通知

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}

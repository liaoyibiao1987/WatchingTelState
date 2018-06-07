package com.example.pingpingliao.watchingtelstate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class BackGroupService extends Service {
    NotificationManager manager; //通知管理器，用于发送通知Notification对象
    @Override
    public void onCreate() {
        super.onCreate();
        //1、创建通知对象
        manager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        btn_no_common();
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        //为管理器设置监听器,监听电话的呼叫状态
        telephonyManager.listen(new DYPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    private void btn_no_common(){
        //NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
        //创建NotificationCompat.Builder
        Notification notification =new NotificationCompat.Builder(this,"default")
                .setContentTitle("电话监听服务")
                .setContentText("服务正式开始")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .build();
        manager.notify(1,notification);

        //2、设置通知对象的各种信息

        //注意：以下6点信息的设置，必须要写小图标，其余的可选择性省略

        //nb.setContentTitle("设置标题"+System.currentTimeMillis());

        //nb.setContentText("内容文本部分");

        //设置info信息，即设置显示在时间右下角的文字

        //nb.setContentInfo("info信息");

        //设置通知时间

        //nb.setWhen(System.currentTimeMillis());
        //nb.setTicker("设置滚动提示的文字");

        //不能手动移除，模态，需要代码控制

        //nb.setOngoing(true);
        int num = 2323;
        num++;

        //manager.notify(num,nb.build());   //发送通知

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


    private class DYPhoneListener extends PhoneStateListener {
        private String num;//记录来电号码
        private MediaRecorder mRecorder;
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING://来电振动
                    Notification notification =new NotificationCompat.Builder(BackGroupService.this,"default")
                            .setContentTitle("来电话了"+incomingNumber)
                            .setContentText("服务正式开始")
                            .setWhen(System.currentTimeMillis())
                            .setSmallIcon(R.mipmap.ic_launcher_round)
                            .build();
                    manager.notify(2,notification);
                    num = incomingNumber;
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:// 当接通电话开始通话时  可以进行录音
                    mRecorder = new MediaRecorder();
                    mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC); //此处只实现了录本地MIC输入的声音,若想录入对立通话者的声音
                    mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mRecorder.setOutputFile("/mnt/sdcard/"+num+"_"+System.currentTimeMillis()+".3gp");
                    mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                    try {
                        mRecorder.prepare();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mRecorder.start();

                    break;
                case TelephonyManager.CALL_STATE_IDLE://挂断电话时停止录音
                    if(mRecorder!=null){
                        mRecorder.stop(); //停止
                        mRecorder.release();//释放
                        mRecorder=null;//垃圾回收
                    }
                    break;
            }
        }
    }
}

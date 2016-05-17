package com.phaseone.shuvankar.automatingphonephase1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;

/**
 * Created by shuvankar on 5/15/2016.
 */
public class AlarmReciver extends BroadcastReceiver{
    private static int NOTIFICATION_ID = 1;
    Bundle bundle;
    int notificationId = 0;
    AudioManager audioManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        try{


           /* audioManager = (AudioManager) context.getSystemService (Context.AUDIO_SERVICE);
            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
//			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), 0);
            notificationId = intent.getExtras().getInt("notificationId");
            NotificationManager manger = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification(R.drawable.ic_launcher, "YourBestSelfApp", System.currentTimeMillis());
            notification.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.defaults =  Notification.DEFAULT_VIBRATE|Notification.DEFAULT_LIGHTS;

//			notification.sound = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.iphone_5_original);
            Intent intent1 = new Intent(context, MainActivity.class);
            intent1.putExtra("activityFrom", "notificationAlarm");
            PendingIntent activity = PendingIntent.getActivity(context,NOTIFICATION_ID + 1, intent1,  PendingIntent.FLAG_UPDATE_CURRENT);
            notification.setLatestEventInfo(context, "hello","", activity);
            notification.number = 1;
            manger.notify(notificationId, notification);*/
        }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }
}

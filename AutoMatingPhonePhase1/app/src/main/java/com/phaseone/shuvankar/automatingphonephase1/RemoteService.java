package com.phaseone.shuvankar.automatingphonephase1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by shuvankar on 5/15/2016.
 */
public class RemoteService extends Service {
    private Handler serviceHandler;
    private int counter;
    int notificationId = 10;
    private Task myTask = new Task();


    @Override
    public IBinder onBind(Intent arg0) {
        Log.d(getClass().getSimpleName(), "onBind()");

        return myRemoteServiceStub;
    }

    private IMyRemoteService.Stub myRemoteServiceStub = new IMyRemoteService.Stub() {
        public int getCounter() throws RemoteException {
            //			startAlarmNotification();
            IntentFilter filter = new IntentFilter();
            filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            filter.addAction(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);
            filter.addAction("your_action_strings"); //further more
            filter.addAction("your_action_strings"); //further more
            registerReceiver(receiver, filter);
            return counter;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(getClass().getSimpleName(),"onCreate()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serviceHandler.removeCallbacks(myTask);
        serviceHandler = null;
        Log.d(getClass().getSimpleName(),"onDestroy()");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        try {
            myRemoteServiceStub.getCounter() ;
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        serviceHandler = new Handler();
        serviceHandler.postDelayed(myTask, 1000L);

        Log.d(getClass().getSimpleName(), "onStart()");

    }

    class Task implements Runnable {
        public void run() {
            ++counter;
            serviceHandler.postDelayed(this,1000L);
            Log.i(getClass().getSimpleName(), "Incrementing counter in the run method");
        }
    }
    public void  startAlarmNotification(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 2);
        Intent alarmintent = new Intent(RemoteService.this, AlarmReciver.class);
        PendingIntent sender = PendingIntent.getBroadcast(this, notificationId, alarmintent,PendingIntent.FLAG_UPDATE_CURRENT | Intent.FILL_IN_DATA);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
//					   	  am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  10, sender);

        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
    }


    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("android.provider.Telephony.SMS_RECEIVED")){
                //action for sms received
//					      	    	        startAlarmNotification();
//					      	    	        unregisterReceiver(receiver);
                Intent i = new Intent();
                i.setClassName("com.collabera.labs.sai", "com.collabera.labs.sai.WelcomeActivity");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);

            }
            else if(action.equals(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED)){
                //action for phone state changed
            }
        }
    };
}


package com.phaseone.shuvankar.automatingphonephase1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by shuvankar on 5/15/2016.
 */
public class StartService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Intent startServiceIntent = new Intent(context, RemoteService.class);
        context.startService(startServiceIntent);
    }
}
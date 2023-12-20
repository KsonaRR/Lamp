package com.groovylamp.models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Будильник сработал!", Toast.LENGTH_SHORT).show();
//        Intent broadcastIntent = new Intent("ALARM_TRIGGERED");
//        context.sendBroadcast(broadcastIntent);
        Intent broadcastIntent = new Intent("MY_ACTION");
        context.sendBroadcast(broadcastIntent);
    }
}

package com.riton.interval_timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ChangeCirNameReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
//        System.out.println("ChangeCirName!");
        Toast.makeText(context, "ChangeCirNameReceiver 收到广播", Toast.LENGTH_SHORT).show();
    }
}

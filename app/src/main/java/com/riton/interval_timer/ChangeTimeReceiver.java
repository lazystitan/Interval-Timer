package com.riton.interval_timer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ChangeTimeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        System.out.println("ChangeTime!");
    }
}

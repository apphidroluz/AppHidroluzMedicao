package com.example.comercial_medicao.androidnovo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Comercial_Medição on 28/03/2018.
 */

public class BootClass extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context, NotificationService.class));
    }
}

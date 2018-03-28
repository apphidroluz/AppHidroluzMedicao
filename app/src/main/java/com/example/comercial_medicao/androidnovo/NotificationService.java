package com.example.comercial_medicao.androidnovo;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mTimer = new Timer();
        mTimer.schedule(timerTask,600000,2 * 600000);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private Timer mTimer;
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            notifiy();
        }
    };

    @Override
    public void onDestroy() {
        try {
            mTimer.cancel();
            timerTask.cancel();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void notifiy() {

        Notification.Builder builder;
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(""));
        @SuppressLint("WrongConstant") PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,mIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
        Context context = getApplicationContext();
        builder= new Notification.Builder(context)
                .setContentTitle("AVISO DE NÍVEL")
                .setContentText("Seu reservaório está ficando vazio")
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.ic_launcher_background);

        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification);
    }

    // Criar notifições para os 3 níveis de aviso:
    // 0% - Vazio
    // 25% - Baixo
    // 100% - Cheio / Risco de transbordo
    // Aguardando DadosArduino para adaptar a leitura do valor

}

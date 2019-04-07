package com.riton.interval_timer;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.VibrationEffect;
import android.os.Vibrator;

import java.util.ArrayList;
import java.util.List;

public class CountDownTimerService extends Service {

    private final String Tag = "CountDownTimerService";

    List<String> names;
    List<String> allnames;
    List<Integer> times;
    List<String> displayCirculaition;
    List<CountDownTimer> countDownTimerList;
    boolean isFinish = true;

    @Override
    public void onCreate() {
        super.onCreate();
        names = new ArrayList<>();
        allnames = new ArrayList<>();
        times = new ArrayList<>();
        displayCirculaition = new ArrayList<>();
        countDownTimerList = new ArrayList<>();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int id = intent.getIntExtra("id",-1);
        Task task = new Task(getApplicationContext());
        task.setId(id);
        task.getTask();

        names.addAll(task.getActivitiesNames());
        for (int j = 0; j < names.size(); j++) {
            times.add(task.getAcitvityTimeByName(names.get(j)));
        }
        //   private long startTime;
        for (int j = 0; j < task.getCirculationNumber(); j++) {
            for (int i = 0; i < names.size(); i++) {
                long activityTime = times.get(i);
                activityTime = activityTime * 1000;
                allnames.add(names.get(i));
                displayCirculaition.add(String.valueOf(j+1)+"/"+String.valueOf(task.getCirculationNumber()));

                countDownTimerList.add(new CountDownTimer(activityTime, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long value = millisUntilFinished / 1000;
                        String mTv = String.valueOf(value / 60) + ":" + String.valueOf(value % 60);
//                        System.out.println(mTv);
                        Intent intent = new Intent();
                        intent.setAction("com.riton.CHANGE_TIME");
                        intent.putExtra("time",mTv);
                        sendBroadcast(intent);
                    }
                    @Override
                    public void onFinish() {
                        isFinish = true;
//                        mTvValue.setText("00:00");

//                 getFinish();
                    }
                });
            }

//            getFinish();
        }

        System.out.println("allnames.size() is :"+allnames.size());
        for (int i = 0; i < allnames.size(); i++) {
            System.out.println("-----------------");
            System.out.print(allnames.get(i));
            System.out.print("：");
            System.out.println(displayCirculaition.get(i));
            System.out.println("-----------------");
        }

        Runnable r = ()->{
            Vibrator vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
//            boolean isFinish = true;
            int i = 0;
            while(isFinish && i < countDownTimerList.size())
            {
                Intent intentChangeText = new Intent();
                intentChangeText.putExtra("circulation",displayCirculaition.get(i));
                intentChangeText.putExtra("activityName",allnames.get(i));
                intentChangeText.setAction("com.riton.CHANGE_CIR_NAME");
                sendBroadcast(intentChangeText);
//                circulation.setText(displayCirculaition.get(i));
//                activityName.setText(allnames.get(i));
                isFinish = false;
                System.out.println("isFinish is "+isFinish);
                countDownTimerList.get(i).start();
                while(!isFinish)
                    continue;
                System.out.println("结束");
                vibrator.vibrate(VibrationEffect.createOneShot(500,128));

                i++;
            }
            Intent finishCountIntent = new Intent();
            finishCountIntent.setAction("com.riton.FINISH_COUNT");
            sendBroadcast(finishCountIntent);
        };

        Thread t = new Thread(r);
        t.start();

        return super.onStartCommand(intent, flags, startId);
    }

    //    @androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (CountDownTimer countDownTimer:countDownTimerList)
            if (countDownTimer != null)
                countDownTimer.cancel();
    }
}

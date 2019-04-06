package com.riton.interval_timer;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CountDownActivity extends AppCompatActivity {
   private TextView mTvValue;
   private TextView circulation;
   private TextView activityName;
   private Task task;
//   private CountDownTimer countDownTimer;
   private List<CountDownTimer> countDownTimerList = new ArrayList<>();
    //   private DBHelper mDataBase;
   private Boolean isFinish=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down);
        mTvValue=(TextView)findViewById(R.id.countDown);
        activityName = (TextView) findViewById(R.id.count_down_activity_name);
        circulation = (TextView) findViewById(R.id.count_down_circulation_number);
//        mDataBase=new DBHelper(CountDownActivity.this);
        Intent intent = getIntent();
        int id=intent.getIntExtra("id",0);
//        TOTAL_TIME=(long)mDataBase.getTask(id).getLength();
        task = new Task(getApplicationContext());
        task.setId(id);
        task.getTask();

        List<String> names = new ArrayList<>();
        List<String> allnames = new ArrayList<>();
        List<Integer> times = new ArrayList<>();
        List<String> displayCirculaition = new ArrayList<>();


        names.addAll(task.getActivitiesNames());
        for (int j = 0; j < names.size(); j++) {
            times.add(task.getAcitvityTimeByName(names.get(j)));
        }




        //   private long startTime;
        for (int j = 0; j < task.getCirculationNumber(); j++) {
//            String circulationString = "循环"+String.valueOf(j+1)+"/"+String.valueOf(task.getCirculationNumber());
//            circulation.setText(circulationString);
            for (int i = 0; i < names.size(); i++) {
                long activityTime = times.get(i);
                activityTime = activityTime * 1000;
                allnames.add(names.get(i));
                displayCirculaition.add(String.valueOf(j+1)+"/"+String.valueOf(task.getCirculationNumber()));
//                activityName.setText(names.get(i));

                countDownTimerList.add(new CountDownTimer(activityTime, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        long value = millisUntilFinished / 1000;
                        String mTv = String.valueOf(value / 60) + ":" + String.valueOf(value % 60);
                        System.out.println(mTv);
                        mTvValue.setText(mTv);
                    }
                    @Override
                    public void onFinish() {
                        isFinish = true;
                        mTvValue.setText("00:00");

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
                circulation.setText(displayCirculaition.get(i));
                activityName.setText(allnames.get(i));
                isFinish = false;
                System.out.println("isFinish is "+isFinish);
                countDownTimerList.get(i).start();
                while(!isFinish)
                    continue;
                System.out.println("结束");
                vibrator.vibrate(VibrationEffect.createOneShot(500,128));

                i++;
            }
        };

        Thread t = new Thread(r);
        t.start();

//        int i = 0;
//        while(isFinish|| (i >= countDownTimerList.size() -1))
//        {
//            circulation.setText(displayCirculaition.get(i));
//            activityName.setText(allnames.get(i));
//            isFinish = false;
//            System.out.println("isFinish is "+isFinish);
//            countDownTimerList.get(i).start();
//            while(!isFinish)
//                continue;
//            System.out.println("结束");
//            i++;
//        }

    }
//    public void getFinish(){
//        isFinish=true;
//        ConstraintLayout recording= (ConstraintLayout) findViewById(R.id.recording);
//        recording.setVisibility(View.GONE);
//        ConstraintLayout complete= (ConstraintLayout) findViewById(R.id.complete);
//        complete.setVisibility(View.VISIBLE);
////        mDataBase.addPerform(id,startTime,TOTAL_TIME,isFinish);
//    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK)
        {
            if(!isFinish)
            {
                AlertDialog.Builder confirmDelete = new AlertDialog.Builder(CountDownActivity.this);
                confirmDelete.setTitle("取消任务？");
                confirmDelete.setCancelable(true);
                confirmDelete.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
//                                countDownTimer.cancel();
                                for (CountDownTimer countDownTimer: countDownTimerList)
                                {
                                    if (countDownTimer != null)
                                        countDownTimer.cancel();
                                }
                                finish();
                                dialog.cancel();
                            }
                        });
                confirmDelete.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = confirmDelete.create();
                alert.show();
            }

        }
        return super.onKeyDown(keyCode, event);
    }




}

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

        Intent startServiceIntent = new Intent(this,CountDownTimerService.class);
        startServiceIntent.putExtra("id",id);
        startService(startServiceIntent);

//        ChangeCirNameReceiver changeCirNameReceiver = new ChangeCirNameReceiver();
//        ChangeTimeReceiver changeTimeReceiver = new ChangeTimeReceiver();
//        FinishCountReceiver finishCountReceiver = new FinishCountReceiver();
//        changeCirNameReceiver.onReceive(getApplicationContext(),null);
//

//        TOTAL_TIME=(long)mDataBase.getTask(id).getLength();
//        task = new Task(getApplicationContext());
//        task.setId(id);
//        task.getTask();




    }

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
//                                for (CountDownTimer countDownTimer: countDownTimerList)
//                                {
//                                    if (countDownTimer != null)
//                                        countDownTimer.cancel();
//                                }
                                Intent stopService = new Intent(getApplicationContext(),CountDownTimerService.class);
                                stopService(stopService);
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

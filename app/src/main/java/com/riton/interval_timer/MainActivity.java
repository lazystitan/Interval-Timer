package com.riton.interval_timer;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton addTask;
    private RecyclerView recyclerView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addTask = (FloatingActionButton) findViewById(R.id.add_task);
        recyclerView = (RecyclerView) findViewById(R.id.task_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(new MainActivityAdapter(this));

        addTask = (FloatingActionButton) findViewById(R.id.add_task);
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                LayoutInflater layoutInflater = LayoutInflater.from(context);

                View view = layoutInflater.inflate(R.layout.add_task_layout,null);

                final EditText editTaskNameAlterDialog = (EditText) view.findViewById(R.id.edit_task_name_alterdialog);
                builder.setView(view);

                builder.setTitle("新建任务");
                builder.setCancelable(true);
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                System.out.println("alright");
                                String taskName = editTaskNameAlterDialog.getText().toString();
                                Intent intent =new Intent(context, EditTaskActivity.class);
                                intent.putExtra("name",taskName);
                                startActivityForResult(intent,100);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        TaskSavingApplication taskSavingApplication = (TaskSavingApplication) this.getApplication();
        taskSavingApplication.setTestFlag(100);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 100:
                if (resultCode == RESULT_OK) {
                    recyclerView.setAdapter(new MainActivityAdapter(this));
//                    recyclerView.notifyAll();
                    System.out.println("success!!");
                }
        }
    }
}

package com.riton.interval_timer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
//                builder.setMessage("这是AlertDialog中的样式");
//                builder.setNegativeButton("取消", null);
//                builder.setPositiveButton("确定", null);
                builder.setCancelable(true);
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                System.out.println("alright");
                                String taskName = editTaskNameAlterDialog.getText().toString();
                                Intent intent =new Intent(context, EditTaskActivity.class);
                                intent.putExtra("name",taskName);
                                context.startActivity(intent);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

    }
}

//编辑任务的界面
//同时也是创建任务后进入的界面
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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class EditTaskActivity extends AppCompatActivity {

    private int id;

    private RecyclerView recyclerView;
    private FloatingActionButton addActivity;

    private TextView taskName;
    private TextView totalTime;
    private TextView complexActivityTime;
    private EditText circulationNumber;

    private Task task;

    private boolean isNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        final TaskSavingApplication taskSavingApplication = (TaskSavingApplication) this.getApplication();
        //创建一个新的空的task对象
        task = new Task(getApplicationContext());
        //将它的引用保存在全局变量中
        taskSavingApplication.setTask(task);

        taskName = (TextView) findViewById(R.id.task_name_edit);
        totalTime = (TextView) findViewById(R.id.total_time_edit);
        complexActivityTime = (TextView) findViewById(R.id.complex_activity_time_edit);
        circulationNumber = (EditText) findViewById(R.id.get_circulation_number_edit);
        //获取新建活动的按钮
        addActivity = (FloatingActionButton) findViewById(R.id.add_activity_fabtn);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);

        recyclerView = findViewById(R.id.edit_task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //id=1代表编辑任务，id=-1代表新建任务
        if (id != -1) {
            isNew = false;
            //设置task的id并从数据库中获取信息补全对象
            task.setId(id);
            task.getTask();
            if (task == null)
                System.out.println("task获取失败！");
            recyclerView.setAdapter(new EditTaskRecyclerViewAdapter(this, task,taskSavingApplication,
                    totalTime, complexActivityTime, circulationNumber));
            //设置展示的信息
            taskName.setText(task.getName());
            totalTime.setText(new StringBuilder("总时长：").append(String.valueOf(task.getTotalTime()).toString()));
            complexActivityTime.setText(new StringBuilder("复合活动时长：").append(String.valueOf(task.getComplexActivityTime())));
            circulationNumber.setText(String.valueOf(task.getCirculationNumber()));

        }
        else
        {
            isNew = true;
            String name = intent.getStringExtra("name");
            //设置顶部的task名
            taskName.setText(name);
            //为空的task添加名字
            task.setName(name);
            recyclerView.setAdapter(new EditTaskRecyclerViewAdapter(this, task, taskSavingApplication,
                    totalTime, complexActivityTime, circulationNumber));
        }

        //为添加任务的按钮绑定响应方法
        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                //弹出一个dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("新的活动");
                LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());

                View view = layoutInflater.inflate(R.layout.add_activity_layout,null);

                final EditText getName = (EditText) view.findViewById(R.id.get_activity_name_adlayout);
                final EditText getTime = (EditText) view.findViewById(R.id.get_activity_time_adlayout);

                builder.setView(view);
                //设置dialog的确定按钮并绑定响应
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //将这个新的activity加入到task的任务中
                                task.addActivity(getName.getText().toString(),Integer.valueOf(getTime.getText().toString()));
                                //重新设置recycler的adapter以刷新页面
                                recyclerView.setAdapter(new EditTaskRecyclerViewAdapter(context,task, taskSavingApplication,
                                        totalTime, complexActivityTime, circulationNumber));

                                //更新其他要显示的信息
                                totalTime.setText(new StringBuilder("总时长：")
                                        .append(String.valueOf(task.getTotalTime()).toString()));
                                complexActivityTime.setText(new StringBuilder("复合活动时长：").
                                        append(String.valueOf(task.getComplexActivityTime())));
                                circulationNumber.setText(String.valueOf(task.getCirculationNumber()));
                            }
                });
                builder.setCancelable(true);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        //EditText的监听器
        circulationNumber.addTextChangedListener(new TextWatcher() {
            CharSequence temp;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                temp = s;
            }

            @Override
            public void afterTextChanged(Editable s) {
                boolean isEmpty = false;
                if  (temp.length() == 0) {
                    isEmpty = true;
                }
                int cu;
                if (isEmpty)
                    cu = 0;
                else
                    cu = Integer.valueOf(String.valueOf(temp));
                task.setCirculationNumber(cu);
                totalTime.setText(new StringBuilder("总时长：")
                        .append(String.valueOf(task.getTotalTime()).toString()));
                complexActivityTime.setText(new StringBuilder("复合活动时长：").
                        append(String.valueOf(task.getComplexActivityTime())));
            }
        });

        ((Button) findViewById(R.id.finish_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNew) {
                    task.saveTask();
                    System.out.println("save completed!");
                }
                else {
                    task.updateTask();
                    System.out.println("update completed!");
                }
//                setResult(0);
                setResult(RESULT_OK);
                finish();
            }
        });

        ((Button) findViewById(R.id.cancel_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        recyclerView.set
    }
}

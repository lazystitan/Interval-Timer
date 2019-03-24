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
import android.widget.TextView;

import org.w3c.dom.Text;

public class EditTaskActivity extends AppCompatActivity {

    private int id;

    private RecyclerView recyclerView;
    private FloatingActionButton addActivity;

    private TextView taskName;
    private TextView totalTime;
    private TextView complexActivityTime;
    private EditText circulationNumber;
//    private TextView  circulationNumber;
//    private TextView containedActivities;
//    private TextView totalTime;

    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        taskName = (TextView) findViewById(R.id.task_name_edit);
        totalTime = (TextView) findViewById(R.id.total_time_edit);
        complexActivityTime = (TextView) findViewById(R.id.complex_activity_time_edit);
        circulationNumber = (EditText) findViewById(R.id.get_circulation_number_edit);
        addActivity = (FloatingActionButton) findViewById(R.id.add_activity_fabtn);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",-1);
        System.out.println("EditTaskActivity is "+id);
        task = new Task(this);

        if (id != -1) {

            task.setId(id);
            task.getTask();

            taskName.setText(task.getName());
            totalTime.setText(new StringBuilder("总时长：").append(String.valueOf(task.getTotalTime()).toString()));
            complexActivityTime.setText(new StringBuilder("复合活动时长：").append(String.valueOf(task.getComplexActivityTime())));
            circulationNumber.setText(String.valueOf(task.getCirculationNumber()));

            recyclerView = findViewById(R.id.edit_task_recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(new EditTaskRecyclerViewAdapter(this, task));
        }
        else
        {
            String name = intent.getStringExtra("name");
            taskName.setText(name);

            task.setName(name);
        }

        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("新的活动");
                LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());

                View view = layoutInflater.inflate(R.layout.add_activity_layout,null);

                final EditText getName = (EditText) view.findViewById(R.id.get_activity_name_adlayout);
                final EditText getTime = (EditText) view.findViewById(R.id.get_activity_time_adlayout);

                builder.setView(view);
                builder.setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MyActivity activity = new MyActivity(context,getName.getText().toString(),Integer.valueOf(getTime.getText().toString()));
                                task.addActivity(activity);
                                recyclerView.setAdapter(new EditTaskRecyclerViewAdapter(context,task));
                            }
                });
                builder.setCancelable(true);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
//        taskName.setText(intent.getStringExtra("taskName"));

//        task = (Task) getIntent().getSerializableExtra("task");
//        Bundle bundle = getIntent().getExtras();
//        task = (Task) bundle.getSerializable("task");
//        StringBuilder taskNameStr = new StringBuilder("任务名:");


//        taskNameStr.append(task.getName());

//        taskName.setText(taskName.toString());



    }
}

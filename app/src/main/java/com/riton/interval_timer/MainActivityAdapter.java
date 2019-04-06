package com.riton.interval_timer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;

    public MainActivityAdapter(Context _context) {
        super();
        context = _context;
        taskList = new ArrayList<Task>();
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id from task",null);
        System.out.println("数据库中有"+cursor.getCount()+"条记录");
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            Task task = new Task(context);
            task.setId(id);
            task.getTask();
            taskList.add(task);
        }
        cursor.close();
        db.close();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        return null;
        View view = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.fragment_task_cardview, viewGroup, false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {

        final Task thisTask = taskList.get(i);
        final int position = i;
        StringBuilder taskName = new StringBuilder("任务名:");
        StringBuilder circulationNumber = new StringBuilder("循环次数:");
        StringBuilder containedActivities = new StringBuilder("包含活动:");
        StringBuilder totalTime = new StringBuilder("总时长:");

        taskName.append(thisTask.getName());
        circulationNumber.append(String.valueOf(thisTask.getCirculationNumber()));
//        containedActivities.append(thisTask.getActivitiesNames());
        Set<String> activityNames = thisTask.getActivitiesNames();
        for (String name:activityNames) {
            containedActivities.append(name);
            containedActivities.append(',');
        }
        containedActivities.deleteCharAt(containedActivities.length()-1);
        totalTime.append(String.valueOf(thisTask.getTotalTime()));

        taskViewHolder.taskName.setText(taskName.toString());
        taskViewHolder.circulationNumber.setText(circulationNumber.toString());
        taskViewHolder.containedActivities.setText(containedActivities.toString());
        taskViewHolder.totalTime.setText(totalTime.toString());

        taskViewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditTaskActivity.class);
                intent.putExtra("id",thisTask.getId());
//                intent.putExtra("taskName",thisTask.getName());
                ((Activity)context).startActivityForResult(intent,100);
//                context.startActivityForResult(intent, 1);

            }
        });

        taskViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("删除任务？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        thisTask.deleteTask();
                        taskList.remove(position);
                        notifyItemRemoved(position);
                    }
                });
//                    builder.setCancelable(true);
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
//        return 0;
        return  taskList.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder{
        protected  TextView taskName;
        protected  TextView  circulationNumber;
        protected  TextView containedActivities;
        protected  TextView totalTime;

        protected CardView cardView;

        protected  Button edit;
        protected  Button start;

        TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.task_name_edit);
            circulationNumber = (TextView) itemView.findViewById(R.id.circulation_number);
            containedActivities = (TextView) itemView.findViewById(R.id.contained_activities);
            totalTime = (TextView) itemView.findViewById(R.id.total_time);

            cardView = (CardView) itemView.findViewById(R.id.fragment_task_cardview_cardview);

            edit = (Button) itemView.findViewById(R.id.edit_task_btn);
            start = (Button) itemView.findViewById(R.id.start_task_btn);
        }
    }
}

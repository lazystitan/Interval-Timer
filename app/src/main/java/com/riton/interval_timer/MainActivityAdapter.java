package com.riton.interval_timer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.TaskViewHolder> {

    List<Task> taskList;
    Context context;

    public MainActivityAdapter(Context _context) {
        super();
//        taskList = _taskList;
        context = _context;
        taskList = new ArrayList<Task>();
        DbHelper dbHelper = new DbHelper(context);
//        int taskCount = dbHelper.getTaskCount();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select id from task",null);
        while(cursor.moveToNext())
        {
            int id = cursor.getInt(0);
            Task task = new Task(context);
            task.setId(id);
            task.getTask();
            taskList.add(task);
        }
        cursor.close();
//        db.close();

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

        Task thisTask = taskList.get(i);
        StringBuilder taskName = new StringBuilder("任务名:");
        StringBuilder circulationNumber = new StringBuilder("循环次数:");
        StringBuilder containedActivities = new StringBuilder("包含活动:");
        StringBuilder totalTime = new StringBuilder("总时长:");

        taskName.append(thisTask.getName());
        circulationNumber.append(String.valueOf(thisTask.getCirculationNumber()));
        containedActivities.append(thisTask.getActivitiesNames());
        totalTime.append(String.valueOf(thisTask.getTotalTime()));

        taskViewHolder.taskName.setText(taskName.toString());
        taskViewHolder.circulationNumber.setText(circulationNumber.toString());
        taskViewHolder.containedActivities.setText(containedActivities.toString());
        taskViewHolder.totalTime.setText(totalTime.toString());

    }

    @Override
    public int getItemCount() {
//        return 0;
        return  taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{
        protected TextView taskName;
        protected TextView  circulationNumber;
        protected TextView containedActivities;
        protected TextView totalTime;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = (TextView) itemView.findViewById(R.id.task_name_edit);
            circulationNumber = (TextView) itemView.findViewById(R.id.circulation_number);
            containedActivities = (TextView) itemView.findViewById(R.id.contained_activities);
            totalTime = (TextView) itemView.findViewById(R.id.total_time);
        }
    }
}
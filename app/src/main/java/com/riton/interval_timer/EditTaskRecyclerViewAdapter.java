package com.riton.interval_timer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class EditTaskRecyclerViewAdapter extends RecyclerView.Adapter<EditTaskRecyclerViewAdapter.ActivityViewHolder> {

//    private Task task;
    private Context context;
    private List<MyActivity> activities;

    public EditTaskRecyclerViewAdapter(Context _context,Task _task)
    {
        context = _context;
        activities = _task.getActivities();
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(R.layout.fragment_activity_cardview, viewGroup,false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder activityViewHolder, int i) {
            MyActivity myActivity = activities.get(i);
            activityViewHolder.activityName.setText(myActivity.getName());
            activityViewHolder.activityTime.setText(new StringBuilder("活动时长：").append(myActivity.getActivityTime()).toString());
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public static  class ActivityViewHolder extends RecyclerView.ViewHolder{
        protected TextView activityName;
        protected TextView activityTime;
        protected Button activityEditBtn;
        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            activityName = (TextView) itemView.findViewById(R.id.activity_name);
            activityTime = (TextView) itemView.findViewById(R.id.activity_time);
            activityEditBtn = (Button) itemView.findViewById(R.id.activity_edit_btn);
        }
    }
}

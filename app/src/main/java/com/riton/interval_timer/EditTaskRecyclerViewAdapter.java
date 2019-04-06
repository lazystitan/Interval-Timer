package com.riton.interval_timer;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EditTaskRecyclerViewAdapter extends RecyclerView.Adapter<EditTaskRecyclerViewAdapter.ActivityViewHolder> {

//    private Task task;
    private Context context;
    private List<String> names;
    private List<Integer> times;
    private TaskSavingApplication taskSavingApplication;

//    private View view;
    private TextView totalTime;
    private TextView complexActivityTime;
    private EditText circulationNumber;

    private void updateInfo(Task _task)
    {
        names = new ArrayList<>();
        times = new ArrayList<>();

        names.addAll(_task.getActivitiesNames());
        for (int i = 0; i < names.size(); i++) {
            times.add(_task.getAcitvityTimeByName(names.get(i)));
        }
    }

    public EditTaskRecyclerViewAdapter(Context _context, Task _task, TaskSavingApplication _taskSavingApplication,
                                       TextView _totalTime, TextView _complexActivityTime, EditText _circulationNumber)
    {
        totalTime = _totalTime;
        complexActivityTime = _complexActivityTime;
        circulationNumber = _circulationNumber;

        context = _context;
        names = new ArrayList<>();
        times = new ArrayList<>();
//        view = _view;

        names.addAll(_task.getActivitiesNames());
        for (int i = 0; i < names.size(); i++) {
            times.add(_task.getAcitvityTimeByName(names.get(i)));
        }
        taskSavingApplication = _taskSavingApplication;
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
            final int position = i;
//            MyActivity myActivity = activities.get(i);
            activityViewHolder.activityName.setText(names.get(i));
            final String name = names.get(i);
//            final View view = activityViewHolder.cardView;
            activityViewHolder.activityTime.setText(new StringBuilder("活动时长：").append(times.get(i)).toString());
            activityViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    // delete confirm
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("删除活动？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Task task = taskSavingApplication.getTask();
//                            Toast.makeText(context, task.getName(),Toast.LENGTH_LONG).show();
                            task.deleteActivityByName(name);
//                            Toast.makeText(context,"成功",Toast.LENGTH_SHORT).show();
                            Toast.makeText(context,String.valueOf(task.getActivityNumber()),Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            updateInfo(task);
                            totalTime.setText(new StringBuilder("总时长：")
                                    .append(String.valueOf(task.getTotalTime()).toString()));
                            complexActivityTime.setText(new StringBuilder("复合活动时长：").
                                    append(String.valueOf(task.getComplexActivityTime())));
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
        return names.size();
    }

    public static  class ActivityViewHolder extends RecyclerView.ViewHolder{
        protected TextView activityName;
        protected TextView activityTime;
        protected CardView cardView;
        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            activityName = (TextView) itemView.findViewById(R.id.activity_name);
            activityTime = (TextView) itemView.findViewById(R.id.activity_time);
            cardView = (CardView) itemView.findViewById(R.id.fragment_activity_cardview_cardview);
        }
    }


}

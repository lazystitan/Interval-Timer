package com.riton.interval_timer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

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
            activityViewHolder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // delete confirm
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("删除活动？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

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


//            activityViewHolder.activityEditBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//                    LayoutInflater layoutInflater = LayoutInflater.from(context);
//                    View view = layoutInflater.inflate(R.layout.edit_activity_layout,null);
//                    builder.setView(view);
//                    builder.setTitle("编辑活动");
//                    final EditText activityName = (EditText) view.findViewById(R.id.get_activityname_edit_activity_dialog);
//                    final EditText activityTime = (EditText) view.findViewById(R.id.get_activitytime_edit_activity_dialog);
//
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            activityViewHolder.activityName.setText(activityName.getText().toString());
//                            activityViewHolder.activityTime.setText(Integer.valueOf(activityTime.getText().toString()));
//                        }
//                    });
//
//                    builder.setCancelable(true);
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//
//                }
//            });
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public static  class ActivityViewHolder extends RecyclerView.ViewHolder{
        protected TextView activityName;
        protected TextView activityTime;
        protected CardView cardView;
//        protected Button activityEditBtn;
        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            activityName = (TextView) itemView.findViewById(R.id.activity_name);
            activityTime = (TextView) itemView.findViewById(R.id.activity_time);
            cardView = (CardView) itemView.findViewById(R.id.fragment_activity_cardview_cardview);
//            activityEditBtn = (Button) itemView.findViewById(R.id.activity_edit_btn);
        }
    }


}

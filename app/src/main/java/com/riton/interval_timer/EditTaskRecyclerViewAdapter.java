package com.riton.interval_timer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class EditTaskRecyclerViewAdapter extends RecyclerView.Adapter<EditTaskRecyclerViewAdapter.ActivityViewHolder> {

    public EditTaskRecyclerViewAdapter(Context context)
    {

    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder activityViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static  class ActivityViewHolder extends RecyclerView.ViewHolder{
        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

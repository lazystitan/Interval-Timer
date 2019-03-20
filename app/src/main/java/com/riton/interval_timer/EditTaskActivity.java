package com.riton.interval_timer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class EditTaskActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        //recyclerView = (RecyclerView) findViewById(R.id.edit_task_recycler_view);

        //recyclerView.setAdapter(new EditTaskRecyclerViewAdapter(this));

    }
}

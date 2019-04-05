package com.riton.interval_timer;

import android.app.Application;

public class TaskSavingApplication extends Application {

    private Task task;
    private int testFlag;

    public void setTask(Task task) {
        this.task = task;
    }

    public Task getTask()
    {
        return task;
    }

    public void setTestFlag(int testFlag) {
        this.testFlag = testFlag;
    }

    public int getTestFlag() {
        return testFlag;
    }
}

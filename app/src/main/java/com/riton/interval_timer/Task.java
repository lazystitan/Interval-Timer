package com.riton.interval_timer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;
import java.util.List;

public class Task {
    private Context context;

    private int id;
    private String name;
    private int circulationNumber;
    private int activityNumber;
    private int [] activityIds;
    private List<MyActivity> activities;

    public Task(Context _context)
    {
        context = _context;
        id = 0;
    }

    //构造方法
    public Task(Context _context,int _id, String _name, int _circulationNumber, int _activityNumber, int [] _activityIds)
    {
        context = _context;

        id = _id;
        name = _name;
        circulationNumber = _circulationNumber;
        activityNumber = _activityNumber;
        activityIds = _activityIds;
    }

    //获得属性
    public int getId() {
        return id;
    }

    public void setId(int _id)
    {
        id = _id;
    }

    public String getName()
    {
        return name;
    }

    public int getCirculationNumber() {
        return circulationNumber;
    }

    public int getActivityNumber()  {
        return activityNumber;
    }

    public int [] getActivityIds()
    {
        return activityIds;
    }

    //保存到数据库
    public void saveTask()
    {
        StringBuilder activityIdsString = new StringBuilder();
        for (int id:activityIds) {
            activityIdsString.append(String.valueOf(id));
            activityIdsString.append(",");
        }
        activityIdsString.deleteCharAt(activityIdsString.length()-1);

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("circulationNumber",circulationNumber);
        contentValues.put("activityNumber",activityNumber);
        contentValues.put("activityIds", activityIdsString.toString());
        db.insertOrThrow("task",null,contentValues);

        db.setTransactionSuccessful();
        db.close();
    }

    //从数据库读取
    public void getTask()
    {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from task where id = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.getCount() == 1)
        {
            cursor.moveToFirst();
            id = cursor.getInt(0);
            name = cursor.getString(1);
            circulationNumber = cursor.getInt(2);
            activityNumber = cursor.getInt(3);
            String [] temp = cursor.getString(4).split(",");
            activityIds = new int[temp.length];
            for (int i=0; i<temp.length;i++)
                activityIds[i] = Integer.valueOf(temp[i]);

            for (int id:activityIds)
            {
                MyActivity myActivity = new MyActivity(context);
                myActivity.setId(id);
                myActivity.getActivity();
                activities.add(myActivity);
            }

            getActivitiesById();
        }
        else
        {
            //没有此任务
            id = -1;
        }
        cursor.close();
        db.close();
    }

    private void getActivitiesById()
    {
        for (MyActivity myActivity:activities)
            myActivity.getActivity();
    }

    public int getComplexActivityTime()
    {
        int complexActivityTime = 0;

        for (MyActivity myActivity: activities)
            complexActivityTime += myActivity.getActivityTime();

        return complexActivityTime;
    }

    public int getTotalTime()
    {
        int complexActivityTime = getComplexActivityTime();
        return circulationNumber*complexActivityTime;
    }

    public String getActivitiesNames()
    {
        StringBuilder names = new StringBuilder();

        for(MyActivity myActivity:activities)
        {
            names.append(myActivity.getName());
            names.append(",");
        }
        names.deleteCharAt(names.length()-1);
        names.append(".");

        return names.toString();
    }
}

package com.riton.interval_timer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static android.content.ContentValues.TAG;

public class Task implements Serializable
{
    private Context context;

    private int id;
    private String name;
    private int circulationNumber;
    private int activityNumber;
    private JSONObject complexActivity;


//    private int [] activityIds;
//    private List<MyActivity> activities;

//    private static int tasksNumber;
//
//    public static int getTasksNumber(Context _context)
//    {
//        DbHelper dbHelper = new DbHelper(_context);
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        db.get
//        return
//    }

    private void updateActivityNumber()
    {
        activityNumber = complexActivity.size();
    }

    public Task(Context _context)
    {
        context = _context;
        id = 0;
        complexActivity = new JSONObject();
    }

    //构造方法
    public Task(Context _context,int _id, String _name, int _circulationNumber, String _complexActivity)
    {
        context = _context;

        id = _id;
        name = _name;
        circulationNumber = _circulationNumber;
        complexActivity = JSONObject.parseObject(_complexActivity);
        activityNumber = this.complexActivity.size();
//        activityIds = _activityIds;
//        activities = new ArrayList<MyActivity>();
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

    public void setName(String _name)
    {
        name = _name;
    }

    public int getCirculationNumber() {
        return circulationNumber;
    }

    public void setCirculationNumber(int circulationNumber) {
        this.circulationNumber = circulationNumber;
    }

    public int getActivityNumber()  {
        return activityNumber;
    }

//    public int [] getActivityIds()
//    {
//        return activityIds;
//    }

    //保存到数据库
    public void saveTask()
    {
//        StringBuilder activityIdsString = new StringBuilder();
//        for (int id:activityIds) {
//            activityIdsString.append(String.valueOf(id));
//            activityIdsString.append(",");
//        }
//        activityIdsString.deleteCharAt(activityIdsString.length()-1);

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("circulationNumber",circulationNumber);
//        contentValues.put("activityNumber",activityNumber);
        contentValues.put("complexActivityString", complexActivity.toString());
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
            complexActivity = JSONObject.parseObject(cursor.getString(3));
            System.out.println("complexActivity的大小是："+complexActivity.size());
//            Log.d(TAG, "getTask: "+complexActivity.size());
            updateActivityNumber();

//            activityNumber = cursor.getInt(3);
//            String [] temp = cursor.getString(4).split(",");
//            activityIds = new int[temp.length];
//            for (int i=0; i<temp.length;i++)
//                activityIds[i] = Integer.valueOf(temp[i]);

//            System.out.println("******This task info******");
//            System.out.println("id:"+id);
//            System.out.println("name:"+name);
//            System.out.println("circulationNumber:"+ circulationNumber);
//            System.out.println("activityNumber:"+activityNumber);
//            System.out.println(activityIds);
//            for (int id:activityIds)
//                System.out.println(id);
//            System.out.println("**************************");

//            for (int id:activityIds)
//            {
//                MyActivity myActivity = new MyActivity(context);
//                myActivity.setId(id);
//                myActivity.getActivity();
//                System.out.println(myActivity.getName());
//                System.out.println(myActivity.getActivityTime());
//                activities.add(myActivity);
//            }

//            getActivitiesById();
        }
        else
        {
            //没有此任务
            id = -1;
        }
        cursor.close();
        db.close();
    }

//    private void getActivitiesById()
//    {
//        for (MyActivity myActivity:activities)
//            myActivity.getActivity();
//    }

    public int getComplexActivityTime()
    {
        int complexActivityTime = 0;

        for (Object activityTime: complexActivity.values())
            complexActivityTime += (int) activityTime;

        return complexActivityTime;
    }

    public int getTotalTime()
    {
        int complexActivityTime = getComplexActivityTime();
        return circulationNumber*complexActivityTime;
    }

//    public String getActivitiesNames()
//    {
//        complexActivity
//
//        return names.toString();
//    }
    public Set<String> getActivitiesNames()
    {
        return complexActivity.keySet();
    }

//    public List<MyActivity> getActivities()
//    {
//        return new ArrayList<MyActivity>(activities);
//    }

    public int getAcitvityTimeByName(String activityName)
    {
        return complexActivity.getInteger(activityName);
    }

    public void addActivity(String key, int value)
    {
        complexActivity.put(key,value);
//        activities.add(_activity);
//        activityNumber +=1;
    }

//    public MyActivity deleteActivityByName(String activityName)
//    {
//        for (MyActivity activity:activities)
//        {
//            if (activity.getName() == activityName)
//            {
//                activities.remove(activity);
//                return activity;
//            }
//        }
//        return null;
//    }
}

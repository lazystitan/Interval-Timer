package com.riton.interval_timer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;
import java.util.Set;


public class Task implements Serializable
{
    private Context context;

    private int id;
    private String name;
    private int circulationNumber;
    private int activityNumber;
    private JSONObject complexActivity;

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


    //保存到数据库
    public void saveTask()
    {

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("circulationNumber",circulationNumber);
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
            updateActivityNumber();
        }
        else
        {
            //没有此任务
            id = -1;
        }
        cursor.close();
        db.close();
    }

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

    public Set<String> getActivitiesNames()
    {
        return complexActivity.keySet();
    }


    public int getAcitvityTimeByName(String activityName)
    {
        return complexActivity.getInteger(activityName);
    }

    public void addActivity(String key, int value)
    {
        complexActivity.put(key,value);
    }
}

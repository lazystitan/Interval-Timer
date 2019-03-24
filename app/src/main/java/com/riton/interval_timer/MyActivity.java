package com.riton.interval_timer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.riton.interval_timer.DbHelper;

import java.io.Serializable;

public class MyActivity implements Serializable {
    private Context context;

    private int id;
    private String name;
    private int activityTime;

    public MyActivity(Context _context)
    {
        context = _context;
        id = 0;
    }

    public MyActivity(Context context, int _id, String _name, int _activityTime)
    {
        id = _id;
        name = _name;
        activityTime = _activityTime;
    }

    public MyActivity(Context context, String _name, int _activityTime)
    {
        id = 0;
        name = _name;
        activityTime = _activityTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int _id)
    {
        id = _id;
    }

    public String getName() {
        return name;
    }

    public int getActivityTime() {
        return activityTime;
    }

    public void saveActivity()
    {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.beginTransaction();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("activity_time", activityTime);
        db.insertOrThrow("activity", null, contentValues);

        db.setTransactionSuccessful();
        db.close();
    }

    public void getActivity()
    {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from activity where id = "+id,null);
//        Cursor cursor = sqLiteDatabase.rawQuery("select * from activity",null);
        System.out.println("select * from activity where id = "+id);

//        System.out.println(cursor.getCount());
        if (cursor.getCount() == 0)
            System.out.println("Wrong");
        cursor.moveToFirst();
        id = cursor.getInt(0);
        name = cursor.getString(1);
        activityTime = cursor.getInt(2);
        cursor.close();
        sqLiteDatabase.close();
    }


}

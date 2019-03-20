package com.riton.interval_timer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";

    //创建任务表
    private static final String CREATE_TASK =
            "create table if not exists task("+
                "id integer primary key autoincrement,"+
                "name text not null,"+
                "circulation_number integer not null,"+
                "activity_number integer not null,"+
                "activity_id_string text not null)";

    //创建活动表
    private static final String CREATE_ACTIVITY =
            "create table if not exists activity("+
                "id integer primary key autoincrement,"+
                "name text,"+
                "activity_time integer not null)";

    public DbHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    //创建表
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK);
        db.execSQL(CREATE_ACTIVITY);

        db.execSQL("insert into activity (name,activity_time) values ('跑步',300)");
        db.execSQL("insert into activity (name,activity_time) values ('休息',180)");
        db.execSQL("insert into activity (name,activity_time) values ('瑜伽',600)");
        db.execSQL("insert into activity (name,activity_time) values ('平静',1200)");
        db.execSQL("insert into task (name,circulation_number,activity_number,activity_id_string) values ('跑步',3,2,'1,2')");
        db.execSQL("insert into task (name,circulation_number,activity_number,activity_id_string) values ('瑜伽',5,2,'3,4')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //暂时不写
    }

    public int getTaskCount()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from task",null);
        int taskCount = cursor.getCount();
        cursor.close();
        return  taskCount;
    }


}

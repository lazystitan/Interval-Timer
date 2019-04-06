package com.riton.interval_timer;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONException;
//import org.json.JSONObject;
import com.alibaba.fastjson.JSONObject;
public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "database.db";

    //创建任务表
    private static final String CREATE_TASK =
            "create table if not exists task("+
                "id integer primary key autoincrement,"+
                "name text not null,"+
                "circulation_number integer not null,"+
                "complex_activity_string text not null)";

    //创建活动表
//    private static final String CREATE_ACTIVITY =
//            "create table if not exists activity("+
//                "id integer primary key autoincrement,"+
//                "name text,"+
//                "activity_time integer not null)";

    public DbHelper(Context context)
    {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    //创建表
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK);
//        db.execSQL(CREATE_ACTIVITY);


        JSONObject running, yoga;
        running = new JSONObject();
        yoga = new JSONObject();

        running.put("休息",180);
        running.put("跑步",300);
        yoga.put("瑜伽",600);
        yoga.put("平静",1200);


//        db.execSQL("insert into activity (name,activity_time) values ('跑步',300)");
//        db.execSQL("insert into activity (name,activity_time) values ('休息',180)");
//        db.execSQL("insert into activity (name,activity_time) values ('瑜伽',600)");
//        db.execSQL("insert into activity (name,activity_time) values ('平静',1200)");
        db.execSQL("insert into task (name,circulation_number,complex_activity_string) values ('跑步',3,'"+running.toString()+"')");
        db.execSQL("insert into task (name,circulation_number,complex_activity_string) values ('瑜伽',5,'"+yoga.toString()+"')");
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

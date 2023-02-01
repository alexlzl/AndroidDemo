package com.example.demo5;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {
    //定义创建计步表的SQL语句，每列内容分别为序号，日期，步数
    final String CREATE_STEPS = "create table tb_step (_id integer primary key autoincrement," +
            "day, steps)";

    //重写构造方法，并设置工厂为null
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }

    //创建计步表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STEPS);
    }

    //重写基类的onUpgrade（）方法，以便数据库版本更新
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("计步表","版本更新"+oldVersion+"-->"+newVersion);
    }
}

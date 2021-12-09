package com.example.cp470groupproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Date;

public class HabitDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Habits.db";
    private static final int VERSION_NUM = 1;

    public static final String TABLE_NAME = "habits";
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "habit";
    //public static final String COLLUMN_NAME_TITLE = "title";
    //public static final String COLLUMN_NAME_DESC = "desc";
    //public static final String COLUMN_NAME_REMINDER = "reminder";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "(" + KEY_ID
            + " integer primary key autoincrement, " + KEY_MESSAGE
            + " text not null);";

    public HabitDatabase(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("HabitDatabase", "Calling onCreate");
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVer, int newVer) {
        Log.i("HabitDatabase", "Calling onUpgrade, oldVersion=" + oldVer + " newVersion=" + newVer);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
package com.example.mowebs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "tbl_chat";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_FROM = "_from";
    public static final String COLUMN_DATE = "date";

    private static final String db_name = "chat.db";
    private static final int db_version = 1;

    //string untuk kueri bikin tabel
    private static final String db_create = "CREATE TABLE " +
            TABLE_NAME+" ( "+
            COLUMN_ID   + " integer primary key autoincrement, "+
            COLUMN_VALUE + " varchar(100) not null, "+
            COLUMN_FROM + " varchar(50) not null, "+
            COLUMN_DATE + " varchar(50) not null);";
    public DBHelper(@Nullable Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(db_create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),"Upgrade db dari versi "+oldVersion+" ke "+newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}

package com.example.mowebs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    // deklaraso konstanta yang digunakan pada database
    // seperti nama tabel, nama kolom, nama db, dan versi db
    public static final String TABLE_NAME = "tbl_chat";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_VALUE = "value";
    public static final String COLUMN_FROM = "_from";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_ISUPDATED = "isupdated";

    private static final String db_name = "chat.db";
    private static final int db_version = 1;

    // perintah untuk membuat tabel database baru
    private static final String db_create = "CREATE TABLE " +
            TABLE_NAME+" ( "+
            COLUMN_ID   + " integer primary key autoincrement, "+
            COLUMN_VALUE + " varchar(100) not null, "+
            COLUMN_FROM + " varchar(50) not null, "+
            COLUMN_DATE + " varchar(50) not null,"+
            COLUMN_ISUPDATED + " numeric not null);";
    public DBHelper(@Nullable Context context) {
        super(context, db_name, null, db_version);
    }

    // mengeksekusi perintah SQL diatas untuk membuat tabel db baru
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(db_create);
    }

    // dijalankan apabila ingin mengupgrade db
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DBHelper.class.getName(),"Upgrade db dari versi "+oldVersion+" ke "+newVersion);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}

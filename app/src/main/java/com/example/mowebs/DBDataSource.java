package com.example.mowebs;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DBDataSource {
        private SQLiteDatabase database;
        private DBHelper dbHelper;

        private String[] allColumns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_VALUE,
                DBHelper.COLUMN_FROM, DBHelper.COLUMN_DATE};

        public DBDataSource(Context context) {
            dbHelper = new DBHelper(context);
        }

        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }
}

package com.example.mowebs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBDataSource {
        private SQLiteDatabase database;
        private DBHelper dbHelper;

        private String[] allColumns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_VALUE,
                DBHelper.COLUMN_FROM, DBHelper.COLUMN_DATE, DBHelper.COLUMN_ISUPDATED};

        public DBDataSource(Context context) {
            dbHelper = new DBHelper(context);
        }

        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        public void close() {
            dbHelper.close();
        }
        public ChatObject createChat(String value, String _from, String date, int isupdated) {
            ContentValues values = new ContentValues();
            values.put(dbHelper.COLUMN_VALUE, value);
            values.put(dbHelper.COLUMN_FROM, _from);
            values.put(dbHelper.COLUMN_DATE, date);
            values.put(dbHelper.COLUMN_ISUPDATED, isupdated);

            long insertId = database.insert(dbHelper.TABLE_NAME, null, values);
            Cursor cursor = database.query(dbHelper.TABLE_NAME, allColumns,
                    dbHelper.COLUMN_ID + "=" + insertId,
                    null, null, null, null);
            cursor.moveToFirst();
            ChatObject newchat = cursorToChat(cursor);
            cursor.close();
            return newchat;
        }
        private ChatObject cursorToChat(Cursor cursor) {
            ChatObject chat = new ChatObject();
            chat.setId(cursor.getLong(0));
            chat.setValue(cursor.getString(1));
            chat.setFrom(cursor.getString(2));
            chat.setDate(cursor.getString(3));
            chat.setIsUpdated(cursor.getInt(4));
            return chat;
        }
        public ArrayList<ChatObject> getAllChat() {
            ArrayList<ChatObject> daftarChat = new ArrayList<ChatObject>();
            Cursor cursor = database.query(dbHelper.TABLE_NAME, allColumns, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ChatObject cht = cursorToChat(cursor);
                daftarChat.add(cht);
                cursor.moveToNext();
            }
            cursor.close();
            return daftarChat;
        }
}

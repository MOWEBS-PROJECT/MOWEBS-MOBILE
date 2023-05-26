package com.example.mowebs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBDataSource {
        // inisialisasi SQLite database
        private SQLiteDatabase database;
        // inisialisasi class DBHelp
        private DBHelper dbHelper;
        // mengambil semua nama kolom
        private String[] allColumns = {DBHelper.COLUMN_ID, DBHelper.COLUMN_VALUE,
                DBHelper.COLUMN_FROM, DBHelper.COLUMN_DATE, DBHelper.COLUMN_ISUPDATED};

        // DBHelper di instansiasi pada contructor
        public DBDataSource(Context context) {
            dbHelper = new DBHelper(context);
        }

        // Membuka atau membuat sambungan baru ke database
        public void open() throws SQLException {
            database = dbHelper.getWritableDatabase();
        }

        // Menutup sambungan ke database
        public void close() {
            dbHelper.close();
        }
        public ChatObject createChat(String value, String _from, String date, int isupdated) {
            // membuat sebuah CotentValue yang berfungsi untuk memasangkan dengan nama kolom pada database
            ContentValues values = new ContentValues();
            values.put(dbHelper.COLUMN_VALUE, value);
            values.put(dbHelper.COLUMN_FROM, _from);
            values.put(dbHelper.COLUMN_DATE, date);
            values.put(dbHelper.COLUMN_ISUPDATED, isupdated);

            // Mengeeksekusi perintah SQL intsert data dengan mengembalikan sebuah insert id
            long insertId = database.insert(dbHelper.TABLE_NAME, null, values);
            // setelah data dimasukan, memanggil perintah select menggunakan kursor untuk menlihat
            // apakah data sudah masuk dengan menyesuaikan id
            Cursor cursor = database.query(dbHelper.TABLE_NAME, allColumns,
                    dbHelper.COLUMN_ID + "=" + insertId,
                    null, null, null, null);
            // pindah ke data yang paling utama
            cursor.moveToFirst();
            // mengubah objek pada kursor pertama tadi kedalam objek ChatObject
            ChatObject newchat = cursorToChat(cursor);
            // close kursor
            cursor.close();
            //mengembalikan chat baru
            return newchat;
        }
        private ChatObject cursorToChat(Cursor cursor) {
            // membuat objek baru
            ChatObject chat = new ChatObject();

            // set atribut pada objek chat dengan data kursor yang diambil dari database
            chat.set_id(cursor.getString(0));
            chat.setValue(cursor.getString(1));
            chat.set_from(cursor.getString(2));
            chat.setDate(cursor.getString(3));
            chat.setIsUpdated(cursor.getInt(4));
            // kembalikan sebagai objek chat
            return chat;
        }

        // mengambil semua data chat
        public ArrayList<ChatObject> getAllChat() {
            ArrayList<ChatObject> daftarChat = new ArrayList<ChatObject>();

            // select all SQL query
            Cursor cursor = database.query(dbHelper.TABLE_NAME, allColumns, null, null, null, null, null);

            // pidah ke data utama
            cursor.moveToFirst();
            // jika masih ada data, masukan data chat ke daftar chat
            while (!cursor.isAfterLast()) {
                ChatObject cht = cursorToChat(cursor);
                daftarChat.add(cht);
                cursor.moveToNext();
            }
            // pastikan kursor tertutup
            cursor.close();
            return daftarChat;
        }
        // menghapus chat
        public void deleteChat(String id){
            // mengambil id ChatObject lalu dimasukan kedalam variable s
            String s = " _id=" + id;
            database.delete(DBHelper.TABLE_NAME,s,null);
        }
        // update chat
        public void updateChat(ChatObject chatObject){
            // mengambil id ChatObject lalu dimasukan kedalam variable s
            String s = "_id = " + chatObject.get_id();
            // mengambil  value IsUpdated pada ChatObject
            int isupdated = (chatObject.getIsUpdated() == true)? 1 : 0;

            // memasukan data kedalam tabel
            ContentValues v = new ContentValues();
            v.put(DBHelper.COLUMN_VALUE, chatObject.getValue());
            v.put(DBHelper.COLUMN_FROM, chatObject.get_from());
            v.put(DBHelper.COLUMN_DATE, chatObject.getDate());
            v.put(DBHelper.COLUMN_ISUPDATED, isupdated);

            database.update(DBHelper.TABLE_NAME, v, s, null);
    }
}

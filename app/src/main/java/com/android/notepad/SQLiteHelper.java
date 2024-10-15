package com.android.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class SQLiteHelper extends SQLiteOpenHelper {
    private SQLiteDatabase sqLiteDatabase;
    public static final String sql = "CREATE TABLE Note ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "content text,"
            + "date text);";
    public static final String tab_user = "CREATE TABLE User ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "username text,"
            + "password text);";

    //创建数据库
    public SQLiteHelper(Context context) {
        super(context, "NotePad.db", null, 2);
        sqLiteDatabase = this.getWritableDatabase();
    }

    //创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
        db.execSQL(tab_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Note");
        db.execSQL("drop table if exists User");
        onCreate(db);
    }

    //添加数据
    public boolean insertData(String userContent, String userTime) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", userContent);
        contentValues.put("date", userTime);
        return sqLiteDatabase.insert("Note", null, contentValues) > 0;
    }

    public boolean insertUser(String username, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        return sqLiteDatabase.insert("User", null, contentValues) > 0;
    }

    //删除数据
    public boolean deleteData(String id) {
        String sql = "id=?";
        //连接字符串，易错点!
        String[] contentValuesArray = new String[]{String.valueOf(id)};
        return sqLiteDatabase.delete("Note", sql, contentValuesArray) > 0;
    }

    //修改数据
    public boolean updateData(String id, String content, String userYear) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("content", content);
        contentValues.put("date", userYear);
        String sql = "id=?";
        String[] strings = new String[]{id};
        return sqLiteDatabase.update("Note", contentValues, sql, strings) > 0;
    }

    //查询数据
    public List<Note> query() {
        List<Note> list = new ArrayList<Note>();
        Cursor cursor = sqLiteDatabase.query("Note", null, null,
                null, null, null, "id desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Note note = new Note();
                String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                note.setId(id);
                note.setContent(content);
                note.setDate(date);
                list.add(note);
            }
            cursor.close();
        }
        return list;
    }

}

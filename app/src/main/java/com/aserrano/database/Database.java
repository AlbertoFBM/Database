package com.aserrano.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private static final String DB_NAME = "comments.sqlite";
    private static final int DB_VERSION = 1;
    private final SQLiteDatabase db;
    private static final String CREATE_TABLE = "CREATE TABLE comments(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, comment TEXT)";

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS COMMENT");
        onCreate(db);
    }

    public void insertComment(String name, String comment){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("comment", comment);
        db.insert("comments", null, contentValues);
    }

    public void deleteComment(int id){

        String[] args = new String[]{String.valueOf(id)};

        /*
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE + " WHERE " + col_id + "=?", args);
        db.close();
        */

        db.delete("comments", "_id=?", args);

    }

    public ArrayList<Comment> getComments(){

        ArrayList<Comment> commentsArrayList = new ArrayList<Comment>();
        Cursor cursor = db.rawQuery("select _id, name, comment from comments", null);
        if (cursor != null && cursor.getCount() > 0){
            cursor.moveToFirst();
            do{

                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("_id"));

                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));

                @SuppressLint("Range") String comment = cursor.getString(cursor.getColumnIndex("comment"));

                Comment c = new Comment(id, name, comment);

                commentsArrayList.add(c);

            }while (cursor.moveToNext());

        }
        cursor.close();
        return commentsArrayList;
    }
}

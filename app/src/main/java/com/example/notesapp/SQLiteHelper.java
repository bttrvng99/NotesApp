package com.example.notesapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NotesDB.db";
    private static final int DATABASE_VERSION = 1;
    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE Notes(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT NOT NULL, " +
                "content TEXT NOT NULL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addNotes(Notes notes){
        String sql = "INSERT INTO Notes(title, content) values(?, ?)";
        String[] args = {notes.getTitle(), notes.getContent()};
        SQLiteDatabase statement = getWritableDatabase();
        statement.execSQL(sql, args);
    }

    public List<Notes> getAll(){
        List<Notes> list = new ArrayList<>();
        SQLiteDatabase statement = getReadableDatabase();
        Cursor resultSet = statement.query("Notes", null, null,
                null, null, null, null);
        while (resultSet!=null && resultSet.moveToNext()){
            int id = resultSet.getInt(0);
            String title = resultSet.getString(1);
            String content = resultSet.getString(2);
            list.add(new Notes(id, title, content));
        }
        return list;
    }

    public int getIdByContent(String title, String content){
        int notes_id = 0;
        String whereClause = "content LIKE ? OR title LIKE ?";
        String whereArgs[] = {"%"+content+"%", "%"+title+"%"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("Notes", null, whereClause, whereArgs, null, null, null);
        while (cursor != null && cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            notes_id = id;
        }
        cursor.close();
        return notes_id;
    }

    public List<Notes> getNotesByKeyword(String query){
        List<Notes> list = new ArrayList<>();
        String whereClause = "content LIKE ? OR title LIKE ?";
        String whereArgs[] = {"%"+query+"%", "%"+query+"%"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query("Notes", null, whereClause, whereArgs, null, null, null);
        while (cursor != null && cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            list.add(new Notes(id, title, content));
        }
        cursor.close();
        return list;
    }

    public int update(Notes notes){
        ContentValues values = new ContentValues();
        values.put("title", notes.getTitle());
        values.put("content", notes.getContent());
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(notes.getId())};
        SQLiteDatabase st = getWritableDatabase();
        return st.update("Notes", values, whereClause, whereArgs);
    }

    public int deleteNote(int id){
        String whereClause = "id = ?";
        String[] whereArgs = {String.valueOf(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.delete("Notes", whereClause, whereArgs);
    }
}

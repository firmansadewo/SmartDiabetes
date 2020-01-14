package com.example.diabetes.Helper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "DIABETES.db";
    public static final String TABLE_NAME = "users";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "email";
    public static final String COL_4 = "password";
    public static final String TABLE_NAME1 = "track_sewaktu";
    public static final String COL_12 = "ID";
    public static final String COL_22 = "tracksewaktu";
    public static final String COL_32 = "tanggalsewaktu";
    public static final String TABLE_NAME2 = "track_puasa";
    public static final String COL_13 = "ID";
    public static final String COL_23= "trackpuasa";
    public static final String COL_33 = "tanggalpuasa";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT, email TEXT, password TEXT)");
        db.execSQL("create table " + TABLE_NAME1 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,tracksewaktu FLOAT,tanggalsewaktu INTEGER)");
        db.execSQL("create table " + TABLE_NAME2 +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,trackpuasa FLOAT,tanggalpuasa INTEGER)");

    }

//beginning of login function section
    //using this method we can add users to user table
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(COL_2, user.userName);

        //Put email in  @values
        values.put(COL_3, user.email);

        //Put password in  @values
        values.put(COL_4, user.password);

        // insert row
        long todo_id = db.insert(TABLE_NAME, null, values);
    }



    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,// Selecting Table
                new String[]{COL_1, COL_2, COL_3, COL_4},//Selecting columns want to query
                COL_3 + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME,// Selecting Table
                new String[]{COL_1, COL_2, COL_3, COL_4},//Selecting columns want to query
                COL_3 + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }

//end of login function section


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }



    public boolean insertData(float tracksewaktu, long tanggalsewaktu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_22,tracksewaktu);
        contentValues.put(COL_32,tanggalsewaktu);
        long result = db.insert(TABLE_NAME1,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean insertDatab(float trackpuasa, long tanggalpuasa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_23,trackpuasa);
        contentValues.put(COL_33,tanggalpuasa);
        long result = db.insert(TABLE_NAME2,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public Cursor getAllDatab() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME1,null);
        return res;
    }
    public Cursor getLastDatab() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME1+" ORDER BY ID DESC LIMIT 1",null);
        return res;
    }
    public Cursor getLastData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" ORDER BY ID DESC LIMIT 1",null);
        return res;
    }
    public Cursor getFirstData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" ORDER BY ID ASC LIMIT 1",null);
        return res;
    }
    public Cursor getFirstDatab() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME1+" ORDER BY ID ASC LIMIT 1",null);
        return res;
    }

    public boolean updateData(String id, float tracksewaktu, long tanggalsewaktu) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_12,id);
        contentValues.put(COL_22,tracksewaktu);
        contentValues.put(COL_32,tanggalsewaktu);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }

    public boolean updateDatab (String id, float trackpuasa, long tanggalpuasa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_13,id);
        contentValues.put(COL_23,trackpuasa);
        contentValues.put(COL_33,tanggalpuasa);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteDatab (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
    }




    public Integer addmoreDatab (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete (TABLE_NAME, "ID = ?", new String[] {id});
    }



}




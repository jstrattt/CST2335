package com.example.jessica.lab1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by Jessica on 2016-10-19.
 */
public class ChatDatabaseHelper extends SQLiteOpenHelper {
    protected static final String CHAT_DATABASE_ACTIVITY ="ChatDatabaseActivity";;
    public static final String DATABASE_NAME = "database5";
    public static final int VERSION_NUM=2;
    public static final String TABLE_COMMENTS= "messageTable";
    public static final String KEY_ID= "_id";
    public static final String KEY_MESSAGE = "comment";



    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        Log.i(CHAT_DATABASE_ACTIVITY, "Calling onCreate");
       db.execSQL( "CREATE TABLE " + TABLE_COMMENTS + " (" + KEY_ID + " " +
                "INT AUTO_INCREMENT, " + KEY_MESSAGE + " VARCHAR(200), CONSTRAINT MESSAGELIST_PK PRIMARY KEY (" + KEY_ID + "));"
       );

    }




    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
       db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
        Log.i(CHAT_DATABASE_ACTIVITY, "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion= " + newVersion);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        onCreate(db);
        Log.i(CHAT_DATABASE_ACTIVITY, "Calling onDowngrade, oldVersion=" + oldVersion + " newVersion= " + newVersion);
    }



}

package com.example.android.waitlist;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.*;
/**
 * Created by Swapnil on 20-12-2016.
 */

public class WaitListDBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "waitlist.db";
    private static final int DATABASE_VERSION = 1;

    public WaitListDBHelper(Context context){
        super(context,DATABASE_NAME,null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
      final String SQL_CREATE_WAITLIST_TABLE = "CREATE TABLE " + WaitListContract.WaitListEntry.TABLE_NAME
              + " (" + WaitListContract.WaitListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
              + WaitListContract.WaitListEntry.COLUMN_GUEST_NAME + " TEXT NOT NULL, "
              + WaitListContract.WaitListEntry.COLUMN_PARTY_SIZE+ " INTEGER NOT NULL, "
              + WaitListContract.WaitListEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP);";
       sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
      sqLiteDatabase.execSQL("DROP IF EXISTS "+ WaitListContract.WaitListEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

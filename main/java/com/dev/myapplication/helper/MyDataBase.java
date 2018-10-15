package com.dev.myapplication.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ALI_SINHA on 27/03/2018.
 */

public class MyDataBase extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "recette";
    public MyDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
      sqLiteDatabase.execSQL(EvenementDataBase.SQL_CREATION_TABLE);
        //sqLiteDatabase.execSQL(EvenementDataBase.SQL_CREATION_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

package com.dev.myapplication.helper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dev.myapplication.app.AppController;
import com.dev.myapplication.objet.cookers;

import java.util.ArrayList;

/**
 * Created by TCHIKS on 08/05/2018.
 */

public class EvenementDataBase {

    public static final String TABLE_COOKERS = "cookers";

    private static final String COL_ID = "ID";
    private static final String COL_NAME = "NAME";
    private static final String COL_EMAIL = "email";
    private static final String COL_PASSWORD = "PASSWORD";

    public static final String SQL_CREATION_TABLE = "CREATE TABLE " + TABLE_COOKERS
            + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_NAME + " TEXT NOT NULL, " + COL_EMAIL + " TEXT NOT NULL, "
            +COL_PASSWORD+" TEXT);";

    public static void insert(cookers userModel) {
        //On ouvre la base en écriture
        SQLiteDatabase bdd = AppController.getMaBaseSQLite().getWritableDatabase();

        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();

        values.put(COL_ID, userModel.getId());
        values.put(COL_NAME, userModel.getName());
        values.put(COL_EMAIL, userModel.getEmail());
        values.put(COL_PASSWORD, userModel.getPassword());

        bdd.insert(TABLE_COOKERS,null,values);
        bdd.close();
    }

    /*public static cookers getOneUser(String name,String pass) {

        SQLiteDatabase db = AppController.getMaBaseSQLite().getReadableDatabase();
        String[] columns={ COL_ID };
        // Select One Query
        String selection= COL_FIRST_NAME + " = ?" +"AND" +COL_PASSWORD+" = ?";
        String selectionArgs[]={name,pass};
        Cursor cursor = db.query(TABLE_ASSET, columns,selection,selectionArgs,null,null,null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserModel user = new UserModel();
                user.setFirst_name(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                return user;
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        cursor.close();
        db.close();
        // return contact list
        return null;

    }*/

    public static cookers getUser() {
        SQLiteDatabase db = AppController.getMaBaseSQLite().getWritableDatabase();
        // Select One Query
        String selectQuery = "SELECT  * FROM "+TABLE_COOKERS;

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                cookers user = new cookers();
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setPassword(cursor.getString(3));
                return user;
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        cursor.close();
        db.close();
        // return contact list
        return null;

    }
}

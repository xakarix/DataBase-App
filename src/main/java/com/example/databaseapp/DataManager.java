package com.example.databaseapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataManager {
    private SQLiteDatabase db;

    public static final String TABLE_ROW_ID = "_id";
    public static final String TABLE_ROW_NAME = "name";
    public static final String TABLE_ROW_SURNAME = "surname";
    public static final String TABLE_ROW_AGE = "age";
    public static final String TABLE_ROW_MALE = "male";
    public static final String TABLE_ROW_NATIONALITY = "nationality";

    private static final  String DB_NAME = "personal_details_db";
    private static final int DB_VERSION = 3;
    private static final  String TABLE_N_AND_S = "names_and_surnames";

    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {

        public CustomSQLiteOpenHelper(Context context){
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String newTableQueryString = "create table "
                    + TABLE_N_AND_S + " ("
                    + TABLE_ROW_ID
                    + " integer primary key autoincrement not null,"
                    + TABLE_ROW_NAME
                    + " text not null,"
                    + TABLE_ROW_SURNAME
                    + " text not null,"
                    + TABLE_ROW_AGE
                    + " text not null,"
                    + TABLE_ROW_MALE
                    + " text not null,"
                    + TABLE_ROW_NATIONALITY
                    + " text not null);";
            db.execSQL(newTableQueryString);

        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_N_AND_S);
            onCreate(db);
        }
    }

    public DataManager(Context context){
        DataManager.CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insert(String name, String surname, String age, String male, String nationality)
    {
        String query = "INSERT INTO " +
                TABLE_N_AND_S +
                " (" +
                TABLE_ROW_NAME +
                ", " +
                TABLE_ROW_SURNAME +
                ", " +
                TABLE_ROW_AGE +
                ", " +
                TABLE_ROW_MALE +
                ", " +
                TABLE_ROW_NATIONALITY +
                ") "+
                "VALUES (" +
                "'" +
                name +
                "'" + ", " +
                "'" +
                surname +
                "'" + ", " +
                "'" +
                age +
                "'" + ", " +
                "'" +
                male +
                "'" + ", " +
                "'" +
                nationality + "');";
        Log.i("insert() = ",query);
        db.execSQL(query);
    }

    public void delete(String name){
        String query = "DELETE FROM " +
                TABLE_N_AND_S +
                " WHERE " +
                TABLE_ROW_NAME +
                " = '" +
                name + "';";
        Log.i("delete() = ", query);
        db.execSQL(query);
    }

    public Cursor selectAll(){
        String spinnerCategory = MainActivity.genderSpinner.getSelectedItem().toString();

        String query = "";

        if(MainActivity.recordCheckBox.isChecked()){
            if(spinnerCategory.equals("Male")){
                query = "SELECT * FROM "
                        + TABLE_N_AND_S
                        + " WHERE "
                        + TABLE_ROW_MALE
                        + " = '"
                        + "Male"
                        + "' LIMIT 1;";
            } else if(spinnerCategory.equals("Female")){
                query = "SELECT * FROM "
                        + TABLE_N_AND_S
                        + " WHERE "
                        + TABLE_ROW_MALE
                        + " = '"
                        + "Female"
                        + "' LIMIT 1;";
            } else if(spinnerCategory.equals("All results")){
                query = "SELECT * from " + TABLE_N_AND_S + " LIMIT 1;";
            }
        }else{
            if(spinnerCategory.equals("Male")){
                query = "SELECT * FROM "
                        + TABLE_N_AND_S
                        + " WHERE "
                        + TABLE_ROW_MALE
                        + " = '"
                        + "Male"
                        + "';";
            } else if(spinnerCategory.equals("Female")){
                query = "SELECT * FROM "
                        + TABLE_N_AND_S
                        + " WHERE "
                        + TABLE_ROW_MALE
                        + " = '"
                        + "Female"
                        + "';";
            } else if(spinnerCategory.equals("All results")){
                query = "SELECT * from " + TABLE_N_AND_S;
            }
        }



        Cursor c = db.rawQuery(query, null);
        return c;
    }

    public Cursor searchName(String name){
        String query = "SELECT * FROM " +
                TABLE_N_AND_S + " WHERE " +
                TABLE_ROW_NAME + " = '" + name + "';";
        Log.i("searchName() =", query);
        Cursor c = db.rawQuery(query,null);
        return c;


    }
}

package com.example.android.testin3.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.testin3.data.database_elements_name.variables_entry;
import com.example.android.testin3.data.database_elements_name.checkboxes_table;

/**
 * Created by shubhashish on 05-02-2018.
 */


public class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context) {
        super(context, database_elements_name.DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       //BELOW IS THE ORIGINAL CODE TO CREATE CLOUMNS FOR TABLE
        // db.execSQL("create table " + TABLE_NAME +" (_ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,SURNAME TEXT,MARKS INTEGER)");

        //HERE IS THE MODIFIED DIFERENT APPROAOCH
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_VARIABLE_TABLE =  "CREATE TABLE " + variables_entry.TABLE_NAME + " ("
                + variables_entry.COLUMN_1_ID + " INTEGER PRIMARY KEY  , "
                + variables_entry.COLUMN_2_VARIABLE_NAME + " TEXT NOT NULL, "
                + variables_entry.COLUMN_3_VALUE_OR_STATUS + " INTEGER );";
       // use the below previuos code if you need to set default value for any of the column
               // + PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
                //+ PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_VARIABLE_TABLE);
        //-------------------------------------------------------------------------------------------------------------------------------------------------------------
       //BELOW IS THE ORIGINAL CODE TO CREATE CLOUMNS FOR TABLE of checkboxes
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_CHECKBOXES_TABLE =  "CREATE TABLE " + checkboxes_table.TABLE_NAME_2 + " ("
                + checkboxes_table.COLUMN_0_Monday + " INTEGER , "
                + checkboxes_table.COLUMN_1_tuesday + " INTEGER , "
                + checkboxes_table.COLUMN_2_wednesday + " INTEGER , "
                + checkboxes_table.COLUMN_3_thursday + " INTEGER , "
                + checkboxes_table.COLUMN_4_friday + " INTEGER , "
                + checkboxes_table.COLUMN_5_saturday + " INTEGER , "
                + checkboxes_table.COLUMN_6_sunday + " INTEGER  );";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_CHECKBOXES_TABLE);
        //--------------------------------------------------------------------------------------------------------------------

        String SQL_CREATE_BROWSER_TABLE =  "CREATE TABLE " + "browser_table" + " ("
                + "ID" + " INTEGER PRIMARY KEY  , "
                + "url" + " TEXT NOT NULL );";
        // use the below previuos code if you need to set default value for any of the column
        // + PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
        //+ PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BROWSER_TABLE);
        //--------------------------------------------------------------------------------------------------------------------

        String SQL_CREATE_BROWSER_TABLE_2 =  "CREATE TABLE " + "browser_table_2" + " ("
                + "title" + " TEXT PRIMARY KEY  , "
                + "value" + " TEXT NOT NULL );";
        // use the below previuos code if you need to set default value for any of the column
        // + PetEntry.COLUMN_PET_GENDER + " INTEGER NOT NULL, "
        //+ PetEntry.COLUMN_PET_WEIGHT + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BROWSER_TABLE_2);



     }
//-------------------------------------------------------------------------------------------------------------
    //below methods are for operations on variables table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+variables_entry.TABLE_NAME);
        onCreate(db);
    }


    //below id parameter for id is string even though in table id is declared as integer ........look into that kater
    public boolean insertData(String id ,String name,  int value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(variables_entry.COLUMN_1_ID,id);
        contentValues.put(variables_entry.COLUMN_2_VARIABLE_NAME,name);
        contentValues.put(variables_entry.COLUMN_3_VALUE_OR_STATUS,value);

        long result = db.insert(variables_entry.TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }



    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+variables_entry.TABLE_NAME,null);
        return res;
    }

    //get specific data .......a single row(where id=1,means no of period row) and of a single column of value column  ...by passing id to this method
    public Cursor get_noOfPeriod_row_Data() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from " + variables_entry.TABLE_NAME + " where " + variables_entry.COLUMN_1_ID + "='1'" , null);
        return res;

    }

    public boolean updateData(String id, String name,  int value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(variables_entry.COLUMN_1_ID,id);
        contentValues.put(variables_entry.COLUMN_2_VARIABLE_NAME,name);
        contentValues.put(variables_entry.COLUMN_3_VALUE_OR_STATUS,value);
        //this below code troubled me  a lot ....one thing to note that ht e below code works only when our name of the string column for id is "ID"
        db.update(variables_entry.TABLE_NAME, contentValues, "ID = ?",new String[] { id });

        return true;
    }

    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(variables_entry.TABLE_NAME, "ID = ?",new String[] {id});
    }
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //below methods are for operations on CHECKBOXES TABLE

    public boolean insertData_2(int value_0 ,int value_1,  int value_2 , int value_3 , int value_4 , int value_5 ,int value_6) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(checkboxes_table.COLUMN_0_Monday,value_0);
        contentValues.put(checkboxes_table.COLUMN_1_tuesday,value_1);
        contentValues.put(checkboxes_table.COLUMN_2_wednesday,value_2);
        contentValues.put(checkboxes_table.COLUMN_3_thursday,value_3);
        contentValues.put(checkboxes_table.COLUMN_4_friday,value_4);
        contentValues.put(checkboxes_table.COLUMN_5_saturday,value_5);
        contentValues.put(checkboxes_table.COLUMN_6_sunday,value_6);


        long result = db.insert(checkboxes_table.TABLE_NAME_2,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    //remember column no passing to it for days start from 0 her because of switch case even though at column 0 we have column of id
    public boolean updateData_perticular_column(int column_no ,int value ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        switch(column_no) {
            case 0:
                contentValues.put(checkboxes_table.COLUMN_0_Monday,value);
                break;
            case 1:
                contentValues.put(checkboxes_table.COLUMN_1_tuesday,value);
                break;
            case 2:
                contentValues.put(checkboxes_table.COLUMN_2_wednesday,value);
                break;
            case 3:
                contentValues.put(checkboxes_table.COLUMN_3_thursday,value);
                break;
            case 4:
                contentValues.put(checkboxes_table.COLUMN_4_friday,value);
                break;
            case 5:
                contentValues.put(checkboxes_table.COLUMN_5_saturday,value);
                break;
            case 6:
                contentValues.put(checkboxes_table.COLUMN_6_sunday,value);
                break;
        }
        //this below code troubled me  a lot ....one thing to note that ht e below code works only when our name of the string column for id is "ID"
        db.update(checkboxes_table.TABLE_NAME_2, contentValues, "tue = ?",new String[] { "0" });

        return true;

    }


    public Cursor getAllData2() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+checkboxes_table.TABLE_NAME_2,null);
        return res;
    }
//--------------------------------------------------------------------------------------------------------------------------------------
//below id parameter for id is string even though in table id is declared as integer ........look into that kater
public boolean insertData_3(String id ,String URL) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(variables_entry.COLUMN_1_ID,id);
    contentValues.put("URL",URL);


    long result = db.insert("browser_table",null ,contentValues);
    if(result == -1)
        return false;
    else
        return true;
}



    public Cursor getAllData_3() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+"browser_table",null);
        return res;
    }



    public boolean updateData_3(String id, String URL) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(variables_entry.COLUMN_1_ID,id);
        contentValues.put("URL",URL);

        //this below code troubled me  a lot ....one thing to note that ht e below code works only when our name of the string column for id is "ID"
        db.update("browser_table", contentValues, "ID = ?",new String[] { id });

        return true;
    }

    public Boolean deleteData_3 (String id) {
       /* SQLiteDatabase db = this.getWritableDatabase();
        db.delete("browser_table", "ID = ?",new String[] { id });

        return true;*/


       /* SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + "browser_table"+ " WHERE "+variables_entry.COLUMN_1_ID+"='"+id+"'");
        db.close();*/


        SQLiteDatabase db = this.getWritableDatabase();
            return db.delete("browser_table", variables_entry.COLUMN_1_ID + "=" + id, null) > 0;


        /*SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = variables_entry.COLUMN_1_ID + "=?";
        String[] whereArgs = new String[]{id};
        db.delete("browser_table", whereClause, whereArgs);*/
    }
    //--------------------------------------------------------------------------------------------------------------------------------------
//below id parameter for id is string even though in table id is declared as integer ........look into that kater
    public boolean insertData_4(String title ,String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("value",value);


        long result = db.insert("browser_table_2",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }



    public Cursor getAllData_4() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+"browser_table_2",null);
        return res;
    }

    public boolean updateData_4(String title ,String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("title",title);
        contentValues.put("value",value);

        //this below code troubled me  a lot ....one thing to note that ht e below code works only when our name of the string column for id is "ID"
        db.update("browser_table_2", contentValues, "title = ?",new String[] { title });

        return true;
    }



}

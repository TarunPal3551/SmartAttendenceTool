package com.example.android.testin3.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



/**
 * Created by shubhashish on 11-03-2018.
 */

public class DatabaseHelper_3 extends SQLiteOpenHelper{



    //---------------------------------TO CREATE A NEW TABLE FOR SUBJECTS NAME AND RELATED STUFF------------------------------------
    //REMEMBER  we are not using databse_elements_name CLASS for naming the column or the table name or dtabas ename.....just the names dirctly given inside inverted commas



    // below variable is global so ....each time create_new_database_table is called ...a code there sets it equal to the name passed to that method ...
    //....later we just  use it in the upgarde method because table name cannot be passed that fucker
    String  name_of_table_dynamic ="dummy";

    public DatabaseHelper_3(Context context) {
        super(context, "SUBJECTS_name_and_related.db", null, 1);
    }

//---------------------------------------------------------------------------------

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    //check the on upgrade method later .... i guess it has relation to do with apk upgrades to a new version

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+name_of_table_dynamic);
        onCreate(db);
    }

    //-------------------------------------------------------------------------------------------------------------


    //below method takes a string name as a parameter for naming the table and making it dynamically....name is supposed to be a week no in inverted commas


    public void create_new_database_table_dyanmic(String tableName) {
        name_of_table_dynamic = tableName;

        final SQLiteDatabase db = getWritableDatabase();
        String CREATE_TABLE_NEW_USER = "CREATE TABLE " + tableName + " ("
                + "ID"+ " INTEGER PRIMARY KEY  , "
                + "name_for_subjects_column" + " STRING , "
                + "OCCURENCE" + " INTEGER );";
        db.execSQL(CREATE_TABLE_NEW_USER);
        db.close();
    }

//-------------------------------------------------SUBJECTS_GIVEN TO PERIODS TABLE INITIALISATION

    public void create_SUBJECTS_GIVEN_TO_PERIODS_TABLE_dynamic(String tableName) {

        final SQLiteDatabase db = getWritableDatabase();
        String CREATE_TABLE_NEW_USER = "CREATE TABLE " +  tableName + " ("
                + "ID"+ " INTEGER PRIMARY KEY  , "
                + "MONDAY" + " STRING , "
                + "TUESDAY" + " STRING , "
                + "WEDNESDAY" + " STRING , "
                + "THURSDAY" + " STRING , "
                + "FRIDAY" + " STRING , "
                + "SATURDAY" + " STRING , "
                + "SUNDAY" + " INTEGER );";
        db.execSQL(CREATE_TABLE_NEW_USER);
        db.close();
    }




    //------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //below method is for reteiving all the name sof the table in databse
    public String[] get_all_table_names() {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);


        //counter for counting the no of tables
        int counter =0;

        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                c.moveToNext();
                counter++;
            }
        }
        //giving one more size to counter because at first index we are storing below extra data
        counter = counter + 1;
        //now declaring a string of more than the amount that of tables
        String[] Table_names = new String[counter];
        //the value at the first index of the array table_names is the value of the counter ...remeber the counter integer variable is given inside a string.valueof() because array is of string
        Table_names[0] = String.valueOf(counter);
        //remember to start array from 1 for storing names
        int i = 1;
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                //storing the name in the string array
                Table_names[i] =  c.getString(0);
                c.moveToNext();
                i++;
            }
        }

        return Table_names;
    }

//----------------------------------------------------------------------------------CONTINUE FROM HERE
    //below methods are for operations on DATA OF DAYS OF A WEEK table


    public boolean insertData(String id ,String value_0 ,int value_1 , String name_of_table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("name_for_subjects_column",value_0);
        contentValues.put("OCCURENCE",value_1);
        long result = db.insert(name_of_table,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }



    public Cursor getAllData(String name_of_table) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+name_of_table,null);
        return res;
    }
    /**
     //get specific data .......a single row(where id=1,means no of period row) and of a single column of value column  ...by passing id to this method
     public Cursor get_noOfPeriod_row_Data() {
     SQLiteDatabase db = this.getWritableDatabase();
     Cursor res = db.rawQuery("select * from " + variables_entry.TABLE_NAME + " where " + variables_entry.COLUMN_1_ID + "='1'" , null);
     return res;

     }
     **/
    public boolean updateData_all_column_at_once(String id ,String value_0 ,int value_1, String name_of_table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("name_for_subjects_column",value_0);
        contentValues.put("OCCURENCE",value_1);
        //this below code troubled me  a lot ....one thing to note that ht e below code works only when our name of the string column for id is "ID"
        db.update(name_of_table, contentValues, "ID = ?",new String[] { id });

        return true;
    }


    //method to check if the table already exists in the database or not
    public boolean isTableExists(String tableName, boolean openDb) {
        SQLiteDatabase db = this.getWritableDatabase();
        if(openDb) {
            if(db == null || !db.isOpen()) {
                db = getReadableDatabase();
            }

            if(!db.isReadOnly()) {
                db.close();
                db = getReadableDatabase();
            }
        }

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
        if(cursor!=null) {
            if(cursor.getCount()>0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    public Integer deleteData (String id ,String name_of_table) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(name_of_table, "ID = ?",new String[] {id});
    }
    //------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //---******************************---------------------below methods are for operations on SUBJECTS_GIVEN_TO_PERIODS_TABLE----------------**********************




    public boolean insertData_2(String id ,String value_0 ,String value_1 ,String value_2 ,String value_3, String value_4 ,String value_5, String value_6, String name_of_table ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID",id);
        contentValues.put("MONDAY",value_0);
        contentValues.put("TUESDAY",value_1);
        contentValues.put("WEDNESDAY",value_2);
        contentValues.put("THURSDAY",value_3);
        contentValues.put("FRIDAY",value_4);
        contentValues.put("SATURDAY",value_5);
        contentValues.put("SUNDAY",value_6);
        long result = db.insert(name_of_table,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updateData_all_column_at_once_2(String id ,String value_0 ,String value_1 ,String value_2 ,String value_3, String value_4 ,String value_5, String value_6, String name_of_table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("MONDAY",value_0);
        contentValues.put("TUESDAY",value_1);
        contentValues.put("WEDNESDAY",value_2);
        contentValues.put("THURSDAY",value_3);
        contentValues.put("FRIDAY",value_4);
        contentValues.put("SATURDAY",value_5);
        contentValues.put("SUNDAY",value_6);
        //this below code troubled me  a lot ....one thing to note that ht e below code works only when our name of the string column for id is "ID"
        db.update(name_of_table, contentValues, "ID = ?",new String[] { id });

        return true;
    }



    public Cursor getAllData_2_(String name_of_table) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+name_of_table,null);
        return res;
    }


    /**
     * //IF EVER NEEDED THEN EDIT AND USE BELOW METHOD


    public Integer deleteData (String id ,String name_of_table) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(name_of_table, "ID = ?",new String[] {id});
    }

**/


//-------------------------------------------------------------------------------------------------------------------------------------------------------------------




}

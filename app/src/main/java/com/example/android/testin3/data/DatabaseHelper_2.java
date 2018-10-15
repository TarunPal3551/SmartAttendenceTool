package com.example.android.testin3.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.testin3.data.database_elements_name.table_of_data_of_a_pericular_week;

/**
 * Created by shubhashish on 05-02-2018.
 */


public class DatabaseHelper_2 extends SQLiteOpenHelper {

   // below variable is global so ....each time create_new_database_table is called ...a code there sets it equal to the name passed to that method ...
    //....later we just  use it in the upgarde method because table name cannot be passed that fucker
    String  name_of_table_dynamic ="dummy";

    public DatabaseHelper_2(Context context) {
        super(context, database_elements_name.DATABASE_NAME_2, null, 1);
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
                + table_of_data_of_a_pericular_week.COLUMN_1_ID + " INTEGER PRIMARY KEY  , "
                + table_of_data_of_a_pericular_week.COLUMN_0_Monday + " STRING , "
                + table_of_data_of_a_pericular_week.COLUMN_1_tuesday + " STRING , "
                + table_of_data_of_a_pericular_week.COLUMN_2_wednesday + " STRING , "
                + table_of_data_of_a_pericular_week.COLUMN_3_thursday + " STRING , "
                + table_of_data_of_a_pericular_week.COLUMN_4_friday + " STRING , "
                + table_of_data_of_a_pericular_week.COLUMN_5_saturday + " STRING , "
                + table_of_data_of_a_pericular_week.COLUMN_6_sunday + " STRING  );";
        db.execSQL(CREATE_TABLE_NEW_USER);
        db.close();
    }

//-------------------------------------------------------------------------------
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


    public boolean insertData(String id ,String value_0 ,String value_1,  String value_2 , String value_3 , String value_4 , String value_5 ,String value_6 , String name_of_table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_1_ID,id);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_0_Monday,value_0);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_1_tuesday,value_1);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_2_wednesday,value_2);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_3_thursday,value_3);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_4_friday,value_4);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_5_saturday,value_5);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_6_sunday,value_6);
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
    public boolean updateData_all_column_at_once(String id ,String value_0 ,String value_1,  String value_2 , String value_3 , String value_4 , String value_5 ,String value_6 , String name_of_table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_1_ID,id);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_0_Monday,value_0);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_1_tuesday,value_1);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_2_wednesday,value_2);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_3_thursday,value_3);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_4_friday,value_4);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_5_saturday,value_5);
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_6_sunday,value_6);
        //this below code troubled me  a lot ....one thing to note that ht e below code works only when our name of the string column for id is "ID"
        db.update(name_of_table, contentValues, "ID = ?",new String[] { id });

        return true;
    }

    //this update method is not used now ......but  for later when the feature of changing the default holidays is introduced
    //remember column no passing to it for days start from 0 her because of switch case even though at column 0 we have column of id
    public boolean updateData_perticular_column(String id ,int column_no ,String value , String name_of_table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(table_of_data_of_a_pericular_week.COLUMN_1_ID,id);
        switch(column_no) {
            case 0:
                contentValues.put(table_of_data_of_a_pericular_week.COLUMN_0_Monday,value);
                break;
            case 1:
                contentValues.put(table_of_data_of_a_pericular_week.COLUMN_1_tuesday,value);
                break;
            case 2:
                contentValues.put(table_of_data_of_a_pericular_week.COLUMN_2_wednesday,value);
                break;
            case 3:
                contentValues.put(table_of_data_of_a_pericular_week.COLUMN_3_thursday,value);
                break;
            case 4:
                contentValues.put(table_of_data_of_a_pericular_week.COLUMN_4_friday,value);
                break;
            case 5:
                contentValues.put(table_of_data_of_a_pericular_week.COLUMN_5_saturday,value);
                break;
            case 6:
                contentValues.put(table_of_data_of_a_pericular_week.COLUMN_6_sunday,value);
                break;
        }
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
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------


}

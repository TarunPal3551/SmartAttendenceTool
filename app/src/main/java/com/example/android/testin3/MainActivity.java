package com.example.android.testin3;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.testin3.data.DatabaseHelper;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    int  is_it_default_for_no_of_period_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myDb = new DatabaseHelper(this);
        //
      //  boolean inserting_default_data_ = myDb.insertData( "nu_of_period_", -2);
                            boolean inserting_default_for_no_of_period = myDb.insertData("1","nu_of_period_",  -2);

        //now retriving the same varaiable
                            Cursor res = myDb.getAllData();
                            //below we pass the row no as a parameter ....rows start from 1
                            res.move(1);
                            //below we pass the column no as a parameter ....column start from 0
                            is_it_default_for_no_of_period_ = Integer.parseInt(res.getString(2));

        //-2 for below if else means means true
        if (is_it_default_for_no_of_period_ == -2) {

            Intent welcomeIntent;
            welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
            startActivity(welcomeIntent);
        }

        else
        {
//retriving the int variable to know which activity is selected for to be opened on strtup
            //  EditorActivity-2
            // attendence_predicting_activity-3
            // web_view-4
            Cursor ress = myDb.getAllData2();
            ress.moveToFirst();
            ress.moveToNext();
           int intent_variable= ress.getInt(ress.getColumnIndex("mon"));

            if(intent_variable == 2) {

                Intent editorIntent;
                editorIntent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(editorIntent);
            }else if(intent_variable == 3) {

                Intent editorIntent;
                editorIntent = new Intent(MainActivity.this, attendence_predicting_activity.class);
                startActivity(editorIntent);
            }else if(intent_variable == 4) {

                Intent editorIntent;
                editorIntent = new Intent(MainActivity.this, web_view.class);
                startActivity(editorIntent);
            }
        }
    }
}

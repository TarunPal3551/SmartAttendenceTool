package com.example.android.testin3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.testin3.data.DatabaseHelper;
import com.example.android.testin3.data.DatabaseHelper_2;


public class WelcomeActivity extends AppCompatActivity{


    boolean status;
    EditText ed_java;
    //below variables are declared globally so that later new values from editfield can be given to them

    int no_of_periods;

    DatabaseHelper myDb;
    DatabaseHelper_2 myDb_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        myDb = new DatabaseHelper(this);
        myDb_2 = new DatabaseHelper_2(this);


////////////////////////////////////////////////----------------------------------------------------------
        final boolean booly_2[] = setting_the_checkboxes();


///////////////////////////////////////////////////////------------------------------------------------------


        Button b1_java = (Button) findViewById(R.id.b1_xml);
        b1_java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //settingtheNoOfPeriod() method returns the  custom word which have the noOfDays and NoOfPeriods
                int no_of_period3 = settingtheNoOfPeriod();


                //now we need to throw the intent to change activity
                //this if statement(for loading editoractivity) is only executed when some new value is fed to the no_of_period object inside if else statement of settingtheNoOfPeriod()method
                // ..as the original values 1 in the settingnoofperiod method
                //its done because we dont want to load the Editoractivity on button click if no entries are given to edittext views
                if (no_of_period3 != -1) {


                    Intent welcome_intent = new Intent(WelcomeActivity.this, time_table_asking_activity.class);
                            //since we have retrived no of period from edit text ...lets store it on database
                            //we just need to update the already inserted default data by mainactivity
                            // boolean inserting_no_of_period_in_database = myDb.updateData(1, "nu_of_period_", no_of_period3);
                                    boolean isUpdate = myDb.updateData("1", "nu_of_period_", no_of_period3);

                            //below method puts the booly_2(array for checkboxes) data in the database
                                    store_the_checkboxes_data_in_database(booly_2);


                    startActivity(welcome_intent);



                }

            }
        });


        AddData();


    }

    private void store_the_checkboxes_data_in_database(boolean bool_of_checkboxes[]) {

        int b;

        int ctr[] = new int[7];

        for(b=0 ; b<7 ; b++){

            if (bool_of_checkboxes[b] == true){ctr[b] = 1;}
            else {ctr[b] = -1;}
        }

        boolean inserting_checkboxes_data = myDb.insertData_2(ctr[0], ctr[1], ctr[2] , ctr[3] , ctr[4] , ctr[5] , ctr[6]);


        //---------------------------------------------------------------------------------
//Inserting editer activity as the default class to open on startup
        //  EditorActivity-2
        // attendence_predicting_activity-3
        // web_view-4
        myDb.insertData_2(2, 0, 0, 0, 0 , 0 , 0);
    }


    private boolean[] setting_the_checkboxes() {

        final CheckBox cb_java[] = new CheckBox[7];

        cb_java[0] = (CheckBox) findViewById(R.id.cb_xml_1);
        cb_java[1] = (CheckBox) findViewById(R.id.cb_xml_2);
        cb_java[2] = (CheckBox) findViewById(R.id.cb_xml_3);
        cb_java[3] = (CheckBox) findViewById(R.id.cb_xml_4);
        cb_java[4] = (CheckBox) findViewById(R.id.cb_xml_5);
        cb_java[5] = (CheckBox) findViewById(R.id.cb_xml_6);
        cb_java[6] = (CheckBox) findViewById(R.id.cb_xml_7);

        final boolean booly_java[] = new boolean[7];
        //
        int h;
        for (h = 0; h < 7; h++) {
            booly_java[h] = false;
        }


        for (h = 0; h < 7; h++) {
            final int finalH = h;

            cb_java[h].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

//below if else works as a toggle....it makes view work as a toggle switch
                    //enable
                    if (booly_java[finalH] == false) {
                        booly_java[finalH] = true;

                        //display in short period of time
                        Toast.makeText(getApplicationContext(), "checked" + finalH + booly_java[finalH],
                                Toast.LENGTH_SHORT).show();

                    } else
                    //disable
                    {
                        booly_java[finalH] = false;

                        //display in short period of time
                        Toast.makeText(getApplicationContext(), "checked" + finalH + booly_java[finalH],
                                Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }

        return booly_java;

    }


    public int settingtheNoOfPeriod() {


        EditText ed_java = (EditText) findViewById(R.id.ed_xml);
        //its declared 1 here for to check at clicking next button that any new value is given to it or not
        int no_of_periods_for_button_check = 1;

        //this if statement is for loading toast  when EditText views are left blank or given negative values
        if (ed_java.getText().toString().trim().length() <= 0) {
            Toast.makeText(WelcomeActivity.this, "enter some fookin number!", Toast.LENGTH_SHORT).show();
        } else
        //if legit values are entered then they are allowed to given to the object pf our custom class
        {
            no_of_periods = Integer.parseInt(ed_java.getText().toString());
            TextView tv3_zava = (TextView) findViewById(R.id.tv_xml);
            /////string.valueof code is implemented in below line because we want the textview object to show numeric or int value and this makes it possible
            tv3_zava.setText(String.valueOf(no_of_periods));

            //entering the legit values to our custom object
            no_of_periods_for_button_check = no_of_periods;
        }

        return no_of_periods_for_button_check;
    }


    //////////////////////////////////////////////////////////////----------------
    //this below code does nothing for now

    public void AddData() {

        Button butto_java = (Button) findViewById(R.id.butto);
        final TextView tv3_java = (TextView) findViewById(R.id.tv3_xml);
        butto_java.setOnClickListener(new View.OnClickListener() {
         public void onClick(View v) {

             myDb_2.create_new_database_table_dyanmic("dummy");
             final boolean[][] boo2 = new boolean[7][5];
             boolean inserting_dummy_data = myDb_2.insertData("1","p","p","p","p","p","a","a","dummy");
             int m, j;
             //for loop for reading all the values of periods elemnt  from the database table and setting the boo2 arraya s it is
             final Cursor res3 = myDb_2.getAllData("dummy");
             for (m = 0; m < 7; m++) {
                 for (j = 0; j < 5; j++) {
                     //below we pass the row no as a parameter ....it is one more than the j because row starts from 1
                     int r = j +1;
                     res3.move(r);
                     // values are true means each period element is disactive and have color white and vice versa
                     //below we pass the column no as a parameter to getstring as one more than j....because column start from 0 and the first column is of id not days
                     int a = m+1;
                     String str = res3.getString(a);
                     tv3_java.setText(String.valueOf(str));
                     if ( str.equals("p"))
                     {boo2[m][j] = false;}
                     else
                     {boo2[m][j] = true ;}
                 }

             }
             //int str = Integer.parseInt(res.getString(2));
           // tv3_java.setText(String.valueOf(str));

         }
        });

        //  Cursor yourdata = myDb.getDetails(this);
        // yourdata.moveToPosition(1);
    }


}
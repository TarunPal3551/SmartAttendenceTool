package com.example.android.testin3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.android.testin3.data.DatabaseHelper;

public class time_table_asking_activity extends AppCompatActivity {

    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_asking_);


        myDb = new DatabaseHelper(this);


        Button add_timetable_java = (Button) findViewById(R.id.add_timetable);
        add_timetable_java.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                load_the_dialog_for_asking_no_of_subjects();


            }
        });

        Button skip_button_java = (Button) findViewById(R.id.skip);
        skip_button_java.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //now changing the activity usinh intent
                Intent intent_3 = new Intent(time_table_asking_activity.this, EditorActivity.class);

                startActivity(intent_3);


            }
        });

    }

    private void load_the_dialog_for_asking_no_of_subjects() {

        // get prompts.xml view
        LayoutInflater li = LayoutInflater.from(time_table_asking_activity.this);
        View promptsView = li.inflate(R.layout.custom, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                time_table_asking_activity.this);

        // set prompts.xml to alertdialog builder
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        // set dialog message
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {

                                // get user input and set it in database

                                //giving default value for later to identify if the dialog was given some value or not
                                int no_of_subjects = -1;

                                 no_of_subjects = Integer.parseInt(String.valueOf(userInput.getText()));


                                if (no_of_subjects != -1);
                                {
                                    boolean inserting_no_of_subjects = myDb.insertData("3", "nu_of_subjects_", no_of_subjects);


                                    Intent take_Intent;
                                    take_Intent = new Intent(time_table_asking_activity.this, time_table_taking_Activity.class);
                                    startActivity(take_Intent);

                                }

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


}

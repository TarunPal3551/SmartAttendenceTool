package com.example.android.testin3;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.testin3.data.DatabaseHelper;
import com.example.android.testin3.data.DatabaseHelper_2;
import com.example.android.testin3.data.DatabaseHelper_3;

import java.sql.Struct;

public class time_table_taking_Activity extends AppCompatActivity {

    DatabaseHelper myDb;
    DatabaseHelper_3 myDb_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_taking_);

        myDb = new DatabaseHelper(this);
        myDb_3 = new DatabaseHelper_3(this);


        //0 means no or _false
        boolean inserting_status_of_subjects_as_default_no_or_false = myDb.insertData("4", "status_of_subjects_", 0);

//inserting the latest version for table of subjects as 0
        boolean inserting_latest_version = myDb.insertData("5", "latest_version_os_subjects_list", 0);




//now retriving the same varaiable for it will b needed as how many ediditext should get open o
        Cursor res = myDb.getAllData();
        res.move(2);
        final int  no_of_sub_2 = Integer.parseInt(res.getString(2));

          String[] sub_names = new String[no_of_sub_2];

        //now retriving the same varaiable inserted above for it will decide what should be inside textviews
        Cursor res2 = myDb.getAllData();
        res2.move(3);
        int  status_of_sub= Integer.parseInt(res2.getString(2));

if (status_of_sub == 0) {
//insert default values to text views
    int h;
    for (h=0;h<no_of_sub_2;h++){
    sub_names[h] = "subject" + h;
    }
}else{
    //here we will enter the name of subjects  which is already in entered
    //but first lets find out the name of the table which need to be opened
    Cursor res_ = myDb.getAllData();
    res_.move(4);
    int  latest_version_ = Integer.parseInt(res_.getString(2));
    latest_version_=latest_version_-1;

    int y;
    for (y=0;y<no_of_sub_2;y++) {
        Cursor res6 = myDb_3.getAllData("first_ever_table_made_for_subjects"+latest_version_);
        res6.move(y+2);
        String name_foe_sub = res6.getString(1);
        sub_names[y] = name_foe_sub;
        res6.close();

    }
}

        auto_edit_text_loader(sub_names);

//setting the functionality to add one more subject button
        Button add_sub_java = (Button) findViewById(R.id.butt_add_sub);
        add_sub_java.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                //now retriving the same varaiable for it will b needed as how many ediditext should get open o
                Cursor res = myDb.getAllData();
                res.move(2);
                 int  no_of_sub_4 = Integer.parseInt(res.getString(2));

                int n = no_of_sub_4+1;
                boolean updating_no_of_subjects = myDb.updateData("3", "nu_of_subjects_", n);

                //now retriving the same varaiable for it will b needed as how many ediditext should get open o
                Cursor res5 = myDb.getAllData();
                res5.move(2);
                final int  no_of_sub_3 = Integer.parseInt(res5.getString(2));
                int m = no_of_sub_3-1;
Add_Line(m ,"subject" + m);
            }});
//-------------------------------------------------------------------------------------------
        //setting the functionality to remove one more subject button
        Button remove_sub_java = (Button) findViewById(R.id.butt_remove_sub);
        remove_sub_java.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                //now retriving the same varaiable for it will b needed as how many ediditext should get open o
                Cursor res = myDb.getAllData();
                res.move(2);
                int  no_of_sub_4 = Integer.parseInt(res.getString(2));

                int n = no_of_sub_4-1;
                if (n > 2) {


                    boolean updating_no_of_subjects = myDb.updateData("3", "nu_of_subjects_", n);

                    remove_Line(n);
                }
            }});


 //--------------------------------------setting the functionality to OK NEXT button--------------------------------------


        Button OK_BUTTON_java = (Button) findViewById(R.id.butt_OK);
        OK_BUTTON_java.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                //-------------------------we will add all the data related to subjects in the databse specifically for subjects



                //now retriving the same varaiable for it will b needed as how many ediditext should get open o
                Cursor res = myDb.getAllData();
                res.move(2);
                int  no_of_sub_4 = Integer.parseInt(res.getString(2));

                //now saving the same data of no_of_subjects in another database also ...so remember its in the two places
                //and it is at the first row and its value will be stored in the thord column because that supports integer

                //but first create the table
                //and first create the name for it which is based on latest version of subjects list

                Cursor res__ = myDb.getAllData();
                res__.move(4);
                int  latest_version = Integer.parseInt(res__.getString(2));


//now inserting the other data only if the activity is opened for first time else the we will fookin update the data
                int i;

                //now retriving the same varaiable inserted above for it will decide what should be inside textviews
                Cursor res2 = myDb.getAllData();
                res2.move(3);
                int  status_of_sub= Integer.parseInt(res2.getString(2));

                if(status_of_sub == 0) {
                    myDb_3.create_new_database_table_dyanmic("first_ever_table_made_for_subjects"+latest_version);
                    boolean inserting_no_of_sub = myDb_3.insertData("1", "no_of_subjects",  no_of_sub_4, "first_ever_table_made_for_subjects"+latest_version);


                    for (i=0;i<no_of_sub_4;i++) {

                    String edit_ID = "" + (i);

                    int resID = getResources().getIdentifier(edit_ID, "id", getPackageName());

                    EditText et = (EditText) findViewById(resID);

                    String sub_name = String.valueOf(et.getText());

                    boolean inserting_sub_names = myDb_3.insertData("" + (i + 2), sub_name, 0, "first_ever_table_made_for_subjects"+latest_version);

                    }
                    //also updating the ;atest version variable
                    //0 means no or _false ...1 means true or yes
                    boolean updating_status_of_subjects_as_default_no_or_false = myDb.updateData("4", "status_of_subjects_", 1);

                    Cursor res_ = myDb.getAllData();
                    res_.move(4);
                    int  latest_version_ = Integer.parseInt(res_.getString(2));

                    latest_version_ = latest_version_+1;

                    boolean inserting_latest_version = myDb.updateData("5", "latest_version_of_subjects_list", latest_version_);

                }else{

                    Cursor res_8= myDb.getAllData();
                    res_8.move(4);
                    int  latest_version_8 = Integer.parseInt(res_8.getString(2));


                    myDb_3.create_new_database_table_dyanmic("first_ever_table_made_for_subjects"+(latest_version_8));
                    boolean inserting_no_of_sub = myDb_3.insertData("1", "no_of_subjects",  no_of_sub_4, "first_ever_table_made_for_subjects"+latest_version_8);


                    for (i=0;i<no_of_sub_4;i++) {


                        String edit_ID = "" + (i);

                        int resID = getResources().getIdentifier(edit_ID, "id", getPackageName());

                        EditText et = (EditText) findViewById(resID);

                        String sub_name = String.valueOf(et.getText());

                        boolean updating_sub_names = myDb_3.insertData("" + (i + 2), sub_name, 0, "first_ever_table_made_for_subjects"+latest_version_8);



                    }
res__.close();
//updating the latest version
                    Cursor res_ = myDb.getAllData();
                    res_.move(4);
                    int  latest_version_ = Integer.parseInt(res_.getString(2));

                    latest_version_ = latest_version_+1;

                    boolean inserting_latest_version = myDb.updateData("5", "latest_version_of_subjects_list", latest_version_);

                }

                //now changing the activity usinh intent
                Intent intent = new Intent(time_table_taking_Activity.this, time_table_taking_Activity_2.class);

                startActivity(intent);
            }});

    }

    private void auto_edit_text_loader(String[] sub_names) {

        //now retriving the same varaiable for it will b needed as how many ediditext should get open o
        Cursor res = myDb.getAllData();
        res.move(2);
        int  no_of_sub_2 = Integer.parseInt(res.getString(2));

        int i ;
        for (i = 0; i<no_of_sub_2; i++) {
            Add_Line(i, sub_names[i]);
        }


    }


     public void Add_Line(int i, String sub_name) {
        int numberOfLines=i;
    LinearLayout ll_java = (LinearLayout) findViewById(R.id.LL_IN_XML_TO_expand);
    // add edittext
    EditText et = new EditText(this);
    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            et.setLayoutParams(p);
            et.setText(sub_name);

     et.setId(numberOfLines);
            ll_java.addView(et);

     }

    private void remove_Line(  int m) {

        String edit_ID = "" +(m);

        int resID = getResources().getIdentifier(edit_ID, "id", getPackageName());

        EditText et = (EditText)findViewById(resID);

        LinearLayout ll_java = (LinearLayout) findViewById(R.id.LL_IN_XML_TO_expand);

        ll_java.removeView(et);


    }
/**
    private void load_the_activity_as_asking_name_of_subjects() {

        //------------------ to crete the edittext feilds inside the dialog--------------------//////////////////
        //now retriving the same varaiable inserted above for it will b needed as how many ediditext should get open on dialogbox
        Cursor res = myDb.getAllData();☺
        res.move(1);
        int  no_of_sub_2 = Integer.parseInt(res.getString(2));

        //java objects of textfeilds
        EditText[] userInput_ = new EditText[no_of_sub_2];

        //loop for setting DEFAULT TEXT
        int k;
        String str;
        for (k=0 ; k<no_of_sub_2 ; k++) {
            // str = "SUBJECT_" ;
           //  userInput_[k].setText(str);



        }

        //making java object of a linerlayout and linking it with the one alraedy in custom2 xml file
        LinearLayout ll_java = (LinearLayout) findViewById(R.id.LL_IN_XML_TO_expand);
        //adding alll the  edittexts in the above linearlayout using loop
        for (k=0 ; k<no_of_sub_2 ; k++) {

          //  LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //converting px units into dp ...because default unit for scale used is not dp
            //int common_padding_7dp = (int) (7 * scale + 0.5f);
            //int bottom_padding_35dp = (int) (35 * scale + 0.5f);
           // userInput_[k].setLayoutParams(layoutParams2);


           // ll_java.addView(userInput_[k]);
        }
        TextView.

        ll_java.addView(userInput_[0]);
    }

    **/
}

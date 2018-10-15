package com.example.android.testin3;

import android.app.AlertDialog;
import android.content.Context;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.testin3.data.DatabaseHelper;
import com.example.android.testin3.data.DatabaseHelper_2;
import com.example.android.testin3.data.DatabaseHelper_3;

import java.util.Arrays;

public class time_table_taking_Activity_2 extends AppCompatActivity {


    LinearLayout ll[][];
    TextView tv[][];

    //the purpose of below 2d array is to store  the subjects for the periods given and then from ther they are stord in database when the OK button od the ACTIVITY Is clicked
    String periods_2D_array_for_subjects[][];


    DatabaseHelper myDb;
    DatabaseHelper_2 myDb_2;

    boolean[] boooty = new boolean[7];

    int no_of_period1;

    DatabaseHelper_3 myDb_3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_taking__2);

        myDb = new DatabaseHelper(this);
        myDb_2 = new DatabaseHelper_2(this);
        myDb_3 = new DatabaseHelper_3(this);
//-----------------------------------------------------------
        //0 means no or _false
        boolean inserting_status_of_time_table_for_subjects_as_default_no_or_false = myDb.insertData("6", "status_of_time_table_for_subjects", 0);

//inserting the latest version for table of subjects as 0
        boolean inserting_latest_version = myDb.insertData("7", "latest_version_of_time_table_for_subjects", 0);


//------------------------------------------------------------------------------------------------------

        //now retriving the no_of_period  varaiable from database
        Cursor res = myDb.getAllData();
        //below we pass the row no as a parameter ....rows start from 1
        res.move(1);
        //below we pass the column no as a parameter ....column start from 0
        no_of_period1 = Integer.parseInt(res.getString(2));


        //NOW ADDING DEFAULT VALUES TO ALL THE PERIODS OF THE SUBJECT in the 2d array for the subjects
        periods_2D_array_for_subjects = new String[no_of_period1][7];
        for (int row = 0; row < no_of_period1; row++) {
            for (int col = 0; col < 7; col++) {
                periods_2D_array_for_subjects[row][col] = "no subject added";
            }
        }

//------------------------------------------------------------------------------------------------------------------------------------------------

        //now retriving the checkboxes  data from database and storing that in new int array which is essentially stored in new boolean array
        Cursor res2 = myDb.getAllData2();
        int ctrr[] = new int[7];
        int r, v = 0;
        //below we pass the row no as a parameter ....rows start from 1
        res2.move(1);
        //below we pass the column no as a parameter ....column start from 0
        ctrr[0] = Integer.parseInt(res2.getString(0));
        ctrr[1] = Integer.parseInt(res2.getString(1));
        ctrr[2] = Integer.parseInt(res2.getString(2));
        ctrr[3] = Integer.parseInt(res2.getString(3));
        ctrr[4] = Integer.parseInt(res2.getString(4));
        ctrr[5] = Integer.parseInt(res2.getString(5));
        ctrr[6] = Integer.parseInt(res2.getString(6));


        for (r = 0; r < 7; r++) {


            if (ctrr[r] == 1) {
                boooty[r] = true;
            } else {
                boooty[r] = false;
            }
        }


        adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, boooty);


        Button save_button_java = (Button) findViewById(R.id.save_xml);
        save_button_java.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                adding_the_subjects_to_the_database();

            }
        });

        Button skip_button_java = (Button) findViewById(R.id.SKIP__xml);
        skip_button_java.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //now changing the activity usinh intent
                Intent intent_3 = new Intent(time_table_taking_Activity_2.this, EditorActivity.class);

                startActivity(intent_3);


            }
        });

        Button edit_subjects_list_java = (Button) findViewById(R.id.edit_SUBJECTS_LIST);
        edit_subjects_list_java.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //now changing the activity usinh intent
                Intent intent_4 = new Intent(time_table_taking_Activity_2.this, time_table_taking_Activity.class);

                startActivity(intent_4);


            }
        });


    }


    public void adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(final int no_of_perod, final int no_of_days, boolean[] boooty) {


        //making arrays of both views
        final LinearLayout ll[][] = new LinearLayout[no_of_days][no_of_perod];
        final TextView tv[][] = new TextView[no_of_days][no_of_perod];

        final String periods_2D_array_for_subjects[][] = new String[no_of_days][no_of_perod];

        //////////////////////////////////////////////////////////////////////--------------------------------------------------------------------------
        //setting each of them with each other()
        int i, k;
        for (k = 0; k < no_of_days; k++) {
            for (i = 0; i < no_of_perod; i++) {

                ll[k][i] = new LinearLayout(this);
                tv[k][i] = new TextView(this);


                //converting px units into dp ...because default unit for scale used is not dp
                final float scale = getResources().getDisplayMetrics().density;
                int width_120dp = (int) (120 * scale + 0.5f);
                int height_60dp = (int) (60 * scale + 0.5f);
                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(width_120dp, height_60dp);
                // LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT );

                layoutParams3.setMargins(0, 12, 0, 0);
                ll[k][i].setLayoutParams(layoutParams3);
                ll[k][i].setBackgroundColor(Color.parseColor("#000000"));

                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

                //converting px units into dp ...because default unit for scale used is not dp
                int common_padding_7dp = (int) (7 * scale + 0.5f);
                int bottom_padding_35dp = (int) (35 * scale + 0.5f);
                layoutParams2.setMargins(common_padding_7dp, common_padding_7dp, common_padding_7dp, bottom_padding_35dp);
                tv[k][i].setLayoutParams(layoutParams2);
                tv[k][i].setBackgroundColor(Color.parseColor("#d500f9"));
                tv[k][i].setText("add a SUB");
                //adding the id to the all tv so that later text can be changed of them
                //adding th id in such a way that i get to use both cordinates and ssomehow the each time its unique
                tv[k][i].setId(1000 * k + i);


                ll[k][i].addView(tv[k][i]);

            }
        }


        LinearLayout ll_for_rows_java[] = new LinearLayout[no_of_days];


        for (k = 0; k < no_of_days; k++) {
            ll_for_rows_java[k] = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll_for_rows_java[k].setLayoutParams(layoutParams4);
            ll_for_rows_java[k].setOrientation(LinearLayout.HORIZONTAL);
            for (i = 0; i < no_of_perod; i++) {
                ll_for_rows_java[k].addView(ll[k][i]);
            }


        }


        LinearLayout ll_of_the_view_already_in_xml = (LinearLayout) findViewById(R.id.Lin_layout2_xml);
        for (k = 0; k < no_of_days; k++) {
            ll_of_the_view_already_in_xml.addView(ll_for_rows_java[k]);

        }


        /////////////////////////////////////////////////////////////////////////------------------------------------------------------

        //final Cursor res4 = myDb_2.getAllData(name_database_table_as_week_year);

///////-----------------------------------------------------------------setting the loading of the dialog box on click of period-----------------------------------------------------------------
        int m, j;
        for (m = 0; m < no_of_days; m++) {
            for (j = 0; j < no_of_perod; j++) {


                final int finalJ = j;
                final int finalM = m;


                ll[m][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        load_the_frame_layout_for_checking_subjects(finalM, finalJ);


                    }
                });


            }
        }
//----------------------------------------------------------------------------------------------------------------
        //aauto deactivating the days selected by the user


        final LinearLayout ll_2_java[] = new LinearLayout[7];

        ll_2_java[0] = (LinearLayout) findViewById(R.id.ll_7_xml);
        ll_2_java[1] = (LinearLayout) findViewById(R.id.ll_8_xml);
        ll_2_java[2] = (LinearLayout) findViewById(R.id.ll_9_xml);
        ll_2_java[3] = (LinearLayout) findViewById(R.id.ll_10_xml);
        ll_2_java[4] = (LinearLayout) findViewById(R.id.ll_11_xml);
        ll_2_java[5] = (LinearLayout) findViewById(R.id.ll_12_xml);
        ll_2_java[6] = (LinearLayout) findViewById(R.id.ll_13_xml);


        int r, v = 0;
        for (r = 0; r < 7; r++) {

            if (this.boooty[r] == true) {

                ll_2_java[r].setBackgroundColor(Color.GRAY);
                //diasbling all the periods of that perticular day
                int h;
                for (h = 0; h < no_of_perod; h++) {

                    tv[r][h].setBackgroundColor(Color.GRAY);

                    // //disabling the click listener on all the items of that day
                    ll[r][h].setClickable(false);
                }


            }


        }
    }

    private void load_the_frame_layout_for_checking_subjects(final int no_of_days_coordinate, final int no_of_perid_coordinate) {

//first of all we will open a dummy frame view as big as screen size which protects the original frame layout when click outside of it as its non responsive ....and also it will kind of give shade to it in the background


        final FrameLayout f_root = (FrameLayout) findViewById(R.id.framelayout_for_background_of_another_background);

        final TextView tv_for_shade =new TextView(this);

        LinearLayout.LayoutParams params33 = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        tv_for_shade.setLayoutParams(params33);
        tv_for_shade.setBackgroundColor(getColorWithAlpha(Color.BLACK, 0.7f));
        f_root.addView(tv_for_shade);

        tv_for_shade.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //do nothing
            }
        });



 //-----------------------------------------------------------------------------------------------
        final FrameLayout fl = (FrameLayout) findViewById(R.id.view_for_frame_layout_to_expand);


        LayoutInflater inflater = LayoutInflater.from(this);

        final View v = inflater.inflate(R.layout.custom_2, null);

        fl.addView(v);


///////////////////////////////-------------------------------------to add the checkboxes and subject names dynamicallly

        //always remmeber that when we need to add things dynamically  to a view then that view should be open ....or on the screen/set content...thats why i doing the dynamic part wfter i have openned the layout to ehich i want to add dynamically

        Cursor res = myDb.getAllData();
        res.move(2);
        final int no_of_sub_2 = Integer.parseInt(res.getString(2));

        final String[] sub_name_array = new String[no_of_sub_2];

        LinearLayout lin_layout_for_checkboxes = (LinearLayout) findViewById(R.id.ll_for_check_sub);


        TextView yv = new TextView(this);
        final Boolean[] cb_status = new Boolean[no_of_sub_2];
        //below code initialises all the elements of thhe array to faalse
        Arrays.fill(cb_status, false);
        int y;
        for (y = 0; y < no_of_sub_2; y++) {
            //here we will enter the name of subjects  which is already in entered
            //but first lets find out the name of the table which need to be opened
            Cursor res_ = myDb.getAllData();
            res_.move(4);
            int latest_version_ = Integer.parseInt(res_.getString(2));

              latest_version_ = latest_version_ - 1;

            Cursor res6 = myDb_3.getAllData("first_ever_table_made_for_subjects" + latest_version_);
            res6.move(y + 2);
            String name_foe_sub = res6.getString(1);


            LinearLayout ll_container_for_row = new LinearLayout(this);
            //always remember to not to give same id to views even if they are of difffernt objecys ....just like i was giving same id to a linearlayoutand a textviews
            ll_container_for_row.setId((100 + y));
            ll_container_for_row.setBackgroundColor(Color.GREEN);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params2.setMargins(10, 15, 10, 1);
            ll_container_for_row.setLayoutParams(params2);
            ll_container_for_row.setPadding(5, 5, 5, 5);
            ll_container_for_row.setOrientation(LinearLayout.HORIZONTAL);

            //-----------------now setting the checkboxes
            final CheckBox cb = new CheckBox(this);
            //below id is given as 300+y to make sure others id of other viwes wont match
            cb.setId((500 + y));
            LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params3.setMargins(10, 10, 10, 1);
            cb.setLayoutParams(params3);
            cb.setChecked(false);


            ll_container_for_row.addView(cb);

            //-----------------now setting the textvies
            final TextView tv_for_sub_name = new TextView(this);
            //below id is given as 300+y to make sure others id of other viwes wont match
            tv_for_sub_name.setId((300 + y));

            sub_name_array[y] = name_foe_sub;

            tv_for_sub_name.setText(name_foe_sub);
            tv_for_sub_name.setTextSize(22);
            tv_for_sub_name.setBackgroundColor(Color.CYAN);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 10, 10, 1);
            tv_for_sub_name.setLayoutParams(params);

            ll_container_for_row.addView(tv_for_sub_name);

            lin_layout_for_checkboxes.addView(ll_container_for_row);

//------------------------------------------------------------container row functioning
            final int finalY = y;
            cb.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {

                    Boolean is_cb_checked = cb.isChecked();
                    if (cb_status[finalY] == false) {
                        cb.setChecked(true);
                        cb_status[finalY] = true;
                        tv_for_sub_name.setBackgroundColor(Color.RED);
                    } else {
                        cb.setChecked(false);
                        tv_for_sub_name.setBackgroundColor(Color.CYAN);
                        cb_status[finalY] = false;

                    }
                }
            });


            res6.close();
        }

//-----------------------------------------------OK BUTTON functionality
        Button ok_button_java = (Button) findViewById(R.id.ok_xml);
        ok_button_java.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
//count keeps the count of if more than one subjects have been marked orr not
                int s;
                int count = 0;
                for (s = 0; s < no_of_sub_2; s++) {
                    if (cb_status[s] == true) {
                        count++;
                    }
                }
                if (count > 1 || count == 0) {
                    Toast.makeText(time_table_taking_Activity_2.this, "select only one subject please",
                            Toast.LENGTH_LONG).show();
                } else {
                    int checked_sub_position = 0, f;

                    for (f = 0; f < no_of_sub_2; f++) {
                        if (cb_status[f] == true) {
                            checked_sub_position = f;
                        }
                    }

                    fl.removeView(v);
                    f_root.removeView(tv_for_shade);

                    save_the_sub_in_ARRAY(no_of_days_coordinate, no_of_perid_coordinate, checked_sub_position, sub_name_array, no_of_period1, no_of_sub_2);


                }

            }
        });

//-----------------------------------------------CANCEL BUTTON functionality
        Button cancel_button_java = (Button) findViewById(R.id.CANCEL_xml);
        cancel_button_java.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                fl.removeView(v);
f_root.removeView(tv_for_shade);

            }
        });


    }

    //-----------------------------------------------------------------------------------------------------------------------------------------------------------
    private void save_the_sub_in_ARRAY(int no_of_days_coordinate, int no_of_perid_coordinate, int the_pos__of_selected_sub, String[] sub_name_array, int total_no_of_periods, int total_no_of_subjects) {


        //SETTING THE SUBJECT NAME TO THAT PERTICULAR PERIOD
        String sub_name = sub_name_array[the_pos__of_selected_sub];
        TextView tv_____ = (TextView) findViewById(1000 * no_of_days_coordinate + no_of_perid_coordinate);
        tv_____.setText(sub_name);


//now updating the periods whose subjects have been added
        periods_2D_array_for_subjects[no_of_perid_coordinate][no_of_days_coordinate] = sub_name;


    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void adding_the_subjects_to_the_database() {

        //but first create the table
        //and first create the name for it which is based on latest version of time table for subjects

        Cursor res__ = myDb.getAllData();
        res__.move(6);
        int latest_version = Integer.parseInt(res__.getString(2));


//now inserting the other data only if the activity is opened for first time else the we will fookin update the data
        int i;

        //now retriving the same varaiable inserted above for it will decide what should be inside textviews
        Cursor res2 = myDb.getAllData();
        res2.move(5);
        int status_of_time_table = Integer.parseInt(res2.getString(2));

        if (status_of_time_table == 0) {
            myDb_3.create_SUBJECTS_GIVEN_TO_PERIODS_TABLE_dynamic("first_ever_table_made_for_time_table_data" + latest_version);

            String sub_name[] = new String[7];
//now inserting all the array data in the table
            for (int row = 0; row < no_of_period1; row++) {
                for (int col = 0; col < 7; col++) {
                    sub_name[col] = periods_2D_array_for_subjects[row][col];
                }
                myDb_3.insertData_2("" + row, sub_name[0], sub_name[1], sub_name[2], sub_name[3], sub_name[4], sub_name[5], sub_name[6], "first_ever_table_made_for_time_table_data" + latest_version);
            }


            //also updating the status and  latest version variable
            //0 means no or _false ...1 means true or yes
            boolean updating_status_of_time_table_as_default_no_or_false = myDb.updateData("6", "status_of_time_table_for_subjects", 1);

            Cursor res_ = myDb.getAllData();
            res_.move(6);
            int latest_version_ = Integer.parseInt(res_.getString(2));

            latest_version_ = latest_version_ + 1;

            boolean inserting_latest_version = myDb.updateData("7", "latest_version_of_time_table_for_subjects", latest_version_);

        } else {


            Cursor res_8 = myDb.getAllData();
            res_8.move(6);
            int latest_version_8 = Integer.parseInt(res_8.getString(2));

            myDb_3.create_SUBJECTS_GIVEN_TO_PERIODS_TABLE_dynamic("first_ever_table_made_for_time_table_data" + latest_version_8);

            String sub_name[] = new String[7];
//now inserting all the array data in the table
            for (int row = 0; row < no_of_period1; row++) {
                for (int col = 0; col < 7; col++) {
                    sub_name[col] = periods_2D_array_for_subjects[row][col];
                }
                  myDb_3.insertData_2("" + row, sub_name[0], sub_name[1], sub_name[2], sub_name[3], sub_name[4], sub_name[5], sub_name[6], "first_ever_table_made_for_time_table_data" + latest_version_8);

            }



            Cursor res_ = myDb.getAllData();
            res_.move(6);
            int latest_version_ = Integer.parseInt(res_.getString(2));

            latest_version_ = latest_version_ + 1;

            boolean inserting_latest_version = myDb.updateData("7", "latest_version_of_time_table_for_subjects", latest_version_);
        }

            //now changing the activity usinh intent
            Intent intent = new Intent(time_table_taking_Activity_2.this, EditorActivity.class);

            startActivity(intent);

    }


//--------------------------------------------------------------------------------------------------------------------------------
    //method for calculation of color schemem.....not to important
    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }
}

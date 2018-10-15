package com.example.android.testin3;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.testin3.data.DatabaseHelper;
import com.example.android.testin3.data.DatabaseHelper_2;
import com.example.android.testin3.data.DatabaseHelper_3;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditorActivity extends AppCompatActivity {

    LinearLayout ll[][];
    TextView tv[][];


    DatabaseHelper myDb;
    DatabaseHelper_2 myDb_2;
    DatabaseHelper_3 myDb_3;
    boolean[] boooty = new boolean[7];

    int week_no_of_year ;
    int year_of_the_week;
    int no_of_period1  ;
    int  current_week_no_of_year;
    int week_no_of_the_month;
    String current_month;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        //-------------------------------testin
        //intent for percent analyse activity
        setUpbottomNavigationBar();
        changeStatusBarColor("#7B1FA2");

        //----------------------------------------------------------------------------------------

        final TextView tv_for_week_name_and_year_textview = (TextView) findViewById(R.id.week_no_xml);
  //      tv_for_week_name_and_year_textview.setBackgroundResource();

        myDb = new DatabaseHelper(this);
        myDb_2 = new DatabaseHelper_2(this);
        myDb_3 = new DatabaseHelper_3(this);

        //now retriving the no_of_period  varaiable from database
        Cursor res = myDb.getAllData();
        //below we pass the row no as a parameter ....rows start from 1
        res.move(1);
        //below we pass the column no as a parameter ....column start from 0
        no_of_period1 = Integer.parseInt(res.getString(2));



        ///////////////////////////////////////////////////----------------------------------------------

        /** /////testing if this activity have recieved the no_of_period  variable or not
         TextView tv_java2 = (TextView) findViewById(R.id.tv_xml2);
         /////string.valueof code is implemented in below line because we want the textview object to show numeric or int value and this makes it possible
         tv_java2.setText(String.valueOf(no_of_period1));
         **/

        //----------------------------------------------------------------------------------------------------------------------------------------------
        //computation for calender related data

        //1st step -to get the current date
        //for which we make first make instance of calender
        //we also make date format according we need in below line
        SimpleDateFormat dateFormat = new SimpleDateFormat(" dd.MM.yyyy");
        final Calendar currentCal = Calendar.getInstance();
        String currentdate = dateFormat.format(currentCal.getTime());//get time means get the cuurent calender data of time date ..etc, since we have specified dd.mm.yy only in date format we will get that only ,and accordingly
//storing year ,month and date in variables
        //because we need to paas that to a method to get the week no
        final int year = currentCal.get(Calendar.YEAR);
        final int month = currentCal.get(Calendar.MONTH);
        final int date = currentCal.get(Calendar.DATE);

//2nd step- get the current week number of the year

//below code can find out the week no of the any date passed to it ...right now i have passed the present date so that later all the dates of week can be found and assigned
        //making new calender instance for this purpose and passing current date
        final Calendar now = Calendar.getInstance();
        now.set(Calendar.YEAR, year);
        now.set(Calendar.MONTH, month);
        now.set(Calendar.DATE, date);

        //  present week no which dosnt changes to the entire activity...and its used for comparison with prev or next
        current_week_no_of_year = now.get(Calendar.WEEK_OF_YEAR);
        //this below week no keep changing as the weeks are switched to prev or next
        week_no_of_year = now.get(Calendar.WEEK_OF_YEAR);
        // passing the year as the current year now
        year_of_the_week = year;
        week_no_of_the_month = now.get(Calendar.WEEK_OF_MONTH);

        //for getting the month name a sstring
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
         current_month = month_date.format(now.getTime());


        //creating a name for the database so that we can made a table for that week and store and access data related to that perticular week
        String name_database_table_as_week_year = "weekNO" + week_no_of_year + "_" + "year" + year_of_the_week;
//setting that name in the top middle textview
    String    text_for_tv_on_the_top = "Week No:- "+ week_no_of_year + "(of " + year_of_the_week +") and " + week_no_of_the_month + "(of " + current_month +")";
        tv_for_week_name_and_year_textview.setText(text_for_tv_on_the_top);







        //-------------------------------------------------------------------------------------------------------------------------------------------------
        boooty = day_column_pre_functioning(no_of_period1 , name_database_table_as_week_year , false);

        //now the loading of period elements and behavior to date/day column is handeled by a single method
        final TextView tv2[][]= adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, boooty, name_database_table_as_week_year , false ,true,week_no_of_year, year_of_the_week);

        // adding_days_and_date_to_column_of_days( week_no_of_year, year_of_the_week,name_database_table_as_week_year);

        //setting_the_toggle_behavior  to top most textview for displaying wwekno
        setting_toogle_to_week_no_textview(tv_for_week_name_and_year_textview , name_database_table_as_week_year , no_of_period1 , false);


        setting_subjects_to_periods_as_per_time_table(no_of_period1, 7, name_database_table_as_week_year,tv2 );

//////////////////////////////////////////////////////////////////////-----------------------------------------------------
        final ImageView prev_button_java = (ImageView) findViewById(R.id.previous_button);

        prev_button_java.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {



                int check_prev_week_no_of_year = week_no_of_year - 1;
                if (check_prev_week_no_of_year > 0)
                {
                    //to remove the background color blue from the previous button if it had any
                    final LinearLayout color_changing_bckgrnd_for_next_button = (LinearLayout) findViewById(R.id.this_aint_container_just_color_changing_backgrnd_for_next);
                    color_changing_bckgrnd_for_next_button.setBackgroundResource(R.drawable.green_circle_for_top_bar_and_prev_next_buttons);


                    //clearing the previus periods from the parent view alraedy present...if its not done new periods will apear along with the already ones
                    LinearLayout ll_of_the_view_already_in_xml = (LinearLayout) findViewById(R.id.Lin_layout2_xml);
                    ll_of_the_view_already_in_xml.removeAllViews();

                    //same for days date container
                    //clearing the previus periods from the parent view alraedy present...if its not done new periods will apear along with the already ones
                    LinearLayout ll_of_the_view_already_in_xmlfor_days_sate = (LinearLayout) findViewById(R.id.ll_5_xml);
                    ll_of_the_view_already_in_xmlfor_days_sate.removeAllViews();


                    week_no_of_year = week_no_of_year - 1;
                    String  prev_name_database_table_as_week_year = "weekNO" + week_no_of_year + "_" + "year" + year_of_the_week;

                    final Calendar prev = Calendar.getInstance();
        prev.set(Calendar.WEEK_OF_YEAR, week_no_of_year);

                week_no_of_the_month = prev.get(Calendar.WEEK_OF_MONTH);

        //for getting the month name a sstring
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
         current_month = month_date.format(prev.getTime());

                  String    text_for_tv_on_the_top = "Week No:- "+ week_no_of_year + "(of " + year_of_the_week +") and " + week_no_of_the_month + "(of " + current_month +")";
        tv_for_week_name_and_year_textview.setText(text_for_tv_on_the_top);


                    //if the current week is disactivated(then it will have framelayout)...the problem is that they dont get removed when weeks are switched  and then if we switch to prev week which is activated will ahve a frame layout even when it shouldnt
                    final FrameLayout frame_layot = (FrameLayout) findViewById(R.id.layout_for_transculrnt_frame_layout);
                    final TextView tv_for_frame_layout = (TextView)findViewById(R.id.tv_for_graying_frame_lay);
                    int child_count =  frame_layot.getChildCount();
                    if (child_count == 2){
                        frame_layot.removeView(tv_for_frame_layout);
                    }



// is it prev week for below method tells the method to give the default values for all the periods as holiday
                    boooty = day_column_pre_functioning(no_of_period1 , prev_name_database_table_as_week_year , true);

                    //now the loading of period elements and behavior to date/day column is handeled by a single method
                    //is it prev week let the method know to not to apply the auto deactivating the days not happened in week because this is past week mens obiviously all the days of it have actually happened
                    TextView tv2[][]=  adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, boooty, prev_name_database_table_as_week_year ,true , false,week_no_of_year, year_of_the_week);

                    //    adding_days_and_date_to_column_of_days(week_no_of_year, year_of_the_week, prev_name_database_table_as_week_year);

                    //setting_the_toggle_behavior  to top most textview for displaying wwekno
                    setting_toogle_to_week_no_textview(tv_for_week_name_and_year_textview , prev_name_database_table_as_week_year , no_of_period1 ,true);

                    setting_subjects_to_periods_as_per_time_table(no_of_period1, 7, prev_name_database_table_as_week_year,tv2 );


                    if(week_no_of_year ==1) {
                        //we want next button to be faded and non clickable when its in the first week of the year.....so

                        final LinearLayout color_changing_bckgrnd_for_prev_button = (LinearLayout) findViewById(R.id.this_aint_container_just_color_changing_backgrnd_for_prev);
                        color_changing_bckgrnd_for_prev_button.setBackgroundResource(R.drawable.blue_circle_for_next_button  );


                    }

                    Toast.makeText(EditorActivity.this, "previous week editer opened",
                            Toast.LENGTH_SHORT).show();



                }
                else{

                    //do nothing



                    Toast.makeText(EditorActivity.this, "attendence dashboard for weeks of  previous year can not be loaded",
                            Toast.LENGTH_SHORT).show();


                }


            }
        });
/////////////////////////////////////////////////////////////////////////////////////////--------------------------------------------------------------------------------------------
        final ImageView next_button_java = (ImageView) findViewById(R.id.next_button);

        next_button_java.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //to remove the background color blue from the previous button if it had any
//                prev_button_java.setBackgroundColor(Color.RED);
                final LinearLayout color_changing_bckgrnd_for_prev_button = (LinearLayout) findViewById(R.id.this_aint_container_just_color_changing_backgrnd_for_prev);
                color_changing_bckgrnd_for_prev_button.setBackgroundResource(R.drawable.green_circle_for_top_bar_and_prev_next_buttons);




                //below if else is for the purpose that next button is functioning only when the next week is not a future week
                if (week_no_of_year != current_week_no_of_year) {


                    //clearing the previus periods from the parent view alraedy present...if its not done new periods will apear along with the already ones
                    LinearLayout ll_of_the_view_already_in_xml = (LinearLayout) findViewById(R.id.Lin_layout2_xml);
                    ll_of_the_view_already_in_xml.removeAllViews();
                    //same for days date container
                    //clearing the previus periods from the parent view alraedy present...if its not done new periods will apear along with the already ones
                    LinearLayout ll_of_the_view_already_in_xmlfor_days_sate = (LinearLayout) findViewById(R.id.ll_5_xml);
                    ll_of_the_view_already_in_xmlfor_days_sate.removeAllViews();


                    int check_next_week_no_of_year = week_no_of_year + 1;

                    // getting the last week no of the year and then comparing it with the next week(above variable)
                    Calendar next = Calendar.getInstance();
                    next.set(Calendar.YEAR, year);
                    next.set(Calendar.MONTH, Calendar.DECEMBER);
                    next.set(Calendar.DATE, 28);
                    int last_week_no_of_current_year = next.get(Calendar.WEEK_OF_YEAR);

                    if (check_next_week_no_of_year != last_week_no_of_current_year) {


                        week_no_of_year = week_no_of_year + 1;
                        String next_name_database_table_as_week_year = "weekNO" + week_no_of_year + "_" + "year" + year_of_the_week;


                    final Calendar next2 = Calendar.getInstance();
        next2.set(Calendar.WEEK_OF_YEAR, week_no_of_year);

                week_no_of_the_month = next2.get(Calendar.WEEK_OF_MONTH);

        //for getting the month name a sstring
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
         current_month = month_date.format(next2.getTime());

                  String    text_for_tv_on_the_top = "Week No:- "+ week_no_of_year + "(of " + year_of_the_week +") and " + week_no_of_the_month + "(of " + current_month +")";
        tv_for_week_name_and_year_textview.setText(text_for_tv_on_the_top);



                        //if the current week is disactivated(then it will have framelayout)...the problem is that they dont get removed when weeks are switched  and then if we switch to prev week which is activated will ahve a frame layout even when it shouldnt
                        final FrameLayout frame_layot = (FrameLayout) findViewById(R.id.layout_for_transculrnt_frame_layout);
                        final TextView tv_for_frame_layout = (TextView)findViewById(R.id.tv_for_graying_frame_lay);
                        int child_count =  frame_layot.getChildCount();
                        if (child_count == 2){
                            frame_layot.removeView(tv_for_frame_layout);
                        }


// is it prev week for below method tells the method to give the default values for all the periods as holiday
                        boooty = day_column_pre_functioning(no_of_period1, next_name_database_table_as_week_year, false);

                        // if the next week is the current week the below method wont gray all the days which has nt occured according to it ...basically its a jugaad
                        if(current_week_no_of_year == week_no_of_year) {

                            //now the loading of period elements and behavior to date/day column is handeled by a single method
                            //is it prev week let the method know to not to apply the auto deactivating the days not happened in week because this is past week mens obiviously all the days of it have actually happened
                            TextView tv2[][]=   adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, boooty, next_name_database_table_as_week_year, false, true,week_no_of_year, year_of_the_week);

                            //     adding_days_and_date_to_column_of_days(week_no_of_year, year_of_the_week, next_name_database_table_as_week_year);

                            //setting_the_toggle_behavior  to top most textview for displaying wwekno
                            setting_toogle_to_week_no_textview(tv_for_week_name_and_year_textview, next_name_database_table_as_week_year, no_of_period1, false);

                            setting_subjects_to_periods_as_per_time_table(no_of_period1, 7, next_name_database_table_as_week_year,tv2 );

                        } else{

                            TextView tv2[][]=      adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, boooty, next_name_database_table_as_week_year, false , false,week_no_of_year, year_of_the_week);

                            //     adding_days_and_date_to_column_of_days(week_no_of_year, year_of_the_week, next_name_database_table_as_week_year);

                            //setting_the_toggle_behavior  to top most textview for displaying wwekno
                            setting_toogle_to_week_no_textview(tv_for_week_name_and_year_textview, next_name_database_table_as_week_year, no_of_period1, false);

                            setting_subjects_to_periods_as_per_time_table(no_of_period1, 7, next_name_database_table_as_week_year,tv2 );
                        }



                    } else {

                        week_no_of_year = 1;
                        // passing the year as the current year now
                        year_of_the_week = year + 1;

                        String next_name_database_table_as_week_year = "weekNO" + week_no_of_year + "_" + "year" + year_of_the_week;

                        tv_for_week_name_and_year_textview.setText(next_name_database_table_as_week_year);


                        //if the current week is disactivated(then it will have framelayout)...the problem is that they dont get removed when weeks are switched  and then if we switch to prev week which is activated will ahve a frame layout even when it shouldnt
                        //if the current week is disactivated(then it will have framelayout)...the problem is that they dont get removed when weeks are switched  and then if we switch to prev week which is activated will ahve a frame layout even when it shouldnt
                        final FrameLayout frame_layot = (FrameLayout) findViewById(R.id.layout_for_transculrnt_frame_layout);
                        final TextView tv_for_frame_layout = (TextView)findViewById(R.id.tv_for_graying_frame_lay);
                        int child_count =  frame_layot.getChildCount();
                        if (child_count == 2){
                            frame_layot.removeView(tv_for_frame_layout);
                        }

// is it prev week for below method tells the method to give the default values for all the periods as holiday
                        boooty = day_column_pre_functioning(no_of_period1, next_name_database_table_as_week_year, false);

                        //now the loading of period elements and behavior to date/day column is handeled by a single method
                        //is it prev week let the method know to not to apply the auto deactivating the days not happened in week because this is past week mens obiviously all the days of it have actually happened
                        TextView tv2[][]=  adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, boooty, next_name_database_table_as_week_year, false ,false,week_no_of_year, year_of_the_week);

                        //                adding_days_and_date_to_column_of_days(week_no_of_year, year_of_the_week, next_name_database_table_as_week_year);

                        //setting_the_toggle_behavior  to top most textview for displaying weekno
                        setting_toogle_to_week_no_textview(tv_for_week_name_and_year_textview, next_name_database_table_as_week_year, no_of_period1, false);

                        setting_subjects_to_periods_as_per_time_table(no_of_period1, 7, next_name_database_table_as_week_year,tv2 );


                    }
                    if(current_week_no_of_year == week_no_of_year) {
                        //we want next button to be faded and non clickable when its in the current week.....so
                        //  setting its color as weell as al opaquecity or transparency

                   /*     next_button_java.setBackgroundColor(getColorWithAlpha(Color.BLUE, 0.5f));*/
                        final LinearLayout color_changing_bckgrnd_for_next_button = (LinearLayout) findViewById(R.id.this_aint_container_just_color_changing_backgrnd_for_next);
                        color_changing_bckgrnd_for_next_button.setBackgroundResource(R.drawable.blue_circle_for_next_button  );



                    }

                    Toast.makeText(EditorActivity.this, "next week editer opened",
                            Toast.LENGTH_SHORT).show();


                }
                else{
                    //do nothing


                    Toast.makeText(EditorActivity.this, "next week hasnt occured yet",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


        if(current_week_no_of_year == week_no_of_year) {
            //we want next button to be faded and non clickable when its in the current week.....so
            //  setting its color as weell as al opaquecity or transparency
//            next_button_java.setBackgroundColor(getColorWithAlpha(Color.BLUE, 0.5f));

            final LinearLayout color_changing_bckgrnd_for_next_button = (LinearLayout) findViewById(R.id.this_aint_container_just_color_changing_backgrnd_for_next);
            color_changing_bckgrnd_for_next_button.setBackgroundResource(R.drawable.blue_circle_for_next_button  );




        }
    }
    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
    ////-----------------------------------------BottomNavigationBar--------------------------------------
    private void setUpbottomNavigationBar(){
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation_editor_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        //bottomNavigationView.getItemBackgroundResource(R.color.colorAccent);

                        Intent intent=new Intent(EditorActivity.this,EditorActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.menu_analyse:
                        Intent intent1=new Intent(EditorActivity.this,attendence_analyse_activity.class);
                        startActivity(intent1);
                        return true;

                    case R.id.menu_setting:
                        Intent intent2=new Intent(EditorActivity.this,SettingsActivity.class);
                        startActivity(intent2);
                        return true;

                    case R.id.attendencepredecting:
                        Intent intent3=new Intent(EditorActivity.this,attendence_predicting_activity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.attendencewebview:
                        Intent intent4=new Intent(EditorActivity.this,web_view.class);
                        startActivity(intent4);
                        return true;



                    default:
                        return false;

                }


            }
        });
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        //bottomNavigationView.getItemBackgroundResource(R.color.colorAccent);

                        Intent intent=new Intent(EditorActivity.this,EditorActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_analyse:
                        Intent intent1=new Intent(EditorActivity.this,attendence_analyse_activity.class);
                        startActivity(intent1);
                        break;
                    case R.id.menu_setting:
                        Intent intent2=new Intent(EditorActivity.this,SettingsActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.attendencepredecting:
                        Intent intent3=new Intent(EditorActivity.this,attendence_predicting_activity.class);
                        startActivity(intent3);
                        break;
                    case R.id.attendencewebview:
                        Intent intent4=new Intent(EditorActivity.this,web_view.class);
                        startActivity(intent4);
                        break;


                    default:
                        Toast.makeText(EditorActivity.this,"Something Wrong",Toast.LENGTH_SHORT).show();
                }

            }
        });
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(0);
        menuItem.setChecked(true);



    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    private void setting_toogle_to_week_no_textview(final TextView tv_for_week_name_and_year_textview, final String prev_name_database_table_as_week_year, final int no_of_period1 , Boolean is_it_prev_week) {


 //-----------------preapring the image view for indicater
        final ImageView iv = (ImageView)findViewById(R.id.circle_iv_of_top_bar);
        iv.setBackgroundResource(R.drawable.green_circle_for_top_bar_and_prev_next_buttons);


//-----------------------------------------------------------------


        //making instance of a frame layout ..which is spefically used to overlap over other views
        final FrameLayout frame_layot_container = (FrameLayout)findViewById(R.id.layout_for_transculrnt_frame_layout) ;


/*
//        frame_layot.setId(R.id.frame_lauout_ID);

        final RelativeLayout rel_layot_of_xml_for_frame_to_expand = (RelativeLayout)findViewById(R.id.container_for_frame_layout_working);
*/

        final TextView tv_for_frame_layout = new TextView(this);
        tv_for_frame_layout.setId(R.id.tv_for_graying_frame_lay);
        RelativeLayout.LayoutParams lp_66 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT ,RelativeLayout.LayoutParams.MATCH_PARENT);

        tv_for_frame_layout.setLayoutParams(lp_66);
        tv_for_frame_layout.setBackgroundColor(Color.parseColor("#999E9E9E"));


//---------------------------------------------
//inserting all values for week_active status row as NULL  so later specific column can be updated
        boolean inserting_week_ative_status_in_db  = myDb_2.insertData(String.valueOf( no_of_period1 + 2), "NULL","NULL","NULL","NULL","NULL","NULL","NULL", prev_name_database_table_as_week_year);

        //retriving the status of week from database
        Cursor res = myDb_2.getAllData(prev_name_database_table_as_week_year);
        //below we pass the row no as a parameter ....rows start from 1 ,but our
        res.move(no_of_period1 + 2);
        //below we pass the column no as a parameter ....column start from 0 but we id column ata that position
        String if_week_is_active = res.getString(1);
        //below if else only works wen the that perticular week is opened for the firset time
        if (if_week_is_active.equals("NULL")) {

            if (is_it_prev_week == false) {
                tv_for_week_name_and_year_textview.setBackgroundColor(Color.WHITE);
                iv.setBackgroundResource(R.drawable.green_circle_for_top_bar_and_prev_next_buttons);
                //inserting default week_ative_status_in_db in below lines
                //row is 2 more than total period because we have a already a row for other variable after total no of periods
                //remember i am passing column no as 1 only nor 1+1 because my database 2 method od updating perticular data does that work itself ...so it will be inserted to monday column
                boolean updating_week_ative_status_in_db = myDb_2.updateData_perticular_column(String.valueOf(no_of_period1 + 2), 0, "WEEK_ACTIVATED", prev_name_database_table_as_week_year);
            } else {

                boolean updating_week_ative_status_in_db = myDb_2.updateData_perticular_column(String.valueOf(no_of_period1 + 2), 0, "WEEK_DISACTIVATED", prev_name_database_table_as_week_year);

                tv_for_week_name_and_year_textview.setBackgroundColor(Color.parseColor("#999E9E9E"));
                //here my circluar view has a circular shape so with defaultcolor green so i am just chnging the background rsource ...inted of changing backgrnd color
                iv.setBackgroundResource(R.drawable.gray_circle_for_top_bar);

                //this below click listener is to create no response to the background views on clicks to this frame
                tv_for_frame_layout.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("Range")
                    public void onClick(View view) {
                        //do nothing
                    }
                });


                frame_layot_container.addView(tv_for_frame_layout);
            }
        }else {
//if the present week is opened and and it was marked diaactivared  then it need to be disabled visually
            //if it was activated then no need to do anything ...
            if(if_week_is_active.equals("WEEK_DISACTIVATED")) {
                tv_for_week_name_and_year_textview.setBackgroundColor(Color.parseColor("#999E9E9E"));
                    //here my circluar view has a circular shape so with defaultcolor green so i am just chnging the background rsource ...inted of changing backgrnd color
                iv.setBackgroundResource(R.drawable.gray_circle_for_top_bar);

              //this below click listener is to create no response to the background views on clicks to this frame
                tv_for_frame_layout.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("Range")
                    public void onClick(View view) {
                        //do nothing

                        Toast.makeText(EditorActivity.this,   "week is deactivated, activate the week first by clicking on the top bar ",
                                Toast.LENGTH_SHORT).show();
                    }
                });


                frame_layot_container.addView(tv_for_frame_layout);
            }else{
                  tv_for_week_name_and_year_textview.setBackgroundColor(Color.WHITE);
}
        }

        tv_for_week_name_and_year_textview.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("Range")
            public void onClick(View view) {

//retriving the status of week from database
                Cursor res = myDb_2.getAllData(prev_name_database_table_as_week_year);
                //below we pass the row no as a parameter ....rows start from 1 ,but our
                res.move(no_of_period1 + 2);
                //below we pass the column no as a parameter ....column start from 0 but we id column ata that position
                String if_week_is_active = res.getString(1);
                //below if else works as a toggle....it makes view work as a toggle switch
                //disable
                if (if_week_is_active.equals("WEEK_ACTIVATED") ) {
                    tv_for_week_name_and_year_textview.setBackgroundColor(Color.parseColor("#999E9E9E"));
                    //here my circluar view has a circular shape so with defaultcolor green so i am just chnging the background rsource ...inted of changing backgrnd color
                    iv.setBackgroundResource(R.drawable.gray_circle_for_top_bar);


                    //remember i am passing column no as r only nor r+1 because my database 2 method od updating perticular data does that work itself
                    boolean inserting_week_ative_status_in_db = myDb_2.updateData_perticular_column(String.valueOf(no_of_period1 + 2), 0, "WEEK_DISACTIVATED", prev_name_database_table_as_week_year);



//this below click listener is to create no response to the background views on clicks to this frame
                    tv_for_frame_layout.setOnClickListener(new View.OnClickListener() {
                        @SuppressLint("Range")
                        public void onClick(View view) {
//do nothing

                            Toast.makeText(EditorActivity.this,   "week is deactivated, activate the week first by clicking on the top bar ",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });


                    frame_layot_container.addView(tv_for_frame_layout);


                    Toast.makeText(EditorActivity.this,   "week is deactivated",
                            Toast.LENGTH_SHORT).show();

                }
                else
                //ACTIVE
                {
                    tv_for_week_name_and_year_textview.setBackgroundColor(Color.WHITE);
                    iv.setBackgroundResource(R.drawable.green_circle_for_top_bar_and_prev_next_buttons);

                    //remember i am passing column no as r only nor r+1 because my database 2 method od updating perticular data does that work itself
                    boolean inserting_week_ative_status_in_db  = myDb_2.updateData_perticular_column(String.valueOf( no_of_period1 + 2), 0 , "WEEK_ACTIVATED", prev_name_database_table_as_week_year);

                    frame_layot_container.removeView(tv_for_frame_layout);

                    Toast.makeText(EditorActivity.this,   "week is activated",
                            Toast.LENGTH_SHORT).show();

                }

            }
        });

        //setting touch listeners so that I can give touch effect on the weekno textview when pressed
        tv_for_week_name_and_year_textview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    tv_for_week_name_and_year_textview.setBackgroundColor(Color.parseColor("#4D7B1FA2"));
                    return false;
                }else if (event.getAction() == MotionEvent.ACTION_UP|| (event.getAction() == MotionEvent.ACTION_CANCEL)){
                    tv_for_week_name_and_year_textview.setBackgroundColor(Color.WHITE);

                    return false;
                }
                return false;
            }
        });


        return ;
    }



    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }



    private boolean[] day_column_pre_functioning(int no_of_period1 , String name_database_table_as_week_year_reference2 , Boolean is_it_prev_week){
        // checking if the table of above name exists in database or not?...if not that means this activity is opened  for the first timr so make the changes
        if (myDb_2.isTableExists(name_database_table_as_week_year_reference2, true)) {//do nothing
        } else {
            //creating a database table for that same name
            myDb_2.create_new_database_table_dyanmic(name_database_table_as_week_year_reference2);

            ///now giving all the days of the week as absent for default in DATABASE(using a LOOP FOR ALL THE DAYS)
            int q;
            if (is_it_prev_week == true) {
                for (q = 1; q <= no_of_period1; q++) {
                    boolean inserting_dummy_data = myDb_2.insertData(String.valueOf(q), "HOLIDAY", "HOLIDAY", "HOLIDAY", "HOLIDAY", "HOLIDAY", "HOLIDAY", "HOLIDAY", name_database_table_as_week_year_reference2);
                }
            }
            else {
                for (q = 1; q <= no_of_period1; q++) {
                    boolean inserting_dummy_data = myDb_2.insertData(String.valueOf(q), "ABSENT", "ABSENT", "ABSENT", "ABSENT", "ABSENT", "ABSENT", "ABSENT", name_database_table_as_week_year_reference2);


                }
            }

            //for the status of  default  days (like default non working days or the days of future) changed by the user  can be stored in the new row (period +1)of name_database_table_as_week_year table
            //giving default value as NULL (BLANK SPACE)  to all (we cannot directly update the values ...there needs to be some value in the row alraedy ....thats y this all)
            boolean inserting_dEFAULT_day_status_data = myDb_2.insertData(String.valueOf(no_of_period1+1), " ", " ", " ", " ", " ", " ", " ", name_database_table_as_week_year_reference2);

            //now retriving the checkboxes  data from database and storing that in new int array which is essentially stored in the above row of the database
            Cursor res9 = myDb.getAllData2();
            int ct[] = new int[7];
            int r, v = 0;
            //below we pass the row no as a parameter ....rows start from 1
            res9.move(1);
            //below we pass the column no as a parameter ....column start from 0
            ct[0] = Integer.parseInt(res9.getString(0));
            ct[1] = Integer.parseInt(res9.getString(1));
            ct[2] = Integer.parseInt(res9.getString(2));
            ct[3] = Integer.parseInt(res9.getString(3));
            ct[4] = Integer.parseInt(res9.getString(4));
            ct[5] = Integer.parseInt(res9.getString(5));
            ct[6] = Integer.parseInt(res9.getString(6));


            for (r = 0; r < 7; r++) {

                if (ct[r] == 1) {
                    //remember i am passing column no as r only nor r+1 because my database 2 method od updating perticular data does that work itself
                    boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf( no_of_period1+ 1), r , "NON_WORKING_DAY", name_database_table_as_week_year_reference2);


                } else {
                    boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf( no_of_period1+ 1), r , "WORKING_DAY", name_database_table_as_week_year_reference2);

                }
            }

            //this above data given to user_updated_day row is for only the time when activity or table for some perticular week is created the first time

        }

        ////////////////////////////////////////////////////////////////////////----------------------------------------------------------------------------------------------------

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

                //if default holiday is true for this perticular day then next step is to check whether ,ever the user changed the default holiday to THAT WEEK(not the default holidya for all the week)
                final Cursor res__ = myDb_2.getAllData(name_database_table_as_week_year_reference2);
                //below we pass the row no as a parameter ....it is one more than the no of period because because till that point only periods status is stored
                res__.move(no_of_period1 + 1);
                // values are true means each period element is disactive and have color white and vice versa
                //below we pass the column no as a parameter to getstring as one more than j....because column start from 0 and the first column is of id not days

                if (res__.getString(r+1 ).equals("NON_WORKING_DAY")) {
                    boooty[r] = true;
                }
                else {
                    boooty[r] = false;
                }
            } else {
                boooty[r] = false;
            }
        }


        return boooty;
    }



    private void adding_days_and_date_to_column_of_days(int week_no_of_year, int year_of_the_week, String name_database_table_as_week_year, TextView[] tv_date) {

//all the mid steps have been transferred to on create method ...and the data is then passsed to this method.

        //step -4 -now we will found out the all the dates of this week no
//again creating an instance of the calender
        Calendar calendar3 = Calendar.getInstance();
        calendar3.clear();
        //below line of code sets the first day of week of our custom calender as monday
        calendar3.setFirstDayOfWeek(Calendar.MONDAY);
        //below lines set the week no and year as we give to them -which is current relevant data fo now
        calendar3.set(Calendar.WEEK_OF_YEAR, week_no_of_year);
        calendar3.set(Calendar.YEAR, year_of_the_week);

        //below lines of code  sets the days to textviews of date day column
        SimpleDateFormat formatter = new SimpleDateFormat("ddMMMyy"); // PST`
        Date startDate = calendar3.getTime();
        String startDate_of_week_of_monday = formatter.format(startDate);
        tv_date[0].setText(startDate_of_week_of_monday);

        //after adding mondays date we will add 1  day to calender (by giving one inside one of its parameter) and then assign that day to respected textview
        calendar3.add(Calendar.DATE, 1);
        Date tue_date = calendar3.getTime();
        String date_tue = formatter.format(tue_date);
        tv_date[1].setText(String.valueOf(date_tue));

        calendar3.add(Calendar.DATE, 1);
        Date wed_date = calendar3.getTime();
        String date_wed = formatter.format(wed_date);
        tv_date[2].setText(String.valueOf(date_wed));

        calendar3.add(Calendar.DATE, 1);
        Date thu_date = calendar3.getTime();
        String date_thu = formatter.format(thu_date);
        tv_date[3].setText(String.valueOf(date_thu));

        calendar3.add(Calendar.DATE, 1);
        Date fri_date = calendar3.getTime();
        String date_fri = formatter.format(fri_date);
        tv_date[4].setText(String.valueOf(date_fri));

        calendar3.add(Calendar.DATE, 1);
        Date sat_date = calendar3.getTime();
        String date_sat = formatter.format(sat_date);
        tv_date[5].setText(String.valueOf(date_sat));

        calendar3.add(Calendar.DATE, 1);
        Date sun_date = calendar3.getTime();
        String date_sun = formatter.format(sun_date);
        tv_date[6].setText(String.valueOf(date_sun));


    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public TextView[][] adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(final int no_of_perod, int no_of_days, final boolean[] boooty, final String name_database_table_as_week_year , Boolean is_it_prev_week , Boolean is_it_current_week,int week_no_of_year,int  year_of_the_week) {


        //making arrays of both views
        final RelativeLayout ll[][] = new RelativeLayout[no_of_days][no_of_perod];
        final TextView tv[][] = new TextView[no_of_days][no_of_perod];
        //   final TextView tv_2[][] = new TextView[no_of_days][no_of_perod];
        final ImageView iv[][] = new ImageView[no_of_days][no_of_perod];
        final ImageView iv_for_A[][] = new ImageView[no_of_days][no_of_perod];
        final ImageView iv_for_P[][] = new ImageView[no_of_days][no_of_perod];
        final TextView view_for_layering[][] = new TextView[no_of_days][no_of_perod];
        //below two linear layouts are to be inserted inside A ad P Relative layouts  because ripple effect can not be given to imageviews so we give it to these linearlayouts and these layouts to Relative layouts of image views
        //update - ripple effect is not possible on imageviews so i am using alternative so just ignore the ripple effect on A naP comments and names
        final RelativeLayout Rl_container_for_ripple_on_A[][] = new RelativeLayout[no_of_days][no_of_perod];
        final RelativeLayout Rl_container_for_ripple_on_P[][] = new RelativeLayout[no_of_days][no_of_perod];

        //below string array stores status of each of the periods ...which can only either be - ACTIVE or DISACTIVE or NOT OCCURED .....this is done for toast messages on click
        final String String_status_for_periods_for_toasts[][] = new String[no_of_days][no_of_perod];
//below string array stores status of each of the dyas ...which can only either be - ACTIVE or DISACTIVE or NOT OCCURED .....this is done for toast messages on click
        final String String_status_for_days_for_toasts[] = new String[7];



        //making a string array of day sname for toasts messages
        final  String Str_arr_for_day_names_toast[] =   {"Monday", "Tuesday" , "Wednesday", "Thursday" , "Friday" , "Saturday" , "Sunday"};

        //--------------------------parameters for image view of green_circle_for_top_bar_and_prev_next_buttons
        //converting px units into dp ...because default unit for scale used is not dp
        final float scale = getResources().getDisplayMetrics().density;

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int) (10 * scale + 0.5f) ,(int) (10 * scale + 0.5f));
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.setMargins(0,(int) (5 * scale + 0.5f),(int) (5 * scale + 0.5f),0);

//================================================================
        //--------------------------parameters for RElative layout of A font
        RelativeLayout.LayoutParams lp_for_RL_of_A = new RelativeLayout.LayoutParams((int) (35 * scale + 0.5f) ,(int) (35 * scale + 0.5f));
        lp_for_RL_of_A.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp_for_RL_of_A.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp_for_RL_of_A.setMargins((int) (6 * scale + 0.5f), 0,0,(int) (3 * scale + 0.5f));
        //   lp_for_iv_of_A.setMargins(0,15,18,0);


        //--------------------------parameters for image view of imageview of A font
        RelativeLayout.LayoutParams lp_for_iv_of_A = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT ,RelativeLayout.LayoutParams.MATCH_PARENT);
//        lp_for_iv_of_A.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        lp_for_iv_of_A.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//        lp_for_iv_of_A.setMargins(15, 0,0,8);
        //   lp_for_iv_of_A.setMargins(0,15,18,0);
//==============================================================
        //--------------------------parameters for image view of imageview of P font
        RelativeLayout.LayoutParams lp_for_iv_of_P = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT ,RelativeLayout.LayoutParams.MATCH_PARENT);



        //--------------------------parameters for RElative layout of image view of P font
        RelativeLayout.LayoutParams lp_for_RL_of_P = new RelativeLayout.LayoutParams((int) (35 * scale + 0.5f) ,(int) (35 * scale + 0.5f));
        lp_for_RL_of_P.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp_for_RL_of_P.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp_for_RL_of_P.setMargins((int) (46 * scale + 0.5f), 0,0,(int) (3 * scale + 0.5f));
        // lp_for_iv_of_P.setMargins(0,15,18,0);
//===================================================

        //--------------------------parameters for  textview of subject name
        RelativeLayout.LayoutParams lp_2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT ,RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp_2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp_2.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp_2.setMargins(25,0,0,8);




        //--------------------------parameters for main relative layout or which is the elemrnt of the grid
        //converting px units into dp ...because default unit for scale used is not dp

        int width_120dp = (int) (120 * scale + 0.5f);
        int height_60dp = (int) (60 * scale + 0.5f);
        int top_padding_7dp = (int) (4 * scale + 0.5f);
        int right_padding_35dp = (int) (5 * scale + 0.5f);
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(width_120dp, height_60dp);
//for the purpose of right margin between elements ...i am just gonna use empty textviews after each elemetns so they look like its Right margin(code is just  after this below loops
        //we are not giving directly the right margin because the below code is not working
        //for top margin ...I have set it directly to the linear layouts for rows
        //    layoutParams5.setMargins(0,0,right_padding_35dp,0);

        //-----------------------------------textview acta as a layer of gray when class is diammissed.
        RelativeLayout.LayoutParams lp_66 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT ,RelativeLayout.LayoutParams.MATCH_PARENT);
        //  RelativeLayout.LayoutParams lp_66 = new RelativeLayout.LayoutParams(width_120dp, height_60dp-10);
        lp_66.setMargins(0,0,0,0);

        //////////////////////////////////////////////////////////////////////--------------------------------------------------------------------------
        //setting each of them with each other()
        int i, k;
        for (k = 0; k < no_of_days; k++) {
            for (i = 0; i < no_of_perod; i++) {

                iv[k][i] = new ImageView(this);
                ll[k][i] = new RelativeLayout(this);
                tv[k][i] = new TextView(this);
                iv_for_A[k][i] = new ImageView(this);
                iv_for_P[k][i] = new ImageView(this);

                Rl_container_for_ripple_on_A[k][i] = new RelativeLayout(this);
                Rl_container_for_ripple_on_P[k][i] = new RelativeLayout(this);


                view_for_layering[k][i]=new TextView(this);

                //----------------------------------------initialising all of the to P-present
                String_status_for_periods_for_toasts[k][i] = "P";
//////////////////////////-----------------------------------------------CONTINUE FROM HERE
                iv[k][i].setImageResource(R.drawable.circle_2);
                iv[k][i].setLayoutParams(lp);
                //          iv[k][i].setBackgroundColor(Color.RED);


                tv[k][i] = new TextView(this);
                tv[k][i].setLayoutParams(lp_2);
                //for setting the limit on the lenth of the subjects name inside the textview
                InputFilter[] filterArray = new InputFilter[1];
                filterArray[0] = new InputFilter.LengthFilter(10);
                tv[k][i].setFilters(filterArray);

                tv[k][i].setTextColor(Color.parseColor("#8E24AA"));
                tv[k][i].setText("hvghjkjkgggggg");
                tv[k][i].setTextSize(Float.parseFloat("16"));


                //  tv[k][i].setTextAppearance(this, R.style.font_for_subject_textView);
                //  tv[k][i].setTypeface(null, Typeface.ITALIC);
                ll[k][i].setLayoutParams(layoutParams5);






                iv_for_A[k][i].setImageResource(R.drawable.a_proper);
                iv_for_A[k][i].setLayoutParams(lp_for_iv_of_A);
                iv_for_A[k][i].setBackgroundColor(Color.parseColor("#909090"));
                //     iv_for_A[k][i].setBackgroundResource(R.drawable.ripple_and_rounded_corner_for_a_and_p_button);
                //        iv_for_A[k][i].setBackgroundColor(Color.RED);

                iv_for_P[k][i].setImageResource(R.drawable.proper_p);
                iv_for_P[k][i].setLayoutParams(lp_for_iv_of_P);
                iv_for_P[k][i].setBackgroundColor(Color.parseColor("#909090"));
                // iv_for_P[k][i].setBackgroundResource(R.drawable.ripple_and_rounded_corner_for_a_and_p_button);


                Rl_container_for_ripple_on_A[k][i].setLayoutParams(lp_for_RL_of_A);
                Rl_container_for_ripple_on_P[k][i].setLayoutParams(lp_for_RL_of_P);
//                Rl_container_for_ripple_on_A[k][i].addView(iv_for_A[k][i]);
//                Rl_container_for_ripple_on_A[k][i].addView(iv_for_P[k][i]);

                Rl_container_for_ripple_on_A[k][i].addView(iv_for_A[k][i]);
                Rl_container_for_ripple_on_P[k][i].addView(iv_for_P[k][i]);

                Rl_container_for_ripple_on_A[k][i].setPadding(2,2,2,2);
                Rl_container_for_ripple_on_P[k][i].setPadding(2,2,2,2);

//                Rl_container_for_ripple_on_A[k][i].setBackgroundResource(R.drawable.ripple_and_rounded_corner_for_a_and_p_button);
//                Rl_container_for_ripple_on_P[k][i].setBackgroundResource(R.drawable.ripple_and_rounded_corner_for_a_and_p_button);



                /*//converting px units into dp ...because default unit for scale used is not dp
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
                tv[k][i].setBackgroundColor(Color.RED);

                ll[k][i].addView(tv[k][i]);*/

                ll[k][i].addView(Rl_container_for_ripple_on_A[k][i]);
                ll[k][i].addView(Rl_container_for_ripple_on_P[k][i]);


                ll[k][i].addView(tv[k][i]);
//                ll[k][i].addView(iv_for_A[k][i]);
//                ll[k][i].addView(iv_for_P[k][i]);
                ll[k][i].addView(iv[k][i]);
                ll[k][i].setBackgroundResource(R.drawable.rounded_corners_and_ripple_for_periods);
                view_for_layering[k][i].setLayoutParams(lp_66);
                view_for_layering[k][i].setBackgroundResource(R.drawable.for_class_dissmised_element);

                //  view_for_layering[k][i].setPadding(0,0,40,40);

            }
        }


        LinearLayout ll_for_rows_java[] = new LinearLayout[no_of_days];
        //for the purpose of right margin between elements ...i am just gonna use empty textviews after each elemetns so they look like its Right margin(code is just  after this below loops
        //we are not giving directly the right margin because the below code is not working
        LinearLayout.LayoutParams lp_for_tv_as_margin = new LinearLayout.LayoutParams((int) (1.5 * scale + 0.5f),LinearLayout.LayoutParams.MATCH_PARENT );
        TextView tv_for_Right_margin[][] = new TextView[no_of_days][no_of_perod];

                            //for firts row of period numberin
                             final TextView tv_for_top_most_row_of_period_numberin[] = new TextView[no_of_perod];
                                    LinearLayout.LayoutParams lp_for_tv_afor_numberin_periods = new LinearLayout.LayoutParams((int) (120 * scale + 0.5f),LinearLayout.LayoutParams.WRAP_CONTENT );
                                    lp_for_tv_afor_numberin_periods.setMargins(0,0,0,(int) (1 * scale + 0.5f));

                                    final String[] strs = {"1st pr.", "2nd pr.", "3rd pr." , "4th pr." , "5th pr.", "6th pr." , "7th pr." , "8th pr." , "9th pr." , "10th pr." , "11th pr."
                                    , "12th pr." , "13th pr." ,"14th pr." , "15th pr."};

                            LinearLayout ll_container_for_first_row_of_numberin = new LinearLayout(this);
                                    LinearLayout.LayoutParams layoutParams_for_container_row = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                    layoutParams_for_container_row.setMargins(0,0,0,(int) (2 * scale + 0.5f));


                            TextView tv_for_Right_margin_for_first_row[] = new TextView[no_of_perod -1];
                                    LinearLayout.LayoutParams lp_for_tv_as_margin_for_firest_row_of_numberin = new LinearLayout.LayoutParams((int) (1 * scale + 0.5f),LinearLayout.LayoutParams.MATCH_PARENT );
                                    lp_for_tv_as_margin_for_firest_row_of_numberin.setMargins(0,(int) (1 * scale + 0.5f),0,(int) (1 * scale + 0.5f));



        for (k = 0; k < no_of_days; k++) {
            ll_for_rows_java[k] = new LinearLayout(this);
            LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams4.setMargins(0,(int) (1.5 * scale + 0.5f),0,0);
            ll_for_rows_java[k].setLayoutParams(layoutParams4);
            ll_for_rows_java[k].setOrientation(LinearLayout.HORIZONTAL);



            for (i = 0; i < no_of_perod; i++) {
                tv_for_Right_margin[k][i] = new TextView(this);
                tv_for_Right_margin[k][i].setLayoutParams(lp_for_tv_as_margin);
                ll_for_rows_java[k].addView(ll[k][i]);
                ll_for_rows_java[k].addView(tv_for_Right_margin[k][i]);


            }


        }


        LinearLayout ll_of_the_view_already_in_xml = (LinearLayout) findViewById(R.id.Lin_layout2_xml);
                                ll_container_for_first_row_of_numberin.setLayoutParams(layoutParams_for_container_row);
                                ll_of_the_view_already_in_xml.addView(ll_container_for_first_row_of_numberin);

                                for( k =0 ; k<no_of_perod ;k ++) {
                                    tv_for_top_most_row_of_period_numberin[k] = new TextView(this);
                                    tv_for_top_most_row_of_period_numberin[k].setLayoutParams(lp_for_tv_afor_numberin_periods);
                                    tv_for_top_most_row_of_period_numberin[k].setText(strs[k]);
                                    tv_for_top_most_row_of_period_numberin[k].setGravity(Gravity.CENTER_HORIZONTAL);
                                    tv_for_top_most_row_of_period_numberin[k].setTypeface(Typeface.create(" serif-monospace ",Typeface.BOLD));
                                    tv_for_top_most_row_of_period_numberin[k].setTextColor(Color.parseColor("#777777"));

                                    ll_container_for_first_row_of_numberin.addView(tv_for_top_most_row_of_period_numberin[k]);
                        //the margin textview is just made on eless then total so its inside if
                                    if (k < no_of_perod - 1) {
                                        tv_for_Right_margin_for_first_row[k] = new TextView(this);
                                        tv_for_Right_margin_for_first_row[k].setLayoutParams(lp_for_tv_as_margin_for_firest_row_of_numberin);
                                        tv_for_Right_margin_for_first_row[k].setBackgroundColor(Color.parseColor("#cdcdcd"));
                                        ll_container_for_first_row_of_numberin.addView(tv_for_Right_margin_for_first_row[k]);
                                    }
                                }



        for (k = 0; k < no_of_days; k++) {
            ll_of_the_view_already_in_xml.addView(ll_for_rows_java[k]);
        }


        //////////////////////////////////////////////////////////-----------------------------------------------------------------------
//setting the color or state of the periods a stored in database


        int m, j;

        for (m = 0; m < no_of_days; m++) {
            for (j = 0; j < no_of_perod; j++) {


                final int finalJ = j;
                final int finalM = m;

                final Cursor res5 = myDb_2.getAllData(name_database_table_as_week_year);
                //below we pass the row no as a parameter ....it is one more than the j because row starts from 1
                res5.move(finalJ + 1);
                // values are true means each period element is disactive and have color white and vice versa
                //below we pass the column no as a parameter to getstring as one more than j....because column start from 0 and the first column is of id not days


                if (res5.getString(finalM + 1).equals("ABSENT")) {
                    iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));
                    iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));
                } else {
                    if (res5.getString(finalM + 1).equals("PRESENT")) {
                        iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#388E3C"));
                        iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#388E3C"));
                    } else {
//                        tv[finalM][finalJ].setBackgroundColor(Color.GRAY);
                        iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#909090"));
                        iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#909090"));
                        iv[finalM][finalJ].setBackgroundColor(Color.GRAY);


                        ll[finalM][finalJ].addView(view_for_layering[finalM][finalJ]);

                    }
                }

                res5.close();
            }
        }


//---------------------------------------------------------------------------------------






/////////////////////////////////////////////////////////////////////////------------------------------------------------------

        //setting color changing toggle behavior with click listeners to every period of every day
        //-also for loop for seeting click listrnrers to the P and A imageviews
        //for loop for setting toggle behaviour
        final Cursor res4 = myDb_2.getAllData(name_database_table_as_week_year);


        for (m = 0; m < no_of_days; m++) {
            for (j = 0; j < no_of_perod; j++) {


                final int finalJ = j;
                final int finalM = m;


                ll[m][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        //first I am gonna check if that day is disactivated ..and if its is then click on the periods would do nothing just toast will appear

if (String_status_for_days_for_toasts[finalM] == "A") {
                            //below if else works as a toggle....it makes view work as a toggle switch
                            //enable
                            final Cursor res4 = myDb_2.getAllData(name_database_table_as_week_year);
                            //below we pass the row no as a parameter ....it is one more than the j because row starts from 1
                            res4.move(finalJ + 1);
                            // values are true means each period element is disactive and have color white and vice versa
                            //below we pass the column no as a parameter to getstring as one more than j....because column start from 0 and the first column is of id not days


                            if (res4.getString(finalM + 1).equals("HOLIDAY") || res4.getString(finalM + 1).equals("NOT_OCCURED_YET")) {

                                iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));
                                iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));
                                ll[finalM][finalJ].removeView(view_for_layering[finalM][finalJ]);

                                // boo2[finalM][finalJ] = true;
                                /////now making changes in that perticular day in DATABASE(using a LOOP FOR ALL THE DAYS)
                                boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(finalJ + 1), finalM, "ABSENT", name_database_table_as_week_year);


                                Toast.makeText(EditorActivity.this, strs[finalJ] + "period is activated and marked absent by default",
                                        Toast.LENGTH_SHORT).show();


                            } else


                            //disable
                            {
                                iv[finalM][finalJ].setBackgroundColor(Color.GRAY);
                                iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#909090"));
                                iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#909090"));
                                ll[finalM][finalJ].addView(view_for_layering[finalM][finalJ]);
                                // boo2[finalM][finalJ] = false;
                                //update data in database
                                boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(finalJ + 1), finalM, "HOLIDAY", name_database_table_as_week_year);


                                Toast.makeText(EditorActivity.this, strs[finalJ] + "period is deactivated/dissmissed/canceled",
                                        Toast.LENGTH_SHORT).show();
                            }
                            res4.close();

                        }

                        if(String_status_for_days_for_toasts[finalM] == "D"){
                            Toast.makeText(EditorActivity.this,   "Activate " + Str_arr_for_day_names_toast[finalM] + " first",
                                    Toast.LENGTH_SHORT).show();

                        }

                        if(String_status_for_days_for_toasts[finalM] == "N"){
                            Toast.makeText(EditorActivity.this,   Str_arr_for_day_names_toast[finalM] +" hasn't occured yet",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
//---------------------------------------click listener fir tv for layering...it just removes itself from ll
                view_for_layering[m][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {


                        if (String_status_for_days_for_toasts[finalM] == "A") {



                       /*     //below if else works as a toggle....it makes view work as a toggle switch
                            //enable
                            final Cursor res4 = myDb_2.getAllData(name_database_table_as_week_year);
                            //below we pass the row no as a parameter ....it is one more than the j because row starts from 1
                            res4.move(finalJ + 1);
                            // values are true means each period element is disactive and have color white and vice versa
                            //below we pass the column no as a parameter to getstring as one more than j....because column start from 0 and the first column is of id not days


                            if (res4.getString(finalM + 1).equals("HOLIDAY") ||  res4.getString(finalM + 1).equals("NOT_OCCURED_YET")) {*/

                            iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));
                            iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));
                            ll[finalM][finalJ].removeView(view_for_layering[finalM][finalJ]);

                            // boo2[finalM][finalJ] = true;
                            /////now making changes in that perticular day in DATABASE(using a LOOP FOR ALL THE DAYS)
                            boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(finalJ + 1), finalM, "ABSENT", name_database_table_as_week_year);

                            Toast.makeText(EditorActivity.this, strs[finalJ] + "period is activated and marked absent by default",
                                    Toast.LENGTH_SHORT).show();



                           /* } else


                            //disable
                            {
                                iv[finalM][finalJ].setBackgroundColor(Color.GRAY);
                                iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#999999"));
                                iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#999999"));
                                ll[finalM][finalJ].addView(view_for_layering[finalM][finalJ]);
                                // boo2[finalM][finalJ] = false;
                                //update data in database
                                boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(finalJ + 1), finalM, "HOLIDAY", name_database_table_as_week_year);


                            }
                            res4.close();*/


                        } else {
                        Toast.makeText(EditorActivity.this, "Activate " + Str_arr_for_day_names_toast[finalM] + " first",
                                Toast.LENGTH_SHORT).show();

                    }


                }
                });





//----------------------------------------------------------
                iv_for_P[m][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
//below code is kind of toggling
                        final Cursor res600 = myDb_2.getAllData(name_database_table_as_week_year);
                        res600.move(finalJ + 1);
                        if (res600.getString(finalM + 1).equals("PRESENT")) {

                            iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));
                            iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#999999"));
                            iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));

                            boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(finalJ + 1), finalM, "ABSENT", name_database_table_as_week_year);


                            Toast.makeText(EditorActivity.this, strs[finalJ] + " of "+ Str_arr_for_day_names_toast[finalM] + "is marked absent",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (res600.getString(finalM + 1).equals("ABSENT")) {

                            iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#388E3C"));
                            iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#388E3C"));
                            iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#909090"));

                            boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(finalJ + 1), finalM, "PRESENT", name_database_table_as_week_year);


                            Toast.makeText(EditorActivity.this, strs[finalJ] +" of "+ Str_arr_for_day_names_toast[finalM] +  " is marked present",
                                    Toast.LENGTH_SHORT).show();
                        }



                    }});
                //--------------------------
                iv_for_A[m][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
//below code is kind of toggling
                        final Cursor res600 = myDb_2.getAllData(name_database_table_as_week_year);
                        res600.move(finalJ + 1);
                        if (res600.getString(finalM + 1).equals("PRESENT")) {

                            iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));
                            iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#909090"));
                            iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));

                            boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(finalJ + 1), finalM, "ABSENT", name_database_table_as_week_year);


                            Toast.makeText(EditorActivity.this, strs[finalJ] +" of "+ Str_arr_for_day_names_toast[finalM] +  " is marked absent",
                                    Toast.LENGTH_SHORT).show();
                        }
                        if (res600.getString(finalM + 1).equals("ABSENT")) {

                            iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#388E3C"));
                            iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#388E3C"));
                            iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#909090"));

                            boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(finalJ + 1), finalM, "PRESENT", name_database_table_as_week_year);


                            Toast.makeText(EditorActivity.this, strs[finalJ] +" of "+ Str_arr_for_day_names_toast[finalM] +  " is marked present",
                                    Toast.LENGTH_SHORT).show();
                        }



                    }});
//----------------------------sttin on touch to both so that their border glow of each(as to show touch  )


                final int finalM1 = m;
                final int finalJ1 = j;
                iv_for_A[m][j].setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_DOWN){

                            Rl_container_for_ripple_on_A[finalM1][finalJ1].setBackgroundColor(Color.parseColor("#66BA68C8"));
                            return false;
                        }else if (event.getAction() == MotionEvent.ACTION_UP|| (event.getAction() == MotionEvent.ACTION_CANCEL)){
                            Rl_container_for_ripple_on_A[finalM1][finalJ1].setBackgroundColor(Color.parseColor("#f2f2f2"));

                            return false;
                        }
                        return false;
                    }
                });



                iv_for_P[m][j].setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(event.getAction() == MotionEvent.ACTION_DOWN){

                            Rl_container_for_ripple_on_P[finalM1][finalJ1].setBackgroundColor(Color.parseColor("#66BA68C8"));
                            return false;
                        }else if (event.getAction() == MotionEvent.ACTION_UP|| (event.getAction() == MotionEvent.ACTION_CANCEL)){
                            Rl_container_for_ripple_on_P[finalM1][finalJ1].setBackgroundColor(Color.parseColor("#f2f2f2"));

                            return false;
                        }
                        return false;
                    }
                });








            }
        }
        //////////////////////////////////////////////////////////--------------------------------
/*            //for loop for setting long on click listener for making period as dissmissed one
            int x, z;
            for (x = 0; x < no_of_days; x++) {
                for (z = 0; z < no_of_perod; z++) {


                    final int finalX = x;
                    final int finalZ = z;

                    //setting long on click listener on ll linearlayout messes with functionality of on click of ll so using tv textview to recognise long click ....later increse thwe size of tv so it covers more area of tv
                    tv[x][z].setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            tv[finalX][finalZ].setBackgroundColor(Color.GRAY);
                            //------------------------boo2[finalX][finalZ] = false;
                            //update data in database
                            boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(finalZ + 1), finalX, "HOLIDAY", name_database_table_as_week_year);


                            return false;
                        }

                    });
                }
            }*/


////========================================================================================================================================================================
//--------------------------------------------------------making the date/days column dynamically
        //making arrays of the views

        final RelativeLayout ll_2_java[] = new RelativeLayout[7];

        final TextView tv_for_date[] = new TextView[7];
        final TextView tv_for_days[]= new TextView[7];
        final ImageView iv_2[] = new ImageView[7];
        final TextView view_for_layering_on_dyas_date[] = new TextView[7];







//---------------------------------------------------parameters for image view of green_circle_for_top_bar_and_prev_next_buttons
        RelativeLayout.LayoutParams lp_of_iv_2 = new RelativeLayout.LayoutParams((int) (10 * scale + 0.5f) ,(int) (10 * scale + 0.5f));
        lp_of_iv_2.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp_of_iv_2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp_of_iv_2.setMargins(0,(int) (7 * scale + 0.5f),(int) (6 * scale + 0.5f),0);

        //--------------------------parameters for tv for date
        RelativeLayout.LayoutParams lp_for_tv_date = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT ,RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp_for_tv_date.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp_for_tv_date.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp_for_tv_date.setMargins((int) (4 * scale + 0.5f), (int) (5 * scale + 0.5f),0,0);
        //   lp_for_iv_of_A.setMargins(0,15,18,0);

        //--------------------------parameters for tv of day
        RelativeLayout.LayoutParams lp_for_tv_day = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT ,RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp_for_tv_day.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lp_for_tv_day.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp_for_tv_day.setMargins((int) (6 * scale + 0.5f), 0,0,(int) (5 * scale + 0.5f));
        // lp_for_iv_of_P.setMargins(0,15,18,0);



        //--------------------------parameters for main relative layout or which is the elemrnt of the column

        RelativeLayout.LayoutParams layoutParams_for_the_ll_2 = new RelativeLayout.LayoutParams((int) (85 * scale + 0.5f), (int) (60 * scale + 0.5f));
        //for the purpose of right margin between elements ...i am just gonna use empty textviews after each elemetns so they look like its Right margin(code is just  after this below loops
        //we are not giving directly the right margin because the below code is not working
        //      layoutParams_for_the_ll_2.setMargins(0,(int) (5 * scale + 0.5f),0,(int) (5 * scale + 0.5f));

//---------------------------------------for textview as  margin
        //for the purpose of right margin between elements ...i am just gonna use empty textviews after each elemetns so they look like its Right margin(code is just  after this below loops
        LinearLayout.LayoutParams lp_for_tv_as_margin_2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,(int) (1.5 * scale + 0.5f));
        TextView tv_for_Right_margin_2[] = new TextView[no_of_days];

        //-----------------------------------textview acta as a layer of gray when class is diammissed.
        RelativeLayout.LayoutParams lp_66_for_gray_layer = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT ,RelativeLayout.LayoutParams.MATCH_PARENT);
//the container linear layout for date days elememnt
        LinearLayout ll_container_for_days_date = (LinearLayout)findViewById(R.id.ll_5_xml);

        //////////////////////////////////////////////////////////////////////--------------------------------------------------------------------------
        //setting each of them with each other()
        for (int u = 0; u < 7; u++) {

            iv_2[u] = new ImageView(this);
            ll_2_java[u] = new RelativeLayout(this);
            tv_for_days[u] = new TextView(this);
            tv_for_date[u] = new TextView(this);
            tv_for_Right_margin_2[u] = new TextView(this);

            view_for_layering_on_dyas_date[u]=new TextView(this);

            //initialising them to a default of activated by "A"
            String_status_for_days_for_toasts[u] = "A";
//////////////////////////-----------------------------------------------CONTINUE FROM HERE
            iv_2[u].setImageResource(R.drawable.circle_for_days_date_column);
            iv_2[u].setLayoutParams(lp_of_iv_2);
            iv_2[u].setBackgroundColor(Color.GREEN);



            tv_for_date[u].setLayoutParams(lp_for_tv_date);
            tv_for_date[u].setTextColor(Color.parseColor("#EEEEEE"));
            tv_for_date[u].setTypeface(Typeface.create("monospace",Typeface.NORMAL));
            tv_for_date[u].setTextSize((int) (7 * scale + 0.5f));
            //           tv[k][i].setTextAppearance(this, R.style.fontForNotificationLandingPage);


            tv_for_days[u].setLayoutParams(lp_for_tv_day);
            tv_for_days[u].setTextColor(Color.parseColor("#4A148C"));
            tv_for_days[u].setTypeface(Typeface.create( "sans-serif-medium",Typeface.BOLD));
            tv_for_days[u].setTextSize((int) (9 * scale + 0.5f));
            //           tv[k][i].setTextAppearance(this, R.style.fontForNotificationLandingPage);

            tv_for_Right_margin_2[u].setLayoutParams(lp_for_tv_as_margin_2);


            //       ll_2_java[u].setLayoutParams(layoutParams5);


            ll_2_java[u].setLayoutParams(layoutParams_for_the_ll_2);
            ll_2_java[u].addView(tv_for_date[u]);
            ll_2_java[u].addView(tv_for_days[u]);
            ll_2_java[u].addView(iv_2[u]);
            ll_2_java[u].setBackgroundResource(R.drawable.for_day_date_column);
            view_for_layering_on_dyas_date[u].setLayoutParams(lp_66_for_gray_layer);
            view_for_layering_on_dyas_date[u].setBackgroundResource(R.drawable.for_day_dissmised_element);

            ll_container_for_days_date.addView(ll_2_java[u]);
            ll_container_for_days_date.addView(tv_for_Right_margin_2[u]);

        }

//------------------also the textviews for days need to be given the days name
        tv_for_days[0].setText("MON");
        tv_for_days[1].setText("TUE");
        tv_for_days[2].setText("WED");
        tv_for_days[3].setText("THU");
        tv_for_days[4].setText("FRI");
        tv_for_days[5].setText("SAT");
        tv_for_days[6].setText("SUN");

//also the textviews for the dated needed to be given the dates
        adding_days_and_date_to_column_of_days(week_no_of_year, year_of_the_week, name_database_table_as_week_year, tv_for_date);




//============================================================================================================================================

        //////////////////////////////////////////////////////////////////--------------------------------------------------------------------------

        //setting the behavior to days/date column



        int t;
        for (t = 0; t < 7; t++) {
            final Cursor res_ = myDb_2.getAllData(name_database_table_as_week_year);
            //below we pass the row no as a parameter ....it is one more than the j because row starts from 1
            res_.move(no_of_perod + 1);
            // values are true means each period element is disactive and have color white and vice versa
            //below we pass the column no as a parameter to getstring as one more than j....because column start from 0 and the first column is of id not days

            if (res_.getString(t + 1).equals("NON_WORKING_DAY")) {

                iv_2[t].setBackgroundColor(Color.GRAY);
                ll_2_java[t].addView(view_for_layering_on_dyas_date[t]);
                for ( int f = 0; f < 7; f++) {
                    for (int h = 0; h < no_of_perod; h++) {


                        //view_for_layering[f][h].setClickable(false);
                        String_status_for_days_for_toasts[t] = "D" ;


                    }
                }

            } else {
                iv_2[t].setBackgroundColor(Color.GREEN);
                // ll_2_java[t].removeView(view_for_layering_on_dyas_date[t]);

            }
        }


        //////////a final and array of boolean variables is used in palce of a simple boolean variable because to include it otherwise, it
        /// is showing error for not making bollean variable(its a cumpulsion for variables to be final before accessed from inside
        /// the on click method),but making it final would have let its value not changing fromm false to true or true to false ehen
        /////when we would have needed so array is used because we can change the boolean elements of a fixed boolean array,
        final boolean[] boo = new boolean[7];

        int f;

        for (f = 0; f < 7; f++) {
            final Cursor res__ = myDb_2.getAllData(name_database_table_as_week_year);
            //below we pass the row no as a parameter ....it is one more than the j because row starts from 1
            res__.move(no_of_perod + 1);
            // values are true means each period element is disactive and have color white and vice versa
            //below we pass the column no as a parameter to getstring as one more than j....because column start from 0 and the first column is of id not days

            if (res__.getString(f + 1).equals("NON_WORKING_DAY")) {
                boo[f] = false;
            } else {
                boo[f] = true;
            }
        }

        //for loop for setting toggle behaviour
        for (f = 0; f < 7; f++) {
            final int finalF = f;

            ll_2_java[finalF].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    if(String_status_for_days_for_toasts[finalF] != "N") {


                        //below if else works as a toggle....it makes view work as a toggle switch
                        //enable
                        if (boo[finalF] == false) {
                            iv_2[finalF].setBackgroundColor(Color.GREEN);
                            // if()
                            ll_2_java[finalF].removeView(view_for_layering_on_dyas_date[finalF]);

                            boo[finalF] = true;

                            boolean inserting_perticular_data_in_user_changed_day_status = myDb_2.updateData_perticular_column(String.valueOf(no_of_perod + 1), finalF, "WORKING_DAY", name_database_table_as_week_year);

                            Toast.makeText(EditorActivity.this, Str_arr_for_day_names_toast[finalF] + " is activated/working day",
                                    Toast.LENGTH_SHORT).show();

                            //A means day activated
                            String_status_for_days_for_toasts[finalF] = "A";

                            //----------------------


                            //enabling all the periods of that perticular day,but keeping thier boo2 variable as false
                            int h;
                            for (h = 0; h < no_of_perod; h++) {

//                                tv[finalF][h].setBackgroundColor(Color.RED);
                                iv[finalF][h].setBackgroundColor(Color.parseColor("#C62828"));
                                iv_for_A[finalF][h].setBackgroundColor(Color.parseColor("#C62828"));
                                iv_for_P[finalF][h].setBackgroundColor(Color.parseColor("#909090"));
                                //-------------------- boo2[finalF][h] = false;

                                if (ll[finalF][h].getChildCount() == 5) {
                                    ll[finalF][h].removeView(view_for_layering[finalF][h]);
                                }

                                //update data in database
                                boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), finalF, "ABSENT", name_database_table_as_week_year);


                                // //enabling the click listener on all the items of that day
                                ll[finalF][h].setClickable(true);
                                view_for_layering[finalF][h].setClickable(true);
                            }
                        } else
                        //disable
                        {

                            iv_2[finalF].setBackgroundColor(Color.GRAY);
                            // if()
                            ll_2_java[finalF].addView(view_for_layering_on_dyas_date[finalF]);
                            boo[finalF] = false;

                            boolean inserting_perticular_data_in_user_changed_day_status = myDb_2.updateData_perticular_column(String.valueOf(no_of_perod + 1), finalF, "NON_WORKING_DAY", name_database_table_as_week_year);

                            Toast.makeText(EditorActivity.this, Str_arr_for_day_names_toast[finalF] + " is deactivated/non working day",
                                    Toast.LENGTH_SHORT).show();

                            String_status_for_days_for_toasts[finalF] = "D";
                            //---------------------------------


                            //diasbling all the periods of that perticular day
                            int h;
                            for (h = 0; h < no_of_perod; h++) {

                                iv[finalF][h].setBackgroundColor(Color.GRAY);
                                iv_for_A[finalF][h].setBackgroundColor(Color.parseColor("#909090"));
                                iv_for_P[finalF][h].setBackgroundColor(Color.parseColor("#909090"));

                                if (ll[finalF][h].getChildCount() == 4) {
                                    ll[finalF][h].addView(view_for_layering[finalF][h]);
                                }
                                //update data in database
                                boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), finalF, "HOLIDAY", name_database_table_as_week_year);


                                // //disabling the click listener on all the items of that day
                                // ll[finalF][h].setClickable(false);
                                //view_for_layering[finalF][h].setClickable(false);


                            }

                        }
                    }else{ Toast.makeText(EditorActivity.this,   Str_arr_for_day_names_toast[finalF] +" hasn't occured yet",
                            Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }







//aauto deactivating the days selected by the user
        int r, v = 0;
        for (r = 0; r < 7; r++) {

            if (boooty[r] == true) {


                iv_2[r].setBackgroundColor(Color.GRAY);
                if(ll_2_java[r].getChildCount()==3) {
                    ll_2_java[r].addView(view_for_layering_on_dyas_date[r]);
                }
                boo[r] = false;

                String_status_for_days_for_toasts[r] = "D" ;
//------------------------------------

                //diasbling all the periods of that perticular day
                int h;
                for (h = 0; h < no_of_perod; h++) {

                    iv[r][h].setBackgroundColor(Color.GRAY);
                    iv_for_A[r][h].setBackgroundColor(Color.parseColor("#909090"));
                    iv_for_P[r][h].setBackgroundColor(Color.parseColor("#909090"));

                    if (ll[r][h].getChildCount()== 4) {
                        ll[r][h].addView(view_for_layering[r][h]);
                    }

                    //----------------------boo2[r][h] = false;

                    //update data in database
                    boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), r, "HOLIDAY", name_database_table_as_week_year);


                    // //disabling the click listener on all the items of that day
 //                   ll[r][h].setClickable(false);
//                    view_for_layering[r][h].setClickable(false);
                }


            }


            //-----------------------------------------------------------------------------------------------------------------------

            //ALSO we need to deactivate the click listeners to all periods of a day if the day was non working day....though the graying of that periods is done all above ...but graying of them nedded to be done after here

            final Cursor res22 = myDb_2.getAllData(name_database_table_as_week_year);
            //below we pass the row no as a parameter ....it is one more than the j because row starts from 1
            res22.move(no_of_perod + 1);

            final int finalM = r;

            if (res22.getString(finalM + 1).equals("WORKING_DAY")) {
                //do nothing
            } else {
                for (j = 0; j < no_of_perod; j++) {
 //                   ll[r][j].setClickable(false);
                }
            }
            res22.close();
        }

///////////////////////////////////////////////////////////////////--------------------------------
        final TextView view_for_layering_on_which_hasnt_occured[][] = new TextView[no_of_days][no_of_perod];
        //--textview acta as a layer of gray when class hasnt occured.
        RelativeLayout.LayoutParams lp_66_66 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT ,RelativeLayout.LayoutParams.MATCH_PARENT);


        final TextView view_for_layering_on_which_hasnt_occured_on_dyas_date[] = new TextView[no_of_days];
        //--textview acta as a layer of gray when class hasnt occured.
        RelativeLayout.LayoutParams lp_66_66_days = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT ,RelativeLayout.LayoutParams.MATCH_PARENT);

//setting each of them with each other()

        for (k = 0; k < no_of_days; k++) {

            view_for_layering_on_which_hasnt_occured_on_dyas_date[k]=new TextView(this);
            view_for_layering_on_which_hasnt_occured_on_dyas_date[k].setLayoutParams(lp_66_66_days);
            view_for_layering_on_which_hasnt_occured_on_dyas_date[k].setBackgroundResource(R.drawable.for_class_not_occured_element);

            for (i = 0; i < no_of_perod; i++) {
                view_for_layering_on_which_hasnt_occured[k][i]=new TextView(this);
                view_for_layering_on_which_hasnt_occured[k][i].setLayoutParams(lp_66_66);
                view_for_layering_on_which_hasnt_occured[k][i].setBackgroundResource(R.drawable.for_class_not_occured_element);
//                view_for_layering_on_which_hasnt_occured[k][i].setText("class hasn't occured yet");
//                view_for_layering_on_which_hasnt_occured[k][i].setTextColor(Color.parseColor("#1A237E"));
//                view_for_layering_on_which_hasnt_occured[k][i].setTypeface(Typeface.create( "monospace",Typeface.ITALIC));
//                view_for_layering_on_which_hasnt_occured[k][i].setTextSize((int) (7 * scale + 0.5f));
//                view_for_layering_on_which_hasnt_occured[k][i].setPadding((int) (6 * scale + 0.5f),(int) (6 * scale + 0.5f),(int) (6 * scale + 0.5f),(int) (6 * scale + 0.5f));
            }
        }

//for loop for graying all the days which have not been occured in the week only when this metod is called for loading present week periods ...that why if staement is used
        if (is_it_current_week == true) {
            if (is_it_prev_week == false) {

                //below line of codes get the prsent day and compare it with the day of the day in the week and if the days are of future they will get greyish
                //to get the current date
                //for which we make first make instance of calender
                //we also make date format according we need in below line
                Calendar current_calendar3 = Calendar.getInstance();
                int day2 = current_calendar3.get(Calendar.DAY_OF_WEEK);

                if (day2 ==Calendar.MONDAY ){
                    //for tuesday layering
                    if (ll_2_java[1].getChildCount() == 4) {
                        ll_2_java[1].removeView(view_for_layering_on_dyas_date[1]);
                    }
                    ll_2_java[1].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[1]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[1] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[1][h].getChildCount() == 5) {
                            ll[1][h].removeView(view_for_layering[1][h]);
                        }
                        ll[1][h].addView(view_for_layering_on_which_hasnt_occured[1][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 1 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                   //     ll[1][h].setClickable(false);
                        //and disabling that day button itself
                   //     ll_2_java[1].setClickable(false);
                    }
                    //for wednasdau layering
                    if (ll_2_java[2].getChildCount() == 4) {
                        ll_2_java[2].removeView(view_for_layering_on_dyas_date[2]);
                    }
                    ll_2_java[2].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[2]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[2] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[2][h].getChildCount() == 5) {
                            ll[2][h].removeView(view_for_layering[2][h]);
                        }
                        ll[2][h].addView(view_for_layering_on_which_hasnt_occured[2][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 2 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                     //   ll[2][h].setClickable(false);
                        //and disabling that day button itself
                  //      ll_2_java[2].setClickable(false);
                    }

                    //for thursday layering
                    if (ll_2_java[3].getChildCount() == 4) {
                        ll_2_java[3].removeView(view_for_layering_on_dyas_date[3]);
                    }
                    ll_2_java[3].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[3]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[3] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[3][h].getChildCount() == 5) {
                            ll[3][h].removeView(view_for_layering[3][h]);
                        }
                        ll[3][h].addView(view_for_layering_on_which_hasnt_occured[3][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 3 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                      //  ll[3][h].setClickable(false);
                        //and disabling that day button itself
                     //   ll_2_java[3].setClickable(false);
                    }
                    //for fridya layering
                    if (ll_2_java[4].getChildCount() == 4) {
                        ll_2_java[4].removeView(view_for_layering_on_dyas_date[4]);
                    }
                    ll_2_java[4].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[4]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[4] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[4][h].getChildCount() == 5) {
                            ll[4][h].removeView(view_for_layering[4][h]);
                        }
                        ll[4][h].addView(view_for_layering_on_which_hasnt_occured[4][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 4 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                    //    ll[4][h].setClickable(false);
                        //and disabling that day button itself
                  //      ll_2_java[4].setClickable(false);
                    }


                    //for saturday layering
                    if (ll_2_java[5].getChildCount() == 4) {
                        ll_2_java[5].removeView(view_for_layering_on_dyas_date[5]);
                    }
                    ll_2_java[5].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[5]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[5] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[5][h].getChildCount() == 5) {
                            ll[5][h].removeView(view_for_layering[5][h]);
                        }
                        ll[5][h].addView(view_for_layering_on_which_hasnt_occured[5][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 5 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                   //     ll[5][h].setClickable(false);
                        //and disabling that day button itself
                 //       ll_2_java[5].setClickable(false);
                    }


                    //for sunday layering
                    if (ll_2_java[6].getChildCount() == 4) {
                        ll_2_java[6].removeView(view_for_layering_on_dyas_date[6]);
                    }
                    ll_2_java[6].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[6]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[6] = "N";


                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[6][h].getChildCount() == 5) {
                            ll[6][h].removeView(view_for_layering[6][h]);
                        }
                        ll[6][h].addView(view_for_layering_on_which_hasnt_occured[6][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 6 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
            //            ll[6][h].setClickable(false);
                        //and disabling that day button itself
             //           ll_2_java[6].setClickable(false);
                    }
                }

                if (day2 ==Calendar.TUESDAY ){
                    //for wednasdau layering
                    if (ll_2_java[2].getChildCount() == 4) {
                        ll_2_java[2].removeView(view_for_layering_on_dyas_date[2]);
                    }
                    ll_2_java[2].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[2]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[2] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[2][h].getChildCount() == 5) {
                            ll[2][h].removeView(view_for_layering[2][h]);
                        }
                        ll[2][h].addView(view_for_layering_on_which_hasnt_occured[2][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 2 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                     //   ll[2][h].setClickable(false);
                        //and disabling that day button itself
                   //     ll_2_java[2].setClickable(false);
                    }

                    //for thursday layering
                    if (ll_2_java[3].getChildCount() == 4) {
                        ll_2_java[3].removeView(view_for_layering_on_dyas_date[3]);
                    }
                    ll_2_java[3].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[3]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[3] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[3][h].getChildCount() == 5) {
                            ll[3][h].removeView(view_for_layering[3][h]);
                        }
                        ll[3][h].addView(view_for_layering_on_which_hasnt_occured[3][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 3 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                 //       ll[3][h].setClickable(false);
                        //and disabling that day button itself
                  //      ll_2_java[3].setClickable(false);
                    }
                    //for fridya layering
                    if (ll_2_java[4].getChildCount() == 4) {
                        ll_2_java[4].removeView(view_for_layering_on_dyas_date[4]);
                    }
                    ll_2_java[4].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[4]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[4] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[4][h].getChildCount() == 5) {
                            ll[4][h].removeView(view_for_layering[4][h]);
                        }
                        ll[4][h].addView(view_for_layering_on_which_hasnt_occured[4][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 4 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                 //       ll[4][h].setClickable(false);
                        //and disabling that day button itself
                //        ll_2_java[4].setClickable(false);
                    }


                    //for saturday layering
                    if (ll_2_java[5].getChildCount() == 4) {
                        ll_2_java[5].removeView(view_for_layering_on_dyas_date[5]);
                    }
                    ll_2_java[5].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[5]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[5] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[5][h].getChildCount() == 5) {
                            ll[5][h].removeView(view_for_layering[5][h]);
                        }
                        ll[5][h].addView(view_for_layering_on_which_hasnt_occured[5][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 5 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                   //     ll[5][h].setClickable(false);
                        //and disabling that day button itself
                   //     ll_2_java[5].setClickable(false);
                    }


                    //for sunday layering
                    if (ll_2_java[6].getChildCount() == 4) {
                        ll_2_java[6].removeView(view_for_layering_on_dyas_date[6]);
                    }
                    ll_2_java[6].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[6]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[6] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[6][h].getChildCount() == 5) {
                            ll[6][h].removeView(view_for_layering[6][h]);
                        }
                        ll[6][h].addView(view_for_layering_on_which_hasnt_occured[6][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 6 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                 //       ll[6][h].setClickable(false);
                        //and disabling that day button itself
                //        ll_2_java[6].setClickable(false);
                    }

                }

                if (day2 ==Calendar.WEDNESDAY ){

                    //for thursday layering
                    if (ll_2_java[3].getChildCount() == 4) {
                        ll_2_java[3].removeView(view_for_layering_on_dyas_date[3]);
                    }
                    ll_2_java[3].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[3]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[3] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[3][h].getChildCount() == 5) {
                            ll[3][h].removeView(view_for_layering[3][h]);
                        }
                        ll[3][h].addView(view_for_layering_on_which_hasnt_occured[3][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 3 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                   //     ll[3][h].setClickable(false);
                        //and disabling that day button itself
                  //      ll_2_java[3].setClickable(false);
                    }
                    //for fridya layering
                    if (ll_2_java[4].getChildCount() == 4) {
                        ll_2_java[4].removeView(view_for_layering_on_dyas_date[4]);
                    }
                    ll_2_java[4].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[4]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[4] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[4][h].getChildCount() == 5) {
                            ll[4][h].removeView(view_for_layering[4][h]);
                        }
                        ll[4][h].addView(view_for_layering_on_which_hasnt_occured[4][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 4 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                 //       ll[4][h].setClickable(false);
                        //and disabling that day button itself
                //        ll_2_java[4].setClickable(false);
                    }


                    //for saturday layering
                    if (ll_2_java[5].getChildCount() == 4) {
                        ll_2_java[5].removeView(view_for_layering_on_dyas_date[5]);
                    }
                    ll_2_java[5].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[5]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[5] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[5][h].getChildCount() == 5) {
                            ll[5][h].removeView(view_for_layering[5][h]);
                        }
                        ll[5][h].addView(view_for_layering_on_which_hasnt_occured[5][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 5 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                   //     ll[5][h].setClickable(false);
                        //and disabling that day button itself
                  //      ll_2_java[5].setClickable(false);
                    }


                    //for sunday layering
                    if (ll_2_java[6].getChildCount() == 4) {
                        ll_2_java[6].removeView(view_for_layering_on_dyas_date[6]);
                    }
                    ll_2_java[6].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[1]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[6] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[6][h].getChildCount() == 5) {
                            ll[6][h].removeView(view_for_layering[6][h]);
                        }
                        ll[6][h].addView(view_for_layering_on_which_hasnt_occured[6][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 6 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                 //       ll[6][h].setClickable(false);
                        //and disabling that day button itself
                 //       ll_2_java[6].setClickable(false);
                    }
                }



                if (day2 ==Calendar.THURSDAY ){
                    //for fridya layering
                    if (ll_2_java[4].getChildCount() == 4) {
                        ll_2_java[4].removeView(view_for_layering_on_dyas_date[4]);
                    }
                    ll_2_java[4].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[4]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[4] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[4][h].getChildCount() == 5) {
                            ll[4][h].removeView(view_for_layering[4][h]);
                        }
                        ll[4][h].addView(view_for_layering_on_which_hasnt_occured[4][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 4 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                 //       ll[4][h].setClickable(false);
                        //and disabling that day button itself
                 //       ll_2_java[4].setClickable(false);
                    }


                    //for saturday layering
                    if (ll_2_java[5].getChildCount() == 4) {
                        ll_2_java[5].removeView(view_for_layering_on_dyas_date[5]);
                    }
                    ll_2_java[5].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[5]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[5] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[5][h].getChildCount() == 5) {
                            ll[5][h].removeView(view_for_layering[5][h]);
                        }
                        ll[5][h].addView(view_for_layering_on_which_hasnt_occured[5][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 5 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                  //      ll[5][h].setClickable(false);
                        //and disabling that day button itself
                  //      ll_2_java[5].setClickable(false);
                    }


                    //for sunday layering
                    if (ll_2_java[6].getChildCount() == 4) {
                        ll_2_java[6].removeView(view_for_layering_on_dyas_date[6]);
                    }
                    ll_2_java[6].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[6]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[6] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[6][h].getChildCount() == 5) {
                            ll[6][h].removeView(view_for_layering[6][h]);
                        }
                        ll[6][h].addView(view_for_layering_on_which_hasnt_occured[6][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 6 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                    //    ll[6][h].setClickable(false);
                        //and disabling that day button itself
                    //    ll_2_java[6].setClickable(false);
                    }
                }

                if (day2 ==Calendar.FRIDAY ){
                    if (ll_2_java[5].getChildCount() == 4) {
                        ll_2_java[5].removeView(view_for_layering_on_dyas_date[5]);
                    }
                    ll_2_java[5].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[5]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[5] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[5][h].getChildCount() == 5) {
                            ll[5][h].removeView(view_for_layering[5][h]);
                        }
                        ll[5][h].addView(view_for_layering_on_which_hasnt_occured[5][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 5 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                    //    ll[5][h].setClickable(false);
                        //and disabling that day button itself
                //        ll_2_java[5].setClickable(false);
                    }


                    //for sunday layering
                    if (ll_2_java[6].getChildCount() == 4) {
                        ll_2_java[6].removeView(view_for_layering_on_dyas_date[6]);
                    }
                    ll_2_java[6].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[6]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[6] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[6][h].getChildCount() == 5) {
                            ll[6][h].removeView(view_for_layering[6][h]);
                        }
                        ll[6][h].addView(view_for_layering_on_which_hasnt_occured[6][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 6 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                    //    ll[6][h].setClickable(false);
                        //and disabling that day button itself
                  //      ll_2_java[6].setClickable(false);
                    }
                }

                if (day2 ==Calendar.SATURDAY ){

                    //for sunday layering
                    if (ll_2_java[6].getChildCount() == 4) {
                        ll_2_java[6].removeView(view_for_layering_on_dyas_date[6]);
                    }
                    ll_2_java[6].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[6]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[6] = "N";

                    for ( int h = 0; h < no_of_perod; h++) {

                        if (ll[6][h].getChildCount() == 5) {
                            ll[6][h].removeView(view_for_layering[6][h]);
                        }
                        ll[6][h].addView(view_for_layering_on_which_hasnt_occured[6][h]);

                        boolean inserting_perticular_data = myDb_2.updateData_perticular_column(String.valueOf(h + 1), 6 , "NOT_OCCURED_YET", name_database_table_as_week_year);

                        // //disabling the click listener on all the items of that day
                  //      ll[6][h].setClickable(false);
                        //and disabling that day button itself
                 //       ll_2_java[6].setClickable(false);
                    }
                }

                //I am doin it inside if loop saying is it current week ..so that it works only for current week
//---------------------------------------seeting the indicater to the front of the day which is current day
                  LinearLayout indicaters_linear_layout[] = new LinearLayout[8];

                indicaters_linear_layout[Calendar.MONDAY] = (LinearLayout)findViewById(R.id.mon_indicater);
                indicaters_linear_layout[Calendar.TUESDAY] = (LinearLayout)findViewById(R.id.tue_indicater);
                indicaters_linear_layout[Calendar.WEDNESDAY] = (LinearLayout)findViewById(R.id.wed_indicater);
                indicaters_linear_layout[Calendar.THURSDAY] = (LinearLayout)findViewById(R.id.thu_indicater);
                indicaters_linear_layout[Calendar.FRIDAY] = (LinearLayout)findViewById(R.id.fri_indicater);
                indicaters_linear_layout[Calendar.SATURDAY] = (LinearLayout)findViewById(R.id.sat_indicater);
                indicaters_linear_layout[Calendar.SUNDAY] = (LinearLayout)findViewById(R.id.sun_indicater);


                Calendar current_calendar33 = Calendar.getInstance();
                int current_day = current_calendar33.get(Calendar.DAY_OF_WEEK);
                indicaters_linear_layout[current_day].setBackgroundResource(R.drawable.green_circle_for_top_bar_and_prev_next_buttons);


            }


            //---------------------------------------click listener fir layering for not ocuured elemnets ...it just gives a toast message
            for (int q = 0; q < 7; q++) {
                for (int h = 0; h < no_of_perod; h++) {
                    final int finalQ = q;
                    view_for_layering_on_which_hasnt_occured[q][h].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {

                            Toast.makeText(EditorActivity.this,   Str_arr_for_day_names_toast[finalQ] +" hasn't occured yet",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
            }else{


            //I am doin it inside else loop saying is it  not current week ..so that it removes the green background which was not removed while switching between weeks
//---------------------------------------seeting the indicater to the front of the day which is current day
            LinearLayout indicaters_linear_layout[] = new LinearLayout[8];

            indicaters_linear_layout[Calendar.MONDAY] = (LinearLayout)findViewById(R.id.mon_indicater);
            indicaters_linear_layout[Calendar.TUESDAY] = (LinearLayout)findViewById(R.id.tue_indicater);
            indicaters_linear_layout[Calendar.WEDNESDAY] = (LinearLayout)findViewById(R.id.wed_indicater);
            indicaters_linear_layout[Calendar.THURSDAY] = (LinearLayout)findViewById(R.id.thu_indicater);
            indicaters_linear_layout[Calendar.FRIDAY] = (LinearLayout)findViewById(R.id.fri_indicater);
            indicaters_linear_layout[Calendar.SATURDAY] = (LinearLayout)findViewById(R.id.sat_indicater);
            indicaters_linear_layout[Calendar.SUNDAY] = (LinearLayout)findViewById(R.id.sun_indicater);


            Calendar current_calendar33 = Calendar.getInstance();
            int current_day = current_calendar33.get(Calendar.DAY_OF_WEEK);
            indicaters_linear_layout[current_day].setBackgroundResource(R.drawable.transparent_circle);




            //////////////////////////////////////////////////////////////////////--------------------------------------------------------------------------
        }

        return tv;

    }


    public void setting_subjects_to_periods_as_per_time_table(int no_of_period1, int no_of_days, String name_database_table_as_week_year, TextView[][] tv) {
        //------------------------------------------------------------------------------------SETTING THE SUBJECTS AS PER THE TIMETABLE.......IF THERE WAS ANY TIMETABLE GIVEN

        //execute below lines of code only when it was not skipped from the timae table ASKING activity


        //giving a variable for status which will tell if a timetable has alrady been assigned or not


        //inserting all values for TIME_TABLE_active status row as NULL  so later specific column can be updated
        //inserting default _status_in_db in below lines
        //row is 3 more than total period because we have a already a row for other variable after total no of periods
        boolean inserting_TIME_TABLE_ative_status_in_db = myDb_2.insertData(String.valueOf(this.no_of_period1 + 3), "NO_TIME_TABLE_GIVEN", "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", name_database_table_as_week_year);

//now checking the same status
        Cursor res_88 = myDb_2.getAllData(name_database_table_as_week_year);
        res_88.move(this.no_of_period1 + 3);
//and checking whether the

        if (res_88.getString(1).equals("NO_TIME_TABLE_GIVEN")) {

            //execute below lines of code only when it was not skipped from the timae table ASKING activity
            //if its skiipped from time table ASKING activity then it would have only one element that is number of period

            Cursor res44 = myDb.getAllData();
            int row_count = res44.getCount();

            if (row_count !=1){

                //now we neeed to check if ther was any timetable given by the user or not
                //now retriving the same varaiable inserted above for it will decide what should be inside textviews
                Cursor res2 = myDb.getAllData();
                res2.move(5);
                int status_of_time_table = Integer.parseInt(res2.getString(2));


                if (status_of_time_table == 0) {
                    //do nothing
                    //remember i am passing column no as 1 only nor 1+1 because my database 2 method od updating perticular data does that work itself ...so it will be inserted to monday column
                    boolean updating_TIME_TABLE_ative_status_in_db = myDb_2.updateData_perticular_column(String.valueOf(this.no_of_period1 + 3), 0, "TIME_TABLE_GIVEN", name_database_table_as_week_year);

                } else {
                    Cursor res__ = myDb.getAllData();
                    res__.move(6);
                    int latest_version = Integer.parseInt(res__.getString(2));
                    latest_version = latest_version - 1;


                    int a, b;
                    for (a = 0; a < no_of_period1; a++) {
                        for (b = 0; b < no_of_days; b++) {
                            Cursor res_33 = myDb_3.getAllData("first_ever_table_made_for_time_table_data" + latest_version);
                            res_33.move(a + 1);
                            //  if (!res_33.getString(b+1).equals("no subject added")) {
                            String sub_name = res_33.getString(b + 1);
                            tv[b][a].setText(sub_name);
                            res_33.close();
                            // }
                        }
                    }
                    //remember i am passing column no as 1 only nor 1+1 because my database 2 method od updating perticular data does that work itself ...so it will be inserted to monday column
                    boolean updating_TIME_TABLE_ative_status_in_db = myDb_2.updateData_perticular_column(String.valueOf(this.no_of_period1 + 3), 0, "TIME_TABLE_GIVEN", name_database_table_as_week_year);
//now attach this perticular timetable with this week
                    boolean inserting_TIME_TABLE_name_in_db = myDb_2.insertData(String.valueOf(this.no_of_period1 + 4), "first_ever_table_made_for_time_table_data" + latest_version, "NULL", "NULL", "NULL", "NULL", "NULL", "NULL", name_database_table_as_week_year);

                }}
        } else {
//now we know that time table is already attached with his perticular week no just need to use it
            Cursor res_44 = myDb_2.getAllData(name_database_table_as_week_year);
            res_44.move(this.no_of_period1 + 4);
            String attached_time_table_name = res_44.getString(1);


            int a, b;
            for (a = 0; a < no_of_period1; a++) {
                for (b = 0; b < no_of_days; b++) {
                    Cursor res_33 = myDb_3.getAllData(attached_time_table_name);
                    res_33.move(a + 1);
                    //  if (!res_33.getString(b+1).equals("no subject added")) {
                    String sub_name = res_33.getString(b + 1);
                    tv[b][a].setText(sub_name);
                    res_33.close();
                    // }
                }
            }


        }






    }
}










package com.example.android.testin3;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.testin3.data.DatabaseHelper;
import com.example.android.testin3.data.DatabaseHelper_2;
import com.example.android.testin3.data.DatabaseHelper_3;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class attendence_predict_2 extends AppCompatActivity {



    DatabaseHelper myDb_1;

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

    float total_no_of_periods_marked_present = 0;
    float total_no_of_periods_marked_ABSENT = 0;
    float total_no_of_periods_marked_HOLIDAY = 0;

    float total_attendence=0f;



    final String[][][] status_1 = new String[1][1][1];

    final String[][][] status_2 = new String[1][1][1];

    final String[][][] status_3 = new String[1][1][1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_predict_2);


        int data[] = getIntent().getIntArrayExtra("ARRAY_FOR_2_DATA");

        no_of_period1 =data[0];




        final TextView tv_for_week_name_and_year_textview = (TextView) findViewById(R.id.week_no_xml);
        //      tv_for_week_name_and_year_textview.setBackgroundResource();

        myDb_1 = new DatabaseHelper(this);

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



//setting that name in the top middle textview
        String    text_for_tv_on_the_top = "Week No:- "+ week_no_of_year + "(of " + year_of_the_week +") and " + week_no_of_the_month + "(of " + current_month +")";
        tv_for_week_name_and_year_textview.setText(text_for_tv_on_the_top);
        final String name_database_table_as_week_year = "weekNO" + week_no_of_year + "_" + "year" + year_of_the_week;



//to add the blue color to the  previous button
        final LinearLayout color_changing_bckgrnd_for_prev_button = (LinearLayout) findViewById(R.id.this_aint_container_just_color_changing_backgrnd_for_prev);
        color_changing_bckgrnd_for_prev_button.setBackgroundResource(R.drawable.blue_circle_for_next_button);

        //---------------------------------------------------
        final int first_week= current_week_no_of_year;
        boolean first_time_1 = true;
       // final String[][][] status_1 = new String[1][1][1];
        status_1[0] = new String[7][no_of_period1];

        final int second_week = current_week_no_of_year + 1;
        final boolean[] first_time_2 = {true};
      //  final String[][][] status_2 = new String[1][1][1];
        status_2[0] = new String[7][no_of_period1];

        final int third_week = current_week_no_of_year+2;
        final boolean[] first_time_3 = {true};
     //   final String[][][] status_3 = new String[1][1][1];
        status_3[0] = new String[7][no_of_period1];



//---------------------------------------------------------------
//todo-jhbj


        //now the loading of period elements and behavior to date/day column is handeled by a single method
        status_1[0] = adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, true,week_no_of_year, year_of_the_week,name_database_table_as_week_year,true, status_1[0]);
        first_time_1=false;


/////////////////////////////////////////////////////////////////////////////////////////--------------------------------------------------------------------------------------------
        final ImageView next_button_java = (ImageView) findViewById(R.id.next_button);

        next_button_java.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                //to remove the background color blue from the previous button if it had any
//                prev_button_java.setBackgroundColor(Color.RED);
                final LinearLayout color_changing_bckgrnd_for_prev_button = (LinearLayout) findViewById(R.id.this_aint_container_just_color_changing_backgrnd_for_prev);
                color_changing_bckgrnd_for_prev_button.setBackgroundResource(R.drawable.green_circle_for_top_bar_and_prev_next_buttons);


/*

                //below if else is for the purpose that next button is functioning only when the next week is not a future week
                if (week_no_of_year != current_week_no_of_year) {
*/
                if (week_no_of_year != third_week) {

                    //clearing the previus periods from the parent view alraedy present...if its not done new periods will apear along with the already ones
                    LinearLayout ll_of_the_view_already_in_xml = (LinearLayout) findViewById(R.id.Lin_layout22_xml);
                    ll_of_the_view_already_in_xml.removeAllViews();


                    //same for days date container
                    //clearing the previus periods from the parent view alraedy present...if its not done new periods will apear along with the already ones
                    LinearLayout ll_of_the_view_already_in_xmlfor_days_sate = (LinearLayout) findViewById(R.id.ll_5_xml);
                    ll_of_the_view_already_in_xmlfor_days_sate.removeAllViews();


                    // getting the last week no of the year and then comparing it with the next week(above variable)
                    Calendar next = Calendar.getInstance();
                    next.set(Calendar.YEAR, year);
                    next.set(Calendar.MONTH, Calendar.DECEMBER);
                    next.set(Calendar.DATE, 28);
                    int last_week_no_of_current_year = next.get(Calendar.WEEK_OF_YEAR);


                    week_no_of_year = week_no_of_year + 1;
                    String next_name_database_table_as_week_year = "weekNO" + week_no_of_year + "_" + "year" + year_of_the_week;


                    final Calendar next2 = Calendar.getInstance();
                    next2.set(Calendar.WEEK_OF_YEAR, week_no_of_year);

                    week_no_of_the_month = next2.get(Calendar.WEEK_OF_MONTH);

                    //for getting the month name a sstring
                    SimpleDateFormat month_date = new SimpleDateFormat("MMM");
                    current_month = month_date.format(next2.getTime());

                    String text_for_tv_on_the_top = "Week No:- " + week_no_of_year + "(of " + year_of_the_week + ") and " + week_no_of_the_month + "(of " + current_month + ")";
                    tv_for_week_name_and_year_textview.setText(text_for_tv_on_the_top);


                    //todo-jhbj

                    if (week_no_of_year == second_week) {
                        if (first_time_2[0] == true) {
                            //now the loading of period elements and behavior to date/day column is handeled by a single method
                            status_2[0] = adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, false, week_no_of_year, year_of_the_week, name_database_table_as_week_year, true, status_2[0]);
                            first_time_2[0] = false;
                        } else {

                            status_2[0] = adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, false, week_no_of_year, year_of_the_week, name_database_table_as_week_year, false, status_2[0]);

                        }

                    }


                    if (week_no_of_year == third_week) {
                        if (first_time_3[0] == true) {
                            //now the loading of period elements and behavior to date/day column is handeled by a single method
                            status_3[0] = adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, false, week_no_of_year, year_of_the_week, name_database_table_as_week_year, true, status_3[0]);
                            first_time_3[0] = false;
                        } else {

                            status_3[0] = adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, false, week_no_of_year, year_of_the_week, name_database_table_as_week_year, false, status_3[0]);

                        }

                        final LinearLayout color_changing_bckgrnd_for_next_button = (LinearLayout) findViewById(R.id.this_aint_container_just_color_changing_backgrnd_for_next);
                        color_changing_bckgrnd_for_next_button.setBackgroundResource(R.drawable.blue_circle_for_next_button);

                    }


                }else{

                    Toast.makeText(attendence_predict_2.this,  "editer can not be opened for more weeks ",
                            Toast.LENGTH_SHORT).show();


                }
            }
        });



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        final ImageView prev_button_java = (ImageView) findViewById(R.id.previous_button);

        prev_button_java.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                //to remove the background color blue from the next button if it had any
                final LinearLayout color_changing_bckgrnd_for_next_button = (LinearLayout) findViewById(R.id.this_aint_container_just_color_changing_backgrnd_for_next);
                color_changing_bckgrnd_for_next_button.setBackgroundResource(R.drawable.green_circle_for_top_bar_and_prev_next_buttons);



                if (week_no_of_year !=first_week) {

//clearing the previus periods from the parent view alraedy present...if its not done new periods will apear along with the already ones
                    LinearLayout ll_of_the_view_already_in_xml = (LinearLayout) findViewById(R.id.Lin_layout22_xml);
                    ll_of_the_view_already_in_xml.removeAllViews();


                    //same for days date container
                    //clearing the previus periods from the parent view alraedy present...if its not done new periods will apear along with the already ones
                    LinearLayout ll_of_the_view_already_in_xmlfor_days_sate = (LinearLayout) findViewById(R.id.ll_5_xml);
                    ll_of_the_view_already_in_xmlfor_days_sate.removeAllViews();


                    // getting the last week no of the year and then comparing it with the next week(above variable)
                    Calendar next = Calendar.getInstance();
                    next.set(Calendar.YEAR, year);
                    next.set(Calendar.MONTH, Calendar.DECEMBER);
                    next.set(Calendar.DATE, 28);
                    int last_week_no_of_current_year = next.get(Calendar.WEEK_OF_YEAR);


                    week_no_of_year = week_no_of_year - 1;
                    String next_name_database_table_as_week_year = "weekNO" + week_no_of_year + "_" + "year" + year_of_the_week;


                    final Calendar next2 = Calendar.getInstance();
                    next2.set(Calendar.WEEK_OF_YEAR, week_no_of_year);

                    week_no_of_the_month = next2.get(Calendar.WEEK_OF_MONTH);

                    //for getting the month name a sstring
                    SimpleDateFormat month_date = new SimpleDateFormat("MMM");
                    current_month = month_date.format(next2.getTime());

                    String text_for_tv_on_the_top = "Week No:- " + week_no_of_year + "(of " + year_of_the_week + ") and " + week_no_of_the_month + "(of " + current_month + ")";
                    tv_for_week_name_and_year_textview.setText(text_for_tv_on_the_top);


                    //todo-jhbj
                    if (week_no_of_year == first_week) {

                        //now the loading of period elements and behavior to date/day column is handeled by a single method
                        status_1[0] = adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, true, week_no_of_year, year_of_the_week, name_database_table_as_week_year, false, status_1[0]);


                        final LinearLayout color_changing_bckgrnd_for_prev_button = (LinearLayout) findViewById(R.id.this_aint_container_just_color_changing_backgrnd_for_prev);
                        color_changing_bckgrnd_for_prev_button.setBackgroundResource(R.drawable.blue_circle_for_next_button);
                    }

                    if (week_no_of_year == second_week) {

                        //now the loading of period elements and behavior to date/day column is handeled by a single method
                        status_2[0] = adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(no_of_period1, 7, false, week_no_of_year, year_of_the_week, name_database_table_as_week_year, false, status_2[0]);

                    }

                }else{

                    Toast.makeText(attendence_predict_2.this,  "editer can not be opened for previous weeks ",
                            Toast.LENGTH_SHORT).show();


                }

            }
        });




///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        calculate_attendance(data);



    }

    private void calculate_attendance(int[] data) {



        total_no_of_periods_marked_present = data[2];
        total_no_of_periods_marked_ABSENT = data[1] - data[2];

/////=======================================================================================================================================================================

        total_attendence = ((total_no_of_periods_marked_present) / (total_no_of_periods_marked_present + total_no_of_periods_marked_ABSENT)) * 100;


        TextView bottom_tv = (TextView)findViewById(R.id.bottom_tv);
        bottom_tv.setText(" the total attendance till yesterday is " +  String.format("%.2f",total_attendence )+" % \n + the marked attendence = " +  String.format("%.2f", total_attendence  ) +" %");



    }

    //#######################################################################################################################################################################

    private void update_the_bootom_tv(String[][] status) {

        float marked_present=0;
        float marked_absent =0;
        float marked_attendence = 0f;

        marked_absent = (int) total_no_of_periods_marked_ABSENT;
        marked_present = (int) total_no_of_periods_marked_present;



        int i, k;
        for (k = 0; k < 7; k++) {
            for (i = 0; i < no_of_period1; i++) {
                if(status_1[0][k][i] == "p"){
                    marked_present= marked_present+1;
                }
                if(status_1[0][k][i] == "a"){
                    marked_absent= marked_absent+1;
                }
                if(status_2[0][k][i] == "p"){
                    marked_present= marked_present+1;
                }
                if(status_2[0][k][i] == "a"){
                    marked_absent= marked_absent+1;
                }
                if(status_3[0][k][i] == "p"){
                    marked_present= marked_present+1;
                }
                if(status_3[0][k][i] == "a"){
                    marked_absent= marked_absent+1;
                }

            }
            }



        marked_attendence = ((marked_present) / (marked_absent + marked_present)) * 100;

        TextView bottom_tv = (TextView)findViewById(R.id.bottom_tv);
        bottom_tv.setText(" the total attendance till yesterday is " +  String.format("%.2f",total_attendence )+" % \n + the marked attendence = " +  String.format("%.2f", marked_attendence  ) +" %");

    }


    private void adding_days_and_date_to_column_of_days(int week_no_of_year, int year_of_the_week, TextView[] tv_date) {

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
//todo-  use this last parameter i have created down below

    public String[][] adding_views_using_2d_array_and_loading_bahvior_to_date_day_column(final int no_of_perod, int no_of_days, Boolean is_it_current_week, int week_no_of_year, int  year_of_the_week , String name_database_table_as_week_year , boolean is_it_the_first_time_opening_for_this_week,final String[][] status) {


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


        LinearLayout ll_of_the_view_already_in_xml = (LinearLayout) findViewById(R.id.Lin_layout22_xml);
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
//todo

//---------------------------------------------------------------------------------------
//I am using this new array to usea sreplacement for the dataabse I was using for toggle in previous code of editer avtivity
        if(is_it_the_first_time_opening_for_this_week == true) {


            //  giving default as Holiday to each of them
            for (int f = 0; f < no_of_days; f++) {
                for (int g = 0; g < no_of_perod; g++) {

                    status[f][g] = "h";


                    iv[f][g].setBackgroundColor(Color.GRAY);
                    iv_for_A[f][g].setBackgroundColor(Color.parseColor("#909090"));
                    iv_for_P[f][g].setBackgroundColor(Color.parseColor("#909090"));
                    ll[f][g].addView(view_for_layering[f][g]);


                }
            }

        }

/////////////////////////////////////////////////////////////////////////------------------------------------------------------

        //setting color changing toggle behavior with click listeners to every period of every day
        //-also for loop for seeting click listrnrers to the P and A imageviews
        //for loop for setting toggle behaviour


        int m,j;
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

                            if (status[finalM][finalJ] == "h") {

                                iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));
                                iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));
                                ll[finalM][finalJ].removeView(view_for_layering[finalM][finalJ]);

                                //changing the status to absent
                                status[finalM][finalJ] = "a";


                                Toast.makeText(attendence_predict_2.this, strs[finalJ] + "period is activated and marked absent by default",
                                        Toast.LENGTH_SHORT).show();


                               // marked_absent = marked_absent+1;
                                update_the_bootom_tv(status);


                            } else


                            //disable
                            {


                                iv[finalM][finalJ].setBackgroundColor(Color.GRAY);
                                iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#909090"));
                                iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#909090"));
                                ll[finalM][finalJ].addView(view_for_layering[finalM][finalJ]);

                                //changing the status to holiday
                                status[finalM][finalJ] = "h";

                                Toast.makeText(attendence_predict_2.this, strs[finalJ] + "period is deactivated/dissmissed/canceled",
                                        Toast.LENGTH_SHORT).show();

                                // task  for the bottom tv
                                if (status[finalM][finalJ] == "a") {
                                    //     marked_absent = marked_absent -1;
                                    update_the_bootom_tv(status);
                                }else if (  status[finalM][finalJ] == "p"  ){
                                    //    marked_present = marked_present -1;
                                    update_the_bootom_tv(status);
                                }
                            }


                        }

                        if (String_status_for_days_for_toasts[finalM] == "D") {
                            Toast.makeText(attendence_predict_2.this, "Activate " + Str_arr_for_day_names_toast[finalM] + " first",
                                    Toast.LENGTH_SHORT).show();

                        }

                        if (String_status_for_days_for_toasts[finalM] == "N") {
                            Toast.makeText(attendence_predict_2.this, "attendance can not  be predicted for class that has occured ",
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                });
//---------------------------------------click listener fir tv for layering...it just removes itself from ll
                view_for_layering[m][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {


                        if (String_status_for_days_for_toasts[finalM] == "A") {


                         //   marked_absent = marked_absent+1;


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

                            status[finalM][finalJ] = "a";

                            Toast.makeText(attendence_predict_2.this, strs[finalJ] + "period is activated and marked absent by default",
                                    Toast.LENGTH_SHORT).show();


                            update_the_bootom_tv(status);
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
                            Toast.makeText(attendence_predict_2.this, "Activate " + Str_arr_for_day_names_toast[finalM] + " first",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });


//----------------------------------------------------------

                iv_for_P[m][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
//below code is kind of toggling

                        if (status[finalM][finalJ] == "p") {

                            iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));
                            iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#999999"));
                            iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));

                            status[finalM][finalJ] = "a";

                            Toast.makeText(attendence_predict_2.this, strs[finalJ] + " of " + Str_arr_for_day_names_toast[finalM] + "is marked absent",
                                    Toast.LENGTH_SHORT).show();


                          //  marked_absent = marked_absent+1;
                            update_the_bootom_tv(status);
                        } else
                            /*              (status[finalM][finalJ] == "a") */ {

                            iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#388E3C"));
                            iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#388E3C"));
                            iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#909090"));

                            status[finalM][finalJ] = "p";

                            Toast.makeText(attendence_predict_2.this, strs[finalJ] + " of " + Str_arr_for_day_names_toast[finalM] + " is marked present",
                                    Toast.LENGTH_SHORT).show();


                        //    marked_present = marked_present+1;
                            update_the_bootom_tv(status);
                        }


                    }
                });
                //--------------------------
                iv_for_A[m][j].setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
//below code is kind of toggling

                        if (status[finalM][finalJ] == "p") {

                            iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));
                            iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#909090"));
                            iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#C62828"));

                            status[finalM][finalJ] = "a";

                            Toast.makeText(attendence_predict_2.this, strs[finalJ] + " of " + Str_arr_for_day_names_toast[finalM] + " is marked absent",
                                    Toast.LENGTH_SHORT).show();


                          //  marked_absent = marked_absent+1;
                            update_the_bootom_tv(status);
                        } else
                            /*                  (status[finalM][finalJ] == "a")*/ {

                            iv[finalM][finalJ].setBackgroundColor(Color.parseColor("#388E3C"));
                            iv_for_P[finalM][finalJ].setBackgroundColor(Color.parseColor("#388E3C"));
                            iv_for_A[finalM][finalJ].setBackgroundColor(Color.parseColor("#909090"));

                            status[finalM][finalJ] = "p";

                            Toast.makeText(attendence_predict_2.this, strs[finalJ] + " of " + Str_arr_for_day_names_toast[finalM] + " is marked present",
                                    Toast.LENGTH_SHORT).show();


                     //       marked_present = marked_present+1;
                            update_the_bootom_tv(status);
                        }


                    }
                });


//----------------------------sttin on touch to both so that their border glow of each(as to show touch  )


                final int finalM1 = m;
                final int finalJ1 = j;
                iv_for_A[m][j].setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            Rl_container_for_ripple_on_A[finalM1][finalJ1].setBackgroundColor(Color.parseColor("#66BA68C8"));
                            return false;
                        } else if (event.getAction() == MotionEvent.ACTION_UP || (event.getAction() == MotionEvent.ACTION_CANCEL)) {
                            Rl_container_for_ripple_on_A[finalM1][finalJ1].setBackgroundColor(Color.parseColor("#f2f2f2"));

                            return false;
                        }
                        return false;
                    }
                });


                iv_for_P[m][j].setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == MotionEvent.ACTION_DOWN) {

                            Rl_container_for_ripple_on_P[finalM1][finalJ1].setBackgroundColor(Color.parseColor("#66BA68C8"));
                            return false;
                        } else if (event.getAction() == MotionEvent.ACTION_UP || (event.getAction() == MotionEvent.ACTION_CANCEL)) {
                            Rl_container_for_ripple_on_P[finalM1][finalJ1].setBackgroundColor(Color.parseColor("#f2f2f2"));

                            return false;
                        }
                        return false;
                    }
                });


            }
        }


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
        LinearLayout ll_container_for_days_date = (LinearLayout) findViewById(R.id.ll_5_xml);

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
        adding_days_and_date_to_column_of_days(week_no_of_year, year_of_the_week, tv_for_date);



//============================================================================================================================================

        //////////////////////////////////////////////////////////////////--------------------------------------------------------------------------

        //setting the behavior to days/date column



    /*    int t;
        for (t = 0; t < 7; t++) {

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
        }*/


/*        //////////a final and array of boolean variables is used in palce of a simple boolean variable because to include it otherwise, it
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
        }*/
//-----------------======================
        //making an status array for days

        final String status_for_days[] = new String[no_of_days];

        //giving all of them default as deactivated
        for (int v = 0; v < 7; v++) {

            status_for_days[v] = "a";
        }

//---------------------------------------------------------------------------------------
        //for loop for setting toggle behaviour
        for (int f = 0; f < 7; f++) {
            final int finalF = f;

            ll_2_java[finalF].setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    if(String_status_for_days_for_toasts[finalF] != "N") {


                        //below if else works as a toggle....it makes view work as a toggle switch
                        //enable
                        if ( status_for_days[finalF] == "d") {
                            iv_2[finalF].setBackgroundColor(Color.GREEN);
                            // if()
                            ll_2_java[finalF].removeView(view_for_layering_on_dyas_date[finalF]);

                            //activate it
                            status_for_days[finalF] = "a";


                            Toast.makeText(attendence_predict_2.this, Str_arr_for_day_names_toast[finalF] + " is activated/working day",
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
                                  //  marked_absent = marked_absent+1;

                                }

                                status[finalF][h] = "a";

                                // //enabling the click listener on all the items of that day
                                ll[finalF][h].setClickable(true);
                                view_for_layering[finalF][h].setClickable(true);
                                update_the_bootom_tv(status);
                            }
                        } else
                        //disable
                        {

                            iv_2[finalF].setBackgroundColor(Color.GRAY);
                            // if()
                            ll_2_java[finalF].addView(view_for_layering_on_dyas_date[finalF]);

                            //deactivated
                            status_for_days[finalF] = "d";

                            Toast.makeText(attendence_predict_2.this, Str_arr_for_day_names_toast[finalF] + " is deactivated/non working day",
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



                                 /*   // task  for the bottom tv
                                    if (status[finalF][h] == "a") {
                                      //  marked_absent = marked_absent -1;
                                        update_the_bootom_tv(status);
                                    }else if (  status[finalF][h] == "p"  ){
                                     //   marked_present = marked_present -1;

                                    }*/
                                }

                                status[finalF][h] = "h";

                                // //disabling the click listener on all the items of that day
                                // ll[finalF][h].setClickable(false);
                                //view_for_layering[finalF][h].setClickable(false);


                                update_the_bootom_tv(status);

                            }

                        }
                    }else{ Toast.makeText(attendence_predict_2.this,   Str_arr_for_day_names_toast[finalF] +" has occcured ",
                            Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }


        if(is_it_the_first_time_opening_for_this_week==true) {
            if (is_it_current_week == true) {


//deactivating the days of future

                Calendar current_calendarm = Calendar.getInstance();
                int dayy = current_calendarm.get(Calendar.DAY_OF_WEEK);


                if (dayy == Calendar.MONDAY) {

                    //for tuesday to sunday layering
                    for (int r = 1; r < 7; r++) {

                        iv_2[r].setBackgroundColor(Color.GRAY);
                        if (ll_2_java[r].getChildCount() == 3) {
                            ll_2_java[r].addView(view_for_layering_on_dyas_date[r]);
                        }


                        status_for_days[r] = "d";

                        String_status_for_days_for_toasts[r] = "D";
//------------------------------------

                        //diasbling all the periods of that perticular day
                        int h;
                        for (h = 0; h < no_of_perod; h++) {

                            iv[r][h].setBackgroundColor(Color.GRAY);
                            iv_for_A[r][h].setBackgroundColor(Color.parseColor("#909090"));
                            iv_for_P[r][h].setBackgroundColor(Color.parseColor("#909090"));

                            if (ll[r][h].getChildCount() == 4) {
                                ll[r][h].addView(view_for_layering[r][h]);
                            }


                            status[r][h] = "h";
                        }
                    }
                }


                if (dayy == Calendar.TUESDAY) {

//for wed to sunday layering
                    for (int r = 2; r < 7; r++) {

                        iv_2[r].setBackgroundColor(Color.GRAY);
                        if (ll_2_java[r].getChildCount() == 3) {
                            ll_2_java[r].addView(view_for_layering_on_dyas_date[r]);
                        }


                        status_for_days[r] = "d";

                        String_status_for_days_for_toasts[r] = "D";
//------------------------------------

                        //diasbling all the periods of that perticular day
                        int h;
                        for (h = 0; h < no_of_perod; h++) {

                            iv[r][h].setBackgroundColor(Color.GRAY);
                            iv_for_A[r][h].setBackgroundColor(Color.parseColor("#909090"));
                            iv_for_P[r][h].setBackgroundColor(Color.parseColor("#909090"));

                            if (ll[r][h].getChildCount() == 4) {
                                ll[r][h].addView(view_for_layering[r][h]);
                            }


                            status[r][h] = "h";
                        }
                    }
                }

                if (dayy == Calendar.WEDNESDAY) {

                    //for thur to sunday layering
                    for (int r = 3; r < 7; r++) {

                        iv_2[r].setBackgroundColor(Color.GRAY);
                        if (ll_2_java[r].getChildCount() == 3) {
                            ll_2_java[r].addView(view_for_layering_on_dyas_date[r]);
                        }


                        status_for_days[r] = "d";

                        String_status_for_days_for_toasts[r] = "D";
//------------------------------------

                        //diasbling all the periods of that perticular day
                        int h;
                        for (h = 0; h < no_of_perod; h++) {

                            iv[r][h].setBackgroundColor(Color.GRAY);
                            iv_for_A[r][h].setBackgroundColor(Color.parseColor("#909090"));
                            iv_for_P[r][h].setBackgroundColor(Color.parseColor("#909090"));

                            if (ll[r][h].getChildCount() == 4) {
                                ll[r][h].addView(view_for_layering[r][h]);
                            }


                            status[r][h] = "h";
                        }
                    }


                }


                if (dayy == Calendar.THURSDAY) {

                    //for friday to sunday layering
                    for (int r = 4; r < 7; r++) {

                        iv_2[r].setBackgroundColor(Color.GRAY);
                        if (ll_2_java[r].getChildCount() == 3) {
                            ll_2_java[r].addView(view_for_layering_on_dyas_date[r]);
                        }


                        status_for_days[r] = "d";

                        String_status_for_days_for_toasts[r] = "D";
//------------------------------------

                        //diasbling all the periods of that perticular day
                        int h;
                        for (h = 0; h < no_of_perod; h++) {

                            iv[r][h].setBackgroundColor(Color.GRAY);
                            iv_for_A[r][h].setBackgroundColor(Color.parseColor("#909090"));
                            iv_for_P[r][h].setBackgroundColor(Color.parseColor("#909090"));

                            if (ll[r][h].getChildCount() == 4) {
                                ll[r][h].addView(view_for_layering[r][h]);
                            }


                            status[r][h] = "h";
                        }
                    }
                }

                if (dayy == Calendar.FRIDAY) {

                    //for sat to sunday layering
                    for (int r = 5; r < 7; r++) {

                        iv_2[r].setBackgroundColor(Color.GRAY);
                        if (ll_2_java[r].getChildCount() == 3) {
                            ll_2_java[r].addView(view_for_layering_on_dyas_date[r]);
                        }


                        status_for_days[r] = "d";

                        String_status_for_days_for_toasts[r] = "D";
//------------------------------------

                        //diasbling all the periods of that perticular day
                        int h;
                        for (h = 0; h < no_of_perod; h++) {

                            iv[r][h].setBackgroundColor(Color.GRAY);
                            iv_for_A[r][h].setBackgroundColor(Color.parseColor("#909090"));
                            iv_for_P[r][h].setBackgroundColor(Color.parseColor("#909090"));

                            if (ll[r][h].getChildCount() == 4) {
                                ll[r][h].addView(view_for_layering[r][h]);
                            }


                            status[r][h] = "h";
                        }
                    }
                }


                if (dayy == Calendar.SATURDAY) {

//for  sunday layering
                    for (int r = 6; r < 7; r++) {

                        iv_2[r].setBackgroundColor(Color.GRAY);
                        if (ll_2_java[r].getChildCount() == 3) {
                            ll_2_java[r].addView(view_for_layering_on_dyas_date[r]);
                        }


                        status_for_days[r] = "d";

                        String_status_for_days_for_toasts[r] = "D";
//------------------------------------

                        //diasbling all the periods of that perticular day
                        int h;
                        for (h = 0; h < no_of_perod; h++) {

                            iv[r][h].setBackgroundColor(Color.GRAY);
                            iv_for_A[r][h].setBackgroundColor(Color.parseColor("#909090"));
                            iv_for_P[r][h].setBackgroundColor(Color.parseColor("#909090"));

                            if (ll[r][h].getChildCount() == 4) {
                                ll[r][h].addView(view_for_layering[r][h]);
                            }


                            status[r][h] = "h";
                        }
                    }

                }


            }
        }






         /*   //-----------------------------------------------------------------------------------------------------------------------

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
        }*/

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


            //below line of codes get the prsent day and compare it with the day of the day in the week and if the days are of future they will get greyish
            //to get the current date
            //for which we make first make instance of calender
            //we also make date format according we need in below line
            Calendar current_calendar3 = Calendar.getInstance();
            int day2 = current_calendar3.get(Calendar.DAY_OF_WEEK);



            if (day2 ==Calendar.TUESDAY ) {
                //for monday layering
                for (int q = 0; q < 1; q++){



                    if (ll_2_java[q].getChildCount() == 4) {
                        ll_2_java[q].removeView(view_for_layering_on_dyas_date[q]);
                    }
                    ll_2_java[q].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[q]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[q] = "N";

                    for (int h = 0; h < no_of_perod; h++) {

                        if (ll[q][h].getChildCount() == 5) {
                            ll[q][h].removeView(view_for_layering[q][h]);
                        }
                        ll[q][h].addView(view_for_layering_on_which_hasnt_occured[q][h]);


                        status[q][h] = "n";

                    }
                }
            }




            if (day2 ==Calendar.WEDNESDAY ){

                //for monday to tuesday layering
                for (int q = 0; q < 2; q++){


                    if (ll_2_java[q].getChildCount() == 4) {
                        ll_2_java[q].removeView(view_for_layering_on_dyas_date[q]);
                    }
                    ll_2_java[q].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[q]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[q] = "N";

                    for (int h = 0; h < no_of_perod; h++) {

                        if (ll[q][h].getChildCount() == 5) {
                            ll[q][h].removeView(view_for_layering[q][h]);
                        }
                        ll[q][h].addView(view_for_layering_on_which_hasnt_occured[q][h]);


                        status[q][h] = "n";
                    }
                }
            }



            if (day2 ==Calendar.THURSDAY ){
                //for monday to wed layering
                for (int q = 0; q < 3; q++){
                    if (ll_2_java[q].getChildCount() == 4) {
                        ll_2_java[q].removeView(view_for_layering_on_dyas_date[q]);
                    }
                    ll_2_java[q].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[q]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[q] = "N";

                    for (int h = 0; h < no_of_perod; h++) {

                        if (ll[q][h].getChildCount() == 5) {
                            ll[q][h].removeView(view_for_layering[q][h]);
                        }
                        ll[q][h].addView(view_for_layering_on_which_hasnt_occured[q][h]);


                        status[q][h] = "n";
                    }
                }
            }

            if (day2 ==Calendar.FRIDAY ){
                //for monday to thu layering
                for (int q = 0; q < 4; q++){
                    if (ll_2_java[q].getChildCount() == 4) {
                        ll_2_java[q].removeView(view_for_layering_on_dyas_date[q]);
                    }
                    ll_2_java[q].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[q]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[q] = "N";

                    for (int h = 0; h < no_of_perod; h++) {

                        if (ll[q][h].getChildCount() == 5) {
                            ll[q][h].removeView(view_for_layering[q][h]);
                        }
                        ll[q][h].addView(view_for_layering_on_which_hasnt_occured[q][h]);


                        status[q][h] = "h";
                    }
                }
            }

            if (day2 ==Calendar.SATURDAY ){

                //for monday to fri layering
                for (int q = 0; q < 5; q++){
                    if (ll_2_java[q].getChildCount() == 4) {
                        ll_2_java[q].removeView(view_for_layering_on_dyas_date[q]);
                    }
                    ll_2_java[q].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[q]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[q] = "N";

                    for (int h = 0; h < no_of_perod; h++) {

                        if (ll[q][h].getChildCount() == 5) {
                            ll[q][h].removeView(view_for_layering[q][h]);
                        }
                        ll[q][h].addView(view_for_layering_on_which_hasnt_occured[q][h]);



                        status[q][h] = "n";
                    }
                }
            }

            if (day2 ==Calendar.SUNDAY ) {

                //for monday to fri layering
                for (int q = 0; q < 6; q++) {
                    if (ll_2_java[q].getChildCount() == 4) {
                        ll_2_java[q].removeView(view_for_layering_on_dyas_date[q]);
                    }
                    ll_2_java[q].addView(view_for_layering_on_which_hasnt_occured_on_dyas_date[q]);
                    //N - means not occure yet
                    String_status_for_days_for_toasts[q] = "N";

                    for (int h = 0; h < no_of_perod; h++) {

                        if (ll[q][h].getChildCount() == 5) {
                            ll[q][h].removeView(view_for_layering[q][h]);
                        }
                        ll[q][h].addView(view_for_layering_on_which_hasnt_occured[q][h]);


                        status[q][h] = "n";
                    }
                }
            }

//=====================================================================================================================================
            //---------------------------------------click listener fir layering for not ocuured elemnets ...it just gives a toast message
            for (int q = 0; q < 7; q++) {
                for (int h = 0; h < no_of_perod; h++) {
                    view_for_layering_on_which_hasnt_occured[q][h].setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {

                            Toast.makeText(attendence_predict_2.this, "attendance can not  be predicted for class that has occured ",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
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



        }else {


            //I am doin it inside else loop saying is it  not current week ..so that it removes the green background which was not removed while switching between weeks
//---------------------------------------seeting the indicater to the front of the day which is current day
            LinearLayout indicaters_linear_layout[] = new LinearLayout[8];

            indicaters_linear_layout[Calendar.MONDAY] = (LinearLayout) findViewById(R.id.mon_indicater);
            indicaters_linear_layout[Calendar.TUESDAY] = (LinearLayout) findViewById(R.id.tue_indicater);
            indicaters_linear_layout[Calendar.WEDNESDAY] = (LinearLayout) findViewById(R.id.wed_indicater);
            indicaters_linear_layout[Calendar.THURSDAY] = (LinearLayout) findViewById(R.id.thu_indicater);
            indicaters_linear_layout[Calendar.FRIDAY] = (LinearLayout) findViewById(R.id.fri_indicater);
            indicaters_linear_layout[Calendar.SATURDAY] = (LinearLayout) findViewById(R.id.sat_indicater);
            indicaters_linear_layout[Calendar.SUNDAY] = (LinearLayout) findViewById(R.id.sun_indicater);


            Calendar current_calendar33 = Calendar.getInstance();
            int current_day = current_calendar33.get(Calendar.DAY_OF_WEEK);
            indicaters_linear_layout[current_day].setBackgroundResource(R.drawable.transparent_circle);


        }

//--------------------------------------------------------------------------------------------------------------------------------------------














        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////--------------------------------------------------------------------------
        //   setting_subjects_to_periods_as_per_time_table
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

        //========================================================================================================================================================

        //code to load the saved ststuses to the periods when the week is not opened the first time or its traversed back

        if(is_it_the_first_time_opening_for_this_week==false){

            for (int f = 0; f < no_of_days; f++) {
                for (int g = 0; g < no_of_perod; g++) {

                    if(  status[f][g] == "h") {


                        iv[f][g].setBackgroundColor(Color.GRAY);
                        iv_for_A[f][g].setBackgroundColor(Color.parseColor("#909090"));
                        iv_for_P[f][g].setBackgroundColor(Color.parseColor("#909090"));
                        ll[f][g].addView(view_for_layering[f][g]);
                    }

                    if(  status[f][g] == "a") {

                        iv[f][g].setBackgroundColor(Color.parseColor("#C62828"));
                        iv_for_A[f][g].setBackgroundColor(Color.parseColor("#C62828"));
                        ll[f][g].removeView(view_for_layering[f][g]);
//also since this had a an period marked absent it cannot have the day marked as holiday so removing the gray layer if it had any

                        //todo---do it later

                    }

                    if(  status[f][g] == "p") {

                        iv[f][g].setBackgroundColor(Color.parseColor("#388E3C"));
                        iv_for_P[f][g].setBackgroundColor(Color.parseColor("#388E3C"));
                        iv_for_A[f][g].setBackgroundColor(Color.parseColor("#909090"));
//also since this had a an period marked absent it cannot have the day marked as holiday so removing the gray layer if it had any

                        //todo---do it later

                    }

                }
            }


        }
























        return status;

    }


/*    public void setting_subjects_to_periods_as_per_time_table(int no_of_period1, int no_of_days, String name_database_table_as_week_year, TextView[][] tv) {
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






    }*/


}
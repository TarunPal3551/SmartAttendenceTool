package com.example.android.testin3;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.testin3.data.DatabaseHelper;
import com.example.android.testin3.data.DatabaseHelper_2;
import com.example.android.testin3.data.DatabaseHelper_3;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class attendence_analyse_activity extends AppCompatActivity  {

    int percentage;
    int no_of_period;
    DatabaseHelper myDb_1;
    DatabaseHelper_2 myDb__2;
    DatabaseHelper_3 myDb_3;
    float total_no_periods_occured = 0;
    float total_no_of_periods_marked_present = 0;
    float total_no_of_periods_marked_ABSENT = 0;
    float total_no_of_periods_marked_HOLIDAY = 0;
    int no_of_subjects;
    List<String> name_of_subjects_list = new ArrayList<String>();
    List<String> name_of_periods_list = new ArrayList<String>();
    List<String> name_of_weekdays_list = new ArrayList<String>();

    //one array for the counters of the occurence of the subjects ;
    int[] occureneces_of_subjects;
    //one array for the counters of the occurence of the weekdays ;
    int[] occureneces_of_weekdays;
    //one array for the counters of the occurence of the periods ;
    int[] occureneces_of_periods;



    // below are the two perimeters for a important graph
    List<String> days_name_x_axis_list = new ArrayList<String>();
    List<Integer> present_periods_counter_for_days_y_axis_list = new ArrayList<Integer>();


    // below are the two perimeters for the lineChart but it shows percentage of a week as the coordinate
    List<String> week_name_x_axis_list = new ArrayList<String>();
    List<Float> week_percentage_y_axis_list = new ArrayList<Float>();
    List<Float> week_percentage_for_timeline_y_axis_list = new ArrayList<Float>();

    List<Float> present_periods_list_for_timeline_chart = new ArrayList<>();
    List<Float> absent_periods_list_for_timeline_chart = new ArrayList<>();




    float combined_attendence_of_all_subjects;
    //----------------------------------------------TARUN PAL -GRAPH VARIABLES--------------------------------


    //one array for name of the subjects
    String[] name_of_subjects;
    float[] data_for_subjects_graph;

    //one array for name of the periods
    String[] name_of_periods;
    float[] data_for_periods_graph;

    //one array for name of the weekdays
    String[] name_of_weekdays;
    float[] data_for_weekdays_graph;

    //------------------------------------------------------------------------------------
      TextView textView_for_total_attendence;
      float total_attendence=0f;


      private LineChart mlinechart;
    private LineChart mmlinechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_calculator_activity);

        PieChart pieChartsubjectwise=(PieChart)findViewById(R.id.piechartsubjectwise);
        PieChart pieChartweekwise=(PieChart)findViewById(R.id.piechartweekdaywise);
        PieChart pieChartperiodwise=(PieChart)findViewById(R.id.piechartperiodwise);
        textView_for_total_attendence=(TextView)findViewById(R.id.totalattendence);
        setUpbottomNavigationBar();
        changeStatusBarColor("#E64A19");

        mlinechart = (LineChart) findViewById(R.id.Linechart);
        mmlinechart = (LineChart) findViewById(R.id.linechart2);



        myDb_1 = new DatabaseHelper(this);
        myDb__2 = new DatabaseHelper_2(this);
        myDb_3 = new DatabaseHelper_3(this);





        Cursor res = myDb_1.getAllData();
        int total_rows = res.getCount();
      //  the no of subjects variable is at 2nd row of table in databse so ..if total no of subjects is equal to null the app crashes so i am making equal to 0 when that happens
        if (total_rows>1) {
            res.move(2);
            no_of_subjects = Integer.parseInt(res.getString(2));
        }else{
            no_of_subjects=0;
        }

        occureneces_of_subjects = new int[no_of_subjects];



        int j;
        for (j = 0; j < no_of_subjects; j++) {
            Cursor res_ = myDb_1.getAllData();
            res_.move(4);
            int latest_version_ = Integer.parseInt(res_.getString(2));

            latest_version_ = latest_version_ - 1;

            Cursor res6 = myDb_3.getAllData("first_ever_table_made_for_subjects" + latest_version_);
            res6.move(j + 2);
            name_of_subjects_list.add(res6.getString(1));

            //initialisng occurences array to a default value of 0 tooo...
            occureneces_of_subjects[j] = 0;

            res_.close();
            res6.close();
        }





//now declaring the below array
        occureneces_of_weekdays = new int[7];
        //initialising the array to a default value
        int r;
        for (r = 0; r < 7; r++) {
            occureneces_of_weekdays[r] = 0;
        }





     calculations_for_attendence_percentage();
   //     textView_for_total_attendence.setText(String.valueOf(total_attendence)+"%");

        //giving the names to below list
        for (r = 0; r < no_of_period; r++) {
            name_of_periods_list.add((r+1) + " period");
        }

        //giving the names to below list
        name_of_weekdays_list.add("Monday");
        name_of_weekdays_list.add("Tuesday");
        name_of_weekdays_list.add("Wednesday");
        name_of_weekdays_list.add("Thursday");
        name_of_weekdays_list.add("Friday");
        name_of_weekdays_list.add("Saturday");
        name_of_weekdays_list.add("Sunday");

 //------------------------------------------------------------------------------TARUN PAL - ON CREATE CODES-------------------------------------
      //  data_for_subjects_graph = new float[no_of_subjects];

     //subjectWise chart
        calculation_for_graph();





                   //----------------------------------------============================================================
        //setting the visibility of subject pie chart as gone  and stopping computation for that graph when therr is no timetable added ....
        if( no_of_subjects !=0) {


            String attendenceSubjectWise = "SubjectWise";
          //  pieChartsubjectwise.setHoleRadius(25f);
         //   pieChartsubjectwise.setTransparentCircleAlpha(0);
            pieChartsubjectwise.setCenterText(attendenceSubjectWise);
            pieChartsubjectwise.setDrawEntryLabels(true);
            pieChartsubjectwise.getDescription().setText(" ");
            pieChartsubjectwise.getDescription().setTextSize(12);

            pieChartperiodwise.setTransparentCircleRadius(54f);
            pieChartperiodwise.setHoleRadius(45f);

            addSubjectWise_Data(pieChartsubjectwise);




        }else{
            pieChartsubjectwise.setVisibility(View.GONE);
            final LinearLayout container_of_pirChartSubject = (LinearLayout) findViewById(R.id.container_of_pirChartSubject);
            container_of_pirChartSubject.setVisibility(View.GONE);
        }


        //WeekWise Chart
        String attendenceWeekWise="WeekWise";
      //  pieChartweekwise.setHoleRadius(25f);
       // pieChartweekwise.setTransparentCircleAlpha(0);
        pieChartweekwise.setCenterText(attendenceWeekWise);
        pieChartweekwise.setDrawEntryLabels(true);
        pieChartweekwise.getDescription().setText(" ");
        pieChartweekwise.getDescription().setTextSize(12);

        pieChartperiodwise.setTransparentCircleRadius(54f);
        pieChartperiodwise.setHoleRadius(45f);

        addWeekWise_data(pieChartweekwise);



        //periodwise chart
        String attendencePeriodWise="PeriodWise";
       // pieChartperiodwise.setHoleRadius(25f);
        //pieChartperiodwise.setTransparentCircleAlpha(0);
        pieChartperiodwise.setCenterText(attendencePeriodWise);
        pieChartperiodwise.setDrawEntryLabels(true);
        pieChartperiodwise.getDescription().setText(" ");
        pieChartperiodwise.getDescription().setTextSize(12);

        pieChartperiodwise.setTransparentCircleRadius(54f);
        pieChartperiodwise.setHoleRadius(45f);

        addperiodWise_data(pieChartperiodwise);

//-------------------------------------------------------------------------------------------------------







/*int  z=0;
Float f;
for (int i=week_percentage_y_axis_list.size()-1;i>-1; i--){
    week_percentage_y_axis_list.set(z,week_percentage_y_axis_list.get(i));

    z++;
    f = week_percentage_y_axis_list.get(i);
    f++;
}*/

/*        Float[] clone_week_indi_percent = week_indivi_percent;*/

   /*     final String TAGG ="yoyo honey singh";
        Log.i(TAGG, "attendance " +"=============%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%===");
        for (int k =0;k<week_indivi_percent.length ;k++){
            Log.i(TAGG, "week name " + week_name_x_axis_list.get(k));
            Log.i(TAGG, "week percent " + week_indivi_percent[k]);
        }
        Log.i(TAGG, "attendance " +"=============%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%===");*/


        String[] values = new String[week_name_x_axis_list.size()];
        int[] values_int = new int[week_name_x_axis_list.size()];

        for (int i = 0; i<week_name_x_axis_list.size(); i++){
            values[i]= week_name_x_axis_list.get(i).substring(0,week_name_x_axis_list.get(i).length()-9).substring(6,8);
            values_int[i]= Integer.parseInt(values[i]);
        }

        /////////////////////////////////////to reverse the elemnts in the arrays
                 /*      int g,h;
                        Float temppp;
                        h = week_indivi_percent.length - 1;     // now j will point to the last element
                        g = 0;         // and i will point to the first element
                        while(g<h)
                        {
                            temppp = week_indivi_percent[g];
                            week_indivi_percent[g] = week_indivi_percent[h];
                            week_indivi_percent[h] = temppp;
                            g++;
                            h--;
                        }*/





        /*int b =0;
        for (int i = week_indivi_percent.length-1; i>-1; i--){
            values[t]= week_name_x_axis_list.get(i).substring(0,week_name_x_axis_list.get(i).length()-9).substring(6,8);
            //    values[t]=  values[t].substring(0,-9);
            values_int[b]= Integer.parseInt(values[t]);
                                Log.i(TAGG, "iiiiiiiiiiiiiiiiiii " + week_indivi_percent[i]);
            week_indivi_percent[b] = week_indivi_percent[i];
                               Log.i(TAGG, "bbbbbbbbbbbbbbbbbbbbbb " + week_indivi_percent[b]);
            b++;
        }*/

       /* final String TAG ="yoyo honey singh";
        Log.i(TAG, "attendance " +"=============$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4===");
        for (int k =0;k<week_indivi_percent.length ;k++){
            Log.i(TAG, "week name " + values[k]);
            Log.i(TAG, "week percent " + week_indivi_percent[k]);
        }
        Log.i(TAG, "attendance " +"=============$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4===");*/



        //--------------------------------below is to done to sort the array of names because sometimes the week numbers get misplaced ...and when i am sorting them i need to sort the array of content too

        int[] sortedArray = new int[values_int.length];

       /* Float[] arr_week_indivisual_percent = new Float[week_percentage_y_axis_list.size()];
        week_percentage_y_axis_list.toArray(arr_week_indivisual_percent);

        Float[] arr_week_timeline_percent = new Float[week_percentage_y_axis_list.size()];
        week_percentage_for_timeline_y_axis_list.toArray(arr_week_timeline_percent);*/

        int temp;
        String temp_2;
        Float temp3;
                Float temp6 , temp7;
        for (int k = 0; k < values_int.length - 1; k++) {// added this for loop, think about logic why do we have to add this to make it work

            for (int i = 0; i < values_int.length - 1; i++) {
                if (values_int[i] > values_int[i + 1]) {

                    temp = values_int[i];
                    temp_2 = values[i];
//                    temp3 = arr_week_indivisual_percent[i];
//                    temp4 = arr_week_timeline_percent[i];
                   temp3 = week_percentage_y_axis_list.get(i);
//                    temp4 = week_percentage_for_timeline_y_axis_list.get(i);
//                    temp5 = week_indivi_percent[i];
                    temp6 = present_periods_list_for_timeline_chart.get(i);
                    temp7 = absent_periods_list_for_timeline_chart.get(i);


                    values_int[i] = values_int[i + 1];
                    values[i] = values[i + 1];
//                    arr_week_indivisual_percent[i] = arr_week_indivisual_percent[i + 1];
//                    arr_week_timeline_percent[i] = arr_week_timeline_percent[i + 1];
                    week_percentage_y_axis_list.set(i,week_percentage_y_axis_list.get(i+1));
//                    week_percentage_for_timeline_y_axis_list.set(i,week_percentage_for_timeline_y_axis_list.get(i+1));
//                    week_indivi_percent[i]= week_indivi_percent[i+1];
                    present_periods_list_for_timeline_chart.set(i,present_periods_list_for_timeline_chart.get(i+1));
                    absent_periods_list_for_timeline_chart.set(i,absent_periods_list_for_timeline_chart.get(i+1));


                    values_int[i + 1] = temp;
                    values[i + 1] = temp_2;
//                    arr_week_indivisual_percent[i + 1] = temp3;
//                    arr_week_timeline_percent[i + 1] = temp4;
                   week_percentage_y_axis_list.set(i+1, temp3);
//                   week_percentage_for_timeline_y_axis_list.set(i+1, temp4);
//                   week_indivi_percent[i+1]= temp5;
                    present_periods_list_for_timeline_chart.set(i+1,temp6);
                    absent_periods_list_for_timeline_chart.set(i+1,temp7);

                    sortedArray = values_int;

                }
            }
        }


//        List<Float> list1 = new ArrayList<Float>(Arrays.asList(arr_week_indivisual_percent));
//        List<Float> list2 = new ArrayList<Float>(Arrays.asList(arr_week_timeline_percent));

        List<Float> trial = new ArrayList<Float>(){};
        ArrayList<Float> floats = new ArrayList<>(Arrays.asList(3.14f, 6.28f, 9.56f));

/*        final String TAG ="yoyo honey singh";*/
     /*   Log.i(TAG, "attendance " +"=================================&&&&&&&&&&&&&&&&&&&&&&&&&&&7&&&&&&&&&&&&&&&&&==================================");
        for (int k =0;k<week_indivi_percent.length ;k++){
            Log.i(TAG, "week name " + values[k]);
            Log.i(TAG, "& week name " + values_int[k]);
            Log.i(TAG, "week percent " + week_indivi_percent[k]);
        }*/



//        Arrays.asList(arr_week_indivisual_percent);
//        Arrays.asList(arr_week_timeline_percent);



/*        ArrayList<Entry> yvalues = new ArrayList<>();
        int z=0;
        for (int i=week_percentage_y_axis_list.size()-1;i>-1; i--){
            yvalues.add(new Entry(z,week_percentage_y_axis_list.get(i)));
            z++;
        }*/

        //----------------------------------for line Chart of indivisual weeks -----------------------------------
        //todo - make a code that ...only enables the chart and the computation for it only when the total activated weeks are more than 1...



//mlinechart.setOnChartGestureListener(attendence_analyse_activity.this);
//mlinechart.setOnChartValueSelectedListener(attendence_analyse_activity.this);

        mlinechart.setDragEnabled(true);
        mlinechart.setScaleYEnabled(false);

        LimitLine limit = new LimitLine(75f ,"SAFE at 75%");
        limit.setLineWidth(3f);
        limit.enableDashedLine(10f,10f,0f);
        limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        limit.setTextSize(10f);
        limit.setTextColor(Color.parseColor( "#009900"));
        limit.setLineColor(Color.parseColor( "#009900"));





        //to remove left y axis
        YAxis leftaxis = mlinechart.getAxisLeft();
        leftaxis.removeAllLimitLines();
        leftaxis.addLimitLine(limit);
        leftaxis.setAxisMaximum(100f);
        leftaxis.setAxisMinimum(0f);
        leftaxis.enableGridDashedLine(10f,10f,0);
        leftaxis.setDrawLimitLinesBehindData(true);

        mlinechart.getAxisRight().setEnabled(false);


        float present_periods_variable_for_line_chart_timeline=0;
        float absent_periods_variable_for_line_chart_timeline=0;

        for (int k = 0 ;k < week_percentage_y_axis_list.size() ;k++){
             present_periods_variable_for_line_chart_timeline = present_periods_variable_for_line_chart_timeline + present_periods_list_for_timeline_chart.get(k);
                absent_periods_variable_for_line_chart_timeline = absent_periods_variable_for_line_chart_timeline + absent_periods_list_for_timeline_chart.get(k);

            week_percentage_for_timeline_y_axis_list.add( (present_periods_variable_for_line_chart_timeline/(present_periods_variable_for_line_chart_timeline + absent_periods_variable_for_line_chart_timeline)) *100 );
        }



        ArrayList<Entry> yvalues = new ArrayList<>();
       // int z=0;
        for (int i=0 ; i < week_percentage_y_axis_list.size(); i++) {
            yvalues.add(new Entry(i, week_percentage_y_axis_list.get(i)));

        }
        /*for (int i=0 ; i < week_indivi_percent.length; i++) {
            yvalues.add(new Entry(i,week_indivi_percent[i]));
        }*/



            LineDataSet set1 = new LineDataSet(yvalues,"attendence percentage of " +
                "each week");
        set1.setFillAlpha(110);
        set1.setColor(Color.BLACK);
        set1.setLineWidth(1.5f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);

ArrayList<ILineDataSet> datasets = new ArrayList<>();
datasets.add(set1);

        LineData Data = new LineData(datasets);
        mlinechart.setData(Data);



        XAxis xaxis = mlinechart.getXAxis();
        xaxis.setValueFormatter(new MyAxisValueFormattter(values));
xaxis.setGranularity(1);
xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);

//----------------------------------------------------------------------------------------------------------------------------------------------------------
 //------------------------------------------------------------for line Chart of attendence TIMELINE-----------------------------------
        //todo - make a code that ...only enables the chart and the computation for it only when the total activated weeks are more than 1...
//this below code ws crashing the app when only one week was active so i have made it only work when activated weeks are more than 1


        if(week_percentage_for_timeline_y_axis_list.size()>1) {
            mmlinechart.setDragEnabled(true);
            mmlinechart.setScaleYEnabled(false);

     /*   LimitLine limit = new LimitLine(75f ,"SAFE at 75%");
        limit.setLineWidth(3f);
        limit.enableDashedLine(10f,10f,0f);
        limit.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        limit.setTextSize(10f);
        limit.setTextColor(Color.parseColor( "#009900"));
        limit.setLineColor(Color.parseColor( "#009900"));
*/


            //to remove left y axis
            YAxis lleftaxis = mlinechart.getAxisLeft();
            lleftaxis.removeAllLimitLines();
            lleftaxis.addLimitLine(limit);
            lleftaxis.setAxisMaximum(100f);
            lleftaxis.setAxisMinimum(0f);
            lleftaxis.enableGridDashedLine(10f, 10f, 0);
            lleftaxis.setDrawLimitLinesBehindData(true);

            mlinechart.getAxisRight().setEnabled(false);

            ArrayList<Entry> yyvalues = new ArrayList<>();

            for (int i = 0; i < week_percentage_for_timeline_y_axis_list.size(); i++) {
                yyvalues.add(new Entry(i, week_percentage_for_timeline_y_axis_list.get(i)));
            }


            LineDataSet set2 = new LineDataSet(yyvalues, "attendence percentage TIMELINE");
            set2.setFillAlpha(110);
            set2.setColor(Color.BLACK);
            set2.setLineWidth(1.5f);
            set2.setValueTextSize(10f);
            set2.setValueTextColor(Color.BLACK);
            set2.setCircleColor(Color.BLACK);

            ArrayList<ILineDataSet> datasets2 = new ArrayList<>();
            datasets2.add(set2);

            LineData Data2 = new LineData(datasets2);
            mmlinechart.setData(Data2);


     /*   String[] values2 = new String[week_name_x_axis_list.size()];
        int t =0;
        for (int i = week_name_x_axis_list.size()-1; i>-1; i--){
            values[t]= week_name_x_axis_list.get(i).substring(0,week_name_x_axis_list.get(i).length()-9).substring(6,8);
            //    values[t]=  values[t].substring(0,-9);
            t++;
        }*/


            XAxis xaxis2 = mmlinechart.getXAxis();
            xaxis2.setValueFormatter(new MyAxisValueFormattter(values));
            xaxis2.setGranularity(1);
            xaxis2.setPosition(XAxis.XAxisPosition.BOTTOM);

        }else{
            LinearLayout container_of_linrchartweek =(LinearLayout) findViewById(R.id.container_of_linrchartweek);
            container_of_linrchartweek.setVisibility(View.GONE);


        }
//------------------------------------------------------------------------------------------------------------

       final LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.expandable_2);
        //set visibility to GONE
        mLinearLayout.setVisibility(View.GONE);

       RelativeLayout mLinearLayoutHeader = (RelativeLayout) findViewById(R.id.top_toolbar);

        final Boolean[] _is_arrow_up_or_layout_minimised = {true};

        final ImageView up_arrow =(ImageView)findViewById(R.id.up_arrow_2) ;
        mLinearLayoutHeader.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               if( _is_arrow_up_or_layout_minimised[0] == true) {
                   up_arrow.animate().rotation(180).start();
                   expand(mLinearLayout);
                   _is_arrow_up_or_layout_minimised[0] =false;
               }else {
                   up_arrow.animate().rotation(0).start();
                   collapse(mLinearLayout);
                   _is_arrow_up_or_layout_minimised[0] =true;
               }
            }});





        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView_2);
//this below code lett the linear layout expand whenveer the scrolled was found at the top position
     /*   scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (scrollView != null) {
                    if (scrollView.getScrollY() == 0) {
                        expand(mLinearLayout);
                    } else {

                    }
                }
            }
        });*/


        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    up_arrow.animate().rotation(0).start();
                    collapse(mLinearLayout);
                    _is_arrow_up_or_layout_minimised[0] =true;
                }
                return false;
            }
        });
//-----------------------------------------------------------------------------------------------

        final LinearLayout container_of_pirChartSubject = (LinearLayout) findViewById(R.id.container_of_pirChartSubject);
        container_of_pirChartSubject.setOutlineProvider(new CustomOutlineProvider_for_ustom_dialog_box(15));
        container_of_pirChartSubject.setClipToOutline(true);

        final LinearLayout container_of_pirChartWeek = (LinearLayout) findViewById(R.id.container_of_pirChartWeek);
        container_of_pirChartWeek.setOutlineProvider(new CustomOutlineProvider_for_ustom_dialog_box(15));
        container_of_pirChartWeek.setClipToOutline(true);

        final LinearLayout container_of_pirChartPeriod = (LinearLayout) findViewById(R.id.container_of_pirChartPeriod);
        container_of_pirChartPeriod.setOutlineProvider(new CustomOutlineProvider_for_ustom_dialog_box(15));
        container_of_pirChartPeriod.setClipToOutline(true);

        final LinearLayout container_of_attendance_tv = (LinearLayout) findViewById(R.id.container_of_attendance_tv);
        container_of_attendance_tv.setOutlineProvider(new CustomOutlineProvider_for_ustom_dialog_box(15));
        container_of_attendance_tv.setClipToOutline(true);

        final LinearLayout container_of_linrchartimeline = (LinearLayout) findViewById(R.id.container_of_linrchartimeline);
        container_of_linrchartimeline.setOutlineProvider(new CustomOutlineProvider_for_ustom_dialog_box(15));
        container_of_linrchartimeline.setClipToOutline(true);

        final LinearLayout container_of_linrchartweek = (LinearLayout) findViewById(R.id.container_of_linrchartweek);
        container_of_linrchartweek.setOutlineProvider(new CustomOutlineProvider_for_ustom_dialog_box(15));
        container_of_linrchartweek.setClipToOutline(true);

textView_for_total_attendence.setText("total current attendance - " +   String.format("%.2f",total_attendence ) +" %");

TextView piechartsubtv =(TextView)findViewById(R.id.piechartsubtv);
        piechartsubtv.setText("Attendance distribution per Subject ");

        TextView piechartweektv =(TextView)findViewById(R.id.piechartweektv);
        piechartweektv.setText("active WeekDays distribution");

        TextView piechartpertv =(TextView)findViewById(R.id.piechartpertv);
        piechartpertv.setText("Attendance distribution per Period ");

        final float scale = getResources().getDisplayMetrics().density;
        container_of_attendance_tv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    up_arrow.animate().rotation(0).start();
                    collapse(mLinearLayout);
                    _is_arrow_up_or_layout_minimised[0] =true;
                }
                return false;
            }
        });



        container_of_pirChartSubject.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    up_arrow.animate().rotation(0).start();
                    collapse(mLinearLayout);
                    _is_arrow_up_or_layout_minimised[0] =true;

                }
                return false;
            }
        });

        container_of_pirChartWeek.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    up_arrow.animate().rotation(0).start();
                    collapse(mLinearLayout);
                    _is_arrow_up_or_layout_minimised[0] =true;

                }
                return false;
            }
        });

        container_of_pirChartPeriod.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    up_arrow.animate().rotation(0).start();
                    collapse(mLinearLayout);
                    _is_arrow_up_or_layout_minimised[0] =true;

                }
                return false;
            }
        });



        container_of_linrchartimeline.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    up_arrow.animate().rotation(0).start();
                    collapse(mLinearLayout);
                    _is_arrow_up_or_layout_minimised[0] =true;
                }
                return false;
            }
        });

        container_of_linrchartweek.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    up_arrow.animate().rotation(0).start();
                    collapse(mLinearLayout);
                    _is_arrow_up_or_layout_minimised[0] =true;

                }
                return false;
            }
        });

        pieChartperiodwise.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    up_arrow.animate().rotation(0).start();
                    collapse(mLinearLayout);
                    _is_arrow_up_or_layout_minimised[0] =true;

                }
                return false;
            }
        });

        pieChartsubjectwise.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    up_arrow.animate().rotation(0).start();
                    collapse(mLinearLayout);
                    _is_arrow_up_or_layout_minimised[0] =true;

                }
                return false;
            }
        });

        pieChartweekwise.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    up_arrow.animate().rotation(0).start();
                    collapse(mLinearLayout);
                    _is_arrow_up_or_layout_minimised[0] =true;

                }
                return false;
            }
        });

        mlinechart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    up_arrow.animate().rotation(0).start();
                    collapse(mLinearLayout);
                    _is_arrow_up_or_layout_minimised[0] =true;
                }
                return false;
            }
        });

        mmlinechart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    up_arrow.animate().rotation(0).start();
                    collapse(mLinearLayout);
                    _is_arrow_up_or_layout_minimised[0] =true;
                }
                return false;
            }
        });

    }




//============================================================================================
    // Now we'll create a ValueAnimator to animate the Layout height from 0 to original height to expand the view and from original height to 0 to collpse the view.

    private void expand(LinearLayout mLinearLayout) {
        //set Visible
        mLinearLayout.setVisibility(View.VISIBLE);

        final int widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        final int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mLinearLayout.measure(widthSpec, heightSpec);

        ValueAnimator mAnimator = slideAnimator(0, mLinearLayout.getMeasuredHeight(),mLinearLayout);
        mAnimator.start();

    }


    private void collapse(final LinearLayout mLinearLayout) {
        int finalHeight = mLinearLayout.getHeight();

        ValueAnimator mAnimator = slideAnimator(finalHeight, 0,mLinearLayout);

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //Height=0, but it set visibility to GONE
                mLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
        mAnimator.start();
    }


    private ValueAnimator slideAnimator(int start, int end, final LinearLayout mLinearLayout) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //Update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mLinearLayout.getLayoutParams();
                layoutParams.height = value;
                mLinearLayout.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }
//============================================================================================

    public class  MyAxisValueFormattter implements IAxisValueFormatter{
        private String[] mvalues;
        public MyAxisValueFormattter(String[] values){
            this.mvalues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mvalues[(int)value];
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
        BottomNavigationView bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation_analyse_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        Intent intent=new Intent(attendence_analyse_activity.this,EditorActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_analyse:
                        Intent intent1=new Intent(attendence_analyse_activity.this,attendence_analyse_activity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.menu_setting:
                        Intent intent2=new Intent(attendence_analyse_activity.this,SettingsActivity.class);
                        startActivity(intent2);
                        return true;

                    case R.id.attendencepredecting:
                        Intent intent3=new Intent(attendence_analyse_activity.this,attendence_predicting_activity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.attendencewebview:
                        Intent intent4=new Intent(attendence_analyse_activity.this,web_view.class);
                        startActivity(intent4);
                        return true;
                        default: return false;
                }


            }
        });
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        Intent intent=new Intent(attendence_analyse_activity.this,EditorActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_analyse:
                        Intent intent1=new Intent(attendence_analyse_activity.this,attendence_analyse_activity.class);
                        startActivity(intent1);
                        break;
                    case R.id.menu_setting:
                        Intent intent2=new Intent(attendence_analyse_activity.this,SettingsActivity.class);
                        startActivity(intent2);
                        break;

                    case R.id.attendencepredecting:
                        Intent intent3=new Intent(attendence_analyse_activity.this,attendence_predicting_activity.class);
                        startActivity(intent3);
                       break;
                    case R.id.attendencewebview:
                        Intent intent4=new Intent(attendence_analyse_activity.this,web_view.class);
                        startActivity(intent4);
                      break;
                    default: Toast.makeText(attendence_analyse_activity.this,"Something Wrong",Toast.LENGTH_LONG).show();

                }

            }
        });
        Menu menu=bottomNavigationView.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);



    }

    private void addperiodWise_data(PieChart pieChartperiodwise) {
        ArrayList<PieEntry>periodwisedata =new ArrayList<>();
        for (int i=0;i<data_for_periods_graph.length;i++){

            periodwisedata.add(new PieEntry(data_for_periods_graph[i],name_of_periods[i]));
        }
        PieDataSet pieDataSet=new PieDataSet(periodwisedata,"Periods");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setSelectionShift(name_of_periods.length);
        pieDataSet.setValueTextSize(12);
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.parseColor("#f44236"));
        colors.add(Color.parseColor("#ea1e63"));
        colors.add(Color.parseColor("#9c28b1"));
        colors.add(Color.parseColor("#3f51b5"));
        colors.add(Color.parseColor("#2196f3"));
        colors.add(Color.parseColor("#009788"));
        colors.add(Color.parseColor("#4cb050"));
        colors.add(Color.parseColor("#cddc39"));
        colors.add(Color.parseColor("#795548"));
        colors.add(Color.parseColor("#9E9E9E"));
        colors.add(Color.parseColor("#607D8B"));
        colors.add(Color.parseColor("#FF9800"));

        pieDataSet.setColors(colors);
        Legend legend=pieChartperiodwise.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
       // legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        legend.setXEntrySpace(name_of_periods.length);
        legend.setYEntrySpace(name_of_periods.length);


        PieData pieData=new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.getDataSetByLabel(String.valueOf(name_of_periods),true);
       pieChartperiodwise.setData(pieData);
        pieChartperiodwise.animateY(3000);
      pieChartperiodwise.invalidate();

    }

    private void addWeekWise_data(PieChart pieChartweekwise) {
        ArrayList<PieEntry>weekwisedata =new ArrayList<>();
        for (int i=0;i<data_for_weekdays_graph.length;i++){

            weekwisedata.add(new PieEntry(data_for_weekdays_graph[i],name_of_weekdays[i]));
        }
        PieDataSet pieDataSet=new PieDataSet(weekwisedata,"Days");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setSelectionShift(name_of_weekdays.length);
        pieDataSet.setValueTextSize(12);
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.parseColor("#f44236"));
        colors.add(Color.parseColor("#ea1e63"));
        colors.add(Color.parseColor("#9c28b1"));
        colors.add(Color.parseColor("#3f51b5"));
        colors.add(Color.parseColor("#2196f3"));
        colors.add(Color.parseColor("#009788"));
        colors.add(Color.parseColor("#4cb050"));
        colors.add(Color.parseColor("#cddc39"));
        colors.add(Color.parseColor("#795548"));
        colors.add(Color.parseColor("#9E9E9E"));
        colors.add(Color.parseColor("#607D8B"));
        colors.add(Color.parseColor("#FF9800"));
        pieDataSet.setColors(colors);
        Legend legend=pieChartweekwise.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
       // legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        legend.setXEntrySpace(name_of_weekdays.length);
        legend.setYEntrySpace(name_of_weekdays.length);


        PieData pieData=new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.getDataSetByLabel(String.valueOf(name_of_weekdays),true);
        pieChartweekwise.setData(pieData);
        pieChartweekwise.animateY(3000);
        pieChartweekwise.invalidate();

    }

    private void addSubjectWise_Data(PieChart pieChart) {
        ArrayList<PieEntry>subjectwisedata =new ArrayList<>();
        for (int i=0;i<data_for_subjects_graph.length;i++){

            subjectwisedata.add(new PieEntry(data_for_subjects_graph[i],name_of_subjects[i]));
        }
        PieDataSet pieDataSet=new PieDataSet(subjectwisedata,"subjects");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setSelectionShift(name_of_subjects.length);
        pieDataSet.setValueTextSize(12);
        ArrayList<Integer> colors=new ArrayList<>();
        colors.add(Color.parseColor("#f44236"));
        colors.add(Color.parseColor("#ea1e63"));
        colors.add(Color.parseColor("#9c28b1"));
        colors.add(Color.parseColor("#3f51b5"));
        colors.add(Color.parseColor("#2196f3"));
        colors.add(Color.parseColor("#009788"));
        colors.add(Color.parseColor("#4cb050"));
        colors.add(Color.parseColor("#cddc39"));
        colors.add(Color.parseColor("#795548"));
        colors.add(Color.parseColor("#9E9E9E"));
        colors.add(Color.parseColor("#607D8B"));
        colors.add(Color.parseColor("#FF9800"));
        pieDataSet.setColors(colors);
        Legend legend=pieChart.getLegend();
        legend.setForm(Legend.LegendForm.SQUARE);
      //  legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);

        legend.setXEntrySpace(name_of_subjects.length);
        legend.setYEntrySpace(name_of_subjects.length);


        PieData pieData=new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.getDataSetByLabel(String.valueOf(name_of_subjects),true);
        pieChart.setData(pieData);
        pieChart.animateY(3000);
        pieChart.invalidate();
    }


    ///-------------------------------------------------------------------------------------------
    private void calculations_for_attendence_percentage() {
        //now retriving the no_of_period  varaiable from database
        Cursor res = myDb_1.getAllData();
        //below we pass the row no as a parameter ....rows start from 1
        res.move(1);
        //below we pass the column no as a parameter ....column start from 0

        no_of_period = Integer.parseInt(res.getString(2));

        //since we have got the no of period we can declare and initialisa an array that neede it
        //now declaring the below array
        occureneces_of_periods = new int[no_of_period];
        //initialising the array to a default value
        int r;
        for (r = 0; r < no_of_period; r++) {
            occureneces_of_periods[r] = 0;
        }

        String[] recieved_table_names = myDb__2.get_all_table_names();
        /** remeber that the first element of above string array is the the number of the size of array or the number of all the tables in the database (including database metadata table) in the string format
         *there database_metadata table at the first position (not the zeroth  position) so ignore it
         * total no of elements below is one more than actual size because of above reason
         */
        int total_no_of_elements_recived_array = Integer.parseInt(recieved_table_names[0]);
        //now actual  table names amount is what we get after ignoring avbove and android_metadata
        int total_no_of_tables = total_no_of_elements_recived_array - 2;

        //below lines for testing purpose
        // TextView percent_tv_java = (TextView) findViewById(R.id.tv_for_percent_xml);
//percent_tv_java.setText(String.valueOf(table_names[6]));

        int i, j;
        //now setting all the names in the new correected  tablenames array for further purpose and ignoring the zeroth and the first position
        String[] corrected_table_names = new String[total_no_of_tables];
        for (i = 2; i < total_no_of_elements_recived_array; i++) {
            corrected_table_names[i - 2] = recieved_table_names[i];

        }


        //week is considered ony when its  activated or disacyivated
        for (i = 0; i < total_no_of_tables; i++) {
            //retriving the status of week from database
            Cursor res2 = myDb__2.getAllData(corrected_table_names[i]);
            //below we pass the row no as a parameter ....rows start from 1 ,but our
            res2.move(no_of_period + 2);
            //below we pass the column no as a parameter ....column start from 0 but we id column ata that position
            String if_week_is_active = res2.getString(1);
            //below if else only works wen the that perticular week is opened for the firset time
            if (if_week_is_active.equals("WEEK_ACTIVATED")) {
                float present_periods_variable_for_line_chart=0;
                float absent_periods_variable_for_line_chart=0;

                //make this week as week name for linechart x axis string list
                week_name_x_axis_list.add( corrected_table_names[i]);

                //IF WEEK is active we want to check next wether day is NON_HOLIDAY or not
                for (j = 0; j < 7; j++) {
                    //retriving the status of days from database
                    Cursor res3 = myDb__2.getAllData(corrected_table_names[i]);
                    res3.move(no_of_period + 1);

                    Cursor res59 = myDb__2.getAllData(corrected_table_names[i]);
                    res59.move( 1);


                    if (res3.getString(j + 1).equals("WORKING_DAY") && !res59.getString(j+1).equals( "NOT_OCCURED_YET")) {

                        //since day is active we nneed to just increment its ocurence...only if all the
                       Boolean did_present_occured=false;
                        for (int g = 0; g < no_of_period; g++){
                            final Cursor res77 = myDb__2.getAllData(corrected_table_names[i]);
                            res77.move(g + 1);

                            if (res77.getString(j + 1).equals("PRESENT")){
                                did_present_occured =true;
                            }
                        }
                        if(did_present_occured==true) {
                            occureneces_of_weekdays[j] = occureneces_of_weekdays[j] + 1;
                        }


                        //if day is active next we want  to first add  amount  of no of period to a variable of total_no_period_occured  each time
                        // total_no_periods_occured = total_no_periods_occured+no_of_period;

                        // next which periods are marked present absent and holiday will increment their respective variables
                        int m;
                        int count_for_period_of_a_day = 0;
                        for (m = 0; m < no_of_period; m++) {

                            final Cursor res5 = myDb__2.getAllData(corrected_table_names[i]);
                            res5.move(m + 1);


                            if (res5.getString(j + 1).equals("PRESENT")) {

                                total_no_of_periods_marked_present = total_no_of_periods_marked_present + 1;

                                //computation for line chart
                                present_periods_variable_for_line_chart++;

                                //if this perticular period is present then i need to find the subject at that period ....and then need to increment that subject tally
                                //but first get the the name of the perticular table of sunjects attached with this week no
                                if(no_of_subjects!=0) {
                                    Cursor res53 = myDb__2.getAllData(corrected_table_names[i]);
                                    res53.move(no_of_period + 4);
                                    String subject_table_name = res53.getString(1);

                                    Cursor res77 = myDb_3.getAllData(subject_table_name);
                                    res77.move(m + 1);
                                    String subject_name = res77.getString(j + 1);

                                    //now the perticular subject name from a perticular period is identified and now we will find that name in our array and then increment its occurences
                                    int k;
                                    for (k = 0; k < no_of_subjects; k++) {
                                        if (name_of_subjects_list.get(k).equals(subject_name)) {
                                            occureneces_of_subjects[k] = occureneces_of_subjects[k] + 1;
                                        }
                                    }
                                    res53.close();
                                    res77.close();
                                }
                                //--------------------------------------------------------------------------------------------------------
//now just increment the corresponding day of occurenceofperiods aray
                                occureneces_of_periods[m] = occureneces_of_periods[m] + 1;

                                count_for_period_of_a_day = count_for_period_of_a_day + 1;
                            }
                            if (res5.getString(j + 1).equals("ABSENT")) {
                                total_no_of_periods_marked_ABSENT = total_no_of_periods_marked_ABSENT + 1;
                                //computation for linechart
                                absent_periods_variable_for_line_chart++;
                            }
                            if (res5.getString(j + 1).equals("HOLIDAY")) {
                                total_no_of_periods_marked_HOLIDAY = total_no_of_periods_marked_HOLIDAY + 1;
                            }


                            res5.close();
                        }

                        present_periods_counter_for_days_y_axis_list.add(count_for_period_of_a_day);
                        //now the computations for the graph
                        String COL_NAME = res3.getColumnName(j + 1);

                        days_name_x_axis_list.add(COL_NAME);


                    } else {

                        total_no_of_periods_marked_HOLIDAY = total_no_of_periods_marked_HOLIDAY + no_of_period;

                    }
                    res3.close();
                    res59.close();

                }
                //computation for linechart
                week_percentage_y_axis_list.add((present_periods_variable_for_line_chart/(present_periods_variable_for_line_chart + absent_periods_variable_for_line_chart))*100);


                present_periods_list_for_timeline_chart.add(present_periods_variable_for_line_chart);
                absent_periods_list_for_timeline_chart.add(absent_periods_variable_for_line_chart);



            }

            res2.close();
        }
        res.close();

        /*float present_periods_variable_for_line_chart_timeline=0;
        float absent_periods_variable_for_line_chart_timeline=0;

        for (int k =week_percentage_y_axis_list.size()-1;k>-1;k--){
             present_periods_variable_for_line_chart_timeline = present_periods_variable_for_line_chart_timeline + p.get(k);
                absent_periods_variable_for_line_chart_timeline = absent_periods_variable_for_line_chart_timeline + a.get(k);

            week_percentage_for_timeline_y_axis_list.add( (present_periods_variable_for_line_chart_timeline/(present_periods_variable_for_line_chart_timeline + absent_periods_variable_for_line_chart_timeline)) *100 );
        }*/



/////=======================================================================================================================================================================




        total_attendence = ((total_no_of_periods_marked_present) / (total_no_of_periods_marked_present + total_no_of_periods_marked_ABSENT)) * 100;



//============================================================================================================================================================================
        //below lines for testing purpose





    /*   final String TAG = "attendence_analyse_";

        Log.i(TAG, "attendance " +total_attendence);
        Log.i(TAG, "present " +total_no_of_periods_marked_present);
        Log.i(TAG, "absent " +total_no_of_periods_marked_ABSENT);
        Log.i(TAG, "attendance " +"=====================================================================================================================");
        Log.i(TAG, "attendance " +"=====================================================================================================================");

*/









/*         for (int k =0;k<week_percentage_y_axis_list.size();k++){
            Log.i(TAG, "week name " + week_name_x_axis_list.get(k));
            Log.i(TAG, "week percent " + week_percentage_y_axis_list.get(k));
        }

        Log.i(TAG, "attendance " +"=====================================================================================================================");
        Log.i(TAG, "attendance " +"=====================================================================================================================");




        for (int k =0;k<week_percentage_y_axis_list.size();k++){
            Log.i(TAG, "week name " + week_name_x_axis_list.get(k));
            Log.i(TAG, "week percent " + week_percentage_for_timeline_y_axis_list.get(k));
        }


Float[] return_indivisual_week_percent_list_as_array = new Float[week_percentage_y_axis_list.size()];
        week_percentage_y_axis_list.toArray(return_indivisual_week_percent_list_as_array);*/


      /*  Log.i(TAG, "attendance " +"#######################################################################################");
        for (int k =0;k<return_indivisual_week_percent_list_as_array.length ;k++){
            Log.i(TAG, "week name " + return_indivisual_week_percent_list_as_array[k]);
            //  Log.i(TAG, "week percent " + .get(k));
        }*/



        //below lines for testing purpose
        /**
        TextView percent_tv_java = (TextView) findViewById(R.id.tv_for_percent_xml);
        total_attendence = ((total_no_of_periods_marked_present) / (total_no_of_periods_marked_present + total_no_of_periods_marked_ABSENT)) * 100;


        percent_tv_java.setText(String.valueOf("total_no_of_periods_marked_ABSENT -" + total_no_of_periods_marked_ABSENT + "" +
                "" + "total_no_of_periods_marked_present -" + total_no_of_periods_marked_present + "" +
                "" + "total_no_of_periods_ever occured -" + total_no_periods_occured + "" +
                "" + "total_no_of_periods_marked_holiday" + total_no_of_periods_marked_HOLIDAY + "" +
                "TOtal percentage " + total_attendence + "%" + "" +
                "------------------------------------------------------" + "" + "" +
                "" +
               // name_of_subjects[1] + "-" + occureneces_of_subjects[1] + "////" + name_of_subjects[0] + "-" + occureneces_of_subjects[0] + "////" + name_of_subjects[2] + "-" + occureneces_of_subjects[2] + "" +
                "-------------------------------------------------------" + "" + "" +
                "" + "monday - " + occureneces_of_weekdays[0] + "////tuesday - " + occureneces_of_weekdays[1] + "////wednesday -" + occureneces_of_weekdays[2] +
                "-------------------------------------------------------" + "" + "" +
                "" + "1st period - " + occureneces_of_periods[0] + "////2nd period - " + occureneces_of_periods[1] + "////3rd period -" +
                "-------------------------------------------------------" + "" + "" +
                "" + "1st-" + days_name_x_axis_list.get(0) + " - " + present_periods_counter_for_days_y_axis_list.get(0) + "////2nd - " + days_name_x_axis_list.get(1) + " -" + present_periods_counter_for_days_y_axis_list.get(1) +
                "-------------------------------------------------------" + "" + "" +
                "" + "total no elements in day list-" + days_name_x_axis_list + " - " + present_periods_counter_for_days_y_axis_list.get(0) + "////2nd - " + days_name_x_axis_list.get(1) + " -" + present_periods_counter_for_days_y_axis_list.get(1)
        ));
**/


    }

//---------------------------------------------------------------TARUN PAL- METHODS----------------------------------------------------------
    private void calculation_for_graph() {
        for (int i = 0; i < no_of_subjects; i++) {
            combined_attendence_of_all_subjects = combined_attendence_of_all_subjects + occureneces_of_subjects[i];
        }
int e=0;
        for(int i =0;i<no_of_subjects;i++) {
            if (occureneces_of_subjects[i] == 0) {
                name_of_subjects_list.remove(i-e);
                e++;
            }
        }
        name_of_subjects = new String[name_of_subjects_list.size()];
            for(int i =0;i<name_of_subjects_list.size();i++){
               name_of_subjects[i] = name_of_subjects_list.get(i);
                }
int k=0;
        data_for_subjects_graph = new float[name_of_subjects.length];
        for (int i = 0; i < occureneces_of_subjects.length; i++) {
            if (occureneces_of_subjects[i] != 0) {
                data_for_subjects_graph[k] = (occureneces_of_subjects[i] / combined_attendence_of_all_subjects) * 100;
                k++;
            }
        }
//--------------
        int g = 0 ;
        for(int i =0;i<no_of_period;i++) {
            if (occureneces_of_periods[i] == 0) {
                name_of_periods_list.remove(i-g);
                g++;
            }
        }
        name_of_periods = new String[name_of_periods_list.size()];
        for(int i =0;i<name_of_periods_list.size();i++){
            name_of_periods[i] = name_of_periods_list.get(i);
        }
int a=0;
        data_for_periods_graph = new float[name_of_periods.length];
        for (int i = 0; i < occureneces_of_periods.length; i++) {
            if (occureneces_of_periods[i] != 0) {
                data_for_periods_graph[a] = (occureneces_of_periods[i] / total_no_of_periods_marked_present) * 100;
           a++;
            }
        }
//--------------
        float combined_attendence_of_all_weekdays = 0;


        for (int i = 0; i < 7; i++) {
            combined_attendence_of_all_weekdays = combined_attendence_of_all_weekdays + occureneces_of_weekdays[i];
        }
        int total_no_of_working_days =0;
        int f =0;
        for(int i =0;i<7;i++){
            if(occureneces_of_weekdays[i]>0){
                total_no_of_working_days++;
            }else{
name_of_weekdays_list.remove(i-f);
f++;
            }
        }
        name_of_weekdays = new String[name_of_weekdays_list.size()];
        for(int i =0;i<name_of_weekdays_list.size();i++){
            name_of_weekdays[i] = name_of_weekdays_list.get(i);
        }

        data_for_weekdays_graph = new float[total_no_of_working_days];
        int j=0;
        for (int i = 0; i <7 ; i++) {

            if(occureneces_of_weekdays[i]>0){
                data_for_weekdays_graph[j] = (occureneces_of_weekdays[i] / combined_attendence_of_all_weekdays) * 100;
                j++;

            }
        }
    }
    //---------------------------------------------------------------------------------------------------------
}

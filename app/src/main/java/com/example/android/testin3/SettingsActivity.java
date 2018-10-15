package com.example.android.testin3;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.testin3.data.DatabaseHelper;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.io.File;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    LinearLayout mLinearLayout;
    LinearLayout mLinearLayoutHeader;
    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setUpbottomNavigationBar();
        changeStatusBarColor("#F57C00");
        builder=new AlertDialog.Builder(this);


        final DatabaseHelper myDb;
        myDb = new DatabaseHelper(this);


        RelativeLayout updatetimetable = (RelativeLayout) findViewById(R.id.rel2);
        updatetimetable.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent percent_intent = new Intent(SettingsActivity.this, time_table_taking_Activity_2.class);
                startActivity(percent_intent);

            }});



       RelativeLayout clr_cache = (RelativeLayout) findViewById(R.id.rel3);
        clr_cache.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
         deleteCache(SettingsActivity.this);

                Toast.makeText(SettingsActivity.this, "cache cleared",
                        Toast.LENGTH_SHORT).show();
            }});


      RelativeLayout clear_app_data = (RelativeLayout) findViewById(R.id.rel4);
        clear_app_data.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                clearAppData();


                Toast.makeText(SettingsActivity.this, "App data cleared",
                        Toast.LENGTH_SHORT).show();
            }});
        RelativeLayout contact_us=(RelativeLayout)findViewById(R.id.rel5);
        contact_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     Intent intent=new Intent(SettingsActivity.this,Contact_us_activity.class);
                     startActivity(intent);
            }
        });
        RelativeLayout rate_us=(RelativeLayout)findViewById(R.id.rel6);
        rate_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +getPackageName())));
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" +getPackageName())));
                }

            }
        });
        RelativeLayout share=(RelativeLayout)findViewById(R.id.rel7);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Download  \n https://play.google.com/store/apps/details?id=" + getPackageName());
                startActivity(Intent.createChooser(intent, "Share Via"));
            }
        });
        ImageView help=(ImageView)findViewById(R.id.imageViewhelp);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Choose Activity");
                builder.setMessage("Choose which activity you want to open on startup.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });


//-----------------------------------------------------------
        final CheckBox cb_tracker = (CheckBox)findViewById(R.id.tracker);
        final CheckBox cb_predicter = (CheckBox)findViewById(R.id.predicter);
        final CheckBox cb_Web_portal = (CheckBox)findViewById(R.id.Web_portal);
        //  EditorActivity-2
        // attendence_predicting_activity-3
        // web_view-4
        Cursor ress = myDb.getAllData2();
        ress.moveToFirst();
        ress.moveToNext();
        int intent_variable= ress.getInt(ress.getColumnIndex("mon"));

        if(intent_variable == 2) {
cb_tracker.setChecked(true);

        }else if(intent_variable == 3) {

            cb_predicter.setChecked(true);
        }else if(intent_variable == 4) {
            cb_Web_portal.setChecked(true);

        }



        //  EditorActivity-2
        // attendence_predicting_activity-3
        // web_view-4

        cb_tracker.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
myDb.updateData_perticular_column(0,2);
cb_predicter.setChecked(false);
                cb_Web_portal.setChecked(false);
            }});


        cb_predicter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                myDb.updateData_perticular_column(0,3);
                cb_tracker.setChecked(false);
                cb_Web_portal.setChecked(false);

            }});

        cb_Web_portal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                myDb.updateData_perticular_column(0,4);
                cb_tracker.setChecked(false);
                cb_predicter.setChecked(false);

            }});


//---------------------------------------------

    }



    private void clearAppData() {
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                ((ActivityManager)getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
            } else {
                String packageName = getApplicationContext().getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear "+packageName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {}
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
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
    private void setUpbottomNavigationBar() {
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_setting_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        //bottomNavigationView.getItemBackgroundResource(R.color.colorAccent);

                        Intent intent = new Intent(SettingsActivity.this, EditorActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_analyse:
                        Intent intent1 = new Intent(SettingsActivity.this, attendence_analyse_activity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.menu_setting:
                        Intent intent2 = new Intent(SettingsActivity.this, SettingsActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.attendencepredecting:
                        Intent intent3=new Intent(SettingsActivity.this,attendence_predicting_activity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.attendencewebview:
                        Intent intent4=new Intent(SettingsActivity.this,web_view.class);
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
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        Intent intent = new Intent(SettingsActivity.this, EditorActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_analyse:
                        Intent intent1 = new Intent(SettingsActivity.this, attendence_analyse_activity.class);
                        startActivity(intent1);
                        break;
                    case R.id.menu_setting:
                        Intent intent2 = new Intent(SettingsActivity.this, SettingsActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.attendencepredecting:
                        Intent intent3=new Intent(SettingsActivity.this,attendence_predicting_activity.class);
                        startActivity(intent3);
                        break;
                    case R.id.attendencewebview:
                        Intent intent4=new Intent(SettingsActivity.this,web_view.class);
                        startActivity(intent4);
                        break;


                }


            }
        });
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);

        //----------------------------------------------------------------------------------------------------

    }






}

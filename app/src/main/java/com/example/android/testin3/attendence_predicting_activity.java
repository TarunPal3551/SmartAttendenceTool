package com.example.android.testin3;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class attendence_predicting_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_predicting_activity);


        CardView bt1 = (CardView) findViewById(R.id.button);
        CardView bt2 = (CardView)findViewById(R.id.cv_bt_2) ;

        setUpbottomNavigationBar();
        changeStatusBarColor("#303F9F");

        //this below line is for preventing the keyboard to open automatically on activitty startup
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);




        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent percent_intent = new Intent(attendence_predicting_activity.this, attendence_predict_1.class);

                startActivity(percent_intent);
            }
        });




        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                EditText ed_per = (EditText) findViewById(R.id.ed_per);
                EditText ed_atten = (EditText) findViewById(R.id.ed_atten);
                EditText ed_presen = (EditText) findViewById(R.id.ed_presen);


                //this if statement is for loading toast  when EditText views are left blank or given negative values
                if (ed_per.getText().toString().trim().length() > 0 && ed_atten.getText().toString().trim().length() > 0 &&  ed_presen.getText().toString().trim().length() > 0) {

                    int data[] = new int[3];
                    data[0] =Integer.parseInt(ed_per.getText().toString());
                    data[1] =Integer.parseInt(ed_atten.getText().toString());
                    data[2] =Integer.parseInt(ed_presen.getText().toString());


                    Intent percent_intent = new Intent(attendence_predicting_activity.this, attendence_predict_2.class);
                    percent_intent.putExtra("ARRAY_FOR_2_DATA", data);

                    startActivity(percent_intent);


                }else{

                    Toast.makeText(attendence_predicting_activity.this, "please enter all the requirements first", Toast.LENGTH_SHORT).show();



                }



            }
        });
//---------------------------------------------------------------------------------------------------

        final LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.expandable_3);
        //set visibility to GONE
        mLinearLayout.setVisibility(View.GONE);

        RelativeLayout mLinearLayoutHeader = (RelativeLayout) findViewById(R.id.top_toolbar_2);
        final Boolean[] _is_arrow_up_or_layout_minimised = {true};

        final ImageView up_arrow =(ImageView)findViewById(R.id.up_arrow) ;
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


        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView_3);



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



        bt1.setOnTouchListener(new View.OnTouchListener() {
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

        bt2.setOnTouchListener(new View.OnTouchListener() {
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





    }


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

    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
    private void setUpbottomNavigationBar() {
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_predecting_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        //bottomNavigationView.getItemBackgroundResource(R.color.colorAccent);

                        Intent intent = new Intent(attendence_predicting_activity.this, EditorActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_analyse:
                        Intent intent1 = new Intent(attendence_predicting_activity.this, attendence_analyse_activity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.menu_setting:
                        Intent intent2 = new Intent(attendence_predicting_activity.this, SettingsActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.attendencepredecting:
                        Intent intent3=new Intent(attendence_predicting_activity.this,attendence_predicting_activity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.attendencewebview:
                        Intent intent4=new Intent(attendence_predicting_activity.this,web_view.class);
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
                        Intent intent = new Intent(attendence_predicting_activity.this, EditorActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_analyse:
                        Intent intent1 = new Intent(attendence_predicting_activity.this, attendence_analyse_activity.class);
                        startActivity(intent1);
                        break;
                    case R.id.menu_setting:
                        Intent intent2 = new Intent(attendence_predicting_activity.this, SettingsActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.attendencepredecting:
                        Intent intent3=new Intent(attendence_predicting_activity.this,attendence_predicting_activity.class);
                        startActivity(intent3);
                        break;
                    case R.id.attendencewebview:
                        Intent intent4=new Intent(attendence_predicting_activity.this,web_view.class);
                        startActivity(intent4);
                        break;


                }


            }
        });
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        //----------------------------------------------------------------------------------------------------


    }





}







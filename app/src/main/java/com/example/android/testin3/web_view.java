package com.example.android.testin3;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.testin3.data.DatabaseHelper;

import java.util.ArrayList;

public class web_view extends AppCompatActivity{


    private ProgressBar progressBar;
    DatabaseHelper myDb;
    Boolean is_url_loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_web_view);
        setUpbottomNavigationBar();
        changeStatusBarColor("#388E3C");
        //this below line is for preventing the keyboard to open automatically on activitty startup
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        myDb = new DatabaseHelper(this);


        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        final WebView webView = (WebView) findViewById(R.id.webView);

        //THIS BELOW CODE makes the url open in the app only not in the browser....but keeping it open is not letting the portal open ..though the login ppage opens ....so i m disabling it by commenticisimg the code.....and the app is opening the url in th app only even after disabling that webview clint ....may be because of chrome client
          webView.setWebViewClient(new MyWebViewClient());


        WebSettings webSettings = webView.getSettings();
        //javascript makes some webpages with javascript as sript open
        webSettings.setJavaScriptEnabled(true);


        //below 2 lines make sure that the webpage is not zoomed in when first loaded
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        //to enable pinch zoom in feature
        webView.getSettings().setBuiltInZoomControls(true);


       // webView.loadUrl("http://www.google.com");


        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final EditText ed_for_url = (EditText)findViewById(R.id.edittext_for_url) ;

        final ArrayList<String> history = new ArrayList<String>();
        //giving starting value as null
        //final String[] current_url_loaded = new String[1];
      //  current_url_loaded[0]="null";


        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);
                    is_url_loading= false;

                    //removing the check on bookmaek if it had any
                    ImageView iv_vheckmark =(ImageView)findViewById(R.id.checkmark_icon);
                    if( iv_vheckmark.getVisibility() == View.VISIBLE){
                        iv_vheckmark.setVisibility(View.GONE);
                    }

                    //first swt the loaded link to the textview
                    ed_for_url.setText(webView.getUrl());
                    //now checking for if its already bookmarked so that tick icon can be added to the bookmark icon
                    for (int i =2 ;i<9;i++) {
                        Cursor res3 = myDb.getAllData_3();
                        res3.move(i);
                        String url_in_db= res3.getString(1);
                      String recieved_url=  webView.getUrl();
                        if(recieved_url.equals(url_in_db) ){
                            iv_vheckmark.setVisibility(View.VISIBLE);
                        }

                        res3.close();

                    }
//for ipec portal related

                    if((webView.getUrl()).equals("http://ipeclive.ipec.org.in/")){
                        myDb.insertData_4("checkbox_status","false");
                        Cursor res = myDb.getAllData_4();
                        res.moveToFirst();
                        if( (res.getString(1)).equals("false"))
                        {
                            set_up_dialog_box_2(webView);
                        }

                    }
                    //----------------------------------------------------
                    history.add(webView.getUrl());
               //     current_url_loaded[0] =webView.getUrl();


                    ImageView back_button =(ImageView)findViewById(R.id.back_button);

                    if(history.size()==4){
                        back_button.setVisibility(View.VISIBLE);
                    }else  if(history.size()==2){
                        back_button.setVisibility(View.GONE);
                    }


                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    is_url_loading= true;

                }
            }
        });

//------------------------------setting the bookmark related things

        //inserting the status of the home[page url at row 1 ....other rows will contain the other bookmarked url
        //giving default as no homepage
        //starting inserting data from row 2 or id 2 because no 1 row have the variable which tells the status of the homepage
     myDb.insertData_3("1","http://www.google.com");
        myDb.insertData_3("2","http://www.google.com");
        myDb.insertData_3("3","null");
        myDb.insertData_3("4","null");
        myDb.insertData_3("5","null");
        myDb.insertData_3("6","null");
        myDb.insertData_3("7","null");
        myDb.insertData_3("8","null");

        Cursor res420 = myDb.getAllData_3();
        res420.moveToFirst();
        webView.loadUrl(res420.getString(1));


        ImageView iv_bookmark =(ImageView)findViewById(R.id.bookmark_icon);
        iv_bookmark.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

               String  url_recieved =  webView.getUrl();

                     int total_legit_db_rows =6;

                     Boolean url_already_exists =false;
                     //checking if url does is bookmarked laready or not
                int no_of_urls =0;
                for (int i =2 ;i<9;i++) {
                    Cursor res3 = myDb.getAllData_3();
                    res3.move(i);
                   String url_in_db= res3.getString(1);
                    if(url_recieved.equals(url_in_db) ){
                        url_already_exists = true;
                    }
                    if(!url_in_db.equals("null") ){
                        no_of_urls ++;
                    }
                    res3.close();
                }

                       /* // values are true means each period element is disactive and have color white and vice versa
                        //below we pass the column no as a parameter to getstring as one more than j....because column start from 0 and the first column is of id not days
                        int a = m+1;
                        String str = res3.getString(a);*/

                     //  if the page is loading then the webview returns null
                       if(no_of_urls -1 <7 && is_url_loading == false && url_already_exists == false) {

                           int the_closest_row_with_null_value =3;
                           for (int j = the_closest_row_with_null_value ;j<9;j++) {
                               Cursor res3 = myDb.getAllData_3();
                               res3.move(j);
                               String url_in_db= res3.getString(1);
                               if(url_in_db.equals("null") ){
                                   break;
                               }
                               the_closest_row_with_null_value++;
                           }
                           //starting inserting data from row 2 or id 2 because no 1 row have the variable which tells the status of the homepage
                           final boolean STATUS =    myDb.updateData_3(String.valueOf(the_closest_row_with_null_value), url_recieved);
                           //removing the check on bookmaek if it had any
                           ImageView iv_vheckmark =(ImageView)findViewById(R.id.checkmark_icon);
                           iv_vheckmark.setVisibility(View.VISIBLE);

                           Toast.makeText(getApplicationContext(), String.valueOf(STATUS)+ "...."+ url_recieved,
                                   Toast.LENGTH_SHORT).show();


                       }else {
                           if (no_of_urls >= 8) {

                               Toast.makeText(getApplicationContext(), "bookmarks limit of 6 is reached...try deleting some bookmarks first ",
                                       Toast.LENGTH_SHORT).show();
                           }
                           if( is_url_loading == true){

                               Toast.makeText(getApplicationContext(), "error bookmarking page ..let the page load first ",
                                       Toast.LENGTH_SHORT).show();
                           }
                           if( url_already_exists == true){

                               Toast.makeText(getApplicationContext(), "url ia already bookmarked ",
                                       Toast.LENGTH_SHORT).show();
                           }
                       }

            }
        });

//--------------------------------------------------------------------------------------------------------


//----------------------------------------------------------------------------------------------------------
        ImageView iv_icon_list =(ImageView)findViewById(R.id.list);
        iv_icon_list.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                set_up_dialog_box(webView);

               RelativeLayout rl_bckgrnd_for_dialog = (RelativeLayout)findViewById(R.id.background_for_dialog_box);
                RelativeLayout rl_for_dialog_box = (RelativeLayout)findViewById(R.id.dialog_box);
             if(  rl_bckgrnd_for_dialog.getVisibility() == View.GONE){

                 rl_bckgrnd_for_dialog.setVisibility(View.VISIBLE);
                 rl_for_dialog_box.setVisibility(View.VISIBLE);


             }else{
                 rl_bckgrnd_for_dialog.setVisibility(View.GONE);
                 rl_for_dialog_box.setVisibility(View.GONE);

             }


            }
        });
//---------------------------------------------------------------------
        ImageView back_button =(ImageView)findViewById(R.id.back_button);
        back_button.setOutlineProvider(new CustomOutlineProvider_for_ustom_dialog_box(50));
        back_button.setClipToOutline(true);


        back_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

          /*      history.remove(history.size()-1);
                String url = history.get(history.size()-1);
                history.remove(history.size()-1);*/
                history.remove(history.size()-1);
                history.remove(history.size()-1);
                webView.loadUrl(history.get(history.size()-1));
                history.remove(history.size()-1);
                history.remove(history.size()-1);

            }
        });




    }

    private void set_up_dialog_box_2(final WebView web_view) {

//i am giving default value as a single spacebar stroke " "

        myDb.insertData_4("username","  ");
        myDb.insertData_4("password","  ");

        //giving rounded corners to the dialog box
        final RelativeLayout dialog_box = (RelativeLayout) findViewById(R.id.dialog_box_2_save_id_psswrd);
        dialog_box.setOutlineProvider(new CustomOutlineProvider_for_ustom_dialog_box(30));
        dialog_box.setClipToOutline(true);


        final RelativeLayout rl_bckgrnd_for_dialog = (RelativeLayout)findViewById(R.id.background_for_dialog_box);
            rl_bckgrnd_for_dialog.setVisibility(View.VISIBLE);
            dialog_box.setVisibility(View.VISIBLE);


        final EditText ed_username =(EditText)findViewById(R.id.edittext_for_ID);
        final EditText ed_password =(EditText)findViewById(R.id.edittext_for_passwrd);


        Cursor res = myDb.getAllData_4();
        res.move(2);
        if(!res.getString(1).equals( "  " )  ) {
            ed_username.setText(res.getString(1));
            res.moveToNext();
            ed_password.setText(res.getString(1));
        }


//----------------------------------------
        final TextView tv_cancel = (TextView)findViewById(R.id.cancel_2);
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                rl_bckgrnd_for_dialog.setVisibility(View.GONE);
                dialog_box.setVisibility(View.GONE);

            }
        });


        final TextView ok = (TextView)findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {



                String test =String.valueOf(ed_username.getText());

                myDb.updateData_4("username", String.valueOf(ed_username.getText()));
                myDb.updateData_4("password",String.valueOf(ed_password.getText()));


                /*String string2 = "165153";
                String string3 = "635262";
                */
                web_view.loadUrl("javascript:var x = document.getElementById('txtuser').value = '" +  String.valueOf(ed_username.getText()) + "';var y = document.getElementById('txtpwd').value='" + String.valueOf(ed_password.getText()) + "';");


                rl_bckgrnd_for_dialog.setVisibility(View.GONE);
                dialog_box.setVisibility(View.GONE);


                CheckBox cb =(CheckBox)findViewById(R.id.cb);
                if(cb.isChecked()){
                    myDb.updateData_4("checkbox_status", "true");
                }
            }
        });

TextView tv_do_not_show_box =(TextView)findViewById(R.id.tv_do_not_show_this_msg_box);
        final CheckBox cb =(CheckBox)findViewById(R.id.cb);
tv_do_not_show_box.setOnClickListener(new View.OnClickListener() {

    public void onClick(View view) {
        if(cb.isChecked()){
            cb.setChecked(false);
        }else {
            cb.setChecked(true);
        }
    }
});

//-------------------------------------


    }

    private void set_up_dialog_box(final WebView web_view) {

        //giving rounded corners to the dialog box
        final RelativeLayout dialog_box = (RelativeLayout) findViewById(R.id.dialog_box);
        dialog_box.setOutlineProvider(new CustomOutlineProvider_for_ustom_dialog_box(30));
        dialog_box.setClipToOutline(true);
//------------------------------------------------------------------------------

//giving the homepage url the homepage url at position 2
        final TextView tv_of_homepage_url =(TextView)findViewById(R.id.tv_homepage);
        Cursor res = myDb.getAllData_3();
           res.move(1);
          tv_of_homepage_url.setText(res.getString(1));
          res.close();

          //setting gclicklistener to it
        final LinearLayout ll_of_homepage_row_in_dialog_box = (LinearLayout)findViewById(R.id.ll_of_homepage_url_in_dialog_box);
        ll_of_homepage_row_in_dialog_box.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                web_view.loadUrl(String.valueOf(tv_of_homepage_url.getText()));

                RelativeLayout rl_bckgrnd_for_dialog = (RelativeLayout)findViewById(R.id.background_for_dialog_box);
                rl_bckgrnd_for_dialog.setVisibility(View.GONE);
                dialog_box.setVisibility(View.GONE);
            }
        });

        ImageView iv_for_homepage_url =(ImageView)findViewById(R.id.iv_for_hompage_url_in_dialog_box);

        add_press_effect_button_to_dialog_elements(ll_of_homepage_row_in_dialog_box,iv_for_homepage_url,tv_of_homepage_url);
//----------------------------------------------------------------------------------------

          //now listing alll the bookmarked url in the list//but mkiang view for them programmattically
        final TextView[] tv_of_bookmarked_url = new TextView[7];
        final LinearLayout[] ll_row_container = new LinearLayout[7];
        final ImageView[] iv_ = new ImageView[7];

        //this below string tells the status of dialog box...it could be NORMAL-default ,CHOOSE_HOMEPAGE-  ,DELETE_BOOKMARKS
        //its an array rather than variable...because it was not been able to be accessed form below codes otherwise
        final String[] Status_of_the_dialog_box = {"NORMAL"};


        final LinearLayout ll_container = (LinearLayout)findViewById(R.id.container_for_url_rows);
        //clearing all the previous textvies
        ll_container.removeAllViews();

        final float scale = getResources().getDisplayMetrics().density;

        final LinearLayout.LayoutParams lp_for_ll_row= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,(int) (30 * scale + 0.5f));
        lp_for_ll_row.setMargins(0,(int) (3 * scale + 0.5f),0,0);

        final LinearLayout.LayoutParams lp_for_tv_for_url= new LinearLayout.LayoutParams((int) (0 * scale + 0.5f),(int) (25 * scale + 0.5f),13.0f);

        final LinearLayout.LayoutParams lp_for_iv= new LinearLayout.LayoutParams((int) (0 * scale + 0.5f),(int) (25 * scale + 0.5f),1.0f);


        for(int i=0; i<7;i++){

            ll_row_container[i] = new LinearLayout(this);
            tv_of_bookmarked_url[i] = new TextView(this);
            iv_[i] = new ImageView(this);


            ll_row_container[i].setLayoutParams(lp_for_ll_row);
            ll_row_container[i].setPadding((int) (3 * scale + 0.5f),(int) (3 * scale + 0.5f),(int) (3 * scale + 0.5f),(int) (3 * scale + 0.5f));
            ll_row_container[i].setOrientation(LinearLayout.HORIZONTAL);
        //    ll_row_container[i].setBackgroundResource(R.drawable.ripple_for_dialog_box_elements);


            tv_of_bookmarked_url[i].setLayoutParams(lp_for_tv_for_url);
          //  tv_of_bookmarked_url[i].setText( );
            tv_of_bookmarked_url[i].setTextSize((int) (16 ));

            iv_[i].setLayoutParams(lp_for_iv);
            iv_[i].setImageResource(R.drawable.arrow_transparent);

            ll_row_container[i].addView(tv_of_bookmarked_url[i]);
            ll_row_container[i].addView(iv_[i]);
           // ll_container.addView(ll_row_container[i]);


//-------------------------------------------------------------------

            Cursor res_3 = myDb.getAllData_3();
            res_3.move(i+2);
            if( !res_3.getString(1).equals("null")) {
                tv_of_bookmarked_url[i].setText(res_3.getString(1));
                ll_container.addView(ll_row_container[i]);
            }


//-----------------------------------------------------------------------------------------------

            final int finalI = i;
            final int finalI1 = i;
            final int finalI2 = i;
            final int finalI3 = i;
            final int finalI4 = i;
            final int finalI5 = i;
            ll_row_container[i].setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {


                    if(Status_of_the_dialog_box[0] == "NORMAL") {
                        web_view.loadUrl(String.valueOf(tv_of_bookmarked_url[finalI].getText()));

                        RelativeLayout rl_bckgrnd_for_dialog = (RelativeLayout) findViewById(R.id.background_for_dialog_box);
                        rl_bckgrnd_for_dialog.setVisibility(View.GONE);
                        dialog_box.setVisibility(View.GONE);

                    }else if(  Status_of_the_dialog_box[0] == "CHOOSE_HOMEPAGE" ){


                        //since only one button can be selected ...i need to deselect all the others if that is the cas
                        for(int i=0; i<7;i++){
                            iv_[i].setImageResource(R.drawable.radio_button_unmarked);
                        }
                        //then mark the one chooosen latest
                        iv_[finalI1].setImageResource(R.drawable.radio_button_marked);

                        boolean test = myDb.updateData_3("1",String.valueOf(tv_of_bookmarked_url[finalI1].getText()));

                        tv_of_homepage_url.setText(tv_of_bookmarked_url[finalI3].getText());


                        TextView bootom_tv_button = (TextView)findViewById(R.id.cancel_tv);
                        bootom_tv_button.setText("OK");

                    }else if(  Status_of_the_dialog_box[0] == "DELETE_BOOKMARKS" ){

                        if(finalI5 != 0 ) {

                           myDb.updateData_3(String.valueOf(finalI5+2),"null");
                         //   myDb.deleteData_3(finalI5);
                           /* Toast.makeText(getApplicationContext(), String.valueOf(Status),
                                    Toast.LENGTH_SHORT).show();*/

                            ll_container.removeView(ll_row_container[finalI5]);

                            TextView bootom_tv_button = (TextView) findViewById(R.id.cancel_tv);
                            bootom_tv_button.setText("OK");
                        }else {
                           // do nothing for first row
                        }

                    }


                }
            });

//----------------------------------------------------------------------------
add_press_effect_button_to_dialog_elements(ll_row_container[i] ,iv_[i],tv_of_bookmarked_url[i]);

        }



//-------------------------------------------------------------------
final TextView tv_cancel = (TextView)findViewById(R.id.cancel_tv);
        tv_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if(tv_cancel.getText().equals("CANCEL")) {
                    RelativeLayout rl_bckgrnd_for_dialog = (RelativeLayout) findViewById(R.id.background_for_dialog_box);
                    rl_bckgrnd_for_dialog.setVisibility(View.GONE);
                    dialog_box.setVisibility(View.GONE);
                }else  if(tv_cancel.getText().equals("OK")){
                    RelativeLayout rl_bckgrnd_for_dialog = (RelativeLayout) findViewById(R.id.background_for_dialog_box);
                    rl_bckgrnd_for_dialog.setVisibility(View.GONE);
                    dialog_box.setVisibility(View.GONE);


                    tv_cancel.setText("CANCEL");

                    ll_row_container[0].setClickable(true);
                }

            }
        });

//------------------------------------------------------------------

final ImageView iv_edit_bookmark_row = (ImageView) findViewById(R.id.edit_iv_of_homepage_row);

    iv_edit_bookmark_row.setOnClickListener(new View.OnClickListener() {

        public void onClick(View view) {

            Status_of_the_dialog_box[0] ="CHOOSE_HOMEPAGE";

            for(int i=0; i<7;i++){

            iv_[i].setImageResource(R.drawable.radio_button_unmarked);

            }

        }
    });
 //-------------------------------------------------------------------------------
        final ImageView iv_edit_delete_bookmarks_row = (ImageView) findViewById(R.id.edit_iv_of_delete_bookmarks_row);

        iv_edit_delete_bookmarks_row.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                Status_of_the_dialog_box[0] ="DELETE_BOOKMARKS";

                iv_[0].setImageResource(R.drawable.blank_icon);


                for(int i=1; i<7;i++){

                    iv_[i].setImageResource(R.drawable.delete);

                }

            }
        });


//---------------------------------------------------------------------
        //giving the pessed button effect to edit icons of the imageviews of edit homepage and delete bookmarks


        iv_edit_bookmark_row.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    iv_edit_bookmark_row.setBackgroundColor(Color.parseColor("#CC4baf4f"));
                    return false;
                }else if (event.getAction() == MotionEvent.ACTION_UP|| (event.getAction() == MotionEvent.ACTION_CANCEL)){
                    iv_edit_bookmark_row.setBackgroundColor(Color.parseColor("#ffffff"));
                    return false;
                }
                return false;
            }
        });

        iv_edit_delete_bookmarks_row.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    iv_edit_delete_bookmarks_row.setBackgroundColor(Color.parseColor("#CC4baf4f"));
                    return false;
                }else if (event.getAction() == MotionEvent.ACTION_UP|| (event.getAction() == MotionEvent.ACTION_CANCEL)){
                    iv_edit_delete_bookmarks_row.setBackgroundColor(Color.parseColor("#ffffff"));
                    return false;
                }
                return false;
            }
        });
    }






    private void add_press_effect_button_to_dialog_elements(final LinearLayout ll , final ImageView iv , final TextView tv) {
        ll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){

                    ll.setBackgroundColor(Color.parseColor("#334baf4f"));
                    iv.setBackgroundColor(Color.parseColor("#334baf4f"));
                    tv.setBackgroundColor(Color.parseColor("#334baf4f"));
                    return false;
                }else if (event.getAction() == MotionEvent.ACTION_UP|| (event.getAction() == MotionEvent.ACTION_CANCEL)){
                    ll.setBackgroundColor(Color.parseColor("#ffffff"));
                    iv.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv.setBackgroundColor(Color.parseColor("#ffffff"));
                    return false;
                }
                return false;
            }
        });

    }


    //this below method makes the webpage load the clicked webpage rather than throwin intent for other browser to open the cllicked link
   //but this method is detached due to some problom(read above)...but still keep it as it is....DO NOT COMMENTASCIE  IT
    private class MyWebViewClient extends WebViewClient {
        /*@SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }*/

        @RequiresApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            view.loadUrl(url);
            return true;
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
    private void setUpbottomNavigationBar() {
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_webview_activity);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        //bottomNavigationView.getItemBackgroundResource(R.color.colorAccent);

                        Intent intent = new Intent(web_view.this, EditorActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.menu_analyse:
                        Intent intent1 = new Intent(web_view.this, attendence_analyse_activity.class);
                        startActivity(intent1);
                        return true;
                    case R.id.menu_setting:
                        Intent intent2 = new Intent(web_view.this, SettingsActivity.class);
                        startActivity(intent2);
                        return true;
                    case R.id.attendencepredecting:
                        Intent intent3=new Intent(web_view.this,attendence_predicting_activity.class);
                        startActivity(intent3);
                        return true;
                    case R.id.attendencewebview:
                        Intent intent4=new Intent(web_view.this,web_view.class);
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
                        Intent intent = new Intent(web_view.this, EditorActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_analyse:
                        Intent intent1 = new Intent(web_view.this, attendence_analyse_activity.class);
                        startActivity(intent1);
                        break;
                    case R.id.menu_setting:
                        Intent intent2 = new Intent(web_view.this, SettingsActivity.class);
                        startActivity(intent2);
                        break;
                    case R.id.attendencepredecting:
                        Intent intent3=new Intent(web_view.this,attendence_predicting_activity.class);
                        startActivity(intent3);
                        break;
                    case R.id.attendencewebview:
                        Intent intent4=new Intent(web_view.this,web_view.class);
                        startActivity(intent4);
                        break;


                }


            }
        });
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);

        //----------------------------------------------------------------------------------------------------



    }








}



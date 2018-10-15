package com.example.android.testin3.data;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.android.testin3.MainActivity;
import com.example.android.testin3.R;

public class SplashAcivity extends AppCompatActivity {
    RelativeLayout splashLayout;
    ImageView splashimageicon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_acivity);
        splashLayout=(RelativeLayout)findViewById(R.id.layoutsplash);
        splashimageicon=(ImageView)findViewById(R.id.imagesplash);
        setSplashimageicon();


    }
    public void setSplashimageicon() {
        splashimageicon.setVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.simpleanimation);

        splashimageicon.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                }, 250);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}

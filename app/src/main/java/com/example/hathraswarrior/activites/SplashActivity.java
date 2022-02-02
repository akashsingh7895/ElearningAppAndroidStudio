package com.example.hathraswarrior.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hathraswarrior.MainActivity;
import com.example.hathraswarrior.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {


    private Animation topAnim,bottomAmin;
    private ImageView logoIv;
    private TextView titleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        topAnim = AnimationUtils.loadAnimation(this,R.anim.splash_top_anim);
        bottomAmin = AnimationUtils.loadAnimation(this,R.anim.splash_bottom_anim);
        logoIv = findViewById(R.id.logoIv);
        titleTv = findViewById(R.id.titleTv);
        logoIv.setAnimation(topAnim);
        titleTv.setAnimation(bottomAmin);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //todo: check if user is already is login

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

                if (firebaseAuth.getCurrentUser() ==null){
                    Intent mainIntent = new Intent(SplashActivity.this,AuthActivity.class);
                    startActivity(mainIntent);
                    finish();
                }else {
                  Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                  startActivity(intent);
                  finish();
                }


            }
        },300);


    }
}
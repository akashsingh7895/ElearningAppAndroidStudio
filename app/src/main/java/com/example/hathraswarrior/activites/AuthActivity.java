package com.example.hathraswarrior.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.example.hathraswarrior.R;
import com.example.hathraswarrior.fragment.LogInFragment;

public class AuthActivity extends AppCompatActivity {

   private FrameLayout frameLayout;
   public static boolean isLogInFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        frameLayout = findViewById(R.id.frameLayoutAuth);

        setDefaultFragment(new LogInFragment());
        isLogInFragment = true;



    }
    private void  setDefaultFragment(Fragment fragment)

    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            if (isLogInFragment){
                finish();
            }else {
                changeFragment(new LogInFragment());
                isLogInFragment = true;
            }
        }
        return false;
    }

    private void  changeFragment(Fragment fragment)

    {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.left_to_right,R.anim.right_to_left);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

}
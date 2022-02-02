package com.example.hathraswarrior;

import static com.example.hathraswarrior.activites.DeliveryActivity.TYPE_COURSE;
import static com.example.hathraswarrior.activites.DeliveryActivity.TYPE_NOTES;
import static com.example.hathraswarrior.activites.DeliveryActivity.TYPE_TEST;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hathraswarrior.activites.AuthActivity;
import com.example.hathraswarrior.activites.DeliveryActivity;
import com.example.hathraswarrior.fragment.HomeFragment;
import com.example.hathraswarrior.fragment.MyAccountFragment;
import com.example.hathraswarrior.fragment.MyCoursesFragment;
import com.example.hathraswarrior.fragment.MyNotesFragment;
import com.example.hathraswarrior.fragment.MyTestFragment;
import com.example.hathraswarrior.fragment.NotificationFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    public static DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FrameLayout parentFrameLayout;
    private TextView titleTv;
    private ImageView notificationMainIcon;
    private FirebaseAuth auth;

    public static int currentFragment = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.activity_main_toolbar);
        parentFrameLayout = findViewById(R.id.main_activity_frameLayout);
        titleTv = findViewById(R.id.titleTvName);
        notificationMainIcon = findViewById(R.id.notification_main_icon);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();



        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawerOpen,R.string.drawerClose);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setItemIconTintList(null);

        if (currentFragment==-1){
            setFragment( new HomeFragment());
        }else if (currentFragment==TYPE_NOTES){
            setFragment(new MyNotesFragment());
        }else if (currentFragment==TYPE_COURSE){
            setFragment(new MyCoursesFragment());
        }else if (currentFragment==TYPE_TEST){
            setFragment(new MyTestFragment());
        }else{

        }

            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                MenuItem menuItem;

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    menuItem = item;

                    if (user == null) {


                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.sigin_dilog);
                      //  dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corner_bacgkround_white));
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.setCancelable(true);

                        Button loginButton = dialog.findViewById(R.id.log_diloag_button);
                        loginButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this,AuthActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                        dialog.show();
                    } else {

                        drawerLayout.closeDrawer(GravityCompat.START);
                        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
                            @Override
                            public void onDrawerClosed(View drawerView) {
                                super.onDrawerClosed(drawerView);

                                switch (menuItem.getItemId()) {

                                    case R.id.nav_home:
                                        goToFragment(new HomeFragment(), "Hathras warrior");


                                        break;

                                    case R.id.nav_courses:
                                        goToFragment(new MyCoursesFragment(), "My courses");


                                        break;

                                    case R.id.nav_myAccount:
                                        goToFragment(new MyAccountFragment(), "MY Account");


                                        break;

                                    case R.id.nav_test:
                                        goToFragment(new MyTestFragment(), "My Test");


                                        break;

                                    case R.id.nav_notification:
                                        goToFragment(new NotificationFragment(), "Notification");

                                        break;


                                    case R.id.nav_shareApp:
                                        // statement
                                        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                                        shareIntent.setType("text/plain");
                                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Insert Subject here");
                                        String app_url = " https://play.google.com/store/apps/details?id= com.example.hathraswarrior";
                                        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, app_url);
                                        startActivity(Intent.createChooser(shareIntent, "Share via"));
                                        break;


                                    case R.id.nav_helpline:
                                        // statement
                                        Intent intent = new Intent(Intent.ACTION_DIAL);
                                        intent.setData(Uri.parse("tel:0123456789"));
                                        startActivity(intent);
                                        break;


                                    case R.id.nav_youtube:
                                        // statement
                                        String videoId = "https://youtu.be/WWhEUIyQ6ws";
                                        Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(videoId));
                                        intent1.setPackage("com.google.android.youtube");
                                        intent1.putExtra("VIDEO_ID", videoId);
                                        startActivity(intent1);
                                        break;


                                    case R.id.nav_telegram:
                                        // statement

                                        String telegramPackage = "org.telegram.messenger";
                                        try {

                                            PackageManager manager = getApplicationContext().getPackageManager();
                                            manager.getPackageInfo(telegramPackage, 0);

                                            Intent myIntent = new Intent(Intent.ACTION_SEND);
                                            myIntent.setType("text/plain");
                                            myIntent.setPackage(telegramPackage);
                                            myIntent.putExtra(Intent.EXTRA_TEXT, "Join Telegram");//
                                            startActivity(Intent.createChooser(myIntent, "Share with"));


                                        } catch (Exception e) {
                                            Toast.makeText(getApplicationContext(), "Telegram app is not installed", Toast.LENGTH_SHORT).show();
                                        }

                                        break;

                                    case R.id.nav_aboutUs:
                                        // statement

                                        String url = "http://www.stackoverflow.com";
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                        break;

                                    case R.id.nav_sign_out:

                                        // todo : clear all data
                                        FirebaseAuth.getInstance().signOut();
                                        Intent auth = new Intent(MainActivity.this, AuthActivity.class);
                                        startActivity(auth);
                                        finish();

                                        break;

                                }
                                drawerLayout.removeDrawerListener(this);

                            }
                        });
                    }
                        return true;

                    }


            });



            notificationMainIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user==null){

                        Dialog dialog = new Dialog(MainActivity.this);
                        dialog.setContentView(R.layout.sigin_dilog);
                        //  dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.rounded_corner_bacgkround_white));
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.setCancelable(true);

                        Button loginButton = dialog.findViewById(R.id.log_diloag_button);
                        loginButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this,AuthActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                    }else {
                        goToFragment(new NotificationFragment(), "Notification");
                    }

                }
            });

    }




    private void goToFragment(Fragment fragment, String title){
        setFragment(fragment);
        titleTv.setText(title);

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        transaction.replace(parentFrameLayout.getId(),fragment);
        transaction.commit();
    }
}
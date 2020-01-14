package com.example.diabetes.Exercises;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;


import com.example.diabetes.About;
import com.example.diabetes.Alarm.AlarmFragment;
import com.example.diabetes.Calculator.CalculatorFragment;
import com.example.diabetes.EditUser;
import com.example.diabetes.Exercises.Muscle.Muscle;
import com.example.diabetes.Exercises.YogaGymnastic.YogaGymnastic;
import com.example.diabetes.Food.FoodFragment;
import com.example.diabetes.Login.Login;
import com.example.diabetes.Helper.SharedPrefManager;
import com.example.diabetes.R;
import com.example.diabetes.Tracking.TrackingFragment;
import com.example.diabetes.Upgrade;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Exercise extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView nama;
    private FrameLayout fragmentContainer;
    private Button muscle;
    private Button swimming;
    private Button cycling;
    private Button jogging;
    private Button yoga;
    long lastPress;
    Toast backpressToast;
    boolean doubleBackToExitPressedOnce = false;
    //Variable Untuk Inisialisasi Database DBMahasiswa
    DrawerLayout mDrawerLayout;
    Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    SharedPrefManager sharedPrefManager;
    NavigationView mNavigationView;
    View mHeaderView;
    TextView nameTextView;
    String name;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private TextView mNameTextView;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        bottomNav.getMenu().findItem(R.id.nav_exercise).setChecked(true);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        sharedPrefManager = new SharedPrefManager(this);


        auth = FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Exercise.this, Login.class));
                    finish();
                }
            }
        };


        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null){
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.main_icon);

        }




        mDrawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        NavigationView navigationView = findViewById(R.id.nav_view);
        mDrawerLayout.addDrawerListener(drawerToggle);
        setupNavDrawer(navigationView);






        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        muscle = (Button) findViewById(R.id.btn_muscle);
        swimming = (Button) findViewById(R.id.btn_swimming);
        cycling = (Button) findViewById(R.id.btn_cycling);
        jogging = (Button) findViewById(R.id.btn_jogging);
        yoga = (Button) findViewById(R.id.btn_yoga);

/*
        SharedPreferences sharedPrefINFO = getSharedPreferences("userInfo", 0);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        String name = sharedPrefINFO.getString("settingUpName", "");
        nameTextView.setText(name);
*/
        muscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMuscle();
            }

            private void openMuscle() {

                Muscle fragment = Muscle.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                transaction.addToBackStack(null);
                transaction.add(R.id.fragment_container, fragment).commit();
            }
        });

        swimming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSwimming();
            }

            private void openSwimming() {
                Swimming fragment = Swimming.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                transaction.addToBackStack(null);
                transaction.add(R.id.fragment_container, fragment).commit();
            }
        });

        cycling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCycling();
            }

            private void openCycling() {
                Cycling fragment = Cycling.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                transaction.addToBackStack(null);
                transaction.add(R.id.fragment_container, fragment).commit();
            }
        });

        jogging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJogging();
            }

            private void openJogging() {
                Jogging fragment = Jogging.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                transaction.addToBackStack(null);
                transaction.add(R.id.fragment_container, fragment).commit();
            }
        });

        yoga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYoga();
            }

            private void openYoga() {
                YogaGymnastic fragment = YogaGymnastic.newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                transaction.addToBackStack(null);
                transaction.add(R.id.fragment_container, fragment).commit();
            }
        });



}




    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_calculator:
                            selectedFragment = new CalculatorFragment();
                            break;
                        case R.id.nav_tracking:
                            selectedFragment = new TrackingFragment();
                            break;
                        case R.id.nav_exercise:
                            selectedFragment = new Fragment();
                            break;
                        case R.id.nav_food:
                            selectedFragment = new FoodFragment();
                            break;
                        case R.id.nav_alarm:
                            selectedFragment = new AlarmFragment();
                           break;
                    }


                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(
                this,
                mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

Fragment fragment = null;

    private void setupNavDrawer(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
/*
                if (id == R.id.nav_edituser) {
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame,new EditUser());
                    ft.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                    ft.addToBackStack(null);
                    ft.commit();
                } else if (id == R.id.nav_upgrade) {
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame,new Upgrade());
                    ft.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                    ft.addToBackStack(null);
                    ft.commit();
                } else */if (id == R.id.nav_about) {
                    android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame,new About());
                    ft.setCustomAnimations(R.anim.fromright, R.anim.toright, R.anim.fromright, R.anim.toright);
                    ft.addToBackStack(null);
                    ft.commit();
                } else if (id == R.id.nav_logout) {
                    signOut();
                    finish();

                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


    }

    //sign out method
    public void signOut() {
        auth.signOut();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }



    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }


}



package com.example.newshunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,RecyclerViewOnclickInterface{
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // for full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // for setting up toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        navigationView = findViewById(R.id.navigation_view);
        drawerLayout = findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);

        // Setting up hamburger icon toggle
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);

        // adding listener to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        //enabling hamburger icon
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();

        //default fragment on app launch
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new HomeFragment(this));
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //close drawer after navigation
        drawerLayout.closeDrawer(GravityCompat.START);

        // getting menu item by id
        int screen = menuItem.getItemId();

        // switch case on different screens
        switch (screen)
        {
            case R.id.home : fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new HomeFragment(this));
                toolbar.setTitle("News Hunt");
                fragmentTransaction.commit();
                break;

            case R.id.entertainment : fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new EntertainmentFragment(this));
                toolbar.setTitle("News Hunt - Entertainment");
                fragmentTransaction.commit();
                break;

            case R.id.health : fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new HealthFragment(this));
                toolbar.setTitle("News Hunt - Health");
                fragmentTransaction.commit();
                break;

            case R.id.sports : fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new SportsFragment(this));
                toolbar.setTitle("News Hunt - Sports");
                fragmentTransaction.commit();
                break;

            case R.id.science : fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new ScienceFragment(this));
                toolbar.setTitle("News Hunt - Science");
                fragmentTransaction.commit();
                break;

            case R.id.technology : fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment,new TechnologyFragment(this));
                toolbar.setTitle("News Hunt - Technology");
                fragmentTransaction.commit();
                break;
        }
        return true;
    }


    @Override
    public void onClick(String url) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_fragment,new WebViewFragment(url));
        fragmentTransaction.addToBackStack(null).commit();
    }
}
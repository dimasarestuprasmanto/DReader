package com.dimasarp.dreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeActivity extends AppCompatActivity {

    private  static final String TAG = HomeActivity.class.getSimpleName();
    ChipNavigationBar botomNav;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final MaterialToolbar topappbar = (MaterialToolbar) findViewById(R.id.topAppBar);


        topappbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.chat:
                        startActivity(new Intent(getApplicationContext(),ChatActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(),SearchActivity.class));
                        overridePendingTransition(0,0);

                        return true;
                }
                return false;
            }
        });


        botomNav = findViewById(R.id.bottom_nav);

        if(savedInstanceState==null){
            botomNav.setItemSelected(R.id.dasboard, true);
            fragmentManager = getSupportFragmentManager();
            DashboardFragment dashboardFragment = new DashboardFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, dashboardFragment)
                    .commit();
        }

        botomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.dasboard:
                        fragment = new DashboardFragment();
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                        transaction.replace(R.id.fragment_container, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case R.id.manga:
                        fragment = new MangaFragment();
                        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                        transaction1.setCustomAnimations( R.anim.enter_from_right,R.anim.exit_to_left);
                        transaction1.replace(R.id.fragment_container, fragment);
                        transaction1.addToBackStack(null);
                        transaction1.commit();
                        break;
                }

            }
        });
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}

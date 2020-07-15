package com.dimasarp.dreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;


import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        BottomNavigationView topappbar = (BottomNavigationView)findViewById(R.id.topAppBar);

        topappbar.setSelectedItemId(R.id.home);

        topappbar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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


    }
}

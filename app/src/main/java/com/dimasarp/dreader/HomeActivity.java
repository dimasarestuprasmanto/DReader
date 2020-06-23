package com.dimasarp.dreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class HomeActivity extends AppCompatActivity {
    private  static final String TAG = HomeActivity.class.getSimpleName();
    ChipNavigationBar botomNav;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        botomNav = findViewById(R.id.bottom_nav);

        if(savedInstanceState==null){
            botomNav.setItemSelected(R.id.book, true);
            fragmentManager = getSupportFragmentManager();
            BookFragment bookFragment = new BookFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, bookFragment)
                    .commit();
        }
        botomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.recently:
                        fragment = new RecentlyFragment();
                        break;
                    case R.id.book:
                        fragment = new BookFragment();
                        break;
                    case R.id.chat:
                        fragment = new ChatFragment();
                        break;
                    case R.id.setting:
                        fragment = new SettingFragment();
                        break;
                }

                if(fragment != null){
                    fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                }else{
                    Log.e(TAG, "Error in creating fragment");
                }
            }
        });
    }
}

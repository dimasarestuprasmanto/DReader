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
            botomNav.setItemSelected(R.id.home, true);
            fragmentManager = getSupportFragmentManager();
            MangaFragment mangaFragment = new MangaFragment();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, homeFragment)
                    .commit();
        }

        botomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i){
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.manga:
                        fragment = new MangaFragment();
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

package com.dimasarp.dreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dimasarp.dreader.Adapter.MyComicListAdapter;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Model.Comic;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements BottomFilter.BottomSheetListener {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recycler_comic;
    EditText search;
    TextWatcher text = null;
    Integer i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        MaterialToolbar topappbar = findViewById(R.id.topAppBar);
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

        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh_manga);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));

        recycler_comic = findViewById(R.id.recycler_comic_list);
        recycler_comic.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_comic.setLayoutManager(mLayoutManager);


        search = topappbar.findViewById(R.id.search_manga);

        text = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fetchSearchComic(search.getText().toString().toLowerCase());


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        search.addTextChangedListener(text);

        Button buttonOpenBottomSheet = findViewById(R.id.filter);
        buttonOpenBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomFilter bottomSheet = new BottomFilter();
                bottomSheet.show(getSupportFragmentManager(), "Example");
            }
        });

    }

    private void fetchSearchComic(String query) {

        List<Comic> comic_search = new ArrayList<>();
        for (Comic comic: Common.comicList){
            if (comic.Name.toLowerCase().contains(query))
                comic_search.add(comic);
        }

        if (query.equals("")){
            comic_search.clear();
        }

        swipeRefreshLayout.setRefreshing(false);

        recycler_comic.setAdapter(new MyComicListAdapter(this.getBaseContext(),comic_search,this));
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

    @Override
    public void onButtonClicked(String status, String category) {
            fetchfilterComic(status,category);


    }

    private void fetchfilterComic(String query1,String query) {
        String[] tag = query.split(",");
        List<Comic> comic_search = new ArrayList<>();
        i=0;
        for (Comic comic : Common.comicList) {
            for (String text : tag) {
                if (comic.Category.toLowerCase().contains(text) & comic.Status.toLowerCase().contains(query1.toLowerCase())) {
                    i++;
                    if (tag.length <= i){
                        comic_search.add(comic);
                        i=0;
                    }

                }
                //category false
            }
            i=0;
        }
        recycler_comic.setAdapter(new MyComicListAdapter(this.getBaseContext(),comic_search,this));
    }
}

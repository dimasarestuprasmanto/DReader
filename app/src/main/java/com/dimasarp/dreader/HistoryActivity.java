package com.dimasarp.dreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dimasarp.dreader.Adapter.MyComicListAdapter;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Model.Comic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.dimasarp.dreader.Common.Common.comicSelected;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recycler_comic_history;
    View back,delete;
    String comichistory;

    public static final String SHARED_PREFS1 = "sharedPrefs1";
    public static final String HISTORY = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recycler_comic_history = findViewById(R.id.recyler_history);
        recycler_comic_history.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_comic_history.setLayoutManager(mLayoutManager);

        back = findViewById(R.id.backtochapter);
        delete = findViewById(R.id.delete);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS1, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        comichistory = sharedPreferences.getString(HISTORY,"");
        editor.apply();

        if (comichistory.length() > 0){
            fetchisi(comichistory);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.apply();
                fetchhistory("kosong");
            }
        });


    }

    private void fetchisi(String comichistory) {
        StringBuilder tag = new StringBuilder();
        tag.append(comichistory);
        if (tag.length() > 0)
            tag.setLength(tag.length()-1);

        String values[] = String.valueOf(tag).split(",");

        StringBuilder compleate = new StringBuilder();
        for (String text : values){
            compleate.append(text).append(",");
        }
        compleate.setLength(compleate.length()-1);
        fetchhistory(String.valueOf(compleate));

    }

    private void fetchhistory(String name) {
        String history[] = name.split(",");
        StringBuilder historylimit = new StringBuilder();
        List<Comic> comic_search = new ArrayList<>();
        int i=0;
        for (String tag : history)
        for (Comic comic: Common.comicList){
            if (comic.Name.toLowerCase().contains(tag.toLowerCase()))
                if (i <10){
                    comic_search.add(comic);
                    historylimit.append(tag).append(",");
                    i++;
                }
        }
        if (i > 10){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS1, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().apply();
            editor.putString(HISTORY, String.valueOf(historylimit));
            editor.apply();
        }

        recycler_comic_history.setAdapter(new MyComicListAdapter(this.getBaseContext(),comic_search,this));
    }
}

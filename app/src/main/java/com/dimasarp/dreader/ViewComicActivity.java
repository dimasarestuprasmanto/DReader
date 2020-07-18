package com.dimasarp.dreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.dimasarp.dreader.Adapter.MyRecyclerViewAdapter;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Model.Chapter;

public class ViewComicActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView txt_chapter_name;
    View back,next;
    LinearLayoutManager layoutManager;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_comic);

        recyclerView = findViewById(R.id.recyler_manga);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        toolbar = findViewById(R.id.toolbar);
        txt_chapter_name = findViewById(R.id.txt_chapter_name);
        next = findViewById(R.id.chapter_next);
        back = findViewById(R.id.chapter_back);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        cek();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Common.chapterIndex--;
                    fetchLinks(Common.chapterList.get(Common.chapterIndex));
                    cek();
        }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Common.chapterIndex++;
                    fetchLinks(Common.chapterList.get(Common.chapterIndex));
                    cek();
            }
        });


        fetchLinks(Common.chapterSelected);
    }

    private void fetchLinks(Chapter chapter) {
                    MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(getBaseContext(),chapter.Links);
                    recyclerView.setAdapter(adapter);
                    txt_chapter_name.setText(String.valueOf("Chapter "+(Common.chapterList.get(Common.chapterIndex).Name)));
    }

    public void cek(){
        if(Common.chapterIndex == 0) {
            back.setVisibility(View.INVISIBLE);
            next.setVisibility(View.VISIBLE);
        }
        else if(Common.chapterIndex == Common.chapterList.size()-1) {
            next.setVisibility(View.INVISIBLE);
            back.setVisibility(View.VISIBLE);
        }else {
            next.setVisibility(View.VISIBLE);
            back.setVisibility(View.VISIBLE);
        }
    }
}


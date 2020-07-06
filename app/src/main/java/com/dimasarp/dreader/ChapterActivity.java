package com.dimasarp.dreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.dimasarp.dreader.Adapter.MyChapterAdapter;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Model.Comic;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;

public class ChapterActivity extends AppCompatActivity {
    RecyclerView recycler_chapter;
    ImageView imagecomic,banner;
    TextView total_chapter, judul, category,status,released;
    LinearLayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        released = (TextView)findViewById(R.id.released);
        banner = (ImageView)findViewById(R.id.banner);
        status = (TextView)findViewById(R.id.status);
        category = (TextView)findViewById(R.id.genre);
        imagecomic = (ImageView)findViewById(R.id.image) ;
        judul = (TextView)findViewById(R.id.judul);
        total_chapter = (TextView)findViewById(R.id.total_chapter);
        recycler_chapter = (RecyclerView)findViewById(R.id.recycler_chapter);
        recycler_chapter.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recycler_chapter.setLayoutManager(layoutManager);
        recycler_chapter.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));



        fetchChapter(Common.comicSelected);
    }

    private void fetchChapter(Comic comicSelected) {

        Common.chapterList = comicSelected.Chapters;
        recycler_chapter.setAdapter(new MyChapterAdapter(this,comicSelected.Chapters));
        total_chapter.setText(new StringBuilder("Total Chapter : ").append(comicSelected.Chapters.size())
        .append(""));
        category.setText(new StringBuilder("Genres : ").append(comicSelected.Category));
        released.setText(new StringBuilder("Released : ").append(comicSelected.Released));
        judul.setText(new StringBuilder("").append(comicSelected.Name));
        Picasso.get().load(comicSelected.Image).into(imagecomic);
        Picasso.get().load(comicSelected.Baner).into(banner);
        if (comicSelected.Status == 1){
            status.setText("Status : On-going");
        }else {
            status.setText("Status : Compleate");
        }
    }
}

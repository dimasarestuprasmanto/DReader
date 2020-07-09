package com.dimasarp.dreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.dimasarp.dreader.Adapter.MyChapterAdapter;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Model.Comic;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class ChapterActivity extends AppCompatActivity {
    RecyclerView recycler_chapter;
    ImageView imagecomic,banner;
    TextView total_chapter, judul, category,status,released,expandable_text;
    LinearLayoutManager layoutManager;
    ExpandableTextView showSinopsis;
    View back;
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
        back = (View)findViewById(R.id.chapter_back);
        expandable_text = (TextView)findViewById(R.id.expandable_text);

        if (Build.VERSION.SDK_INT >= 26) {
            expandable_text.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

        recycler_chapter = (RecyclerView)findViewById(R.id.recycler_chapter);
        recycler_chapter.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,4);
        recycler_chapter.setLayoutManager(layoutManager);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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
        showSinopsis = (ExpandableTextView) findViewById(R.id.expand_text_view);
        showSinopsis.setText(new StringBuilder("").append(comicSelected.Sinopsis));
    }
}

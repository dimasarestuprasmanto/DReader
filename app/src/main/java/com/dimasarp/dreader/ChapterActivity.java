package com.dimasarp.dreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import com.dimasarp.dreader.Adapter.CategoryAdapter;
import com.dimasarp.dreader.Adapter.MyChapterAdapter;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Model.Comic;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.File;
import java.util.List;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;
import static com.dimasarp.dreader.Common.Common.comicSelected;

public class ChapterActivity extends AppCompatActivity {
    RecyclerView recycler_chapter,recycler_category;
    ImageView imagecomic,banner;
    TextView total_chapter, judul,status,released,expandable_text;
    LinearLayoutManager layoutManager,layoutManager1;
    ExpandableTextView showSinopsis;
    net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout collapsing_container;
    CategoryAdapter myadapter;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "";
    public static final String SHARED_PREFS1 = "sharedPrefs1";
    public static final String HISTORY = "";
    String comicbookmark,comichistory;
    ToggleButton test;
    StringBuilder test1,test11;

    View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        test = findViewById(R.id.togglebookmark);
        released = (TextView)findViewById(R.id.released);
        banner = (ImageView)findViewById(R.id.banner);
        status = (TextView)findViewById(R.id.status);
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
        recycler_category = (RecyclerView)findViewById(R.id.recycler_category);
        recycler_category.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,4);
        recycler_chapter.setLayoutManager(layoutManager);

        layoutManager1 = new GridLayoutManager(this,4);
        recycler_category.setLayoutManager(layoutManager1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Common.chapterList = comicSelected.Chapters;
        recycler_chapter.setAdapter(new MyChapterAdapter(this,comicSelected.Chapters));
        total_chapter.setText(new StringBuilder("Total Chapter : ").append(comicSelected.Chapters.size())
                .append(""));
        released.setText(new StringBuilder("Released : ").append(comicSelected.Released));
        judul.setText(new StringBuilder("").append(comicSelected.Name));
        Picasso.get().load(comicSelected.Image).into(imagecomic);
        Picasso.get().load(comicSelected.Baner).into(banner);
        status.setText(new StringBuilder("Status : ").append(comicSelected.Status));

        String[] categorylist = comicSelected.Category.split(" ");
        myadapter = new CategoryAdapter(categorylist,this);
        recycler_category.setAdapter(myadapter);

        showSinopsis = (ExpandableTextView) findViewById(R.id.expand_text_view);
        showSinopsis.setText(new StringBuilder("").append(comicSelected.Sinopsis));

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        comicbookmark = sharedPreferences.getString(TEXT, "");
        editor.apply();

        SharedPreferences sharedPreferences1 = getSharedPreferences(SHARED_PREFS1, MODE_PRIVATE);
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        comichistory = sharedPreferences1.getString(HISTORY,"");
        StringBuilder history = new StringBuilder();
        history.append(comicSelected.Name).append(",").append(comichistory);
        editor1.putString(HISTORY, String.valueOf(history));
        editor1.apply();


        if (comicbookmark.contains(comicSelected.Name))
                test.setChecked(true);


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test.isChecked()){
                    StringBuilder bookmark = new StringBuilder();
                    bookmark.append(comicbookmark);
                    bookmark.append(comicSelected.Name).append(",");
                    editor.putString(TEXT, String.valueOf(bookmark));
                    editor.apply();
                }else {
                    StringBuilder bookmarkbaru = new StringBuilder();
                    StringBuilder bookmarkbaru1 = new StringBuilder();
                    bookmarkbaru.append(comicbookmark);
                    if(bookmarkbaru.length() > 0)
                    bookmarkbaru.setLength(bookmarkbaru.length()-1);
                    String tag[] = String.valueOf(bookmarkbaru).split(",");
                    for (String text : tag){
                        if (comicSelected.Name.contains(text)){
                        }else {
                            bookmarkbaru1.append(text).append(",");
                        }
                    }
                    editor.clear();
                    editor.putString(TEXT, String.valueOf(bookmarkbaru1));
                    editor.apply();
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nothing, R.anim.bottom_down);
    }

}

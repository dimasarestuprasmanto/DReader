package com.dimasarp.dreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dimasarp.dreader.Adapter.MyComicListAdapter;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Model.Comic;

import java.util.ArrayList;
import java.util.List;

public class ListMangaCategory extends AppCompatActivity {
    RecyclerView recycler_comic_category;
    TextView txt_category;
    View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_manga_category);

        recycler_comic_category = findViewById(R.id.recyler_manga_category);
        recycler_comic_category.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recycler_comic_category.setLayoutManager(mLayoutManager);

        txt_category = findViewById(R.id.txt_category_name);
        back = findViewById(R.id.backtochapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        fetchCategory(Common.categorySelected);
    }

    private void fetchCategory(String categorySelected) {
        List<Comic> comic_search = new ArrayList<>();
        for (Comic comic: Common.comicList){
            if (comic.Category.toLowerCase().contains(categorySelected.toLowerCase()))
                comic_search.add(comic);
        }

        txt_category.setText(categorySelected.toUpperCase().replace("_"," "));
        recycler_comic_category.setAdapter(new MyComicListAdapter(this.getBaseContext(),comic_search,this));
    }
}

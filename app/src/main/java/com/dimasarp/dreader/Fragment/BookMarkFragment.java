package com.dimasarp.dreader.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.dimasarp.dreader.Adapter.MyComicListAdapter;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Model.Comic;
import com.dimasarp.dreader.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class BookMarkFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recycler_comic;
    TextView txt_comic;
    String comicbookmark;

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "";


    public BookMarkFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookmark, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh_bookmark);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));

        recycler_comic = view.findViewById(R.id.recycler_bookmark_list);
        recycler_comic.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycler_comic.setLayoutManager(mLayoutManager);

        txt_comic = (TextView) view.findViewById(R.id.txt_bookmark);

        final SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        comicbookmark = sharedPreferences.getString(TEXT, "");




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (comicbookmark.length() > 1)
                fetchSearchComic(comicbookmark);
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (comicbookmark.length() > 1)
                fetchSearchComic(comicbookmark);
            }
        });

        if (comicbookmark.length() > 1)
        fetchSearchComic(comicbookmark);

        return view;

    }


    private void fetchSearchComic(String query) {
        StringBuilder bookmarkbaru = new StringBuilder();
        bookmarkbaru.append(query);
        bookmarkbaru.setLength(bookmarkbaru.length()-1);

        String tag[] = String.valueOf(bookmarkbaru).split(",");
        List<Comic> comic_search = new ArrayList<>();
        for (String text : tag) {
            for (Comic comic : Common.comicList) {
                if (comic.Name.toLowerCase().contains(text.toLowerCase()))
                    comic_search.add(comic);
            }
        }


        swipeRefreshLayout.setRefreshing(false);


        recycler_comic.setAdapter(new MyComicListAdapter(getActivity().getBaseContext(),comic_search,getActivity()));
        txt_comic.setText(new StringBuilder("BOOKMARK (")
                .append(comic_search.size())
                .append(")"));
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
    }
}

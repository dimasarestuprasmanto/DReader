package com.dimasarp.dreader.Fragment;

import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import com.dimasarp.dreader.Adapter.MyComicListAdapter;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Model.Comic;
import com.dimasarp.dreader.R;



import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class MangaFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recycler_comic;
    TextView txt_comic;




    public MangaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manga, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh_manga);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));

        recycler_comic = view.findViewById(R.id.recycler_comic_list);
        recycler_comic.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycler_comic.setLayoutManager(mLayoutManager);

        txt_comic = (TextView) view.findViewById(R.id.txt_comic);




        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchSearchComic("");
            }
        });


            fetchSearchComic("");





        return view;

    }


    private void fetchSearchComic(String query) {

        List<Comic> comic_search = new ArrayList<>();

        for (Comic comic:Common.comicList){
            if (comic.Name.toLowerCase().contains(query))
                comic_search.add(comic);
        }


        swipeRefreshLayout.setRefreshing(false);


        recycler_comic.setAdapter(new MyComicListAdapter(getActivity().getBaseContext(),comic_search,getActivity()));
        txt_comic.setText(new StringBuilder("MANGA (")
                .append(comic_search.size())
                .append(")"));
    }

}

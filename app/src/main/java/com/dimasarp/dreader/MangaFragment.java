package com.dimasarp.dreader;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dimasarp.dreader.Adapter.MyComicAdapter;
import com.dimasarp.dreader.Adapter.MyComicListAdapter;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Interface.IBannerLoadDone;
import com.dimasarp.dreader.Interface.IComicLoadDone;
import com.dimasarp.dreader.Model.Comic;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roger.catloadinglibrary.CatLoadingView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class MangaFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recycler_comic;
    TextView txt_comic,logo_name;
    EditText search;
    ImageView btn_search,logo,hide;
    TextWatcher text = null;
    Integer i;

    CatLoadingView mView;


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

    private void fetchfilterComic(String query1,String query) {
        String[] tag = query1.split(" ");
        List<Comic> comic_search = new ArrayList<>();
        i=0;
        for (Comic comic : Common.comicList) {
                for (String text : tag) {
                        if (comic.Category.toLowerCase().contains(text) & comic.Status.contains(query)) {
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
        txt_comic.setText(new StringBuilder("MANGA (")
                .append(i)
                .append(")"));
        recycler_comic.setAdapter(new MyComicListAdapter(getActivity().getBaseContext(),comic_search,getActivity()));
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

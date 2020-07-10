package com.dimasarp.dreader;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

import static com.dimasarp.dreader.Common.Common.comicList;


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

        recycler_comic = (RecyclerView) view.findViewById(R.id.recycler_comic_list);
        recycler_comic.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recycler_comic.setLayoutManager(mLayoutManager);

        txt_comic = (TextView) view.findViewById(R.id.txt_comic);
        search = (EditText) view.findViewById(R.id.search);
        search.setVisibility(View.INVISIBLE);

        btn_search = (ImageView) view.findViewById(R.id.btn_search);
        logo = (ImageView) view.findViewById(R.id.icon);
        logo_name = (TextView) view.findViewById(R.id.logo_name);

        hide = (ImageView) view.findViewById(R.id.hide);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchSearchComic("");
                logo_name.setVisibility(View.VISIBLE);
                logo.setVisibility(View.VISIBLE);
                search.setVisibility(View.INVISIBLE);
                hide.setVisibility(View.INVISIBLE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                search.setText("");
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setVisibility(View.VISIBLE);
                hide.setVisibility(View.VISIBLE);
                logo.setVisibility(View.INVISIBLE);
                logo_name.setVisibility(View.INVISIBLE);
                search.requestFocus();
                imm.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                fetchSearchComic(search.getText().toString().toLowerCase());
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                return false;
            }
        });

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

        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logo_name.setVisibility(View.VISIBLE);
                logo.setVisibility(View.VISIBLE);
                search.setVisibility(View.INVISIBLE);
                hide.setVisibility(View.INVISIBLE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                search.setText("");
                fetchSearchComic(search.getText().toString().toLowerCase());

            }
        });

        fetchSearchComic("");
        return view;

    }

    private void fetchSearchComic(String query) {

        List<Comic> comic_search = new ArrayList<>();
        for (Comic comic: comicList){
            if (comic.Name.toLowerCase().contains(query)){
                comic_search.add(comic);
            }else {

            }
        }
        swipeRefreshLayout.setRefreshing(false);

        recycler_comic.setAdapter(new MyComicListAdapter(getActivity().getBaseContext(),comic_search));
        txt_comic.setText(new StringBuilder("MANGA (")
                .append(comic_search.size())
                .append(")"));
    }

}

package com.dimasarp.dreader;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dimasarp.dreader.Adapter.MyCategoryAdapter;
import com.dimasarp.dreader.Adapter.MyComicAdapter;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Model.Category;
import com.dimasarp.dreader.Model.Comic;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MangaFragment extends Fragment {
    RecyclerView recycler_comic;
    Chip action;
    FragmentManager fragmentManager;
    TextView coba;
    MyCategoryAdapter adapter;

    public MangaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manga, container, false);


        recycler_comic = (RecyclerView) view.findViewById(R.id.recycler_filter_search);
        recycler_comic.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
        recycler_comic.setLayoutManager(mLayoutManager);


        return view;
    }

    private void fetchFilterCategory(String query) {
        List<Comic> comic_filtered = new ArrayList<>();
        for (Comic comic:Common.comicList){
            if (comic.Category != null){
                if (comic.Category.contains(query))
                    comic_filtered.add(comic);
            }
        }
        recycler_comic.setAdapter(new MyComicAdapter(getActivity().getBaseContext(),comic_filtered));

    }

}

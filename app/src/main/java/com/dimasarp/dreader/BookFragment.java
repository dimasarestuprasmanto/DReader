package com.dimasarp.dreader;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dimasarp.dreader.Adapter.MyComicAdapter;
import com.dimasarp.dreader.Adapter.MySliderAdapter;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Interface.IBannerLoadDone;
import com.dimasarp.dreader.Interface.IComicLoadDone;
import com.dimasarp.dreader.Model.Comic;
import com.dimasarp.dreader.Service.PicassoLoadingService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.roger.catloadinglibrary.CatLoadingView;


import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import ss.com.bannerslider.Slider;

import static com.dimasarp.dreader.Common.Common.comicList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment implements IBannerLoadDone, IComicLoadDone {
    Slider slider;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recycler_comic;
    TextView txt_comic;
    EditText search;
    ImageView btn_search;
    CatLoadingView catLoadingView;

    //database
    DatabaseReference banners,comics;

    //Listener
    IBannerLoadDone bannerListener;
    IComicLoadDone comicListener;

    public BookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book, container, false);


        //init database
        banners = FirebaseDatabase.getInstance().getReference("Banners");
        comics =  FirebaseDatabase.getInstance().getReference("Comic");

        //init banner
        bannerListener = this;
        comicListener = this;


        slider = (Slider) view.findViewById(R.id.slider);
        Slider.init(new PicassoLoadingService());

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBanner();
                loadComic();
            }
        });

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                loadBanner();
                loadComic();
            }
        });
        recycler_comic = (RecyclerView) view.findViewById(R.id.recycler_comic);
        recycler_comic.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recycler_comic.setLayoutManager(mLayoutManager);

        txt_comic = (TextView) view.findViewById(R.id.txt_comic);
        search = (EditText) view.findViewById(R.id.search);
        search.setVisibility(View.INVISIBLE);

        btn_search = (ImageView) view.findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (search.getVisibility() == View.VISIBLE){
                    fetchSearchComic(search.getText().toString().toLowerCase());
                }else {
                    search.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;


    }



    private void loadComic() {
        catLoadingView = new CatLoadingView();
        if (!swipeRefreshLayout.isRefreshing())
            catLoadingView.show(getFragmentManager(), "");

        comics.addListenerForSingleValueEvent(new ValueEventListener() {
            List<Comic> comic_load = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot comicSnapshot:dataSnapshot.getChildren()){
                    Comic comic = comicSnapshot.getValue(Comic.class);
                    comic_load.add(comic);
                }
                comicListener.onComicLoadDoneListener(comic_load);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),""+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadBanner() {
        banners.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<String> bannerList = new ArrayList<>();
                for (DataSnapshot bannerSnapShot:dataSnapshot.getChildren())
                {
                    String image = bannerSnapShot.getValue(String.class);
                    bannerList.add(image);
                }
                //memanggil list
                bannerListener.onBannerLoadDoneListener(bannerList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),""+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBannerLoadDoneListener(List<String> banners) {
        slider.setAdapter(new MySliderAdapter(banners));
    }

    @Override
    public void onComicLoadDoneListener(List<Comic> comicList) {
        Common.comicList = comicList;

        recycler_comic.setAdapter(new MyComicAdapter(getActivity().getBaseContext(),comicList));

        txt_comic.setText(new StringBuilder("NEW COMIC(")
        .append(comicList.size())
        .append(")"));

        if (!swipeRefreshLayout.isRefreshing())
            catLoadingView.dismiss();

    }

    private void fetchSearchComic(String query) {

        List<Comic> comic_search = new ArrayList<>();
        for (Comic comic:Common.comicList){
            if (comic.Name.toLowerCase().contains(query))
                comic_search.add(comic);
        }

        recycler_comic.setAdapter(new MyComicAdapter(getActivity().getBaseContext(),comic_search));
    }

}

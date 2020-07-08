package com.dimasarp.dreader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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

import ss.com.bannerslider.Slider;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements IBannerLoadDone, IComicLoadDone {
    Slider slider;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recycler_comic;
    TextView txt_comic,logo_name;
    EditText search;
    ImageView btn_search,logo,hide;
    TextWatcher text = null;
    boolean koneksi;
    //database
    DatabaseReference banners,comics;

    //Listener
    IBannerLoadDone bannerListener;
    IComicLoadDone comicListener;

    CatLoadingView mView;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //init database
        banners = FirebaseDatabase.getInstance().getReference("Banners");
        comics =  FirebaseDatabase.getInstance().getReference("Comic");

        //init banner
        bannerListener = this;
        comicListener = this;

        koneksi = false;

        slider = (Slider) view.findViewById(R.id.slider);
        Slider.init(new PicassoLoadingService());

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));
        Connected();

        if (koneksi = true) {
            mView = new CatLoadingView();
            mView.setCancelable(false);
            if (!swipeRefreshLayout.isRefreshing());
            mView.show(getFragmentManager(), "");
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
        }else {
        }


        recycler_comic = (RecyclerView) view.findViewById(R.id.recycler_comic);
        recycler_comic.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recycler_comic.setLayoutManager(mLayoutManager);

        txt_comic = (TextView) view.findViewById(R.id.txt_comic);
        search = (EditText) view.findViewById(R.id.search);
        search.setVisibility(View.INVISIBLE);

        btn_search = (ImageView) view.findViewById(R.id.btn_search);
        logo = (ImageView) view.findViewById(R.id.icon);
        logo_name = (TextView) view.findViewById(R.id.logo_name);

        hide = (ImageView) view.findViewById(R.id.hide);

        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

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


        return view;


    }

    public boolean Connected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            koneksi = true;
        }else {
            koneksi = false;
        }
        return true;
    }

    private void loadComic() {

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
                slider.setInterval(3000);
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
            mView.dismiss();
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

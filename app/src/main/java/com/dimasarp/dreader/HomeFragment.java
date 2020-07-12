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
import com.dimasarp.dreader.Model.Category;
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
public class HomeFragment extends Fragment implements IBannerLoadDone,IComicLoadDone {
    Slider slider;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recycler_comic,recycler_comic1,recycler_comic2;
    TextView txt_comic,logo_name;
    EditText search;
    ImageView btn_search,logo,hide;
    TextWatcher text = null;
    boolean koneksi,alreadyExecuted;;
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

        recycler_comic = (RecyclerView) view.findViewById(R.id.recycler_comic);
        recycler_comic1 = (RecyclerView) view.findViewById(R.id.recycler_comic1);
        recycler_comic2 = (RecyclerView) view.findViewById(R.id.recycler_comic2);
        recycler_comic.setHasFixedSize(true);
        recycler_comic1.setHasFixedSize(true);
        recycler_comic2.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(),1, GridLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager1 = new GridLayoutManager(getContext(),1, GridLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(getContext(),1, GridLayoutManager.HORIZONTAL, false);
        recycler_comic.setLayoutManager(mLayoutManager);
        recycler_comic1.setLayoutManager(mLayoutManager1);
        recycler_comic2.setLayoutManager(mLayoutManager2);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadBanner();
                loadComic();
            }
        });

        if (koneksi = true) {
            mView = new CatLoadingView();
            mView.setCancelable(false);
            if (!swipeRefreshLayout.isRefreshing())
            mView.show(getFragmentManager(), "");
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        loadBanner();
                        loadComic();
                    }
                });

        }else {
        }

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
        return false;
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

    private void loadComic() {

        comics.orderByChild("Name").addListenerForSingleValueEvent(new ValueEventListener() {
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

    @Override
    public void onComicLoadDoneListener(List<Comic> comicList) {
        Common.comicList = comicList;

        fetchComic();
        fetchComiccategory();
        if (!swipeRefreshLayout.isRefreshing())
            mView.dismiss();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void fetchComic(){
        List<Comic> comictop = new ArrayList<>();
        List<Comic> comicrek = new ArrayList<>();


        for (Comic comic:Common.comicList){
            if (comic.Badge.contains("T")){
                comictop.add(comic);
            }else if (comic.Badge.contains("R")){
                comicrek.add(comic);
            }
            recycler_comic.setAdapter(new MyComicAdapter(getActivity().getBaseContext(),comictop));
            recycler_comic1.setAdapter(new MyComicAdapter(getActivity().getBaseContext(),comicrek));

        }
    }
    private void fetchComiccategory(){
        List<Comic> comicfav = new ArrayList<>();
        for (Comic comic:Common.comicList){
            if (comic.Name.contains(""))
                comicfav.add(comic);
        }
        recycler_comic2.setAdapter(new MyComicAdapter(getActivity().getBaseContext(),comicfav));
    }

}

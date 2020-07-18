package com.dimasarp.dreader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.dimasarp.dreader.R;

public class SlideAdapter extends PagerAdapter  {

    Context context;
    LayoutInflater layoutInflater;

    public SlideAdapter(Context context){
        this.context = context;
    }



    //array
    public int[] slide_img = {

            R.drawable.wk1,
            R.drawable.wk2,
            R.drawable.wk3
    };

    public String[] slide_head = {
            "Manga",
            "Chat",
            "Search & Filter"
    };

    public String[] slide_desc = {
                "Baca secara online ratusan manga",
                "Chat bareng seluruh anime lovers lainnya untuk berbagi pengalaman membaca",
                "Mudahnya mencari manga dengan nama dan genresnya"
    };
    @Override
    public int getCount() {
        return slide_head.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (LinearLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideImageView =  view.findViewById(R.id.slide_img);
        TextView slideheading =  view.findViewById(R.id.slide_head);
        TextView slideDescription = view.findViewById(R.id.slide_desc);

        slideImageView.setImageResource(slide_img[position]);
        slideheading.setText(slide_head[position]);
        slideDescription.setText(slide_desc[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}

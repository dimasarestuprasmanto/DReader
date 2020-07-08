package com.dimasarp.dreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dimasarp.dreader.Adapter.SlideAdapter;
import com.dimasarp.dreader.SharedPreferences.PrefManager;

public class MainActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private SlideAdapter slideAdapter;

    private LinearLayout mDotLayout;
    private TextView[] mDots;
    private PrefManager prefManager;

    private Button mBtnNext;
    private Button mBtnPrev;

    private int mCurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        mSlideViewPager = (ViewPager) findViewById(R.id.slideViewPager);
        mDotLayout = (LinearLayout) findViewById(R.id.dotslayout);

        mBtnNext = (Button) findViewById(R.id.btnNext);
        mBtnPrev = (Button) findViewById(R.id.btnPrev);

        slideAdapter = new SlideAdapter(this);
        mSlideViewPager.setAdapter(slideAdapter);

        addDotsIndicator(0);

        mSlideViewPager.addOnPageChangeListener(viewListener);

        //fungsi klik pada button
         mBtnNext.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View view){

                 move();
             }
         });

        mBtnPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                mSlideViewPager.setCurrentItem(mCurrentPage - 1);
            }
        });
    }

    private void launchHomeScreen() {
        PrefManager prefManager = new PrefManager(getApplicationContext());
        prefManager.setFirstTimeLaunch(false);
        Intent pindah = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(pindah);
        finish();
    }

    //Membuat dot pada walk through
    public void addDotsIndicator(int position){

        mDots = new TextView[3];
        mDotLayout.removeAllViews();

        for(int i=0; i< mDots.length; i++){
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(50);
            mDots[i].setTextColor(getResources().getColor(R.color.colorTransparantWhite));

            mDotLayout.addView(mDots[i]);
        }
        if(mDots.length > 0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private void move(){
        if(mCurrentPage == mDots.length - 1){
            launchHomeScreen();
        }else{
            mSlideViewPager.setCurrentItem(mCurrentPage + 1);
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
            mCurrentPage = i;

            if(i == 0){
                mBtnNext.setEnabled(true);
                mBtnPrev.setEnabled(false);
                mBtnPrev.setVisibility(View.INVISIBLE);

                mBtnNext.setText("NEXT");
                mBtnPrev.setText("");
            }else if (i == mDots.length - 1){
                mBtnNext.setEnabled(true);
                mBtnPrev.setEnabled(true);
                mBtnPrev.setVisibility(View.VISIBLE);

                mBtnNext.setText("FINISH");
                mBtnPrev.setText("BACK");
            }else{
                mBtnNext.setEnabled(true);
                mBtnPrev.setEnabled(true);
                mBtnPrev.setVisibility(View.VISIBLE);

                mBtnNext.setText("NEXT");
                mBtnPrev.setText("BACK");
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };
}

package com.dimasarp.dreader;

import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {

    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_chat, container, false);

        WebView chat = (WebView) view.findViewById(R.id.chat);
        chat.getSettings().setBuiltInZoomControls(true);
        chat.getSettings().setJavaScriptEnabled(true);
        chat.getSettings().setDomStorageEnabled(true);
        chat.getSettings().setPluginState(WebSettings.PluginState.ON);
        chat.loadUrl("http://dreadermanga.chatango.com/");
        chat.setWebViewClient(new WebViewClient());
        return view;
    }



}

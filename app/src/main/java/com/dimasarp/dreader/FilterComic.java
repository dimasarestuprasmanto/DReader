package com.dimasarp.dreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class FilterComic extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_comic);

        Button coba = findViewById(R.id.coba);
        final ToggleButton action = findViewById(R.id.action);
        final ToggleButton comedy = findViewById(R.id.comedy);

        coba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer result = new StringBuffer();
                if (action.isChecked())
                result.append("Action");
                if (comedy.isChecked())
                result.append("Comedy");
                Toast.makeText(FilterComic.this, result.toString(),Toast.LENGTH_SHORT).show();
            }
        });



        }
}

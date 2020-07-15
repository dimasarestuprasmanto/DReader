package com.dimasarp.dreader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class FilterComic extends AppCompatActivity {
    View batal;
    Button coba;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_comic);

       batal = findViewById(R.id.batal);
       coba = findViewById(R.id.coba);
        final RadioButton all = findViewById(R.id.all);
        final RadioButton ongoing = findViewById(R.id.ongoing);
        final RadioButton complete = findViewById(R.id.complete);

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        coba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer result = new StringBuffer();
                if (all.isChecked())
                result.append("ALL");
                if (ongoing.isChecked())
                result.append("Ongoing");
                if (complete.isChecked())
                    result.append("Complete");

                String r = result.toString();

                Toast.makeText(FilterComic.this, r,Toast.LENGTH_SHORT).show();
            }
        });


    }
}

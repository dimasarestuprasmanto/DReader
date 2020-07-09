package com.dimasarp.dreader.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Model.Category;
import com.dimasarp.dreader.R;

import java.util.ArrayList;
import java.util.List;

public class MyCategoryAdapter extends RecyclerView.Adapter<MyCategoryAdapter.MyViewHolder> {
    Context context;
    List<Category> categories;
    LayoutInflater inflater;

    public MyCategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.layout_category,parent, false);
        return new MyCategoryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_category.setText(categories.toString());
    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_category;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_category = (TextView) itemView.findViewById(R.id.txt_category);
        }
    }
}

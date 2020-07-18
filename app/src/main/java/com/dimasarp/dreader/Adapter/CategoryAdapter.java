package com.dimasarp.dreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Interface.IRecyclerItemClickListener;
import com.dimasarp.dreader.ListMangaCategory;
import com.dimasarp.dreader.R;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private String categorylist[];
    private Context context;
    LayoutInflater inflater;


    public CategoryAdapter(String[] categorylist, Context context) {
        this.categorylist = categorylist;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.category_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.MyViewHolder holder, int position) {
        holder.category.setText(categorylist[position].replace("_", " ").toUpperCase());

        //event
        holder.setRecyclerItemClickListener(new IRecyclerItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Common.categorySelected = categorylist[position];
                view.getContext().startActivity(new Intent(context, ListMangaCategory.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return categorylist.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView category;


        IRecyclerItemClickListener recyclerItemClickListener;

        public void setRecyclerItemClickListener(IRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            category = itemView.findViewById(R.id.listcategory);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerItemClickListener.onClick(v,getAdapterPosition());
        }
    }
}

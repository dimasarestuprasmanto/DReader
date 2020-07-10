package com.dimasarp.dreader.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dimasarp.dreader.ChapterActivity;
import com.dimasarp.dreader.Common.Common;
import com.dimasarp.dreader.Interface.IRecyclerItemClickListener;
import com.dimasarp.dreader.Model.Comic;
import com.dimasarp.dreader.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyComicListAdapter extends RecyclerView.Adapter<MyComicListAdapter.MyViewHolder> {
    Context context;
    List<Comic> comicList;
    LayoutInflater inflater;

    public MyComicListAdapter(Context context, List<Comic> comicList) {
        this.context = context;
        this.comicList = comicList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_comic_list,parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Picasso.get().load(comicList.get(position).Image).into(holder.comic_image);
        holder.comic_name.setText(Common.formatString(comicList.get(position).Name));
        holder.genre.setText(new StringBuilder("Genres : ").append(comicList.get(position).Category)
                .append(""));
        holder.released.setText(new StringBuilder("Released : ").append(comicList.get(position).Released)
                .append(""));
        holder.totchap.setText(new StringBuilder("Total Chapter : ").append(comicList.get(position).Chapters.size())
                .append(""));
        if (comicList.get(position).Category == "1"){
            holder.status.setText("Status : On-going");
        }else {
            holder.status.setText("Status : Compleate");
        }

        //event
        holder.setRecyclerItemClickListener(new IRecyclerItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Common.comicSelected = comicList.get(position);
                view.getContext().startActivity(new Intent(context, ChapterActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {

        return comicList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView comic_name,genre,status,released,totchap;
        ImageView comic_image;

        IRecyclerItemClickListener recyclerItemClickListener;

        public void setRecyclerItemClickListener(IRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            comic_image = (ImageView)itemView.findViewById(R.id.image_comic);
            comic_name = (TextView)itemView.findViewById(R.id.comic_name);
            genre = (TextView)itemView.findViewById(R.id.genre);
            status = (TextView)itemView.findViewById(R.id.status);
            released = (TextView)itemView.findViewById(R.id.released);
            totchap = (TextView)itemView.findViewById(R.id.tot_chap);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerItemClickListener.onClick(v,getAdapterPosition());
        }
    }
}

package com.example.gerges.newsapp;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.gerges.newsapp.R.id.recView;

/**
 * Created by gerge on 24/02/2017.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>   {

   private SwipeRefreshLayout mySwiper;

    ArrayList<FeedItem> feedItems;
    Context context;



    public Adapter(Context context , ArrayList<FeedItem>feedItems){
        this.feedItems=feedItems;
        this.context= context;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);

             /// if return null cause null pointer exception
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        YoYo.with(Techniques.FadeInUp).playOn(holder.cardView);
        final FeedItem current = feedItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Date.setText(current.getDate());
        holder.Desc.setText(current.getDescription());
          //adding  image from internet using picasso library
        Picasso.with(context).load(current.getThumbUrl()).into(holder.Thumb);

        holder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetails.class);
                intent.putExtra("Link", current.getLink());
                context.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {

        return feedItems.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView Title, Desc, Date;
        ImageView Thumb;

        public MyViewHolder(View itemView) {
            super(itemView);

            Title = (TextView) itemView.findViewById(R.id.title);
            Desc  = (TextView) itemView.findViewById(R.id.desc);
            Date  = (TextView) itemView.findViewById(R.id.date);
            Thumb =  (ImageView) itemView.findViewById(R.id.thumb);
            cardView = (CardView) itemView.findViewById(R.id.cardview);

        }
    }
}

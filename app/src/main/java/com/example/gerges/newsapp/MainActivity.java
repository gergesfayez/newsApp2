package com.example.gerges.newsapp;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout mySwiper;
    RecyclerView recView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mySwiper = (SwipeRefreshLayout) findViewById(R.id.swiperRefresh);

        // link recycler view
        recView = (RecyclerView) findViewById(R.id.recView);
        RssReader  reader = new RssReader(this,recView);
        mySwiper.setOnRefreshListener(this);
        reader.execute();

        if(mySwiper.isRefreshing()){
            mySwiper.setRefreshing(true);
        }
    }

    @Override
    public void onRefresh() {
        RssReader  reader2 = new RssReader(this,recView);
        reader2.execute();
    }
}

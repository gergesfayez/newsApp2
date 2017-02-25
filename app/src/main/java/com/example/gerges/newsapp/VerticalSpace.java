package com.example.gerges.newsapp;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by gerge on 25/02/2017.
 */

public class VerticalSpace extends RecyclerView.ItemDecoration {

    int space ;

    public VerticalSpace (int spaceValue){
        this.space = spaceValue;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
       outRect.left = space;
        outRect.right= space;
        outRect.bottom=space;
        if(parent.getChildLayoutPosition(view)== 0){
            outRect.top=space;
        }
    }
}

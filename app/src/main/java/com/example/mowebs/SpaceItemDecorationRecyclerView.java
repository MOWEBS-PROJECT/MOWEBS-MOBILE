package com.example.mowebs;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpaceItemDecorationRecyclerView extends RecyclerView.ItemDecoration {

    private int horizontalSpaceHeight;
    private int verticalSpaceHeight;

    public SpaceItemDecorationRecyclerView( int horizontal,int vertical) {
        this.horizontalSpaceHeight  = horizontal;
        this.verticalSpaceHeight    = vertical;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        outRect.right   = horizontalSpaceHeight / 2;
        outRect.left    = horizontalSpaceHeight / 2;

        if (parent.getChildAdapterPosition(view) == 0 || parent.getChildAdapterPosition(view) == 1) {
            outRect.top = verticalSpaceHeight / 2;
        }

        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom  = verticalSpaceHeight;
        }


    }
}

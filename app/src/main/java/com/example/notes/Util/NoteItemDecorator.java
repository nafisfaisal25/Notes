package com.example.notes.Util;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteItemDecorator extends RecyclerView.ItemDecoration {
    private final int mVerticalSpacing;

    public NoteItemDecorator(int mVerticalSpacing) {
        this.mVerticalSpacing = mVerticalSpacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.bottom = mVerticalSpacing;
    }
}

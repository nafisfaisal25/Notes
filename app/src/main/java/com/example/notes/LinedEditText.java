package com.example.notes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatEditText;

public class LinedEditText extends AppCompatEditText {
    private static final String TAG = "LinedEditText";
    Paint mPaint;
    Rect mRect;
    public LinedEditText(Context context) {
        super(context);
    }

    public LinedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        mPaint.setColor(0xFFFFD966);
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "onDraw: ");
        int lineNumber = getHeight() / getLineHeight();

        int baseLine = getLineBounds(0, mRect);
        for(int i = 0 ; i < lineNumber; i++) {
            canvas.drawLine(mRect.left, baseLine, mRect.right, baseLine, mPaint);
            baseLine += getLineHeight();
        }
    }
}

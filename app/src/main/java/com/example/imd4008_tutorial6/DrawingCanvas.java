package com.example.imd4008_tutorial6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;

public class DrawingCanvas extends View {
    private Paint mPaint;
    private Path mPath;
    public LinkedList<Paint> paintContainer;
    public LinkedList<Path> pathsContainer;
    public int pathColour = Color.BLUE;

    public DrawingCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintContainer = new LinkedList<Paint>();
        pathsContainer = new LinkedList<Path>();

        mPaint = new Paint();
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
        if(!paintContainer.isEmpty()) {
            for(int i = 0; i < paintContainer.size(); i++) {
                canvas.drawPath(pathsContainer.get(i), paintContainer.get(i));
            }
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchCount = event.getPointerCount();

        if(touchCount == 1) {
            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPaint.setColor(pathColour);
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeJoin(Paint.Join.ROUND);
                    mPaint.setStrokeCap(Paint.Cap.ROUND);
                    mPaint.setStrokeWidth(10);

                    pathsContainer.addLast(mPath);
                    paintContainer.addLast(mPaint);

                    pathsContainer.getLast().moveTo(event.getX(), event.getY());
                    break;

                case MotionEvent.ACTION_MOVE:
                    pathsContainer.getLast().lineTo(event.getX(), event.getY());
                    invalidate();
                    break;

                case MotionEvent.ACTION_UP:
                    if (pathColour == Color.BLUE) pathColour = Color.RED;
                    else pathColour = Color.BLUE;
                    mPaint = new Paint();
                    mPath = new Path();
                    //mPaint.setColor(Color.BLUE);
                    resetPaint();
                    break;
            }
        } else if (touchCount == 2) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mPaint.setColor(pathColour);
                    mPaint.setStyle(Paint.Style.STROKE);
                    mPaint.setStrokeJoin(Paint.Join.ROUND);
                    mPaint.setStrokeCap(Paint.Cap.ROUND);
                    mPaint.setStrokeWidth(10);

                    pathsContainer.getLast().moveTo(event.getX(0), event.getY(0));
                    pathsContainer.getLast().moveTo(event.getX(1), event.getY(1));
                    break;
                case MotionEvent.ACTION_MOVE:
                    pathsContainer.getLast().reset();
                    pathsContainer.getLast().moveTo(event.getX(0), event.getY(0));
                    pathsContainer.getLast().moveTo(event.getX(1), event.getY(1));
                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    if (pathColour == Color.BLUE) pathColour = Color.RED;
                    else pathColour = Color.BLUE;
                    mPaint = new Paint();
                    mPath = new Path();
                    //mPaint.setColor(Color.BLUE);
                    resetPaint();
                    break;
            }
        }
        return true;
    }

    private void resetPaint() {
        mPaint.setColor(Color.BLUE);
    }

    public void setPathColour(int newColour) {
        pathColour = newColour;
    }
}

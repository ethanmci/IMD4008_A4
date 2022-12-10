package com.example.imd4008_A4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Debug;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class DrawingCanvas extends View {
    private Paint mPaint;
    private Path mPath;

    private Paint[] mPaintArray;
    private Path[] mPathArray;

    public LinkedList<Paint> paintContainer;
    public LinkedList<Path> pathsContainer;
    public int pathColour = Color.BLUE;

    public final int DRAWING = 0;
    public final int POLYLINE = 1;
    public final int RECT = 2;
    public final int CIRCLE = 3;

    public boolean isErasing;

    public int CURRENT_MODE = DRAWING;

    public DrawingCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintContainer = new LinkedList<Paint>();
        pathsContainer = new LinkedList<Path>();

        mPaint = new Paint();
        mPath = new Path();

        mPaintArray = new Paint[10];
        mPathArray = new Path[10];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawPath(mPath, mPaint);
        if(!paintContainer.isEmpty()) {
            for(int i = 0; i < paintContainer.size(); i++) {
                canvas.drawPath(pathsContainer.get(i), paintContainer.get(i));
            }
        }

        for(int i = 0; i < mPathArray.length; i++) {
            if (mPathArray[i] != new Path() && mPathArray[i] != null
                    && mPaintArray[i] != new Paint() && mPaintArray[i] != null) {
                canvas.drawPath(mPathArray[i], mPaintArray[i]);
            }
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchCount = event.getPointerCount();
        int index = event.getPointerId(event.getActionIndex());

        if (isErasing && index == 0) {
            if(event.getActionMasked() == MotionEvent.ACTION_DOWN
                    || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                Log.d("TOUCH", Arrays.toString(mPaintArray));
                mPathArray[index] = new Path();
                mPaintArray[index] = new Paint();
                mPaintArray[index].setColor(Color.WHITE);
                mPaintArray[index].setStyle(Paint.Style.STROKE);
                mPaintArray[index].setStrokeJoin(Paint.Join.ROUND);
                mPaintArray[index].setStrokeCap(Paint.Cap.ROUND);
                mPaintArray[index].setStrokeWidth(20);
                mPathArray[index].moveTo(event.getX(), event.getY());
            } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                mPathArray[index].lineTo(event.getX(), event.getY());
                invalidate();
            } else if (event.getActionMasked() == MotionEvent.ACTION_UP
                    || event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
                //Log.d("TOUCH", "FINGER UP");
                pathsContainer.add(mPathArray[index]);
                paintContainer.add(mPaintArray[index]);
                mPathArray[index] = new Path();
                mPaintArray[index] = new Paint();
            }
        } else {
            if(CURRENT_MODE == DRAWING) {
                if(event.getActionMasked() == MotionEvent.ACTION_DOWN
                        || event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                    Log.d("TOUCH", Arrays.toString(mPaintArray));
                    mPathArray[index] = new Path();
                    mPaintArray[index] = new Paint();
                    mPaintArray[index].setColor(pathColour);
                    mPaintArray[index].setStyle(Paint.Style.STROKE);
                    mPaintArray[index].setStrokeJoin(Paint.Join.ROUND);
                    mPaintArray[index].setStrokeCap(Paint.Cap.ROUND);
                    mPaintArray[index].setStrokeWidth(10);
                    mPathArray[index].moveTo(event.getX(), event.getY());
                } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                    mPathArray[index].lineTo(event.getX(), event.getY());
                    invalidate();
                } else if (event.getActionMasked() == MotionEvent.ACTION_UP
                        || event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
                    //Log.d("TOUCH", "FINGER UP");
                    pathsContainer.add(mPathArray[index]);
                    paintContainer.add(mPaintArray[index]);
                    mPathArray[index] = new Path();
                    mPaintArray[index] = new Paint();
                }
            }
        }
        //TODO: other modes



        // TODO: Reimplement?
        /*
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
                    mPaint = new Paint();
                    mPath = new Path();
                    //mPaint.setColor(Color.BLUE);
                    resetPaint();
                    break;
            }
        }

        else if (touchCount == 2) {
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
         */
        return true;
    }

    private void resetPaint() {
        mPaint.setColor(Color.BLUE);
    }

    public void setPathColour(int newColour) {
        pathColour = newColour;
    }

    public void undo() {
        if(paintContainer.size() != 0 && pathsContainer.size() != 0) {
            paintContainer.removeLast();
            pathsContainer.removeLast();
        }
        invalidate();
    }

    public void clearCanvas() {
        paintContainer.clear();
        pathsContainer.clear();
        invalidate();
    }

    public void setMode(int newMode) {
        Log.d("NEW MODE", String.valueOf(newMode));
        CURRENT_MODE = newMode;
    }

    public void toggleErase(boolean erase) {
        isErasing = erase;
    }
}

package com.example.aircraft_play_demo.MyComponent;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.render.*;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.multimodalinput.event.MmiPoint;
import ohos.multimodalinput.event.TouchEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class DrawBoard extends ComponentContainer implements Component.DrawTask, Component.TouchEventListener {


    public class paintData {

        private Paint paint;
        private Path path;


        paintData(Paint paint, Path path) {
            this.paint = paint;
            this.path = path;
        }

        public void draw(Canvas canvas) {
            canvas.drawPath(mPath, mPaint);
        }
    }

    //存储已经画的列表
    public ArrayList<paintData> PaintedList = new ArrayList<>();
    //存储撤销的列表
    public ArrayList<paintData> MovedList = new ArrayList<>();

    //最多记录20画笔迹
    private int MAX_PAINT_RECORED = 20;

    private static final String TAG = "DrawComponment";
    Path mPath = new Path();
    Paint mPaint;


    //撤销
    public void revoked() {
        if (PaintedList.size() > 0) {
            paintData paint = PaintedList.get(PaintedList.size() - 1);
            PaintedList.remove(PaintedList.size() - 1);
            MovedList.add(paint);
            invalidate();
        }
    }

    //清除
    public void clear() {
        PaintedList.clear();
        MovedList.clear();
        invalidate();
    }


    public DrawBoard(Context context) {
        this(context,null);
    }

    public DrawBoard(Context context, AttrSet attrSet) {
        super(context,attrSet);
        ShapeElement element_back = new ShapeElement ();
        element_back.setRgbColor(new RgbColor(255,255,255));
        element_back.setStroke(5,new RgbColor(0,0,0));
        super.setBackground(element_back);

        //初始化paint
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2f);
        mPaint.setStyle(Paint.Style.STROKE_STYLE);
//添加绘制任务
        addDrawTask(this::onDraw);
//设置TouchEvent监听
        setTouchEventListener(this::onTouchEvent);

    }

    @Override
    public void onDraw(Component component, Canvas canvas) {
        if (PaintedList.size() > 0)
            for (paintData apaint : PaintedList) {
                canvas.drawPath(apaint.path, apaint.paint);
            }
        if (mPath != null)
            canvas.drawPath(mPath, mPaint);
    }

    @Override
    public boolean onTouchEvent(Component component, TouchEvent touchEvent) {
        MmiPoint point = touchEvent.getPointerPosition(touchEvent.getIndex());
        System.out.println(point.getX()+"  "+point.getY());
        try {
            switch (touchEvent.getAction()) {
                case TouchEvent.PRIMARY_POINT_DOWN: {
                    mPath = new Path();
//                    MmiPoint point = touchEvent.getPointerPosition(touchEvent.getIndex());
                    mPath.moveTo(point.getX(), point.getY());

                    return true;
                }
                case TouchEvent.POINT_MOVE: {
//                    MmiPoint point = touchEvent.getPointerPosition(touchEvent.getIndex());
                    mPath.lineTo(point.getX(), point.getY());
                    invalidate();
                    break;
                }
                // 每次抬起画笔就记录之前的动作
                case TouchEvent.PRIMARY_POINT_UP:
                    PaintedList.add(new paintData(mPaint, mPath));
                    mPath = null;
                    break;
            }

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
        return true;
    }
}

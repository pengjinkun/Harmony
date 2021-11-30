package com.example.aircraft_play_demo.MyComponent;

import ohos.agp.colors.RgbColor;
import ohos.agp.components.*;
import ohos.agp.components.element.Element;
import ohos.agp.components.element.ShapeElement;
import ohos.agp.render.ColorMatrix;
import ohos.agp.utils.Color;
import ohos.app.Context;
import ohos.rpc.*;

import static ohos.agp.components.ComponentContainer.LayoutConfig.MATCH_CONTENT;

public class DrawContainer extends ComponentContainer {
    public Button mBtnClear;
    public Button mBtnRevoke;
    public Button mBtnChange;
    public DrawBoard board;
    ShapeElement element_btn = new ShapeElement();
    Text mTxtSubject;
    String subjects[] = {"鱼", "飞机", "苹果", "风车", "鼠标", "钥匙"};
    int i = 0;

    public DrawContainer(Context context, AttrSet attrSet) {
        super(context, attrSet);
        board = new DrawBoard(context, attrSet);

        DirectionalLayout verLayout = new DirectionalLayout(context);
        verLayout.setOrientation(Component.VERTICAL);


        // 声明布局
        DirectionalLayout horLayout = new DirectionalLayout(context);
        // 设置布局大小
        horLayout.setWidth(900);
        horLayout.setHeight(100);
        ShapeElement element = new ShapeElement();
        element.setRgbColor(new RgbColor(255, 255, 255));
//        element.setStroke(5,new RgbColor(0,0,0));
        horLayout.setBackground(element);
        horLayout.setOrientation(Component.HORIZONTAL);

        //设置题目
        mTxtSubject = new Text(context);
        mTxtSubject.setWidth(900);
        mTxtSubject.setHeight(200);
        mTxtSubject.setTextSize(150);
        mTxtSubject.setMaxTextHeight(25);
        mTxtSubject.setText("题目：" + subjects[i]);

        // 设置按钮
        element.setRgbColor(new RgbColor(86, 157, 204));
        mBtnClear = new Button(context);
        initButton(mBtnClear, "清空");
        mBtnChange = new Button(context);
        initButton(mBtnChange, "更换题目");
        mBtnRevoke = new Button(context);
        initButton(mBtnRevoke, "撤销");

        board.setWidth(900);
        board.setHeight(1300);
//        ShapeElement element_back = new ShapeElement ();
//        element_back.setRgbColor(new RgbColor(255,255,255));
//        element_back.setStroke(5,new RgbColor(0,0,0));
//        board.setBackground(element_back);

        horLayout.addComponent(mBtnChange);
        horLayout.addComponent(mBtnRevoke);
        horLayout.addComponent(mBtnClear);

        mBtnRevoke.setClickedListener(new ClickedListener() {
            @Override
            public void onClick(Component component) {
                board.revoked();
            }
        });

        mBtnClear.setClickedListener(new ClickedListener() {
            @Override
            public void onClick(Component component) {
                board.clear();
            }
        });

        mBtnChange.setClickedListener(new ClickedListener() {
            @Override
            public void onClick(Component component) {
                i++;
                mTxtSubject.setText("题目：" + subjects[i]);
            }
        });

        verLayout.addComponent(mTxtSubject);
        verLayout.addComponent(horLayout);
        verLayout.addComponent(board);
        this.addComponent(verLayout);
    }

    private void initButton(Button btn, String str) {
        btn.setText(str);
        btn.setTextColor(Color.RED);
        btn.setTextSize(50);
        btn.setWidth(MATCH_CONTENT);
        btn.setHeight(MATCH_CONTENT);
        btn.setMarginLeft(50);
        btn.setMarginRight(50);
        btn.setMarginTop(20);
        btn.setBackground(element_btn);
    }

    public DrawContainer(Context context, Button mBtnClear, Button mBtnRevoke, DrawBoard board) {
        super(context);
        this.mBtnClear = mBtnClear;
        this.mBtnRevoke = mBtnRevoke;
        this.board = board;
    }


}

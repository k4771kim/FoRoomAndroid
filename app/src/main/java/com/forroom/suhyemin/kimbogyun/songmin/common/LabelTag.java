package com.forroom.suhyemin.kimbogyun.songmin.common;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.forroom.suhyemin.kimbogyun.songmin.ForRoomApplication;
import com.forroom.suhyemin.kimbogyun.songmin.R;

/**
 * ssongmin by ccei on 2016-02-11.
 */

public class LabelTag extends ImageButton implements View.OnTouchListener {
    private final int START_DRAG = 0;
    private final int END_DRAG = 1;
    private int isMoving;
    float dataX, dataY;
    int color = 11;
    String text;
    static tagText tagDetailDialog;
    //    = "텍스트 더미";
    public static boolean movable;
    public static int layoutNumber;
    public int index;
    public Context mConext;

    public LabelTag(Context context, AttributeSet attrs) {
        super(context, attrs);
        mConext = context;
        this.setBackgroundResource(R.drawable.btn_t_brown_11_2);
        this.setOnTouchListener(this);
    }

    public LabelTag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mConext = context;
        this.setBackgroundResource(R.drawable.btn_t_brown_11_2);
        this.setOnTouchListener(this);
    }

    public LabelTag(Context context) {

        super(context);
        mConext = context;
        this.setBackgroundResource(R.drawable.btn_t_brown_11_2);
        this.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        if (movable) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                isMoving = START_DRAG;
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                isMoving = END_DRAG;

            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                if (isMoving == START_DRAG) {
                    if (LabelImageView.Start < event.getRawY() && (LabelImageView.END > event.getRawY() && LabelImageView.left < event.getRawX() && LabelImageView.right > event.getRawX())) {

                        float x, y;
                        x = event.getRawX() - (v.getWidth() / 2) - LabelImageView.left;
                        y = event.getRawY() - (v.getHeight() / 2) - LabelImageView.Start;
                        v.setX((int) x);
                        v.setY((int) y);

                        dataX = x / (float) (LabelImageView.right - LabelImageView.left);
                        dataY = y / (float) (LabelImageView.END - LabelImageView.Start);

                        LabelImageView.tagArr.get(index).dataX = dataX;
                        LabelImageView.tagArr.get(index).dataY = dataY;

                    }
                }
            }

        } else {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                tagDetailDialog = new tagText(ForRoomApplication.getTagContext());
                tagDetailDialog.setText(getText());

            }

        }
        return false;
    }

    RelativeLayout LL;

    public void MoveXY(float X, float Y, float width, float height) {
        float x = X;
        float y = Y;

        this.setX(x);
        this.setY(y);

        dataX = x / (float) (LabelImageView.right - LabelImageView.left);
        dataY = y / (float) (LabelImageView.END - LabelImageView.Start);

    }


    public void setLayoutNumber(int n) {
        layoutNumber = n;
    }

    public float getDataX() {
        return dataX;
    }

    public String getString() {
        return text;
    }

    public int getColor() {
        return color;
    }

    public float getDataY() {
        return dataY;
    }

    public String getText() {
        return text;
    }

    public void setText(String s) {
        text = s;
    }

    public void setMovable(boolean b) {
        movable = b;
    }
//
//    public static void stsetClick(boolean a){
//            setClickable(a);
//    }


    public static class tagText extends Dialog {


        static TextView tagText;
        ImageButton tagExitBtn;

        public tagText(Context context) {
            super(context);
            if(isShowing()){
                return;
            }else {


                setContentView(R.layout.dialog_tag_read);
                tagText = (TextView) findViewById(R.id.tag_detail_text);
                tagExitBtn = (ImageButton) findViewById(R.id.tag_detial_exit);
                show();
                tagExitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        setClick(true);
                        dismiss();
                    }
                });
            }
        }

        public static void setText(String text) {
            tagText.setText(text);
        }

    }
}


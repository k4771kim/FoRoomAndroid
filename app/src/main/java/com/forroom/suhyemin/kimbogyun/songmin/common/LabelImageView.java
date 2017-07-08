package com.forroom.suhyemin.kimbogyun.songmin.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.forroom.suhyemin.kimbogyun.songmin.R;

import java.util.ArrayList;

public class LabelImageView extends RelativeLayout implements View.OnTouchListener {
    public static RelativeLayout RL;
    public static int Start, END, right, left, toolbarheight;
    public static ArrayList<LabelTag> tagArr;
    public static int LayoutNumber;
    public static boolean setnumber = true;




    public final int[] unselectedTagImageViewResource = {R.drawable.btn_t_wood_01, R.drawable.btn_t_white_02, R.drawable.btn_t_grey_03, R.drawable.btn_t_beige_04, R.drawable.btn_t_green_05,
            R.drawable.btn_t_violet_06, R.drawable.btn_t_red_07, R.drawable.btn_t_black_08, R.drawable.btn_t_blue_09, R.drawable.btn_t_gold_10,
            R.drawable.btn_t_brown_11, R.drawable.btn_t_pink_12};
    public final int[] selectedTagImageViewResource = {R.drawable.btn_t_wood_01_s, R.drawable.btn_t_white_02_s, R.drawable.btn_t_grey_03_s, R.drawable.btn_t_beige_04_s, R.drawable.btn_t_green_05_s,
            R.drawable.btn_t_violet_06_s, R.drawable.btn_t_red_07_s, R.drawable.btn_t_black_08_s, R.drawable.btn_t_blue_09_s, R.drawable.btn_t_gold_10_s,
            R.drawable.btn_t_brown_11_s, R.drawable.btn_t_pink_12_s};

    public final int[] tagresource = {R.drawable.btn_t_wood_01_2, R.drawable.btn_t_white_02_2, R.drawable.btn_t_grey_03_2, R.drawable.btn_t_beige_04_2, R.drawable.btn_t_green_05_2,
            R.drawable.btn_t_violet_06_2, R.drawable.btn_t_red_07_2, R.drawable.btn_t_black_08_2, R.drawable.btn_t_blue_09_2, R.drawable.btn_t_gold_10_2,
            R.drawable.btn_t_brown_11_2, R.drawable.btn_t_pink_12_2};
    public final int[] tagViewId = {
            R.id.mr_tag_color_1, R.id.mr_tag_color_2, R.id.mr_tag_color_3, R.id.mr_tag_color_4, R.id.mr_tag_color_5, R.id.mr_tag_color_6,
            R.id.mr_tag_color_7, R.id.mr_tag_color_8, R.id.mr_tag_color_9, R.id.mr_tag_color_10, R.id.mr_tag_color_11, R.id.mr_tag_color_12

    };
    ImageView[] tagViews;


    public LabelImageView(Context context) {
        super(context);
        tagArr = new ArrayList<>();
    }

    public class tagDialog extends Dialog {
        EditText tagTXTedit;

        protected tagDialog(Context context) {
            super(context);
            setContentView(R.layout.dialog_tag_insert);
//            super(context,R.style.custom_dialog_theme);
//            LayoutInflater inflater = getLayoutInflater();
//            final View dialogView = inflater.inflate(R.layout.dialog_tag_insert, null);
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setView(dialogView);
//            final AlertDialog dialog = builder.create();
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
////            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
//            dialog.setCanceledOnTouchOutside(false);
//
//            dialog.show();
//

            listener listener = new listener();
            tagViews = new ImageView[12];
            for (int i = 0; i < 12; i++) {
                tagViews[i] = (ImageView) findViewById(tagViewId[i]);
                tagViews[i].setOnClickListener(listener);
            }


            tagTXTedit = (EditText) findViewById(R.id.tag_txt_edit);
            ImageView iv2 = (ImageView) findViewById(R.id.tag_dialog_cancle);
            ImageView iv1 = (ImageView) findViewById(R.id.tag_dialog_ok);
            iv1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    tagArr.get(tagArr.size() - 1).text = tagTXTedit.getText().toString();
                    setClickable(true);
                    LT.text = tagTXTedit.getText().toString();

                    dismiss();
                }
            });

            iv2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeView(tagArr.get(tagArr.size() - 1));
                    tagArr.remove(tagArr.get(tagArr.size() - 1));
                    setClickable(true);
                    dismiss();

                }
            });
        }

        public class listener implements View.OnClickListener

        {

            @Override
            public void onClick(View v) {
                resetBtn();

                switch (v.getId()) {
                    case R.id.mr_tag_color_1:
                        LT.color = 1;
                        LT.setBackgroundResource(tagresource[0]);

                        break;
                    case R.id.mr_tag_color_2:
                        LT.color = 2;
                        LT.setBackgroundResource(tagresource[1]);
                        break;
                    case R.id.mr_tag_color_3:
                        LT.color = 3;
                        LT.setBackgroundResource(tagresource[2]);
                        break;
                    case R.id.mr_tag_color_4:
                        LT.color = 4;
                        LT.setBackgroundResource(tagresource[3]);
                        break;
                    case R.id.mr_tag_color_5:
                        LT.color = 5;
                        LT.setBackgroundResource(tagresource[4]);
                        break;
                    case R.id.mr_tag_color_6:
                        LT.color = 6;
                        LT.setBackgroundResource(tagresource[5]);
                        break;
                    case R.id.mr_tag_color_7:
                        LT.color = 7;
                        LT.setBackgroundResource(tagresource[6]);
                        break;
                    case R.id.mr_tag_color_8:
                        LT.color = 8;
                        LT.setBackgroundResource(tagresource[7]);
                        break;
                    case R.id.mr_tag_color_9:
                        LT.color = 9;
                        LT.setBackgroundResource(tagresource[8]);
                        break;
                    case R.id.mr_tag_color_10:
                        LT.color = 10;
                        LT.setBackgroundResource(tagresource[9]);
                        break;
                    case R.id.mr_tag_color_11:
                        LT.color = 11;
                        LT.setBackgroundResource(tagresource[10]);
                        break;
                    case R.id.mr_tag_color_12:
                        LT.color = 12;
                        LT.setBackgroundResource(tagresource[11]);
                        break;

                }
                tagViews[LT.color - 1].setImageResource(selectedTagImageViewResource[LT.color - 1]);
//                Log.e("TT", "TT");
            }
        }

        public void resetBtn() {
            for (int i = 0; i < 12; i++) {
                tagViews[i].setImageResource(unselectedTagImageViewResource[i]);
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        RL = this;
        this.setOnTouchListener(this);
        Start = getTop();
        END = getBottom();
        right = getRight();
        left = getLeft();
        Log.e("라이트" + right, "레프트" + left);
        Log.e("위" + Start, "아래" + END);
        toolbarheight = 120;
    }

    public LabelImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        tagArr = new ArrayList<>();
    }

    public LabelImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        tagArr = new ArrayList<>();
    }

    LabelTag LT;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        setClickable(false);
        LT = new LabelTag(getContext());
        LT.setMovable(true);
        LT.MoveXY(event.getX(), event.getY(), event.getRawX(), event.getRawY());
        addView(LT);
        tagArr.add(LT);
        tagDialog a = new tagDialog(getContext());
        a.show();

        return false;
    }

    public static void setLayoutNumber(int n) {
        LayoutNumber = n;
        setnumber = false;
    }

    public static RelativeLayout getLayout() {
        return RL;
    }
    public static ArrayList<LabelTag> getTags() {
        return tagArr;
    }
}

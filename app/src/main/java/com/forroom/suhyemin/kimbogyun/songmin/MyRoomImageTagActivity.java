
package com.forroom.suhyemin.kimbogyun.songmin;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.common.LabelImageView;
import com.forroom.suhyemin.kimbogyun.songmin.common.LabelTag;

public class MyRoomImageTagActivity extends AppCompatActivity {
  /*  private LabelImageView tag_image;
    private ImageView tag_imageView;
    private ImageButton tag_back_image;
    public static int position;
    public static int height;
    public static int width;
    public static int barHeight;
    public static RelativeLayout relativeLayout, bar;

    String s;
    int tagindex;
    public final int[] tagresource = {R.drawable.btn_t_wood_01_2, R.drawable.btn_t_white_02_2, R.drawable.btn_t_grey_03_2, R.drawable.btn_t_beige_04_2, R.drawable.btn_t_green_05_2,
            R.drawable.btn_t_violet_06_2, R.drawable.btn_t_red_07_2, R.drawable.btn_t_black_08_2, R.drawable.btn_t_blue_09_2, R.drawable.btn_t_gold_10_2,
            R.drawable.btn_t_brown_11_2, R.drawable.btn_t_pink_12_2};

    //    public static RelativeLayout FL;
//    LabelImageView tag_image;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_room_image_tag);

        Intent intent = getIntent();
        s = intent.getStringExtra("toTagActivity");
        position = intent.getIntExtra("kbgposition", 0);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        bar = (RelativeLayout) findViewById(R.id.TB);
        tag_image = (LabelImageView) findViewById(R.id.tag_imageview);
        tag_imageView = (ImageView) findViewById(R.id.tag_imageview_image);
        tag_back_image = (ImageButton)findViewById(R.id.tag_back_btn);
        tag_back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        if (MyRoomWriteActivity.LabelTagArrayListArray[position] != null) {

            relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                public int top, bottom;

                @Override
                public void onGlobalLayout() {
                    Glide.with(getApplicationContext()).load(s).listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            target.onLoadCleared(resource);
                            Log.e("", resource.getIntrinsicHeight() + "");
                            top = tag_image.getTop();
                            bottom = tag_image.getBottom();
                            Log.e(tag_image.getLeft() + "", resource.getIntrinsicWidth() + "");

                            LabelImageView.left = tag_imageView.getLeft();
                            LabelImageView.right = tag_imageView.getLeft() + resource.getIntrinsicWidth();

                            barHeight = bar.getHeight();
                            LabelImageView.Start = bar.getHeight();

                            Log.e("스타트" +LabelImageView.Start, "레프트" + LabelImageView.left );

                            if (MyRoomWriteActivity.LabelTagArrayListArray[position] != null) {
                                tagindex = 0;
                                for (int i = 0; i < MyRoomWriteActivity.LabelTagArrayListArray[position].size(); i++) {
                                    LabelTag LT = new LabelTag(getApplicationContext());
                                    LT.index = tagindex++;
                                    int imageHeight = resource.getIntrinsicHeight();
                                    int imageWidth = resource.getIntrinsicWidth();

                                    if (imageHeight + barHeight > relativeLayout.getHeight())
                                        imageHeight = relativeLayout.getHeight() - barHeight;


                                    LT.MoveXY((MyRoomWriteActivity.LabelTagArrayListArray[position].get(i).getDataX() * (LabelImageView.left + imageWidth)), (MyRoomWriteActivity.LabelTagArrayListArray[position].get(i).getDataY() * (imageHeight)), 1, 2);
                                    LT.setBackgroundResource(tagresource[MyRoomWriteActivity.LabelTagArrayListArray[position].get(i).getColor() - 1]);
                                    LT.movable = true;

                                    tag_image.addView(LT);
                                }
                            }
                            LabelImageView.tagArr = MyRoomWriteActivity.LabelTagArrayListArray[position];


                            return true;
                        }
                    }).into(tag_imageView);//
                    // Ensure you call it only once :
                    relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);


//                    Drawable d = Drawable.createFromPath(s);
//                    tag_image.setBackground(d);


//                    Glide.with(ForRoomApplication.getFRContext()).load(s).asBitmap().into(new SimpleTarget<Bitmap>(tag_image.getWidth(), 1080) {
//                        @Override
//                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> eAnimation) {
//                            Drawable drawable = new BitmapDrawable(resource);
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//                                tag_image.setBackground(drawable);
//                            }
//                        }
//                    });

//                    Glide.with(ForRoomApplication.getFRContext()).load(s).into(tag_image);
                    Display display = getWindowManager().getDefaultDisplay();
                    height = display.getHeight();
                    width = display.getWidth();

                    ImageButton saveBtn = (ImageButton) findViewById(R.id.tag_save_btn);
                    saveBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MyRoomWriteActivity.LabelTagArrayListArray[position] = LabelImageView.tagArr;

                            Intent intent = new Intent();
                            intent.putExtra("position", position);
                            setResult(ForRoomConstant.MYROOM_WRITE_TAG_FINISH, intent);
                            finish();
                        }
                    });
                }
            });

        }

    }


    public static int getPosition() {
        return position;
    }

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }
*/

}

//package com.forroom.suhyemin.kimbogyun.songmin;
//
//import android.annotation.TargetApi;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.os.Build;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.view.Display;
//import android.view.View;
//import android.view.ViewTreeObserver;
//import android.widget.ImageButton;
//import android.widget.RelativeLayout;
//
//import com.forroom.suhyemin.kimbogyun.songmin.common.LabelImageView;
//import com.forroom.suhyemin.kimbogyun.songmin.common.LabelTag;
//
//public class MyRoomImageTagActivity extends AppCompatActivity {
//    private LabelImageView tag_image;
//    public static int position;
//    public static int height;
//    public static int width;
//    public static RelativeLayout relativeLayout;
//    String s;
//    int tagindex;
//    public final int[] tagresource = {R.drawable.btn_t_wood_01_2, R.drawable.btn_t_white_02_2, R.drawable.btn_t_grey_03_2, R.drawable.btn_t_beige_04_2, R.drawable.btn_t_green_05_2,
//            R.drawable.btn_t_violet_06_2, R.drawable.btn_t_red_07_2, R.drawable.btn_t_black_08_2, R.drawable.btn_t_blue_09_2, R.drawable.btn_t_gold_10_2,
//            R.drawable.btn_t_brown_11_2, R.drawable.btn_t_pink_12_2};
//    //    public static RelativeLayout FL;
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_room_image_tag);
//
//        Intent intent = getIntent();
//        s = intent.getStringExtra("toTagActivity");
//        position = intent.getIntExtra("kbgposition", 0);
//        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
//
//
//        if (MyRoomWriteActivity.LabelTagArrayListArray[position] != null) {
//
//            relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                public int top, bottom;
//
//                @Override
//                public void onGlobalLayout() {
//                    // Ensure you call it only once :
//                    relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                    LabelImageView tag_image = (LabelImageView) findViewById(R.id.tag_imageview);
//                    top = tag_image.getTop();
//                    bottom = tag_image.getBottom();
//
//                    if (MyRoomWriteActivity.LabelTagArrayListArray[position] != null) {
//                        tagindex = 0;
//                        for (int i = 0; i < MyRoomWriteActivity.LabelTagArrayListArray[position].size(); i++) {
//                            LabelTag LT = new LabelTag(getApplicationContext());
//                            LT.index = tagindex++;
//                            LT.MoveXY((MyRoomWriteActivity.LabelTagArrayListArray[position].get(i).getDataX() * MyRoomWriteActivity.getWidth()), (MyRoomWriteActivity.LabelTagArrayListArray[position].get(i).getDataY() * (bottom - top)), 1, 2);
//                            LT.setBackgroundResource(tagresource[MyRoomWriteActivity.LabelTagArrayListArray[position].get(i).getColor()-1]);
//                            LT.movable = true;
//
//                            tag_image.addView(LT);
//                        }
//                    }
//                    LabelImageView.tagArr = MyRoomWriteActivity.LabelTagArrayListArray[position];
//
//                    Drawable d = Drawable.createFromPath(s);
//                    tag_image.setBackground(d);
//                    Display display = getWindowManager().getDefaultDisplay();
//                    height = display.getHeight();
//                    width = display.getWidth();
//
//                    ImageButton saveBtn = (ImageButton) findViewById(R.id.tag_save_btn);
//                    saveBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            MyRoomWriteActivity.LabelTagArrayListArray[position] = LabelImageView.tagArr;
////                            for (int i = 0; i < LabelImageView.tagArr.size(); i++) {
////                                Log.e("태그택스트", "" + LabelImageView.tagArr.get(i).getText());
////                                Log.e("태그엑스", "" + LabelImageView.tagArr.get(i).getDataX());
////                                Log.e("태그와이", "" + LabelImageView.tagArr.get(i).getDataY());
////                            }
//                            Intent intent = new Intent();
//                            intent.putExtra("position", position);
//                            setResult(18, intent);
//                            finish();
//                        }
//                    });
//                }
//            });
//
//        }
//
//    }
//
//
//
//    public static int getPosition() {
//        return position;
//    }
//
//    public static int getHeight() {
//        return height;
//    }
//
//    public static int getWidth() {
//        return width;

//    }

//


//
//}

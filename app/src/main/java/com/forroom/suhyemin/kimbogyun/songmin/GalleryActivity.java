package com.forroom.suhyemin.kimbogyun.songmin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.forroom.suhyemin.kimbogyun.songmin.common.ForRoomConstant;
import com.forroom.suhyemin.kimbogyun.songmin.profilePage.ProfileActivity;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.PhotoValueObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryActivity extends AppCompatActivity {
    private ArrayList<String> ImageList;
    private GalleryManager mGalleryManager;
    private RecyclerView recyclerGallery;
    private GalleryAdapter galleryAdapter;
    private ImageView Gallery_ok;
    private String[] intentimage;
    private String fromintent;
    private ImageView galleryLeftIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        galleryLeftIcon = (ImageView) findViewById(R.id.gallery_left_back);
        galleryLeftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        fromintent = intent.getStringExtra("toGallery");

        recyclerGallery = (RecyclerView) findViewById(R.id.Gallery_recyclerView);
        galleryAdapter = new GalleryAdapter(GalleryActivity.this, initGalleryPathList(), R.layout.item_gallery_photo);
        galleryAdapter.setOnItemClickListener(mOnItemClickListener);
        recyclerGallery.setAdapter(galleryAdapter);
        recyclerGallery.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerGallery.setItemAnimator(new DefaultItemAnimator());
        recyclerGallery.addItemDecoration(new GridDividerDecoration(getResources(), R.drawable.divider_recycler_gallery));
        ImageList = new ArrayList<>();

//        Log.e("ssong s", fromintent);

        Gallery_ok = (ImageView) findViewById(R.id.Gallery_ok_button);
        Gallery_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromintent.equalsIgnoreCase("fromProfile") && ImageList.size() != 1) {
                    Toast.makeText(ForRoomApplication.getFRContext(), "사진을 한장만 선택해주세요", Toast.LENGTH_SHORT).show();
                } else if (fromintent.equalsIgnoreCase("fromProfile") && ImageList.size() == 1) {
                    ProfileActivity.galleryFile = new File(ImageList.get(0));
                    Intent intent = new Intent();
                    intent.putExtra("toProfile", ImageList.get(0));
                    setResult(ForRoomConstant.GALLAY_RESULT_CODE, intent);
                    finish();
                } else if (ImageList.size() > 3) {
                    Toast.makeText(ForRoomApplication.getFRContext(), "사진은 최대 3장만 업로드 가능합니다", Toast.LENGTH_SHORT).show();
                } else if (ImageList.size() == 0) {
                    Toast.makeText(ForRoomApplication.getFRContext(), "사진을 선택해 주세요", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("이미지 선택된거" ,ImageList+"" );
                    intentimage = new String[ImageList.size()];
                    for (int i = 0; i < ImageList.size(); i++) {
                        intentimage[i] = ImageList.get(i);

                        File f = new File(intentimage[i]);
                        //이미지 사이즈를 줄임

//                        Log.e("파일사이즈", f.length() + "");

                        if (f.length() > 500000) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            if (f.length() > 2000000) {
                                options.inSampleSize = 8;
                            } else if (f.length() > 1000000) {
                                options.inSampleSize = 4;
                            } else if (f.length() > 500000) {
                                options.inSampleSize = 2;
                            }
                            Bitmap src = BitmapFactory.decodeFile(intentimage[i], options);
                            intentimage[i] = SaveBitmapToFileCache(src, getFilesDir() + File.separator + "imageCache_" + i + ".jpg");
//                            intentimage[i] = SaveBitmapToFileCache(src, getExternalCacheDir() + File.separator + "imageCache_" + i + ".jpg");
                        }

//                        Bitmap resized = Bitmap.createScaledBitmap(src, getBitmapOfWidth(ImageList.get(i)), getBitmapOfHeight(ImageList.get(i)), true);

//                        intentimage[i] = "/storage/sdcard0/foroom/" + i + ".jpg";
                    }
                    Intent intent = new Intent(GalleryActivity.this, MyRoomWriteActivity.class);
//                    Log.e("이미지 선택된거" ,intentimage[0]+"/"+intentimage[1]+"/"+intentimage[2] );
                    intent.putExtra("tomyroomwrite", intentimage);
                    startActivityForResult(intent, 58);

                }
            }
        });
    }


    private String SaveBitmapToFileCache(Bitmap bitmap, String strFilePath) {

        File fileCacheItem = new File(strFilePath);
        if(fileCacheItem.exists()){
            fileCacheItem.delete();
        }
        OutputStream out = null;
        String result = null;
        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return strFilePath;
        }

    }

/*    public static int getBitmapOfWidth(String fileName) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(fileName, options);
            return options.outWidth;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getBitmapOfHeight(String fileName) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(fileName, options);
            return options.outHeight;
        } catch (Exception e) {
            return 0;
        }
    }*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("Gallery result Code", resultCode + "");
//        Log.e("Gallery requestCode", requestCode+"");
        if (resultCode == ForRoomConstant.MYROOM_WRITE_BACK) {
            Intent intent = new Intent();
            setResult(ForRoomConstant.GALLERY_TO_MYROOM_LIST, intent);
            finish();
        }
    }

    private List<PhotoValueObject> initGalleryPathList() {

        mGalleryManager = new GalleryManager(getApplicationContext());
        //return mGalleryManager.getDatePhotoPathList(2015, 9, 19);
        return mGalleryManager.getAllPhotoPathList();
    }

    private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

        @Override
        public void OnItemClick(GalleryAdapter.PhotoViewHolder photoViewHolder, int position) {

            PhotoValueObject photoVO = galleryAdapter.getmPhotoList().get(position);


            if (photoVO.isSelected()) {
                photoVO.setSelected(false);
                Log.e("ssong deselect", photoVO.getImgPath());
                ImageList.remove(photoVO.getImgPath());

            } else {
                photoVO.setSelected(true);
                Log.e("ssongtest", photoVO.getImgPath());
                ImageList.add(photoVO.getImgPath());
            }

            galleryAdapter.getmPhotoList().set(position, photoVO);
            galleryAdapter.notifyDataSetChanged();

        }
    };


    public class GalleryManager {
        private Context mContext;

        public GalleryManager(Context context) {
            mContext = context;
        }

        private List<PhotoValueObject> getAllPhotoPathList() {
            ArrayList<PhotoValueObject> photoList = new ArrayList<>();
            Uri uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            String[] projection = {
                    MediaStore.MediaColumns.DATA,
                    MediaStore.Images.Media.DATE_ADDED
            };

            Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
            int columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            while (cursor.moveToNext()) {
                PhotoValueObject photoVO = new PhotoValueObject(cursor.getString(columnIndexData), false);
                photoList.add(photoVO);
            }
            Collections.reverse(photoList);
            cursor.close();

            return photoList;
        }
    }

    public class GridDividerDecoration extends RecyclerView.ItemDecoration {

        private final int[] ATTRS = {android.R.attr.listDivider};

        private Drawable mDivider;
        private int mInsets;

        public GridDividerDecoration(Resources resources, int layout) {

            mDivider = resources.getDrawable(layout);
            mInsets = 1;
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            drawVertical(c, parent);
            drawHorizontal(c, parent);
        }

        public void drawVertical(Canvas c, RecyclerView parent) {
            if (parent.getChildCount() == 0) return;

            final int left = parent.getPaddingLeft();
            final int right = parent.getWidth() - parent.getPaddingRight();

            final View child = parent.getChildAt(0);
            if (child.getHeight() == 0) return;

            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin + mInsets;
            int bottom = top + mDivider.getIntrinsicHeight();

            final int parentBottom = parent.getHeight() - parent.getPaddingBottom();
            while (bottom < parentBottom) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);

                top += mInsets + params.topMargin + child.getHeight() + params.bottomMargin + mInsets;
                bottom = top + mDivider.getIntrinsicHeight();
            }
        }

        public void drawHorizontal(Canvas c, RecyclerView parent) {
            final int top = parent.getPaddingTop();
            final int bottom = parent.getHeight() - parent.getPaddingBottom();

            final int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params =
                        (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getRight() + params.rightMargin + mInsets;
                final int right = left + mDivider.getIntrinsicWidth();
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(mInsets, mInsets, mInsets, mInsets);
        }
    }

    public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.PhotoViewHolder> {


        private Activity mActivity;

        private int itemLayout;
        private List<PhotoValueObject> mPhotoList;

        private OnItemClickListener onItemClickListener;

        public List<PhotoValueObject> getmPhotoList() {
            return mPhotoList;
        }

//        public List<PhotoValueObject> getSelectedPhotoList() {
//
//            List<PhotoValueObject> mSelectPhotoList = new ArrayList<>();
//
//
//            for (int i = 0; i > mPhotoList.size(); i++) {
//
//                PhotoValueObject photoVO = mPhotoList.get(i);
//                if (photoVO.isSelected()) {
//                    mSelectPhotoList.add(photoVO);
//                }
//            }
//            return mSelectPhotoList;
//        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        public GalleryAdapter(Activity activity, List<PhotoValueObject> photoList, int itemLayout) {

            mActivity = activity;

            this.mPhotoList = photoList;
            this.itemLayout = itemLayout;

        }

        @Override
        public PhotoViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout, viewGroup, false);
            return new PhotoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final PhotoViewHolder viewHolder, final int position) {

            final PhotoValueObject photoVO = mPhotoList.get(position);
            //Bitmap  bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeFile(photoVO.getImgPath()), 1080,660,false);
            try {
                Glide.with(mActivity)
                        .load(photoVO.getImgPath())
                        .centerCrop()
                        .crossFade()
                        .into(viewHolder.imgPhoto);
            } catch (OutOfMemoryError error) {
                Log.e("업로드메모리에러", error.toString());

            }
            if (photoVO.isSelected()) {
                viewHolder.layoutSelect.setVisibility(View.VISIBLE);
            } else {
                viewHolder.layoutSelect.setVisibility(View.INVISIBLE);
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (onItemClickListener != null) {
                        onItemClickListener.OnItemClick(viewHolder, position);
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mPhotoList.size();
        }

        public class PhotoViewHolder extends RecyclerView.ViewHolder {

            public ImageView imgPhoto;
            public RelativeLayout layoutSelect;

            public PhotoViewHolder(View itemView) {
                super(itemView);

                imgPhoto = (ImageView) itemView.findViewById(R.id.imgPhoto);
                layoutSelect = (RelativeLayout) itemView.findViewById(R.id.layoutSelect);
            }

        }
    }

    private interface OnItemClickListener {

        void OnItemClick(GalleryAdapter.PhotoViewHolder photoViewHolder, int position);
    }


}

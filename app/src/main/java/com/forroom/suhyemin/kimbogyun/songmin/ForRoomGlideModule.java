package com.forroom.suhyemin.kimbogyun.songmin;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by ccei on 2016-02-01.
 */
public class ForRoomGlideModule implements GlideModule{
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {

        MemorySizeCalculator calculator = new MemorySizeCalculator(context);
        int defaultMemotyCacheSize = calculator.getMemoryCacheSize();
        int defaultBitmapPoolSize = calculator.getBitmapPoolSize();

        int customMemoryCacheSize = (int)(1.3 * defaultMemotyCacheSize);
        int customBitmapPoolSize = (int)(1.3 * defaultBitmapPoolSize);

        builder.setMemoryCache(new LruResourceCache(customMemoryCacheSize));
        builder.setBitmapPool(new LruBitmapPool(customBitmapPoolSize));

        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }
    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}

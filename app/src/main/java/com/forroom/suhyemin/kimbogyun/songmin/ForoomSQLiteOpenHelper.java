package com.forroom.suhyemin.kimbogyun.songmin;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.forroom.suhyemin.kimbogyun.songmin.common.DBConstant;
import com.forroom.suhyemin.kimbogyun.songmin.valueobject.GoodsItemValueObject;

import java.util.ArrayList;

/**
 * Created by ccei on 2016-02-12.
 */
public class ForoomSQLiteOpenHelper extends SQLiteOpenHelper {
    public static int DB_VERSION = 1;
    private static final String DB_NAME = "foroom_sqlite_goods_item.db";
    private static ForoomSQLiteOpenHelper dbHelper;
    private static final String SERIESE11 = "소파";
    private static final String SERIESE12 = "의자&스툴";
    private static final String SERIESE13 = "책상&책장";
    private static final String SERIESE14 = "식탁세트&테이블";
    private static final String SERIESE21 = "거실장&소파테이블";
    private static final String SERIESE22 = "화장대&콘솔";
    private static final String SERIESE23 = "침대&협탁";
    private static final String SERIESE24 = "옷장&서랍장";
    private static final String SERIESE31 = "조명";
    private static final String SERIESE32 = "선반&행거";
    private static final String SERIESE33 = "커튼&패브릭";
    private static final String SERIESE34 = "소품&장식";

    public ForoomSQLiteOpenHelper() {
        super(ForRoomApplication.getFRContext(), DB_NAME, null, DB_VERSION);
    }

    public static synchronized ForoomSQLiteOpenHelper getInstace() {
        if (dbHelper == null) {
            dbHelper = new ForoomSQLiteOpenHelper();
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder tableOne = new StringBuilder();
        tableOne.append(" CREATE TABLE " + DBConstant.TABLE_NAME)
                .append("(")
                .append(DBConstant.GOODS_ID + " TEXT PRIMARY KEY , ")
                .append(DBConstant.GOODS_SERIES + " TEXT NOT NULL ,")
                .append(DBConstant.GOODS_NAME + " TEXT NOT NULL ,")
                .append(DBConstant.GOODS_IMAGE + " TEXT NOT NULL ")
                .append(" );");

        db.execSQL(tableOne.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + DBConstant.TABLE_NAME);
//        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS " + DBConstant.TABLE_NAME);
//        onCreate(db);
//        super.onDowngrade(db,oldVersion,newVersion);
    }

    public boolean insertGoodsItem(GoodsItemValueObject goodsVO) {
        boolean flag = false;
        SQLiteDatabase db = null;
        int companyID = 0;
        try {
            db = getWritableDatabase();

            ContentValues values = new ContentValues();

//            Log.e("ssong testgoodid", goodsVO.goods_id);
//            Log.e("ssong testgoodsers", goodsVO.goods_series);
//            Log.e("ssong testgoodname", goodsVO.goods_name);
//            Log.e("ssong testgoodimage", goodsVO.goods_image);

            values.put(DBConstant.GOODS_ID, goodsVO.goods_id);
            values.put(DBConstant.GOODS_SERIES, goodsVO.goods_series);
            values.put(DBConstant.GOODS_NAME, goodsVO.goods_name);
            values.put(DBConstant.GOODS_IMAGE, goodsVO.goods_image);

            db.beginTransaction();
            long insertedID = db.insertOrThrow(DBConstant.TABLE_NAME, null, values);
            if (insertedID > 0) {
                flag = true;
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("insert GoodsItem", "디비 입력 잘못 ", e);
        } finally {
            db.endTransaction();
            DBCloseManager.close(db, null);
        }
        return flag;
    }

    public ArrayList<GoodsItemValueObject> findGoodsItemListAll(int seriesno) {
        ArrayList<GoodsItemValueObject> list = null;
        SQLiteDatabase db;
        Cursor cursor = null;
        String series = null;
        ForoomSQLiteOpenHelper helper;
        switch (seriesno) {
            case 11:
                series = SERIESE11;
                break;
            case 12:
                series = SERIESE12;
                break;
            case 13:
                series = SERIESE13;
                break;
            case 14:
                series = SERIESE14;
                break;
            case 21:
                series = SERIESE21;
                break;
            case 22:
                series = SERIESE22;
                break;
            case 23:
                series = SERIESE23;
                break;
            case 24:
                series = SERIESE24;
                break;
            case 31:
                series = SERIESE31;
                break;
            case 32:
                series = SERIESE32;
                break;
            case 33:
                series = SERIESE33;
                break;
            case 34:
                series = SERIESE34;
                break;
        }

        try {
            helper = new ForoomSQLiteOpenHelper();
//            db = helper.getReadableDatabase();
            db = helper.getWritableDatabase();

            cursor = db.query(DBConstant.TABLE_NAME, null, null, null, null, null, null);
//            cursor = db.rawQuery("SELECT * FROM "+DBConstant.TABLE_NAME+" WHERE " +
//                    DBConstant.GOODS_SERIES+" = "+"'"+series+"'",null);
            if (cursor.getCount() > 0) {
                cursor.moveToNext();
                list = new ArrayList<>();
                while (!cursor.isAfterLast()) {
//                    Log.e("yyyyyyyyyyyyyyyyy","yyyyyyyyyyyyyyyy");
                    GoodsItemValueObject goodsVO = new GoodsItemValueObject();
                    goodsVO.goods_id = cursor.getString(cursor.getColumnIndex(DBConstant.GOODS_ID));
                    goodsVO.goods_series = cursor.getString(cursor.getColumnIndex(DBConstant.GOODS_SERIES));
                    goodsVO.goods_name = cursor.getString(cursor.getColumnIndex(DBConstant.GOODS_NAME));
                    goodsVO.goods_image = cursor.getString(cursor.getColumnIndex(DBConstant.GOODS_IMAGE));
                    list.add(goodsVO);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e("ssong find GoodItem", "dberror", e);
        } finally {
        }
        return list;
    }

}

package com.forroom.suhyemin.kimbogyun.songmin;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ccei on 2016-02-12.
 */
public class DBCloseManager {
    public static void close(SQLiteDatabase db, Cursor cursor){
        if(cursor !=null){
            cursor.close();
        }if( db != null){
            db.close();
        }
    }
}

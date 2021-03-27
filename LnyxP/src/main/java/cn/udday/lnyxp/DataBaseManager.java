package cn.udday.lnyxp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class DataBaseManager {
    public static DataBaseManager manager;
    private static SQLiteDatabase db;
    private static final String DB_NAME = "cache.db";

    public DataBaseManager(Context context) {
        DataBaseSQLite sqLite = new DataBaseSQLite(context,DB_NAME,null,1);
        db = sqLite.getWritableDatabase();
    }

    public static DataBaseManager getInstance(Context context) {
        if (manager == null){
            manager = new DataBaseManager(context);
        }
        return manager;
    }

    public static void remove() {
        try{
            db.delete("cache_table",null,null);
            Log.e("图片缓存","删除成功");
        }catch (Exception e){
            Log.e("图片缓存","删除失败");
        }
    }
    public static String get(String urlMD5) {
        String md5key;
        String pcontent = null;
        Cursor query = db.query(true,"cache_table",null,null,null,null,null,null,null);
        if (query.moveToFirst()){
            do{
                md5key = query.getString(query.getColumnIndex("ke"));
                if (urlMD5.equals(md5key)){
                    pcontent = query.getString(query.getColumnIndex("content"));
                    break;
                }
            }while (query.moveToNext());
        }
        query.close();
        return pcontent;
    }

    public static void put(String urlMD5, String path) {
        ContentValues values = new ContentValues();
        values.put("ke",urlMD5);
        values.put("content",path);
        db.insert("cache_table",null,values);
    }
}

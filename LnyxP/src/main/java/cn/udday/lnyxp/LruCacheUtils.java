package cn.udday.lnyxp;

import android.graphics.Bitmap;
import android.util.LruCache;

public class LruCacheUtils extends LruCache {
    private static LruCacheUtils lruCacheUtils;

    public LruCacheUtils(int maxSize){
        super(maxSize);
    }

    public static LruCacheUtils getInstance() {
        if(lruCacheUtils == null){
            lruCacheUtils = new LruCacheUtils(1000);
        }
        return lruCacheUtils;
    }
    protected  int sizeOf(Object key,Object value){
        return super.sizeOf(key,value);
    }
}

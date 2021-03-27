package cn.udday.lnyxp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DiskCacheUtils {
    private static final String PICTURE = "Picture";
    private  static volatile DiskCacheUtils instance;
    private String cachePath = "Cache";
    private static final byte[] lock = new byte[0];
    private File cacheFile;
    private Context context;
    public DiskCacheUtils(Context context) {
        this.context = context;
        cacheFile = new File(getDir(context));
     }

    public void put(String key, Bitmap value){
        if(value != null){
            FileOutputStream fos = null;
            try {
                File temp = new File(key);
                if (temp.exists()) temp.delete();
                temp.createNewFile();
                fos = new FileOutputStream(temp);
                value.compress(Bitmap.CompressFormat.PNG,100,fos);
            } catch (IOException e) {
                e.printStackTrace();
                if (fos != null){
                    try{
                        fos.flush();
                        fos.close();
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
    public Bitmap get(String key){
//        Log.e("gt",cacheFile+"/"+key + ".png");
        if (new File(cacheFile+"/"+key + ".png").exists())
        return BitmapFactory.decodeFile(cacheFile+"/"+key + ".png");
        return null;
    }
    public String getPath(String key){
        return cacheFile + "/" + key + ".png";
    }

    public static DiskCacheUtils getInstance(Context context){
        if (instance == null){
            synchronized (lock){
                if (null == instance){
                    instance = new DiskCacheUtils(context);
                }
            }
        }
        return instance;
    }
    public static String getDir(Context context) {
        String dir;
            dir = context.getExternalFilesDir("") + File.separator + PICTURE + File.separator;
        File file = new File(dir);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
//            Log.e("1",file.mkdirs()+"");
        }
        return dir+"/";
    }
}

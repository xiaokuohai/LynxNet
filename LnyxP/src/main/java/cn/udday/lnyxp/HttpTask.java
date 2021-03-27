package cn.udday.lnyxp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.BlockingQueue;

public class HttpTask extends Thread {
    LruCacheUtils lruCacheUtils;
    DiskCacheUtils diskCacheUtils;
    private BlockingQueue<BitmapRequest> queue;
    private Handler handler = new Handler(Looper.myLooper());
    private Context context;
    public HttpTask(BlockingQueue<BitmapRequest> queue,Context context ) {
       this.queue = queue;
       this.context = context;
    }

    @Override
    public void run() {
        super.run();
        while (true){
            try {
                BitmapRequest request = queue.take();
                showDefaultP(request);
                Bitmap bitmap = loadBitmap(request);
                showP(request,bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }

    private void showP(BitmapRequest request, Bitmap bitmap) {
        final ImageView imageView = request.getImageView();
        if(bitmap != null && request.getImageView() != null && request.getImageView().getTag().equals(request.getUrlMD5())){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageBitmap(bitmap);
                }
            });
        }
    }

    private Bitmap loadBitmap(BitmapRequest request) {
        Bitmap bitmap = null;
        this.lruCacheUtils = LruCacheUtils.getInstance();
        bitmap = (Bitmap) lruCacheUtils.get(request.getUrlMD5());
        if (bitmap != null){
            return bitmap;
        }
        this.diskCacheUtils = DiskCacheUtils.getInstance(context);
        bitmap = diskCacheUtils.get(request.getUrlMD5());
        if (bitmap != null){
            //DataBaseManager.put(request.getUrlMD5(),diskCacheUtils.getPath(request.getUrlMD5()));
            diskCacheUtils.put(DataBaseManager.get(request.getUrlMD5()),bitmap);
            return bitmap;
        }

        bitmap = downLoadUrlP(request.getUrl(),request);
        if (bitmap != null){
            CachePut(request,bitmap);
        }
        return bitmap;
    }

    private void CachePut(BitmapRequest request, Bitmap bitmap) {
        lruCacheUtils.put(request.getUrlMD5(),bitmap);
        DataBaseManager.put(request.getUrlMD5(),diskCacheUtils.getPath(request.getUrlMD5()));
        diskCacheUtils.put(DataBaseManager.get(request.getUrlMD5()),bitmap);
    }

    private Bitmap downLoadUrlP(String url1, BitmapRequest request) {
        FileOutputStream fos = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(url1);
            HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();
            is = httpURLConnection.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
//            Log.e("下载","图片"+request.getUrl()+"下载成功");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
            if(fos != null) {
                fos.close();
            }
            if(is != null){
                is.close();
            }
                } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    private void showDefaultP(BitmapRequest request) {
        if(request.getLoadingId() > 0 && request.getImageView() != null){
            final int res = request.getLoadingId();
            final ImageView imageView = request.getImageView();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    imageView.setImageResource(res);
                }
            });
        }
    }
}

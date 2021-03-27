package cn.udday.lnyxp;

import android.content.Context;
import android.media.Image;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;

public class BitmapRequest {
    private Context context;
    private String url;
    private SoftReference<ImageView> imageViewSoftReference;
    private int loadingId;
    private RequestCallBack requestCallBack;
    private String urlMD5;
    private static ArrayList<String> md5 = new ArrayList<>();
    public BitmapRequest(Context context) {
        this.context= context;
        DataBaseManager.getInstance(context);
    }
    public BitmapRequest load(String url){
        this.url = url;
        this.urlMD5 = MD5Utils.encode(url);
        md5.add(urlMD5);
        return this;
    }
    public void removeCache(){
        for ( String md5 : md5){
            String path = DataBaseManager.get(md5);
            if (path != null){
                File temp = new File(path);
                if (temp != null){
                    temp.delete();
                }
            }

        }
        DataBaseManager.remove();
    }
    public BitmapRequest setLoadingId(int loadingId){
        this.loadingId = loadingId;
        return this;
    }
    public BitmapRequest setCallBack(RequestCallBack requestCallBack){
        this.requestCallBack = requestCallBack;
        return this;
    }
    public void into(ImageView imageView){
        imageView.setTag(this.urlMD5);
        this.imageViewSoftReference = new SoftReference<>(imageView);
        HttpThreadPool.getInstance(context).addRequest(this);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ImageView getImageView() {
        return imageViewSoftReference.get();
    }

    public int getLoadingId() {
        return loadingId;
    }

    public RequestCallBack getRequestCallBack() {
        return requestCallBack;
    }

    public void setRequestCallBack(RequestCallBack requestCallBack) {
        this.requestCallBack = requestCallBack;
    }

    public String getUrlMD5() {
        return urlMD5;
    }

    public void setUrlMD5(String urlMD5) {
        this.urlMD5 = urlMD5;
    }

    public static ArrayList<String> getMd5() {
        return md5;
    }

    public static void setMd5(ArrayList<String> md5) {
        BitmapRequest.md5 = md5;
    }
}

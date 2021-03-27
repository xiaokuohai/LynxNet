package cn.udday.lnyxp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CompressP {
    public static Bitmap decodeBitmap(byte[] stream, int reqWidth, int reqHeight){
        int inPHeight;
        int inPWidth;
        int inSample = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(stream,0,stream.length,options);
        inPHeight = options.outHeight;
        inPWidth = options.outWidth;
        BitmapFactory.Options resultOption = tol(options,inPHeight,inPWidth,reqWidth,reqHeight,inSample);
        return BitmapFactory.decodeByteArray(stream,0,stream.length,resultOption);
    }
    private static BitmapFactory.Options tol(BitmapFactory.Options options,int inPHeight,int inPWidth,int reqWidth,int reqHeight,int inSample){
        if(inPWidth > reqWidth || inPHeight > reqHeight){
            while (inPWidth / inSample > reqWidth || inPHeight / inSample > reqHeight){
                inSample *= 2;
            }
        }
        options.inSampleSize = inSample;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = false;
        options.inMutable = true;
        return options;
    }
}

package cn.udday.lnyxp;

import android.content.Context;

public interface LnyxP {
    public static BitmapRequest with(Context context){
        return new BitmapRequest(context);
    };
}

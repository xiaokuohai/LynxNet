package cn.udday.lnyxp;

import android.graphics.Bitmap;

public interface RequestCallBack {
        void onSucess(Bitmap bitmap);
        void onFailed();
}

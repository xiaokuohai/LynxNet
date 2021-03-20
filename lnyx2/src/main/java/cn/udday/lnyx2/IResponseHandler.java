package cn.udday.lnyx2;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;

public interface IResponseHandler {
    void handlerSuccess(Callback callback, Response response);
    void handFail(Callback callback, Request request, IOException e);
    IResponseHandler RESPONSE_HANDLER = new IResponseHandler() {

        Handler HANDLER = new Handler(Looper.getMainLooper());

        @Override
        public void handlerSuccess(final Callback callback, final Response response) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    callback.onResponse(response);
                }
            };
            execute(runnable);
        }

        @Override
        public void handFail(final Callback callback, final Request request, final IOException e) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    callback.onFail(request, e);
                }
            };
            execute(runnable);
        }


        public void removeAllMessage() {
            HANDLER.removeCallbacksAndMessages(null);
        }

        private void execute(Runnable runnable) {
            HANDLER.post(runnable);
        }

    };
}

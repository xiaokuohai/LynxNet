package cn.udday.lnyx2;

import java.io.IOException;

public class HttpTask implements Runnable{
    private HttpCall call;
    private Callback callback;
    private IRequestHandler requestHandler;
    private IResponseHandler handler = IResponseHandler.RESPONSE_HANDLER;

    public HttpTask(HttpCall call, Callback callback, IRequestHandler requestHandler) {
        this.call = call;
        this.callback = callback;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try {
            Response response = requestHandler.handlerRequest(call);
            handler.handlerSuccess(callback, response);
        } catch (IOException e) {
            handler.handFail(callback, call.request, e);
            e.printStackTrace();
        }
    }

}

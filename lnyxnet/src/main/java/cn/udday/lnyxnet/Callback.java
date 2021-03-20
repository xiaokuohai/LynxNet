package cn.udday.lnyxnet;

import java.io.IOException;

public interface Callback {
    void onResponse(Response response);
    void onFail(Request request, IOException e);
}

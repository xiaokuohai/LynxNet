package cn.udday.lnyx2;

import java.io.IOException;

public interface Callback {
    void onResponse(Response response);
    void onFail(Request request, IOException e);

}

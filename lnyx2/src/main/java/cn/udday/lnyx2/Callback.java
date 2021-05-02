package cn.udday.lnyx2;

import java.io.IOException;

public interface Callback {
    void onResponse(Response response) throws IOException;
    void onFail(Request request, IOException e);

}

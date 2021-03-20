package cn.udday.lnyxnet;

import java.io.IOException;

public interface IRequestHandler {
    Response handlerRequest(HttpCall call) throws IOException;
}

package cn.udday.lnyx2;

import java.io.IOException;

public interface IRequestHandler {
    Response handlerRequest(HttpCall call) throws IOException;
}

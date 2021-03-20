package cn.udday.lnyxnet;

import java.io.IOException;
import java.io.OutputStream;

public abstract class RequestBody {
    abstract String contentType();
    abstract void writeTo(OutputStream os) throws IOException;
}

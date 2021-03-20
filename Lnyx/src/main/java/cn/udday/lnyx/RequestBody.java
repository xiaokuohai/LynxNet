package cn.udday.lnyx;

import java.io.IOException;
import java.io.OutputStream;

public abstract class RequestBody {
    abstract String contentType();
    abstract void writeTo(OutputStream os) throws IOException;
}

package cn.udday.lnyxnet;

import java.io.UnsupportedEncodingException;

public class ResponseBody {
    byte[] bytes;

    public ResponseBody(byte[] bytes) {
        this.bytes = bytes;
    }

    public byte[] bytes() {
        return this.bytes;
    }

    public String string() {
        try {
            return new String(bytes(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}

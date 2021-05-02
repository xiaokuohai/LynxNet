package cn.udday.lnyx2;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;

import cn.udday.lnyxg.LnyxJson;

public class ResponseBody {
    byte[] bytes;
    public <T> T gson(Class<T> c){
        return LnyxJson.fromJson(string(),c);
    }
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

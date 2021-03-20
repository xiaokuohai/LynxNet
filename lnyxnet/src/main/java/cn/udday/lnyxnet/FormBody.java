package cn.udday.lnyxnet;

import android.text.TextUtils;
import android.util.ArrayMap;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/*
    add(String key, String value)
    map(Map<String, String> map)
 */
public class FormBody extends RequestBody  {
    public static final int MAX_FROM = 1000;

    final Map<String, String> map;

    public FormBody(Builder builder) {
        this.map = builder.map;
    }

    @Override
    public String contentType() {
        return "application/x-www-form-urlencoded; charset=UTF-8";
    }

    @Override
    public void writeTo(OutputStream ous) throws IOException {
        try {
            ous.write(transToString(map).getBytes("UTF-8"));
            ous.flush();
        } finally {
            if (ous != null) {
                ous.close();
            }
        }
    }
    //拼接请求参数
    private String transToString(Map<String, String> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        //set不重复，获取
        Set<String> keys = map.keySet();
        for (String key : keys) {
            if (!TextUtils.isEmpty(sb)) {
                //取一对加一个&
                sb.append("&");
            }
            sb.append(URLEncoder.encode(key, "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(map.get(key), "UTF-8"));
        }
        return sb.toString();
    }

    //建造者模式构建
    public static class Builder {
        private Map<String, String> map;

        public Builder() {
            map = new ArrayMap<>();
        }

        public Builder add(String key, String value) {
            if (map.size() > MAX_FROM) throw new IndexOutOfBoundsException(" 请求参数过多");
            Util.checkMap(key, value);
            map.put(key, value);
            return this;
        }

        public Builder map(Map<String, String> map) {
            if (map.size() > MAX_FROM) throw new IndexOutOfBoundsException(" 请求参数过多");
            this.map = map;
            return this;
        }

        public FormBody build() {
            return new FormBody(this);
        }

    }
}

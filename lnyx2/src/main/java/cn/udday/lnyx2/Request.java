package cn.udday.lnyx2;

import android.text.TextUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class Request {
    final Method method;
    final String url;
    final Map<String, String> headers;
    final Map<String, String> parameterHandlers;
    final boolean hasBody;
    public Request(Builder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.headers = builder.headers;
        this.parameterHandlers = builder.parameterHandlers;
        this.hasBody = builder.hasBody;
    }
    public static final class Builder {

        private Method method;
        private String url;
        private String baseUrl;
        private String nextUrl;
        private Map<String, String> headers;
        private Map<String, String> parameterHandlers;
        boolean hasBody;
        public Builder(Method method, String baseUrl, String nextUrl, Map<String, String> headers, boolean hasBody, Map<String, String> parameterHandlers) {
            this.method = method;
            this.baseUrl = baseUrl;
            this.nextUrl = nextUrl;
            this.headers = headers;
            this.parameterHandlers = parameterHandlers;
            this.hasBody = hasBody;
        }

        public Request build() {
                this.url = baseUrl+nextUrl;
            if (url == null) {
                throw new IllegalStateException("访问url不能为空");
            }

            headers.put("Connection", "Keep-Alive");
            headers.put("Charset", "UTF-8");
            return new Request(this);
        }

    }
}
